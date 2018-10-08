package com.xb.busmore.moudle.card;


import com.xb.busmore.dao.manage.DBManager;
import com.xb.busmore.dao.manage.PosManager;

/**
 * Created by Administrator on 2017/9/23.
 */

//        0-2	消费金额，3字节HEX，例如1元为0x00 0x00 0x64(前后门多票制上车机的时候，为当前站到终点票价；下车机的时候，为上车站到当前站的票价)
//        3-5	普通卡金额，3字节HEX(前后门多票制上车机的时候，为当前站到终点的普通卡票价; 下车机的时候，为上车站到当前站的普通卡票价)
//        6     是否黑名单
//        7 	是否是白名单卡1字节HEX，0x01表示是白名单卡，其他表示不是。（预留）
//        8-10	车号3字节BCD，例如123456车号位0x12 0x34 0x56
//        11-12	线路号2字节HEX
//        13	司机上下班状态1字节hex，1表示有司机上班，0表示没有司机签到。
//        14-17	当班司机号4字节BCD
//        18	行驶方向1字节HEX，0x00表示上行，0x01表示下行
//        19	当前站点号1字节HEX,从0x00开始，0x00表示第一站
//        20-22	单票制普通卡金额

public class SendPayData {

    private String MoneyHex;    //        0-2	消费金额
    private String RecodeMoenyHex;//        3-5 基础金额
    private String IsBlack;//        6     是否黑名单
    private String IsWrite;//        7 	是否是白名单卡
    private String BusNo;//        8-10	车号3字节BCD
    private String LineNo;//        11-12	线路号2字节HEX
    private String DriverStatus;//        13	司机上下班状态1字节hex
    private String DriverNo;//        14-17	当班司机号4字节BCD
    private String exposure;//        18	行驶方向1字节HEX
    private String station;//        19	当前站点号1字节HEX
    private String Normal_money;//        20-22	单票制普通卡金额


    public static SendPayData GetData(String moneyhex,String recodehex,String normal_money,String isblack){
       String code="01";
        if (PosManager.getInstance().getSign()){
            code="00";
        }
        SendPayData sendPayData=new SendPayData();
        sendPayData.setMoneyHex(moneyhex).setRecodeMoenyHex(recodehex).
                setIsBlack(isblack).setIsWrite().setBusNo().setLineNo().
                setDriverStatus(code).setDriverNo().setExposure().setStation().
                setNormal(normal_money);
        return sendPayData;
    }

    @Override
    public String toString() {
        return MoneyHex+RecodeMoenyHex+IsBlack+IsWrite+BusNo+LineNo+DriverStatus+DriverNo+exposure+station+ Normal_money;
    }

    public SendPayData setMoneyHex(String moneyHex) {
        MoneyHex = moneyHex;
        return this;
    }

    public SendPayData setRecodeMoenyHex(String recodeMoenyHex) {
        RecodeMoenyHex = recodeMoenyHex;
        return this;
    }

    public SendPayData setIsBlack(String isBlack) {
        IsBlack = isBlack;
        return this;
    }

    public SendPayData setIsWrite() {
        IsWrite = "01";
        return this;
    }

    public SendPayData setBusNo() {
        BusNo = PosManager.getInstance().getCarConfig().getBusNo();
        return this;
    }

    public SendPayData setLineNo() {
        LineNo =  PosManager.getInstance().getUseConfig().getLine();
        return this;
    }

    public SendPayData setDriverStatus(String code) {
        DriverStatus = code;
        return this;
    }

    public SendPayData setDriverNo() {
        DriverNo = PosManager.getInstance().getUseConfig().getDriverNo();
        return this;
    }

    public SendPayData setExposure() {
        this.exposure = "00";
        return this;
    }

    public SendPayData setStation() {
        this.station = "00";
        return this;
    }

    public SendPayData setNormal(String Normal_money) {
        this.Normal_money = Normal_money;
        return this;
    }
}
