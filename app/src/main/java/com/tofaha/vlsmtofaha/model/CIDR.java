package com.tofaha.vlsmtofaha.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class CIDR extends RealmObject {
    @PrimaryKey
    private long id;
    private String ipAddress;
    private int maskIp4;
    private int maskIp6;
    private String time;
    private String type;

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIpAddress() {
        return this.ipAddress;
    }

    public int getMaskIp4() {
        return this.maskIp4;
    }

    public int getMaskIp6() {
        return this.maskIp6;
    }

    public String getType() {
        return this.type;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public void setMaskIp4(int maskIp4) {
        this.maskIp4 = maskIp4;
    }

    public void setMaskIp6(int maskIp6) {
        this.maskIp6 = maskIp6;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime() {
        return this.time;
    }
}
