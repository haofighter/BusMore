package com.xb.busmore.entity;

public class OldConfig {
    private String busNo;//车辆号
    private String LineName;//线路号 旧APP
    private String line;//线路号 4位 新APP

    public String getBusNo() {
        return busNo;
    }

    public String getLineName() {
        return LineName;
    }

    public void setLineName(String lineName) {
        LineName = lineName;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public void setBusNo(String busNo) {
        this.busNo = busNo;
    }
}
