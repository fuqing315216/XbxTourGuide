package com.xbx.tourguide.beans;

import java.io.Serializable;
import java.util.List;

/**
 * Created by shuzhen on 2016/4/15.
 */
public class ProvinceBeans implements Serializable {

    private String id;
    private String name;
    private List<CityBeans> city;

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

    public List<CityBeans> getCity() {
        return city;
    }

    public void setCity(List<CityBeans> city) {
        this.city = city;
    }
}
