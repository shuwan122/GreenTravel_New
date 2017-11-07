package com.example.zero.bean;

public class MsgBean {
    private String title;
    private String content;
    private String time;
    private String img;

    public void setText(String title, String content, String time, String imgId) {
        this.title = title;
        this.content = content;
        this.time = time;
        this.img = imgId;
    }

    public String getTitle() {
        return title;
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
