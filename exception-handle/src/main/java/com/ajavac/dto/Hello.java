package com.ajavac.dto;

import java.util.Date;

/**
 * DTO
 * Created by wyp0596 on 21/03/2017.
 */
public class Hello {

    private Date time;

    private String string;

    private int number;

    public Hello() {
        this(new Date(System.currentTimeMillis()),"Hello World ! 你好",8080);
    }

    public Hello(Date time, String string, int number) {
        this.time = time;
        this.string = string;
        this.number = number;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
