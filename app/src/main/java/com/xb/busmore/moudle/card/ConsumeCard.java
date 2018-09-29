package com.xb.busmore.moudle.card;


import com.xb.busmore.util.HexUtil;
import com.xb.busmore.util.Util;

import static java.lang.System.arraycopy;

/**
 * 作者：Tangren on 2018-07-19
 * 包名：com.szxb.buspay.db.entity.bean.card
 * 邮箱：996489865@qq.com
 * TODO:一句话描述
 */
public class ConsumeCard {
    String cardnum;//卡号
    String status;//标示
    String cardType;//卡大类
    String cardType2;//卡类型
    String useTag;//启用标志
    String useTime;//启用日期
    String canUseTime;//有效期截止
    String black;//黑名单
    int balance;//卡余额
    String employy;//员工卡标志
    String driverid;//司机id
    String oneYear;
    String oneBalance;
    String twoYear;
    String twoBalance;
    String threeYear;
    String threeBalance;
    String fourYear;
    String fourBalance;
    String fiveYear;
    String fiveBalance;
    String sixYear;
    String sixBalance;
    String sevenYear;
    String sevenBalance;
    String eightYear;
    String eightBalance;
    String nineYear;
    String nineBalance;
    String tenYear;
    String tenBalance;
    String eleveYear;
    String eleveBalance;
    String twelveYear;
    String twelveBalance;

    public String getCardnum() {
        return cardnum;
    }

    public void setCardnum(String cardnum) {
        this.cardnum = cardnum;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCardType2() {
        return cardType2;
    }

    public void setCardType2(String cardType2) {
        this.cardType2 = cardType2;
    }

    public String getUseTag() {
        return useTag;
    }

    public void setUseTag(String useTag) {
        this.useTag = useTag;
    }

    public String getUseTime() {
        return useTime;
    }

    public void setUseTime(String useTime) {
        this.useTime = useTime;
    }

    public String getCanUseTime() {
        return canUseTime;
    }

    public void setCanUseTime(String canUseTime) {
        this.canUseTime = canUseTime;
    }

    public String getBlack() {
        return black;
    }

    public void setBlack(String black) {
        this.black = black;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getEmployy() {
        return employy;
    }

    public void setEmployy(String employy) {
        this.employy = employy;
    }

    public String getDriverid() {
        return driverid;
    }

    public void setDriverid(String driverid) {
        this.driverid = driverid;
    }

    public String getOneYear() {
        return oneYear;
    }

    public void setOneYear(String oneYear) {
        this.oneYear = oneYear;
    }

    public String getOneBalance() {
        return oneBalance;
    }

    public void setOneBalance(String oneBalance) {
        this.oneBalance = oneBalance;
    }

    public String getTwoYear() {
        return twoYear;
    }

    public void setTwoYear(String twoYear) {
        this.twoYear = twoYear;
    }

    public String getTwoBalance() {
        return twoBalance;
    }

    public void setTwoBalance(String twoBalance) {
        this.twoBalance = twoBalance;
    }

    public String getThreeYear() {
        return threeYear;
    }

    public void setThreeYear(String threeYear) {
        this.threeYear = threeYear;
    }

    public String getThreeBalance() {
        return threeBalance;
    }

    public void setThreeBalance(String threeBalance) {
        this.threeBalance = threeBalance;
    }

    public String getFourYear() {
        return fourYear;
    }

    public void setFourYear(String fourYear) {
        this.fourYear = fourYear;
    }

    public String getFourBalance() {
        return fourBalance;
    }

    public void setFourBalance(String fourBalance) {
        this.fourBalance = fourBalance;
    }

    public String getFiveYear() {
        return fiveYear;
    }

    public void setFiveYear(String fiveYear) {
        this.fiveYear = fiveYear;
    }

    public String getFiveBalance() {
        return fiveBalance;
    }

    public void setFiveBalance(String fiveBalance) {
        this.fiveBalance = fiveBalance;
    }

    public String getSixYear() {
        return sixYear;
    }

    public void setSixYear(String sixYear) {
        this.sixYear = sixYear;
    }

    public String getSixBalance() {
        return sixBalance;
    }

    public void setSixBalance(String sixBalance) {
        this.sixBalance = sixBalance;
    }

    public String getSevenYear() {
        return sevenYear;
    }

    public void setSevenYear(String sevenYear) {
        this.sevenYear = sevenYear;
    }

    public String getSevenBalance() {
        return sevenBalance;
    }

    public void setSevenBalance(String sevenBalance) {
        this.sevenBalance = sevenBalance;
    }

    public String getEightYear() {
        return eightYear;
    }

    public void setEightYear(String eightYear) {
        this.eightYear = eightYear;
    }

    public String getEightBalance() {
        return eightBalance;
    }

    public void setEightBalance(String eightBalance) {
        this.eightBalance = eightBalance;
    }

    public String getNineYear() {
        return nineYear;
    }

    public void setNineYear(String nineYear) {
        this.nineYear = nineYear;
    }

    public String getNineBalance() {
        return nineBalance;
    }

    public void setNineBalance(String nineBalance) {
        this.nineBalance = nineBalance;
    }

    public String getTenYear() {
        return tenYear;
    }

    public void setTenYear(String tenYear) {
        this.tenYear = tenYear;
    }

    public String getTenBalance() {
        return tenBalance;
    }

    public void setTenBalance(String tenBalance) {
        this.tenBalance = tenBalance;
    }

    public String getEleveYear() {
        return eleveYear;
    }

    public void setEleveYear(String eleveYear) {
        this.eleveYear = eleveYear;
    }

    public String getEleveBalance() {
        return eleveBalance;
    }

    public void setEleveBalance(String eleveBalance) {
        this.eleveBalance = eleveBalance;
    }

    public String getTwelveYear() {
        return twelveYear;
    }

    public void setTwelveYear(String twelveYear) {
        this.twelveYear = twelveYear;
    }

    public String getTwelveBalance() {
        return twelveBalance;
    }

    public void setTwelveBalance(String twelveBalance) {
        this.twelveBalance = twelveBalance;
    }


    @Override
    public String toString() {
        return "ConsumeCard{" +
                "cardnum='" + cardnum + '\'' +
                ", cardType='" + cardType + '\'' +
                ", cardType2='" + cardType2 + '\'' +
                ", useTag='" + useTag + '\'' +
                ", useTime='" + useTime + '\'' +
                ", canUseTime='" + canUseTime + '\'' +
                ", black='" + black + '\'' +
                ", balance='" + balance + '\'' +
                ", employy='" + employy + '\'' +
                ", driverid='" + driverid + '\'' +
                ", oneYear='" + oneYear + '\'' +
                ", oneBalance='" + oneBalance + '\'' +
                ", twoYear='" + twoYear + '\'' +
                ", twoBalance='" + twoBalance + '\'' +
                ", threeYear='" + threeYear + '\'' +
                ", threeBalance='" + threeBalance + '\'' +
                ", fourYear='" + fourYear + '\'' +
                ", fourBalance='" + fourBalance + '\'' +
                ", fiveYear='" + fiveYear + '\'' +
                ", fiveBalance='" + fiveBalance + '\'' +
                ", sixYear='" + sixYear + '\'' +
                ", sixBalance='" + sixBalance + '\'' +
                ", sevenYear='" + sevenYear + '\'' +
                ", sevenBalance='" + sevenBalance + '\'' +
                ", eightYear='" + eightYear + '\'' +
                ", eightBalance='" + eightBalance + '\'' +
                ", nineYear='" + nineYear + '\'' +
                ", nineBalance='" + nineBalance + '\'' +
                ", tenYear='" + tenYear + '\'' +
                ", tenBalance='" + tenBalance + '\'' +
                ", eleveYear='" + eleveYear + '\'' +
                ", eleveBalance='" + eleveBalance + '\'' +
                ", twelveYear='" + twelveYear + '\'' +
                ", twelveBalance='" + twelveBalance + '\'' +
                '}';
    }

    public ConsumeCard(byte[] receDatas) {

        int index = 0;
        byte[] tag = new byte[1];
        arraycopy(receDatas, index, tag, 0, tag.length);
        status = HexUtil.printHexBinary(tag);

        byte[] status_byte = new byte[8];
        arraycopy(receDatas, index += tag.length, status_byte, 0, status_byte.length);
        cardnum = HexUtil.bcd2Str(status_byte);

        byte[] cardType_byte = new byte[1];
        arraycopy(receDatas, index += status_byte.length, cardType_byte, 0, cardType_byte.length);
        cardType = HexUtil.printHexBinary(cardType_byte);

        byte[] cardType_byte2 = new byte[1];
        arraycopy(receDatas, index += cardType_byte.length, cardType_byte2, 0, cardType_byte2.length);
        cardType2 = HexUtil.printHexBinary(cardType_byte2);

        byte[] transType_byte = new byte[1];
        arraycopy(receDatas, index += cardType_byte2.length, transType_byte, 0, transType_byte.length);
        useTag = HexUtil.printHexBinary(transType_byte);

        byte[] transNo_byte = new byte[4];
        arraycopy(receDatas, index += transType_byte.length, transNo_byte, 0, transNo_byte.length);
        useTime = HexUtil.printHexBinary(transNo_byte);

        byte[] cardNo_byte = new byte[4];
        arraycopy(receDatas, index += transNo_byte.length, cardNo_byte, 0, cardNo_byte.length);
        canUseTime = HexUtil.bcd2Str(cardNo_byte);

        byte[] cardBalance_byte = new byte[1];
        arraycopy(receDatas, index += cardNo_byte.length, cardBalance_byte, 0, cardBalance_byte.length);
        black = Util.hex2IntStr(HexUtil.printHexBinary(cardBalance_byte));

        byte[] payFee_byte = new byte[3];
        arraycopy(receDatas, index += cardBalance_byte.length, payFee_byte, 0, payFee_byte.length);
        balance = Integer.parseInt(Util.hex2IntStr(HexUtil.printHexBinary(payFee_byte)));

        byte[] transTime_byte = new byte[1];
        arraycopy(receDatas, index += payFee_byte.length, transTime_byte, 0, transTime_byte.length);
        employy = HexUtil.bcd2Str(transTime_byte);

        byte[] transNo2_byte = new byte[4];
        arraycopy(receDatas, index += transTime_byte.length, transNo2_byte, 0, transNo2_byte.length);
        driverid = HexUtil.bcd2Str(transNo2_byte);

        byte[] tac_byte = new byte[2];
        arraycopy(receDatas, index += transNo2_byte.length, tac_byte, 0, tac_byte.length);
        oneYear = HexUtil.bcd2Str(tac_byte);

        byte[] tac_byte2 = new byte[1];
        arraycopy(receDatas, index += tac_byte.length, tac_byte2, 0, tac_byte2.length);
        oneBalance = Util.hex2IntStr(HexUtil.printHexBinary(tac_byte2));

        byte[] lineNoe_byte = new byte[2];
        arraycopy(receDatas, index += tac_byte2.length, lineNoe_byte, 0, lineNoe_byte.length);
        twoYear = HexUtil.printHexBinary(lineNoe_byte);

        byte[] busNo_byte = new byte[1];
        arraycopy(receDatas, index += lineNoe_byte.length, busNo_byte, 0, busNo_byte.length);
        twoBalance = Util.hex2IntStr(HexUtil.printHexBinary(busNo_byte));

        byte[] driverNo_byte = new byte[2];
        arraycopy(receDatas, index += busNo_byte.length, driverNo_byte, 0, driverNo_byte.length);
        threeYear = HexUtil.printHexBinary(driverNo_byte);

        byte[] pasmNo_byte = new byte[1];
        arraycopy(receDatas, index += driverNo_byte.length, pasmNo_byte, 0, pasmNo_byte.length);
        threeBalance = Util.hex2IntStr(HexUtil.printHexBinary(pasmNo_byte));


        byte[] n1 = new byte[2];
        arraycopy(receDatas, index += pasmNo_byte.length, n1, 0, driverNo_byte.length);
        fourYear = HexUtil.printHexBinary(n1);

        byte[] n2 = new byte[1];
        arraycopy(receDatas, index += n1.length, n2, 0, pasmNo_byte.length);
        fourBalance = Util.hex2IntStr(HexUtil.printHexBinary(n2));


        byte[] n3 = new byte[2];
        arraycopy(receDatas, index += n2.length, n3, 0, driverNo_byte.length);
        fiveYear = HexUtil.printHexBinary(n3);

        byte[] n4 = new byte[1];
        arraycopy(receDatas, index += n3.length, n4, 0, pasmNo_byte.length);
        fiveBalance = Util.hex2IntStr(HexUtil.printHexBinary(n4));


        byte[] n5 = new byte[2];
        arraycopy(receDatas, index += n4.length, n5, 0, driverNo_byte.length);
        sixYear = HexUtil.printHexBinary(n5);

        byte[] n6 = new byte[1];
        arraycopy(receDatas, index += n5.length, n6, 0, pasmNo_byte.length);
        sixBalance = Util.hex2IntStr(HexUtil.printHexBinary(n6));


        byte[] n7 = new byte[2];
        arraycopy(receDatas, index += n6.length, n7, 0, driverNo_byte.length);
        sevenYear = HexUtil.printHexBinary(n7);

        byte[] n8 = new byte[1];
        arraycopy(receDatas, index += n7.length, n8, 0, pasmNo_byte.length);
        sevenBalance = Util.hex2IntStr(HexUtil.printHexBinary(n8));

        byte[] n9 = new byte[2];
        arraycopy(receDatas, index += n8.length, n9, 0, driverNo_byte.length);
        eightYear = HexUtil.printHexBinary(n9);

        byte[] n10 = new byte[1];
        arraycopy(receDatas, index += n9.length, n10, 0, pasmNo_byte.length);
        eightBalance = Util.hex2IntStr(HexUtil.printHexBinary(n10));


        byte[] n11 = new byte[2];
        arraycopy(receDatas, index += n10.length, n11, 0, driverNo_byte.length);
        nineYear = HexUtil.printHexBinary(n11);

        byte[] n12 = new byte[1];
        arraycopy(receDatas, index += n11.length, n12, 0, pasmNo_byte.length);
        nineBalance = Util.hex2IntStr(HexUtil.printHexBinary(n12));

        byte[] n13 = new byte[2];
        arraycopy(receDatas, index += n12.length, n13, 0, driverNo_byte.length);
        tenYear = HexUtil.printHexBinary(n13);

        byte[] n14 = new byte[1];
        arraycopy(receDatas, index += n13.length, n14, 0, pasmNo_byte.length);
        tenBalance = Util.hex2IntStr(HexUtil.printHexBinary(n14));

        byte[] n15 = new byte[2];
        arraycopy(receDatas, index += n14.length, n15, 0, driverNo_byte.length);
        eleveYear = HexUtil.printHexBinary(n15);

        byte[] n16 = new byte[1];
        arraycopy(receDatas, index += n15.length, n16, 0, pasmNo_byte.length);
        eleveBalance = Util.hex2IntStr(HexUtil.printHexBinary(n16));

        byte[] n17 = new byte[2];
        arraycopy(receDatas, index += n16.length, n17, 0, driverNo_byte.length);
        twelveYear = HexUtil.printHexBinary(n15);

        byte[] n18 = new byte[1];
        arraycopy(receDatas, index += n17.length, n18, 0, pasmNo_byte.length);
        twelveBalance = Util.hex2IntStr(HexUtil.printHexBinary(n16));

    }


}
