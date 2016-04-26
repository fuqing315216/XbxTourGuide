package com.xbx.tourguide.beans;

import java.io.Serializable;

/**
 * Created by xbx on 2016/4/25.
 */
public class UnFinishedOrderBeans implements Serializable {
    private String data_type;
    private String unpay;
    private GoingBeans going;
    private String uncomment;

    public String getData_type() {
        return data_type;
    }

    public String getUnpay() {
        return unpay;
    }

    public GoingBeans getGoing() {
        return going;
    }

    public String getUncomment() {
        return uncomment;
    }

    public void setData_type(String data_type) {
        this.data_type = data_type;
    }

    public void setUnpay(String unpay) {
        this.unpay = unpay;
    }

    public void setGoing(GoingBeans going) {
        this.going = going;
    }

    public void setUncomment(String uncomment) {
        this.uncomment = uncomment;
    }

    @Override
    public String toString() {
        return "UnFinishedOrderBeans{" +
                "data_type='" + data_type + '\'' +
                ", unpay='" + unpay + '\'' +
                ", going=" + going +
                ", uncomment='" + uncomment + '\'' +
                '}';
    }
}
