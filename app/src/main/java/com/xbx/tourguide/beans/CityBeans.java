package com.xbx.tourguide.beans;

import java.io.Serializable;

/**
 * Created by shuzhen on 2016/4/15.
 */
public class CityBeans implements Serializable {

    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "CityBeans{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
