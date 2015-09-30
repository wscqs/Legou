package com.cqs.entity;

/**
 * Created by chenqiusong on 15/9/15.
 */
public class Helper {
    private int code;
    private String text;
    private boolean isSent;
    private String time;


    public Helper(int code, String text, boolean isSent,String time) {
        this.code = code;
        this.text = text;
        this.isSent = isSent;
        this.time=time;
    }

    public Helper(String text, boolean isSent) {
        this.text = text;
        this.isSent = isSent;
    }

    public Helper(String text, boolean isSent, String time) {
        this.text = text;
        this.isSent = isSent;
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean getIsSent() {
        return isSent;
    }

    public void setIsSent(boolean isSent) {
        this.isSent = isSent;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }


}
