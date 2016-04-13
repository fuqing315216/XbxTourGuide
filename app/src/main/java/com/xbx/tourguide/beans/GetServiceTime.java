package com.xbx.tourguide.beans;

import java.io.Serializable;

/**
 * Created by shuzhen on 2016/4/8.
 */
public class GetServiceTime implements Serializable {

    private String free_time;
    private int server_type;
    private String guide_instant_price;
    private String guide_reserve_price;

    public String getFree_time() {
        return free_time;
    }

    public void setFree_time(String free_time) {
        this.free_time = free_time;
    }

    public int getServer_type() {
        return server_type;
    }

    public void setServer_type(int server_type) {
        this.server_type = server_type;
    }

    public String getGuide_instant_price() {
        return guide_instant_price;
    }

    public void setGuide_instant_price(String guide_instant_price) {
        this.guide_instant_price = guide_instant_price;
    }

    public String getGuide_reserve_price() {
        return guide_reserve_price;
    }

    public void setGuide_reserve_price(String guide_reserve_price) {
        this.guide_reserve_price = guide_reserve_price;
    }
}
