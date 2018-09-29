package com.xb.busmore.entity.car;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 车辆的相关配置参数
 */
@Entity
public class CarConfig {
    @Id(autoincrement = true)
    Long id;
    int GPS;
    //更新的时间
    Long time;
    //车辆号
    String busNo;
    //银联商户号
    String unionTraNo;
    //银联终端号
    String unionPos;
    //卡机设备号
    String posSn;
    //备注
    String mark;


    @Generated(hash = 668637602)
    public CarConfig(Long id, int GPS, Long time, String busNo, String unionTraNo,
            String unionPos, String posSn, String mark) {
        this.id = id;
        this.GPS = GPS;
        this.time = time;
        this.busNo = busNo;
        this.unionTraNo = unionTraNo;
        this.unionPos = unionPos;
        this.posSn = posSn;
        this.mark = mark;
    }

    @Generated(hash = 994675825)
    public CarConfig() {
    }

    public Long getId() {
        return this.id;
    }

    public CarConfig setId(Long id) {
        this.id = id;
        return this;
    }

    public int getGPS() {
        return this.GPS;
    }

    public CarConfig setGPS(int GPS) {
        this.GPS = GPS;
        return this;
    }

    public Long getTime() {
        return this.time;
    }

    public CarConfig setTime(Long time) {
        this.time = time;
        return this;
    }

    public String getBusNo() {
        return this.busNo;
    }

    public CarConfig setBusNo(String busNo) {
        this.busNo = busNo;
        return this;
    }

    public String getUnionTraNo() {
        return this.unionTraNo;
    }

    public CarConfig setUnionTraNo(String unionTraNo) {
        this.unionTraNo = unionTraNo;
        return this;
    }

    public String getPosSn() {
        return this.posSn;
    }

    public CarConfig setPosSn(String posSn) {
        this.posSn = posSn;
        return this;
    }

    public String getMark() {
        return this.mark;
    }

    public CarConfig setMark(String mark) {
        this.mark = mark;
        return this;
    }

    public String getUnionPos() {
        return this.unionPos;
    }

    public CarConfig setUnionPos(String unionPos) {
        this.unionPos = unionPos;
        return this;
    }

}
