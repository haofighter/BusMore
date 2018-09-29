package com.xb.busmore.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class AllLineInfo {


    /**
     * acnt : 5
     * routeno : 22
     * routename : 周村分公司 22路
     * routeversion : 20180730151620
     */
    @Id(autoincrement = true)
    private Long id;
    private String acnt;
    private String routeno;
    private String routename;
    private String routeversion;
    private long uptatetime;

    @Generated(hash = 225707312)
    public AllLineInfo(Long id, String acnt, String routeno, String routename,
            String routeversion, long uptatetime) {
        this.id = id;
        this.acnt = acnt;
        this.routeno = routeno;
        this.routename = routename;
        this.routeversion = routeversion;
        this.uptatetime = uptatetime;
    }

    @Generated(hash = 1157510657)
    public AllLineInfo() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAcnt() {
        return acnt;
    }

    public void setAcnt(String acnt) {
        this.acnt = acnt;
    }

    public String getRouteno() {
        return routeno;
    }

    public void setRouteno(String routeno) {
        this.routeno = routeno;
    }

    public String getRoutename() {
        return routename;
    }

    public void setRoutename(String routename) {
        this.routename = routename;
    }

    public String getRouteversion() {
        return routeversion;
    }

    public void setRouteversion(String routeversion) {
        this.routeversion = routeversion;
    }

    public long getUptatetime() {
        return uptatetime;
    }

    public void setUptatetime(long uptatetime) {
        this.uptatetime = uptatetime;
    }
}
