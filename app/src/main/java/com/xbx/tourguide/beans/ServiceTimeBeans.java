package com.xbx.tourguide.beans;

import java.io.Serializable;

/**
 * Created by shuzhen on 2016/4/7.
 */
public class ServiceTimeBeans implements Serializable {

    private boolean isSelected;
    private DateBeans date;

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

    public ServiceTimeBeans(boolean isSelected,DateBeans date){
        this.isSelected=isSelected;
        this.date=date;
    }

    public ServiceTimeBeans(){

    }

}
