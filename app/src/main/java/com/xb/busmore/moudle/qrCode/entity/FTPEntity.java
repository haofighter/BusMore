package com.xb.busmore.moudle.qrCode.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 作者: TangRen on 2017/12/17
 * 包名：com.czgj.entity.scan
 * 邮箱：996489865@qq.com
 * TODO:FTP 信息
 */

@Entity
public class FTPEntity {


    /**
     * i : 120.21.212.25
     * p : 22
     * u : m1234
     * psw : m1234
     * k : sHB62^84z073x/b66..
     */

    @Id(autoincrement = true)
    private Long id;
    private String i;
    private int p;
    private String u;
    private String psw;
    private String k;

    public FTPEntity(String i, int p, String u, String psw) {
        this.i = i;
        this.p = p;
        this.u = u;
        this.psw = psw;
    }

    @Generated(hash = 1079504792)
    public FTPEntity(Long id, String i, int p, String u, String psw, String k) {
        this.id = id;
        this.i = i;
        this.p = p;
        this.u = u;
        this.psw = psw;
        this.k = k;
    }

    @Generated(hash = 1789605369)
    public FTPEntity() {
    }

    public String getI() {
        return i;
    }

    public void setI(String i) {
        this.i = i;
    }

    public int getP() {
        return p;
    }

    public void setP(int p) {
        this.p = p;
    }

    public String getU() {
        return u;
    }

    public void setU(String u) {
        this.u = u;
    }

    public String getPsw() {
        return psw;
    }

    public void setPsw(String psw) {
        this.psw = psw;
    }

    public String getK() {
        return k;
    }

    public void setK(String k) {
        this.k = k;
    }

    @Override
    public String toString() {
        return "FTPEntity{" +
                "i='" + i + '\'' +
                ", p=" + p +
                ", u='" + u + '\'' +
                ", psw='" + psw + '\'' +
                ", k='" + k + '\'' +
                '}';
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
