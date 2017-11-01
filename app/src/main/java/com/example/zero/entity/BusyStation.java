package com.example.zero.entity;

/**
 * Created by ZERO on 2017/10/28.
 */

public class BusyStation {
    /**
     * 名称
     */
    String name;
    /**
     * 繁忙类型
     */
    busyType type;
    /**
     * 站点名称
     */
    String stationName;
    /**
     * 繁忙信息
     */
    String info;

    public enum busyType{
        JAM,
        EVENT
    }

    public BusyStation(){}

    public BusyStation(String name, busyType type, String stationName, String info) {
        this.name = name;
        this.type = type;
        this.stationName = stationName;
        this.info = info;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public busyType getType() {
        return type;
    }

    public void setType(busyType type) {
        this.type = type;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
