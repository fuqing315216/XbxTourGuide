package com.xbx.tourguide.beans;

import java.io.Serializable;

/**
 * Created by xbx on 2016/4/22.
 */
public class OrderNumBeans implements Serializable {
    private String id;
    private String num;

    public void setId(String id) {
        this.id = id;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getId() {
        return id;
    }

    public String getNum() {
        return num;
    }

    @Override
    public String toString() {
        return "OrderNumBeans{" +
                "id='" + id + '\'' +
                ", num='" + num + '\'' +
                '}';
    }
}
