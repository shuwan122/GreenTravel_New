package com.example.zero.bean;

/**
 * Created by jojo on 2017/10/16.
 */

public class FavorItemBean {
    private String name;
    private String content;
    public static final String BUTTON = "Button";
    public static final String ITEM = "Item";
    private String type;

    public void setText(String name, String content) {
        this.name = name;
        this.content = content;
    }

    public FavorItemBean setType(String type) {
        this.type = type;
        return this;
    }

    public String getName() {
        return name;
    }

    public String getContent() {
        return content;
    }

    public String getType() {
        return type;
    }
}
