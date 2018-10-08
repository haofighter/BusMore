package com.xb.busmore.entity.car;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 车辆运行过程中的参数
 */
@Entity
public class CarRunInfo {
    @Id(autoincrement = true)
    Long id;
    int diraction;//方向 0上行  1下行
    int deviceStatus;//卡机状态  1上车机器 0下车机器 2即上又下机器 第一次默认机器为上车机
    int bianStatu;//变站模式  0自动变站  1手动变站
    int price;//票价
    boolean sign;//司机的签到状态
    String coefficient;//折扣信息

    @Generated(hash = 293031920)
    public CarRunInfo(Long id, int diraction, int deviceStatus, int bianStatu,
            int price, boolean sign, String coefficient) {
        this.id = id;
        this.diraction = diraction;
        this.deviceStatus = deviceStatus;
        this.bianStatu = bianStatu;
        this.price = price;
        this.sign = sign;
        this.coefficient = coefficient;
    }

    @Generated(hash = 236881571)
    public CarRunInfo() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getDiraction() {
        return this.diraction;
    }

    public CarRunInfo setDiraction(int diraction) {
        this.diraction = diraction;
        return this;
    }

    public int getPrice() {
        return this.price;
    }

    public CarRunInfo setPrice(int price) {
        this.price = price;
        return this;
    }

    public boolean getSign() {
        return this.sign;
    }

    public void setSign(boolean sign) {
        this.sign = sign;
    }

    public int getDeviceStatus() {
        return this.deviceStatus;
    }

    public CarRunInfo setDeviceStatus(int deviceStatus) {
        this.deviceStatus = deviceStatus;
        return this;
    }

    public int getBianStatu() {
        return this.bianStatu;
    }

    public CarRunInfo setBianStatu(int bianStatu) {
        this.bianStatu = bianStatu;
        return this;
    }

    public String getCoefficient() {
        return this.coefficient;
    }

    public CarRunInfo setCoefficient(String coefficient) {
        this.coefficient = coefficient;
        return this;
    }


}
