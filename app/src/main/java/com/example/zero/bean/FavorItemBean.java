package com.example.zero.bean;

/**
 * Created by jojo on 2017/10/16.
 */

public class FavorItemBean {
    private String name;
    private String content;

    public void setText(String name, String content) {
        this.name = name;
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public String getContent() {
        return content;
    }
}
