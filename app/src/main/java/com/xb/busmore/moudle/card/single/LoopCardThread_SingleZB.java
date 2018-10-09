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

import java.text.DecimalFormat;

import static java.lang.Integer.parseInt;


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
                cardNoTemp = "00000000";
                return;
            }
            //卡数据解析
            searchCard = new MifareGetCard(searchBytes);

            Log.i("检测到卡片", "进入消费流程" + Utils.printHexBinary(searchBytes));
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

            if (searchCard.getCardType().equals("06") || searchCard.getCardType().equals("46")) {
                driverSignAction(searchCard, searchBytes);
            } else {
                if (PosManager.getInstance().getCarRunInfo().getSign()) {
                    //去重刷,同一个卡号1.5S
                    if (!Util.check(cardNoTemp, searchCard.getCardNumber(), lastTime)) {
//                        BusToast.showToast(App.getInstance(), "您已刷过[" + searchCard.getCardType() + "]", false);
                        return;
                    } else {
                        if (searchCard.getMType().equals("F0")) {
                            unionPayCard(searchCard);
                        } else {
                            passengerAction(searchCard, searchBytes);
                        }
                    }
                } else {
                    BusToast.showToast(App.getInstance(), "司机还未上班", false);
                }
            }
            //保存刷卡记录  用于处理频繁刷卡
            cardNoTemp = searchCard.getCardNumber();
            lastTime = SystemClock.elapsedRealtime();
            Log.i("检测到卡片", "消费完成");
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
                    isNull ? 0 : bankICResponse.getLastTime(), PosManager.getInstance().getUnionPayPrice(), searchCard.getUnipayCard());
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
        //解析扣费命令返回数据
        String recordHEX = Utils.printHexBinary(record);
        CardRecord cardRecord = new CardRecord();
        cardRecord = cardRecord.bulider(recordHEX);
        if (!searchCard.getStatus().equals("FF")) {
            //记录上传
            PosManager.getInstance().insertRecord(cardRecord);
        }
        if (cardRecord.getPayType().equals("13")) {
            SoundPoolUtil.play(25);//下班
            PosManager.getInstance().setSign(false);
        } else if (cardRecord.getPayType().equals("12")) {
            SoundPoolUtil.play(26);
            PosManager.getInstance().setSign(true);
        }
    }

    //乘客刷卡
    public void passengerAction(MifareGetCard searchCard, byte[] records) {
        SendPayData sendPayData = SendPayData.GetData(getPayPrice(searchCard.getCardType()), getPayPrice("199"), getPayPrice(CardType.CARDT_NORMAL), "00");
        //消费命令发送
        byte[] record = Utils.byteMerger(Utils.hexStringToByte(sendPayData.toString()), records);
        int ret = libszxb.qxcardprocess(record);
        //解析扣费命令返回数据
        String recordHEX = Utils.printHexBinary(record);
        CardRecord cardRecord = new CardRecord();
        cardRecord = cardRecord.bulider(recordHEX);
        if (!cardRecord.getStatus().equals("FF")) {
            //记录上传
            PosManager.getInstance().insertRecord(cardRecord);
        }

        if (recordHEX != null && ret == 0 && !cardRecord.getDateTime().equals("00000000000000")) {//去掉无用的记录
            switch (cardRecord.getPayType()) {
                case "12":
                    SoundPoolUtil.play(25);//上班
                    PosManager.getInstance().setSign(true);
                    break;
                case "13":
                    SoundPoolUtil.play(26);//下班
                    PosManager.getInstance().setSign(false);
                    break;
                case "16":
//                    byte[] bt = new byte[2];
////                    System.arraycopy(record, 4, bt, 0, 2);
////                    OnLineInfo onl;
////                    onl = DBManager.getLineFromMessage(Utils.printHexBinary(bt));
////                    if (onl.getLine() == null) {
////                        soundPool.play(erro, 1, 1, 0, 0, 1);
////                        BusToast.showToast(BusApp.getInstance(), "无此线路号" + Utils.printHexBinary(bt), false);
////                    } else {
////                        RxBus.getInstance().post(new RxMessage(RxMessage.FTPUI, 1));
////                        BusToast.showToast(BusApp.getInstance(), "线路：" + onl.getLine() + "设置成功", true);
////                        soundPool.play(34);
////                    }
                    break;
                default:
                    if (!searchCard.getStatus().equals("FF")) {
                        if (searchCard.getMType().equals("08")) {//M1卡状态   M1卡消费 语音提示
                            //需要扣费且 余额不足的卡
                            M1Music(cardRecord);
                            M1Toast(cardRecord);
                        } else {//CPU卡
                            //CPU卡消费 语音提示
                            CPUMusic(cardRecord);
                            //CPU卡消费 界面提示
                            CPUToast(cardRecord);
                        }
                    }
                    break;
            }
        }
    }

    //M1界面提示
    public void M1Toast(CardRecord cardRecord) {
        switch (cardRecord.getCardType()) {
            case "00":
                break;
            case "12":
                BusToast.showToast(App.getInstance(), "签点卡", true);
                break;
            case "13":
                BusToast.showToast(App.getInstance(), "检测卡", true);
                break;
            case "18":
                BusToast.showToast(App.getInstance(), "稽查卡", true);
                break;
            default:
                Log.d("datarecord", cardRecord.toString());
                double mon = Integer.valueOf(cardRecord.getCardMoney(), 16) / 100.00;
                double paym = parseInt(getPayPrice(cardRecord.getCardType()), 16) / 100.00;
                DecimalFormat df = new DecimalFormat("######0.00");
                BusToast.showToast(App.getInstance(), "本次扣款" + df.format(paym) + "元" + "\n余额" + df.format(mon) + "元", true);
                break;

        }
    }

    //M1卡提示音
    public void M1Music(CardRecord cardRecord) {
        switch (cardRecord.getCardType()) {
            case "00":
                break;
            //普通卡
            case CardType.CARDT_NORMAL:
                if (parseInt(cardRecord.getCardMoney(), 16) < 500) {
                    SoundPoolUtil.play(22);
                } else {
                    SoundPoolUtil.play(34);
                }
                break;
            //学生卡
            case CardType.CARDT_STUDENT_A:
                SoundPoolUtil.play(15);
                break;
            //老年卡
            case CardType.CARDT_OLDMAN:
                SoundPoolUtil.play(16);
                break;

            //免费卡--语音提示修改为荣军卡
            case CardType.CARDT_FREE:
                SoundPoolUtil.play(37);
                break;

            //纪念卡
            case CardType.CARDT_MEMORY:
                SoundPoolUtil.play(28);
                break;


            //员工卡
            case CardType.CARDT_DRIVER:
                SoundPoolUtil.play(42);
                break;

            //优惠卡
            case CardType.CARDT_FAVOR:
                SoundPoolUtil.play(33);
                break;

            //学生卡
            case CardType.CARDT_STUDENT_B:
                SoundPoolUtil.play(15);
                break;

            //下班语音
            case CardType.CARDT_SET:
                SoundPoolUtil.play(26);
                break;
            case CardType.CARDT_GATHER://
                SoundPoolUtil.play(34);
                break;
            case CardType.CARDT_SIGNED:
                SoundPoolUtil.play(34);
                break;
            case CardType.CARDT_CHECKED:
                SoundPoolUtil.play(34);
                break;
            case CardType.CARDT_CHECK:
                SoundPoolUtil.play(34);
                break;
            case "46":
                SoundPoolUtil.play(42);
                break;
            default:
                SoundPoolUtil.play(28);
                break;
        }
    }

    //CPU卡提示音
    public void CPUMusic(CardRecord cardRecord) {
        switch (cardRecord.getCardType()) {
            //普通卡
            case "00":
                if (parseInt(cardRecord.getCardMoney(), 16) < 500) {
                    SoundPoolUtil.play(22);
                } else {
                    SoundPoolUtil.play(34);
                }
                break;
            case "43":
                SoundPoolUtil.play(16);
                break;
            case "41":
                if (parseInt(cardRecord.getCardMoney(), 16) < 500) {
                    SoundPoolUtil.play(22);
                } else {
                    SoundPoolUtil.play(34);
                }
                break;
            case "42":
                SoundPoolUtil.play(15);
                break;
            case "44":
                if (cardRecord.getPayType().equals("07")) {
                    SoundPoolUtil.play(17);
                }
                if (cardRecord.getPayType().equals("06")) {
                    SoundPoolUtil.play(34);
                }
                break;
            case "46":
                SoundPoolUtil.play(42);
                break;
            case "45"://优惠卡
                SoundPoolUtil.play(33);
                break;
            case "88":
                SoundPoolUtil.play(34);
                break;
            case "04":
                if (parseInt(cardRecord.getCardMoney(), 16) < 500) {
                    SoundPoolUtil.play(22);
                } else {
                    SoundPoolUtil.play(34);
                }
                break;
            case "08":
                SoundPoolUtil.play(34);
                break;
            default:
                SoundPoolUtil.play(34);
                break;
        }


    }

    //CPU卡界面提示
    public void CPUToast(CardRecord cardRecord) {
        double mon = Integer.valueOf(cardRecord.getCardMoney(), 16) / 100.00;
        double payMon = Integer.valueOf(cardRecord.getPayMoney(), 16) / 100.00;
        DecimalFormat df = new DecimalFormat("######0.00");
        BusToast.showToast(App.getInstance(), "本次扣款" + df.format(payMon) + "元" + "\n余额" + df.format(mon) + "元", true);
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
