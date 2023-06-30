package com.example.myapplication;

import android.graphics.drawable.Drawable;

public class Inquiry {

    String sender;
    String title;
    String body;


    public Inquiry() {
    }


    public Inquiry(String sender, String title, String body) {
        this.sender = sender;
        this.title = title;
        this.body = body;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
