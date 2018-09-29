package com.xb.busmore.entity.car;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 *
 */
@Entity
public class UseConfig {
    @Id(autoincrement = true)
    Long id;
    int station;//站点号
    String line_chinese_name;//线路中文名
    String line;//线路号 6位
    String driverNo;//司机号
    int WxTx_Id;////微信订单号 0-999999

    @Generated(hash = 2063450035)
    public UseConfig(Long id, int station, String line_chinese_name, String line,
            String driverNo, int WxTx_Id) {
        this.id = id;
        this.station = station;
        this.line_chinese_name = line_chinese_name;
        this.line = line;
        this.driverNo = driverNo;
        this.WxTx_Id = WxTx_Id;
    }

    @Generated(hash = 1362993685)
    public UseConfig() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getStation() {
        return this.station;
    }

    public void setStation(int station) {
        this.station = station;
    }

    public String getLine_chinese_name() {
        return this.line_chinese_name;
    }

    public void setLine_chinese_name(String line_chinese_name) {
        this.line_chinese_name = line_chinese_name;
    }

    public String getLine() {
        return this.line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public String getDriverNo() {
        return this.driverNo;
    }

    public void setDriverNo(String driverNo) {
        this.driverNo = driverNo;
    }

    public int getWxTx_Id() {
        return this.WxTx_Id;
    }

    public void setWxTx_Id(int WxTx_Id) {
        this.WxTx_Id = WxTx_Id;
    }

}
