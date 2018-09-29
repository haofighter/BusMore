package com.xb.busmore.moudle.qrCode.entity;

public class XbScanInfo {

    /**
     * l : 6363
     * n : 999999
     * t : 1
     * t2 : 0
     * k : s5l6jibZz222x:b78V.
     */

    private String l;
    private String n;
    private int t;
    private int t2;
    private String k;

    public String getL() {
        return l;
    }

    public void setL(String l) {
        this.l = l;
    }

    public String getN() {
        return n;
    }

    public void setN(String n) {
        this.n = n;
    }

    public int getT() {
        return t;
    }

    public void setT(int t) {
        this.t = t;
    }

    public int getT2() {
        return t2;
    }

    public void setT2(int t2) {
        this.t2 = t2;
    }

    public String getK() {
        return k;
    }

    public void setK(String k) {
        this.k = k;
    }

    @Override
    public String toString() {
        return "XbScanInfo{" +
                "l='" + l + '\'' +
                ", n='" + n + '\'' +
                ", t=" + t +
                ", t2=" + t2 +
                ", k='" + k + '\'' +
                '}';
    }
}
