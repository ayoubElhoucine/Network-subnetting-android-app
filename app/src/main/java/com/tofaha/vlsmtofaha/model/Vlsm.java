package com.tofaha.vlsmtofaha.model;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Vlsm extends RealmObject {
    private RealmList<HostNumber> hostNumberList;
    @PrimaryKey
    private long id;
    private String ipAddress;
    private String time;

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime() {
        return this.time;
    }

    public RealmList<HostNumber> getHostNumberList() {
        return this.hostNumberList;
    }

    public String getIpAddress() {
        return this.ipAddress;
    }

    public void setHostNumberList(RealmList<HostNumber> hostNumberList) {
        this.hostNumberList = hostNumberList;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
