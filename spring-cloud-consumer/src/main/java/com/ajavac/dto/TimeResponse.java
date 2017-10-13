package com.ajavac.dto;

/**
 * Created by wyp0596 on 13/10/2017.
 */
public class TimeResponse {

    private String name;

    private String time;

    private Long millis;

    public TimeResponse() {
    }

    public TimeResponse(String name, String time, Long millis) {
        this.name = name;
        this.time = time;
        this.millis = millis;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Long getMillis() {
        return millis;
    }

    public void setMillis(Long millis) {
        this.millis = millis;
    }
}
