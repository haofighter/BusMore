package com.xb.busmore.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class LinePriceInfo {
    /**
     * no : 1
     * price : 500
     */
    @Id(autoincrement = true)
    private Long id;
    private int no;
    private int price;
    private String line;
    private int diraction;
    private Long time;
    @Generated(hash = 1816313056)
    public LinePriceInfo(Long id, int no, int price, String line, int diraction,
            Long time) {
        this.id = id;
        this.no = no;
        this.price = price;
        this.line = line;
        this.diraction = diraction;
        this.time = time;
    }
    @Generated(hash = 1700486196)
    public LinePriceInfo() {
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
    public int getPrice() {
        return this.price;
    }
    public void setPrice(int price) {
        this.price = price;
    }
    public String getLine() {
        return this.line;
    }
    public void setLine(String line) {
        this.line = line;
    }
    public int getDiraction() {
        return this.diraction;
    }
    public void setDiraction(int diraction) {
        this.diraction = diraction;
    }
    public Long getTime() {
        return this.time;
    }
    public void setTime(Long time) {
        this.time = time;
    }


}
