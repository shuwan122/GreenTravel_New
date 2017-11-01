package com.example.zero.entity;

/**
 * Created by ZERO on 2017/10/27.
 */

public class Coupon {
    /**
     * 店铺名称
     */
    private String name;
    /**
     * 维度
     */
    private double lat;
    /**
     * 经度
     */
    private  double lon;

    public void Coupon(){}

    public void Coupon(String name, double lat, double lon){
        this.name = name;
        this.lat = lat;
        this.lon = lon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {

        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    @Override
    public String toString() {
        return "Coupon{" +
                "name='" + name + '\'' +
                ", lat=" + lat +
                ", lon=" + lon +
                '}';
    }
}
