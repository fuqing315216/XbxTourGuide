package com.xbx.tourguide.beans;

import java.io.Serializable;

/**
 * Created by shuzhen on 2016/4/1.
 */
public class MyOrderBeans implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String order_number;
    private String pay_money;
    private String server_status;
    private String pay_status;
    private String end_addr;
    private String server_type;
    private String user_type;
    private String order_time;

    public String getOrder_time() {
        return order_time;
    }

    public void setOrder_time(String order_time) {
        this.order_time = order_time;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getOrder_number() {
        return order_number;
    }

    public void setOrder_number(String order_number) {
        this.order_number = order_number;
    }

    public String getPay_money() {
        return pay_money;
    }

    public void setPay_money(String pay_money) {
        this.pay_money = pay_money;
    }

    public String getServer_status() {
        return server_status;
    }

    public void setServer_status(String server_status) {
        this.server_status = server_status;
    }

    public String getPay_status() {
        return pay_status;
    }

    public void setPay_status(String pay_status) {
        this.pay_status = pay_status;
    }

    public String getEnd_addr() {
        return end_addr;
    }

    public void setEnd_addr(String end_addr) {
        this.end_addr = end_addr;
    }

    public String getServer_type() {
        return server_type;
    }

    public void setServer_type(String server_type) {
        this.server_type = server_type;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }
}
