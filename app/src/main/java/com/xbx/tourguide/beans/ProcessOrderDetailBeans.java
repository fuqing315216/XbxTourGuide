package com.xbx.tourguide.beans;

import java.io.Serializable;

/**
 * Created by shuzhen on 2016/4/12.
 */
public class ProcessOrderDetailBeans implements Serializable {

    private String mobile;
    private String head_image;
    private String nickname;
    private String lon;
    private String lat;
    private String server_start_time;
    private String end_addr;
    private String now_time;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getHead_image() {
        return head_image;
    }

    public void setHead_image(String head_image) {
        this.head_image = head_image;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getServer_start_time() {
        return server_start_time;
    }

    public void setServer_start_time(String server_start_time) {
        this.server_start_time = server_start_time;
    }

    public String getEnd_addr() {
        return end_addr;
    }

    public void setEnd_addr(String end_addr) {
        this.end_addr = end_addr;
    }

    public String getNow_time() {
        return now_time;
    }

    public void setNow_time(String now_time) {
        this.now_time = now_time;
    }
}
