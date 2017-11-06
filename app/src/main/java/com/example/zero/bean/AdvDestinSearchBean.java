package com.example.zero.bean;


import java.util.ArrayList;

/**
 * Created by kazu_0122 on 2017/9/22.
 */

public class AdvDestinSearchBean {
    private boolean isStation;
    private boolean isExtend;
    private String stationTag;
    private String title;
    private String time;
    private int comments;
    private int img;
    private String img_url;
    private int price;
    private float rate;
    private ArrayList<String> labels;

    public void setText(boolean isStation,String stationTag,String title, int comments, int
            price, String time, int imgId, float rate, ArrayList<String> labels) {
        this.isStation = isStation;
        this.isExtend = false;
        this.stationTag = stationTag;
        this.title = title;
        this.time = time;
        this.comments = comments;
        this.img = imgId;
        this.price = price;
        this.rate = rate;
        this.labels = labels;
    }

    public void setText(boolean isStation,String stationTag,String title, int comments, int
            price, String time, String img_url, float rate, ArrayList<String> labels) {
        this.isStation = isStation;
        this.isExtend = false;
        this.stationTag = stationTag;
        this.title = title;
        this.time = time;
        this.comments = comments;
        this.img_url = img_url;
        this.price = price;
        this.rate = rate;
        this.labels = labels;
    }

    public boolean isStation() {
        return isStation;
    }

    public boolean isStore() {
        return !isStation;
    }

    public boolean getToggle() {
        return isExtend;
    }

    public void setToggle(boolean toggle) {
        isExtend = toggle;
    }

    public String getStationTag() {
        return stationTag;
    }

    public String getTitle() {
        return title;
    }

    public String getTime() {
        return time;
    }

    public int getComments() {
        return comments;
    }

    public int getImg() {
        return img;
    }

    public int getPrice() {
        return price;
    }

    public float getRate() {
        return rate;
    }

    public ArrayList<String> getLabels() {
        return labels;
    }

}
