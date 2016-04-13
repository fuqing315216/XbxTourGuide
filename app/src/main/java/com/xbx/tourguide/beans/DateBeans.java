package com.xbx.tourguide.beans;

import java.io.Serializable;

/**
 * Created by shuzhen on 2016/4/8.
 */
public class DateBeans implements Serializable {

    private String year;
    private String month;
    private String date;

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public DateBeans(String year,String month,String date){
        this.year=year;
        this.month=month;
        this.date=date;
    }
}
