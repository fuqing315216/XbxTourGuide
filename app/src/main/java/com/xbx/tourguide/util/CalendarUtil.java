package com.xbx.tourguide.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * 日期获取
 *
 * @author Administrator
 */
public class CalendarUtil {

    /**
     * 获取星期几
     *
     * @param year
     * @param month
     * @param day
     * @return 0为星期日
     */
    public static int StringData(int year, int month, int day) {
        final Calendar c = Calendar.getInstance();
        c.set(year, month - 1, day);
        String mWay = String.valueOf(c.get(Calendar.DAY_OF_WEEK));
        int type = 0;
        if ("1".equals(mWay)) {
            // mWay = "天";
            type = 0;
        } else if ("2".equals(mWay)) {
            // mWay = "一";
            type = 1;
        } else if ("3".equals(mWay)) {
            // mWay = "二";
            type = 2;
        } else if ("4".equals(mWay)) {
            // mWay = "三";
            type = 3;
        } else if ("5".equals(mWay)) {
            // mWay = "四";
            type = 4;
        } else if ("6".equals(mWay)) {
            // mWay = "五";
            type = 5;
        } else if ("7".equals(mWay)) {
            // mWay = "六";
            type = 6;
        }
        return type;
    }

    /**
     * 增加天数
     *
     * @param s 起始日期
     * @param n 增加天数
     * @return
     */
    public static String addDay(String s, int n) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            Calendar cd = Calendar.getInstance();
            cd.setTime(sdf.parse(s));
            cd.add(Calendar.DATE, n);//增加一天
            //cd.add(Calendar.MONTH, n);//增加一个月

            return sdf.format(cd.getTime());

        } catch (Exception e) {
            return null;
        }

    }

    /**
     * 获取年
     *
     * @param date yyyy-mm-dd
     * @return
     */
    public static String getYear(String date) {
        return date.substring(0, 4);
    }

    /**
     * 获取月
     *
     * @param date yyyy-mm-dd
     * @return
     */
    public static String getMonth(String date) {
        return date.substring(5, 7);
    }

    /**
     * 获取日
     *
     * @param date yyyy-mm-dd
     * @return
     */
    public static String getDay(String date) {
        return date.substring(8, date.length());
    }
}
