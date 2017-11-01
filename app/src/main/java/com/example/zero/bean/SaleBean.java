package com.example.zero.bean;

/**
 * Created by jojo on 2017/9/11.
 */

public class SaleBean {
    private String name;
    private String price;
    private String content;
    private String time;
    private String img;

    public void setText(String name, String price, String content, String time, String imgId) {
        this.name = name;
        this.price = price;
        this.content = content;
        this.time = time;
        this.img = imgId;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getContent() {
        return content;
    }

    public String getTime() {
        return time;
    }

    public String getImage() {
        return img;
    }
}
