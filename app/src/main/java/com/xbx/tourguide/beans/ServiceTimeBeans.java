package com.xbx.tourguide.beans;

import java.io.Serializable;

/**
 * Created by shuzhen on 2016/4/7.
 */
public class ServiceTimeBeans implements Serializable {

    private boolean isSelected;
    private boolean isDay;
    private DateBeans date;

    public boolean isDay() {
        return isDay;
    }

    public void setIsDay(boolean day) {
        isDay = day;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public DateBeans getDate() {
        return date;
    }

    public void setDate(DateBeans date) {
        this.date = date;
    }

    public ServiceTimeBeans(boolean isSelected, DateBeans date) {
        this.isSelected = isSelected;
        this.date = date;
    }

    public ServiceTimeBeans() {

    }

    @Override
    public String toString() {
        return "ServiceTimeBeans{" +
                "isSelected=" + isSelected +
                ", isDay=" + isDay +
                ", date=" + date +
                '}';
    }
}
