package com.xb.busmore.moudle.card.single;

import com.xb.busmore.util.Utils;

/**
 * Created by Administrator on 2017/9/23.
 */

public class MifareGetCard {
    public MifareGetCard(byte[] data) {
        byte[] status = new byte[1];
        byte[] driverC = new byte[4];
        byte[] driverB = new byte[4];
        byte[] cardType = new byte[1];
        byte[] mtype = new byte[1];
        byte[] stationDiraction = new byte[1];
        byte[] station = new byte[1];
        byte[] useState = new byte[1];
        byte[] unipaylenth = new byte[1];
        byte[] unipayCard = new byte[16];
        System.arraycopy(data, 0, status, 0, 1);
        System.arraycopy(data, 1, driverC, 0, 4);
        System.arraycopy(data, 5, driverB, 0, 4);
        System.arraycopy(data, 9, cardType, 0, 1);
        System.arraycopy(data, 10, mtype, 0, 1);
        System.arraycopy(data, 11, stationDiraction, 0, 1);
        System.arraycopy(data, 12, station, 0, 1);
        System.arraycopy(data, 13, useState, 0, 1);
        System.arraycopy(data, 14, unipaylenth, 0, 1);
        Status = Utils.printHexBinary(status);
        Cardhy = Utils.printHexBinary(driverC);
        CardNumber = Utils.printHexBinary(driverB);
        CardType = Utils.printHexBinary(cardType);
        MType = Utils.printHexBinary(mtype);
        StationDiraction = Utils.printHexBinary(stationDiraction);
        Station = Utils.printHexBinary(station);
        UseState = Utils.printHexBinary(useState);
        Unipaylenth = Utils.printHexBinary(unipaylenth);

        unipayCard = new byte[Integer.parseInt(Unipaylenth)];
        System.arraycopy(data, 15, unipayCard, 0, Integer.parseInt(Unipaylenth));
        UnipayCard = Utils.printHexBinary(unipayCard);
    }

    private String Status;//状态位 00成功
    private String Cardhy;//行业流水
    private String CardNumber;//卡号
    private String CardType;//卡类型
    private String MType;//M1 CPU卡 08M1卡 其余都是CPU卡
    private String StationDiraction;//上车方向，1字节HEX，只有下车机用，0x00表示上行，0x01表示下行
    private String Station;//上车站点，1字节HEX，只有下车机用,从0x00开始，0x00表示第一站
    private String UseState;//此卡本次要做上车，还是下车。0x00表示上车，0x01表示下车
    private String Unipaylenth;//银联卡AID长度，1字节HEX，范围8-16

    public String getStationDiraction() {
        return StationDiraction;
    }

    public String getStation() {
        return Station;
    }

    public String getUseState() {
        return UseState;
    }

    public String getUnipaylenth() {
        return Unipaylenth;
    }

    public String getUnipayCard() {
        return UnipayCard;
    }

    private String UnipayCard;//银联卡AID，靠前开始放，例如是8字节的AID，则在15-22字节，后面补0

    public String getCardhy() {
        return Cardhy;
    }

    public String getStatus() {
        return Status;
    }

    public String getCardNumber() {
        return CardNumber;
    }

    public String getCardType() {
        return CardType;
    }

    public String getMType() {
        return MType;
    }

    @Override
    public String toString() {
        return "MifareGetCard{" +
                "Status='" + Status + '\'' +
                ", Cardhy='" + Cardhy + '\'' +
                ", CardNumber='" + CardNumber + '\'' +
                ", CardType='" + CardType + '\'' +
                ", MType='" + MType + '\'' +
                ", StationDiraction='" + StationDiraction + '\'' +
                ", Station='" + Station + '\'' +
                ", UseState='" + UseState + '\'' +
                ", Unipaylenth='" + Unipaylenth + '\'' +
                ", UnipayCard='" + UnipayCard + '\'' +
                '}';
    }
}
