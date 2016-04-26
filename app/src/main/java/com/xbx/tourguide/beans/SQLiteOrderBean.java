package com.xbx.tourguide.beans;

/**
 * Created by 阿斯卡 on 2016/4/26.
 */
public class SQLiteOrderBean {
    private String num;
    private String date;

    public String getNum() {
        return num;
    }

    public String getDate() {
        return date;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "SQLiteOrderBean{" +
                "num='" + num + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
