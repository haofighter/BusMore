package com.xb.busmore.moudle.card;

import android.util.Log;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by Evergarden on 2017/8/17.
 */

//        0	1	HEX	卡类型
//        1	1	HEX	交易类型
//        2	3	HEX	设备交易序号
//        5	8	BCD	卡号
//        13	3	HEX	卡余额
//        16	3	HEX	扣款金额
//        19	7	BCD	交易时间
//        26	2	HEX	用户卡脱机交易序号
//        28	4	HEX	TAC码
//        32	2	HEX	线路号
//        34	3	BCD	车号
//        37	4	BCD	当班司机号
//        41	6	BCD	PSAM卡号
//        47	1	HEX	行驶方向
//        48	1	HEX	站点号
//        49	1	HEX	补票标志
@Entity
public class CardRecord {


    @Id(autoincrement = true)
    private Long id;//id
    private String Status;//状态码

    private String CardType;//卡类型
    private String PayType;//交易类型
    private String DeviceNo;//设备交易序列号
    private String CardNumber;//卡号
    private String CardMoney;//卡内余额（扣款后）
    private String PayMoney;//消费金额
    private String DateTime;//交易日期和时间
    private String TrantScationNo;//用户卡脱机交易序号
    private String TACNo;//tac码
    private String LineNo;//线路号
    private String BusNo;//车号
    private String DriverNo;//驾驶员号
    private String PSAMNo;//PSAM卡号
    private String exposure;//方向
    private String up_station;//出站点
    private String ticket;//补票
    private String UpLoad;//是否上传 0未 1已

    public CardRecord bulider(String code) {
        CardRecord cardRecord = new CardRecord();
        cardRecord.setStatus(code.substring(0, 2));
        cardRecord.setCardType(code.substring(2, 4));
        cardRecord.setPayType(code.substring(4, 6));
        cardRecord.setDeviceNo(code.substring(6, 12));
        cardRecord.setCardNumber(code.substring(12, 28));
        cardRecord.setCardMoney(code.substring(28, 34));
        cardRecord.setPayMoney(code.substring(34, 40));
        cardRecord.setDateTime(code.substring(40, 54));
        cardRecord.setTrantScationNo(code.substring(54, 58));
        cardRecord.setTACNo(code.substring(58, 66));
        cardRecord.setLineNo(code.substring(66, 70));
        cardRecord.setBusNo(code.substring(70, 76));
        cardRecord.setDriverNo(code.substring(76, 84));
        cardRecord.setPSAMNo(code.substring(84, 96));
        cardRecord.setExposure(code.substring(96, 98));
        cardRecord.setUp_station(code.substring(98, 100));
        cardRecord.setTicket("00");
        cardRecord.setUpLoad("0");
        Log.d("CardRecord",
                "bulider(CardRecord.java:75)" + toString());
        return cardRecord;
    }

    @Generated(hash = 1400023658)
    public CardRecord(Long id, String Status, String CardType, String PayType,
                      String DeviceNo, String CardNumber, String CardMoney, String PayMoney,
                      String DateTime, String TrantScationNo, String TACNo, String LineNo,
                      String BusNo, String DriverNo, String PSAMNo, String exposure,
                      String up_station, String ticket, String UpLoad) {
        this.id = id;
        this.Status = Status;
        this.CardType = CardType;
        this.PayType = PayType;
        this.DeviceNo = DeviceNo;
        this.CardNumber = CardNumber;
        this.CardMoney = CardMoney;
        this.PayMoney = PayMoney;
        this.DateTime = DateTime;
        this.TrantScationNo = TrantScationNo;
        this.TACNo = TACNo;
        this.LineNo = LineNo;
        this.BusNo = BusNo;
        this.DriverNo = DriverNo;
        this.PSAMNo = PSAMNo;
        this.exposure = exposure;
        this.up_station = up_station;
        this.ticket = ticket;
        this.UpLoad = UpLoad;
    }

    @Generated(hash = 19461391)
    public CardRecord() {
    }

    public Long getId() {
        return this.id;
    }

    public CardRecord setId(Long id) {
        this.id = id;
        return this;
    }

    public String getStatus() {
        return this.Status;
    }

    public CardRecord setStatus(String Status) {
        this.Status = Status;
        return this;
    }

    public String getCardType() {
        return this.CardType;
    }

    public CardRecord setCardType(String CardType) {
        this.CardType = CardType;
        return this;
    }

    public String getPayType() {
        return this.PayType;
    }

    public CardRecord setPayType(String PayType) {
        this.PayType = PayType;
        return this;
    }

    public String getDeviceNo() {
        return this.DeviceNo;
    }

    public CardRecord setDeviceNo(String DeviceNo) {
        this.DeviceNo = DeviceNo;
        return this;
    }

    public String getCardNumber() {
        return this.CardNumber;
    }

    public CardRecord setCardNumber(String CardNumber) {
        this.CardNumber = CardNumber;
        return this;
    }

    public String getCardMoney() {
        return this.CardMoney;
    }

    public CardRecord setCardMoney(String CardMoney) {
        this.CardMoney = CardMoney;
        return this;
    }

    public String getPayMoney() {
        return this.PayMoney;
    }

    public CardRecord setPayMoney(String PayMoney) {
        this.PayMoney = PayMoney;
        return this;
    }

    public String getDateTime() {
        return this.DateTime;
    }

    public CardRecord setDateTime(String DateTime) {
        this.DateTime = DateTime;
        return this;
    }

    public String getTrantScationNo() {
        return this.TrantScationNo;
    }

    public CardRecord setTrantScationNo(String TrantScationNo) {
        this.TrantScationNo = TrantScationNo;
        return this;
    }

    public String getTACNo() {
        return this.TACNo;
    }

    public CardRecord setTACNo(String TACNo) {
        this.TACNo = TACNo;
        return this;
    }

    public String getLineNo() {
        return this.LineNo;
    }

    public CardRecord setLineNo(String LineNo) {
        this.LineNo = LineNo;
        return this;
    }

    public String getBusNo() {
        return this.BusNo;
    }

    public CardRecord setBusNo(String BusNo) {
        this.BusNo = BusNo;
        return this;
    }

    public String getDriverNo() {
        return this.DriverNo;
    }

    public CardRecord setDriverNo(String DriverNo) {
        this.DriverNo = DriverNo;
        return this;
    }

    public String getPSAMNo() {
        return this.PSAMNo;
    }

    public CardRecord setPSAMNo(String PSAMNo) {
        this.PSAMNo = PSAMNo;
        return this;
    }

    public String getExposure() {
        return this.exposure;
    }

    public CardRecord setExposure(String exposure) {
        this.exposure = exposure;
        return this;
    }

    public String getUp_station() {
        return this.up_station;
    }

    public CardRecord setUp_station(String up_station) {
        this.up_station = up_station;
        return this;
    }

    public String getTicket() {
        return this.ticket;
    }

    public CardRecord setTicket(String ticket) {
        this.ticket = ticket;
        return this;
    }

    public String getUpLoad() {
        return this.UpLoad;
    }

    public CardRecord setUpLoad(String UpLoad) {
        this.UpLoad = UpLoad;
        return this;
    }


    @Override
    public String toString() {
        return "CardRecord{" +
                "id=" + id +
                ", Status='" + Status + '\'' +
                ", CardType='" + CardType + '\'' +
                ", PayType='" + PayType + '\'' +
                ", DeviceNo='" + DeviceNo + '\'' +
                ", CardNumber='" + CardNumber + '\'' +
                ", CardMoney='" + CardMoney + '\'' +
                ", PayMoney='" + PayMoney + '\'' +
                ", DateTime='" + DateTime + '\'' +
                ", TrantScationNo='" + TrantScationNo + '\'' +
                ", TACNo='" + TACNo + '\'' +
                ", LineNo='" + LineNo + '\'' +
                ", BusNo='" + BusNo + '\'' +
                ", DriverNo='" + DriverNo + '\'' +
                ", PSAMNo='" + PSAMNo + '\'' +
                ", exposure='" + exposure + '\'' +
                ", up_station='" + up_station + '\'' +
                ", ticket='" + ticket + '\'' +
                ", UpLoad='" + UpLoad + '\'' +
                '}';
    }
}
