package com.xb.busmore.moudle.card;

import android.os.SystemClock;
import android.util.Log;

import com.xb.busmore.util.HexUtil;
import com.xb.busmore.util.Util;
import com.xb.busmore.util.Utils;

import com.szxb.jni.libszxb;


/**
 * 作者：Tangren on 2018-07-19
 * 包名：com.szxb.buspay.task.card
 * 邮箱：996489865@qq.com
 */

public class LoopCardThread extends Thread {

    private boolean isBlack = false;
    private boolean isWhite = false;

    private String cardNoTemp = "0";
    private long lastTime = 0;
    private SearchCard searchCard;

    @Override
    public void run() {
        super.run();
        try {
            byte[] searchBytes = new byte[64];
            byte[] type = HexUtil.int2Bytes(5, 1);
            byte[] SearchR = Utils.byteMerger(type, searchBytes);
            int status = libszxb.MifareGetSNR(SearchR);
            if (status < 0) {
                if (status == -2) {
                    //重启K21
                    libszxb.deviceReset();
                }
                searchCard = null;
                return;
            }

            if (SearchR[0] != (byte) 0x00) {
                //如果寻卡状态不等于00..无法处理此卡
                searchCard = null;
                Log.i("获取到卡状态ZY", "   " + searchBytes[0]);
                return;
            }

            searchCard = new SearchCard(searchBytes);


            //1S防抖动
            if (!filter(SystemClock.elapsedRealtime(), lastTime)) {
                return;
            }


            //防止重复刷卡
            //去重刷,同一个卡号1.5S
            if (!Util.check(cardNoTemp, searchCard.cardNo, lastTime)) {
//                BusToast.showToast(App.getInstance(), "您已刷过[" + searchCard.cardType + "]", false);
                return;
            }
            elseCardControl(searchCard);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean filter(long l, long lastTime) {
        if (Math.abs(l - lastTime) > 1000)
            return true;
        return false;
    }


    /**
     * @param searchCard 其他卡操作
     */
    private void elseCardControl(SearchCard searchCard) {

        int pay_fee = 0;
        int normal_pay = 0;
        ConsumeCard response = response(pay_fee, normal_pay, isBlack, isWhite, true, false, searchCard.cardModuleType);

    }


    /**
     * 消费报文
     *
     * @param pay_fee    实际扣款
     * @param normal_pay 普通卡金额
     * @param isBlack    是否是黑名单：0x01黑名单锁卡
     * @param isWhite    是否是白名单,预留
     * @return 消费响应
     */
    public static ConsumeCard response(int pay_fee, int normal_pay, boolean isBlack, boolean isWhite,
                                       boolean workStatus, boolean isSign, String cardModuleType) {
        int total_fee = 0;
        byte[] data = new byte[128];
        byte[] amount = HexUtil.int2Bytes(pay_fee, 3);
        byte[] baseAmount = HexUtil.int2Bytes(total_fee, 3);
        byte[] black = new byte[]{(byte) (isBlack ? 0x01 : 0x00)};
        byte[] white = new byte[]{0x01};
        byte[] busNo = HexUtil.str2Bcd("000000");
        byte[] lineNo = HexUtil.hex2byte("0000");
        byte[] workStatus_ = new byte[]{(byte) (workStatus ? 0x01 : 0x00)};
        byte[] driverNo = HexUtil.str2Bcd("00000000");
        byte[] direction = new byte[]{0x00};
        byte[] stationId = new byte[]{0x01};
        byte[] normalAmount = HexUtil.int2Bytes(normal_pay, 3);
        byte[] sendData = HexUtil.mergeByte(amount, baseAmount, black, white, busNo, lineNo,
                workStatus_, driverNo, direction, stationId, normalAmount, data);

        Log.i("消费", HexUtil.bcd2Str(sendData));
        int ret = libszxb.qxcardprocess(sendData);
        return new ConsumeCard(sendData);
    }

}
