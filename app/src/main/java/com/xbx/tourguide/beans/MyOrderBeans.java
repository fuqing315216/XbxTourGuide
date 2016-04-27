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
    private String order_time;
    private String end_addr;
    private String server_type;
    private String order_status;

    public String getOrder_number() {
        return order_number;
    }

    public String getPay_money() {
        return pay_money;
    }

    public String getOrder_time() {
        return order_time;
    }

    public String getEnd_addr() {
        return end_addr;
    }

    public String getServer_type() {
        return server_type;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_number(String order_number) {
        this.order_number = order_number;
    }

    public void setPay_money(String pay_money) {
        this.pay_money = pay_money;
    }

    public void setOrder_time(String order_time) {
        this.order_time = order_time;
    }

    public void setEnd_addr(String end_addr) {
        this.end_addr = end_addr;
    }

    public void setServer_type(String server_type) {
        this.server_type = server_type;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    @Override
    public String toString() {
        return "MyOrderBeans{" +
                "order_number='" + order_number + '\'' +
                ", pay_money='" + pay_money + '\'' +
                ", order_time='" + order_time + '\'' +
                ", end_addr='" + end_addr + '\'' +
                ", server_type='" + server_type + '\'' +
                ", order_status='" + order_status + '\'' +
                '}';
    }
}
