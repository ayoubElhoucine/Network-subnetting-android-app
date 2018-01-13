package com.tofaha.vlsmtofaha.model;

public class Subnet {
    public String address;
    public int allocatedSize;
    public String broadcast;
    public String decMask;
    public String mask;
    public String name;
    public int neededSize;
    public String range;

    public Subnet(){}

    public Subnet(int neededSize, int allocatedSize, String mask, String decMask, String range, String broadcast) {
        this.neededSize = neededSize;
        this.allocatedSize = allocatedSize;
        this.mask = mask;
        this.decMask = decMask;
        this.range = range;
        this.broadcast = broadcast;
    }
}
