package com.xb.busmore.moudle.card.single;

import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;

import com.xb.busmore.base.App;
import com.xb.busmore.dao.manage.PosManager;
import com.xb.busmore.moudle.card.CardRecord;
import com.xb.busmore.moudle.card.SendPayData;
import com.xb.busmore.moudle.unionpay.dispose.BankCardParse;
import com.xb.busmore.moudle.unionpay.dispose.BankResponse;
import com.xb.busmore.util.AppUtil;
import com.xb.busmore.util.BusToast;
import com.xb.busmore.util.HexUtil;
import com.xb.busmore.util.Util;
import com.xb.busmore.util.Utils;

import com.szxb.jni.libszxb;
import com.xb.busmore.util.logcat.LogcatEvent;
import com.xb.busmore.util.sound.SoundPoolUtil;


/**
 * 作者：Tangren on 2018-07-19
 * 包名：com.szxb.buspay.task.card
 * 邮箱：996489865@qq.com
 */

public class LoopCardThread_SingleZB extends Thread {

    private boolean isBlack = false;
    private boolean isWhite = false;

    private String cardNoTemp = "0";
    private long lastTime = 0;
    private MifareGetCard searchCard;

    @Override
    public void run() {
        super.run();
        try {
            //巡卡数据
            byte[] searchBytes = new byte[64];
            int status = libszxb.MifareGetSNR(searchBytes);
            if (status < 0) {
                if (status == -2) {
                    //重启K21
                    libszxb.deviceReset();
                }
                searchCard = null;
                return;
            }

            if (searchBytes[0] != (byte) 0x00) {
                //如果寻卡状态不等于00..无法处理此卡
                searchCard = null;
                Log.i("获取到卡状态ZY", "   " + searchBytes[0]);
                cardNoTemp = searchCard.getCardNumber();
                return;
            }
            //卡数据解析
            searchCard = new MifareGetCard(searchBytes);

            Log.i("检测到卡片", Utils.printHexBinary(searchBytes));
            if (PosManager.getInstance().getCarConfig().getBusNo().equals("未设置")) {
                BusToast.showToast(App.getInstance(), "还未设置车辆号", false);
                return;
            } else if (PosManager.getInstance().getUseConfig().getLine().equals("000000")) {
                BusToast.showToast(App.getInstance(), "还未设置线路", false);
                return;
            }
            //TODO GPS识别
//            else if (PosManager.getInstance().getCarConfig().getGPS() == 0 && !searchCard.getCardType().equals("06")) {
//                BusToast.showToast(App.getInstance(), "GPS未启用", false);
//                return;
//            }


            //去重刷,同一个卡号1.5S
            if (!Util.check(cardNoTemp, searchCard.getCardNumber(), lastTime)) {
                BusToast.showToast(App.getInstance(), "您已刷过[" + searchCard.getCardType() + "]", false);
                return;
            } else {
                if (searchCard.getCardType().equals("06")) {
                    driverSignAction(searchCard, searchBytes);
                } else {
                    if (PosManager.getInstance().getCarRunInfo().getSign()) {
                        if (searchCard.getMType().equals("F0")) {
                            unionPayCard(searchCard);
                        } else {

                        }
                    } else {
                        BusToast.showToast(App.getInstance(), "司机还未上班", false);
                    }
                }
            }
            //保存刷卡记录  用于处理频繁刷卡
            cardNoTemp = searchCard.getCardNumber();
            lastTime = SystemClock.elapsedRealtime();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //银联支付
    public void unionPayCard(MifareGetCard searchCard) {
        BankResponse bankICResponse = new BankResponse();
        try {
            if (!AppUtil.checkNetStatus()) {
                BusToast.showToast(App.getInstance(), "网络异常\n请选择其他方式乘车", false);
                return;
            }
            BusToast.showToast(App.getInstance(), "银联支付中", true);
            boolean isNull = bankICResponse.getResCode() == -999;
            BankCardParse cardParse = new BankCardParse();
            bankICResponse = cardParse.parseResponse(bankICResponse,
                    isNull ? "0" : bankICResponse.getMainCardNo(),
                    isNull ? 0 : bankICResponse.getLastTime(), PosManager.getInstance().getUnionPayPrice(), searchCard.getUnipayCard())
            ;
            if (bankICResponse.getResCode() > 0) {
                BusToast.showToast(App.getInstance(), bankICResponse.getMsg(), true);
            } else {
                BusToast.showToast(App.getInstance(), bankICResponse.getMsg() + "[" + bankICResponse.getResCode() + "]", false);
            }

        } catch (Exception e) {
            e.printStackTrace();
            BusToast.showToast(App.getInstance(), "", false);
        }
    }

    //司机卡刷卡
    public void driverSignAction(MifareGetCard searchCard, byte[] records) {
        //消费命令组建,1分钟连刷
        SendPayData sendPayData = SendPayData.GetData(getPayPrice(searchCard.getCardType()), PosManager.getInstance().getCarRunInfo().getPrice() + "", getPayPrice(CardType.CARDT_NORMAL), "00");
        byte[] record = Utils.byteMerger(Utils.hexStringToByte(sendPayData.toString()), records);
        int ret = libszxb.qxcardprocess(record);
        if (ret < 0) {
            LogcatEvent.getInstance().WritingCom();
        }
        //解析扣费命令返回数据
        String recordHEX = Utils.printHexBinary(record);
        CardRecord cardRecord = new CardRecord();
        cardRecord = cardRecord.bulider(recordHEX);
        if (cardRecord.getPayType().equals("13")) {
            SoundPoolUtil.play(21);//下班
            PosManager.getInstance().setSign(false);
        } else if (cardRecord.getPayType().equals("12")) {
            SoundPoolUtil.play(20);
            PosManager.getInstance().setSign(true);
        }
    }


    //实际票价计算 普通卡、学生卡、老年卡、免费卡、残疾人卡、员工卡、优惠卡2、优惠卡3、微信、银联、济南卡
    String getPayPrice(String type) {
        String coeff = PosManager.getInstance().getCarRunInfo().getCoefficient();
        int price = PosManager.getInstance().getCarRunInfo().getPrice();
        int money_int;

        if (coeff.length() < 18) {
            return "00";
        }

        switch (type) {
            case "199":
                money_int = price;
                break;

            case CardType.CARDT_NORMAL:
            case CardType.CARDT_MEMORY://纪念卡
            case "41":
                money_int = Integer.parseInt(coeff.substring(0, 3)) * price / 100;
                Log.d("CardPay Money:", money_int + "");
                break;

            case CardType.CARDT_STUDENT_A:
            case "42":
                money_int = Integer.parseInt(coeff.substring(3, 6)) * price / 100;
                Log.d("CardPay Money:", money_int + "");
                break;

            case CardType.CARDT_OLDMAN:
                money_int = Integer.parseInt(coeff.substring(6, 9)) * price / 100;
                Log.d("CardPay Money:", money_int + "");
                break;

            case CardType.CARDT_FREE:
                money_int = Integer.parseInt(coeff.substring(9, 12)) * price / 100;
                Log.d("CardPay Money:", money_int + "");
                break;

            case "45": //残疾人卡
//            case CardType.CARDT_MEMORY:
                money_int = Integer.parseInt(coeff.substring(12, 15)) * price / 100;
                Log.d("CardPay Money:", money_int + "");
                break;

            case CardType.CARDT_DRIVER:
                Log.d("CARDT_DRIVER", coeff.substring(15, 18));
                money_int = Integer.parseInt(coeff.substring(12, 15)) * price / 100;
                Log.d("CardPay Money:", money_int + "");
                break;

            case "43":
                money_int = Integer.parseInt(coeff.substring(6, 9)) * price / 100;
                Log.d("CardPay Money:", money_int + "");
                break;

//            case CardType.CARDT_FREE:
//                if (!mifareGetCard.getCardhy().equals("08")){
//                    money_int = Integer.parseInt(coeff.substring(30, 33)) * price / 100 ;
//                    break;
//                }
            case "44":
                money_int = Integer.parseInt(coeff.substring(9, 12)) * price / 100;
                Log.d("CardPay Money:", money_int + "");
                break;

            default:
                money_int = Integer.parseInt(coeff.substring(0, 3)) * price / 100;
                Log.d("CardPay Money:", money_int + "");
                break;
        }
        byte[] money_b = Utils.int2Bytes(money_int, 3);
        return Utils.printHexBinary(money_b);

    }


}
