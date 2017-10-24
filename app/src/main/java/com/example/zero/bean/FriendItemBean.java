package com.example.zero.bean;

/**
 * Created by jojo on 2017/10/24.
 */

public class FriendItemBean {
    private String name;
    private String phone;
    private int img;

    public void setText(String name, String phone, int imgId) {
        this.name = name;
        this.phone = phone;
        this.img = imgId;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public int getImg() {
        return img;
    }
}
