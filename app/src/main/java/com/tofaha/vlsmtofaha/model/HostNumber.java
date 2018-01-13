package com.tofaha.vlsmtofaha.model;

import io.realm.RealmObject;

public class HostNumber extends RealmObject {
    private int hostNumber;

    public void setHostNumber(int hostNumber) {
        this.hostNumber = hostNumber;
    }

    public int getHostNumber() {
        return this.hostNumber;
    }
}
