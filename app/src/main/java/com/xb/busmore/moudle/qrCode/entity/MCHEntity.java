package com.xb.busmore.moudle.qrCode.entity;

/**
 * 作者: TangRen on 2017/12/17
 * 包名：com.czgj.entity.scan
 * 邮箱：996489865@qq.com
 * TODO:商户信息
 */

public class MCHEntity {

    /**
     * m : mchID商户ID
     * c : city_code城市编码
     * o : order_desc备注
     * K : 秘钥
     */

    private String m;
    private String c;
    private String o;
    private String k;

    public MCHEntity(String m, String c, String o, String k) {
        this.m = m;
        this.c = c;
        this.o = o;
        this.k = k;
    }

    public String getK() {
        return k;
    }

    public void setK(String k) {
        this.k = k;
    }

    public String getM() {
        return m;
    }

    public void setM(String m) {
        this.m = m;
    }

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }

    public String getO() {
        return o;
    }

    public void setO(String o) {
        this.o = o;
    }
}
