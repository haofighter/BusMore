package com.xb.busmore.moudle.qrCode.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;

/**
 * 作者: Tangren on 2017-12-13
 * 包名：com.czgj.entity.qr
 * 邮箱：996489865@qq.com
 * TODO:一句话描述
 */
@Entity
public class MacKeyEntity {
    @Id(autoincrement = true)
    private Long id;
    @Unique
    private String key_id;
    @Unique
    private String pubkey;
    private String time;
    @Generated(hash = 1214124432)
    public MacKeyEntity(Long id, String key_id, String pubkey, String time) {
        this.id = id;
        this.key_id = key_id;
        this.pubkey = pubkey;
        this.time = time;
    }
    @Generated(hash = 1027612983)
    public MacKeyEntity() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getKey_id() {
        return this.key_id;
    }
    public void setKey_id(String key_id) {
        this.key_id = key_id;
    }
    public String getPubkey() {
        return this.pubkey;
    }
    public void setPubkey(String pubkey) {
        this.pubkey = pubkey;
    }
    public String getTime() {
        return this.time;
    }
    public void setTime(String time) {
        this.time = time;
    }

}
