package com.xb.busmore.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class LineStation {

    /**
     * no : 1
     * name : 周村
     * latitude : 117.86698240728
     * longitude : 36.797499004843
     * distance : 150
     */
    @Id(autoincrement = true)
    private Long id;
    private int no;
    private String line;
    private String name;
    private float latitude;
    private float longitude;
    private double distance;

    private Long time;
    private int diraction;//0 上行 1下行
    @Generated(hash = 1645680928)
    public LineStation(Long id, int no, String line, String name, float latitude,
            float longitude, double distance, Long time, int diraction) {
        this.id = id;
        this.no = no;
        this.line = line;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.distance = distance;
        this.time = time;
        this.diraction = diraction;
    }
    @Generated(hash = 1192534599)
    public LineStation() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public int getNo() {
        return this.no;
    }
    public void setNo(int no) {
        this.no = no;
    }
    public String getLine() {
        return this.line;
    }
    public void setLine(String line) {
        this.line = line;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public float getLatitude() {
        return this.latitude;
    }
    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }
    public float getLongitude() {
        return this.longitude;
    }
    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }
    public double getDistance() {
        return this.distance;
    }
    public void setDistance(double distance) {
        this.distance = distance;
    }
    public Long getTime() {
        return this.time;
    }
    public void setTime(Long time) {
        this.time = time;
    }
    public int getDiraction() {
        return this.diraction;
    }
    public void setDiraction(int diraction) {
        this.diraction = diraction;
    }


}
