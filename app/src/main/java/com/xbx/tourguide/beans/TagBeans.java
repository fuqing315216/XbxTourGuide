package com.xbx.tourguide.beans;

import java.io.Serializable;

/**
 * Created by xbx on 2016/4/25.
 */
public class TagBeans implements Serializable {
    private String tag_id;
    private String tag_name;
    private String tag_times;

    public String getTag_id() {
        return tag_id;
    }

    public String getTag_name() {
        return tag_name;
    }

    public String getTag_times() {
        return tag_times;
    }

    public void setTag_id(String tag_id) {
        this.tag_id = tag_id;
    }

    public void setTag_name(String tag_name) {
        this.tag_name = tag_name;
    }

    public void setTag_times(String tag_times) {
        this.tag_times = tag_times;
    }

    @Override
    public String toString() {
        return "TagBeans{" +
                "tag_id='" + tag_id + '\'' +
                ", tag_name='" + tag_name + '\'' +
                ", tag_times='" + tag_times + '\'' +
                '}';
    }
}
