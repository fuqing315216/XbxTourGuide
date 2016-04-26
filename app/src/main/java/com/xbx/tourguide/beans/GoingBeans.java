package com.xbx.tourguide.beans;

import java.io.Serializable;

/**
 * Created by xbx on 2016/4/25.
 */
public class GoingBeans implements Serializable {
    private String order_number;
    private String guide_type;

    public String getOrder_number() {
        return order_number;
    }

    public String getGuide_type() {
        return guide_type;
    }

    public void setOrder_number(String order_number) {
        this.order_number = order_number;
    }

    public void setGuide_type(String guide_type) {
        this.guide_type = guide_type;
    }

    @Override
    public String toString() {
        return "GoingBeans{" +
                "order_number='" + order_number + '\'' +
                ", guide_type='" + guide_type + '\'' +
                '}';
    }
}
