package com.xbx.tourguide.util;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * 日期获取
 * 
 * @author Administrator
 *
 */
public class CalendarUtil {
	Calendar mCalendar;

	public CalendarUtil() {

		mCalendar = Calendar.getInstance();
	}

	/**
	 * 根据年 月 获取对应的月份 天数
	 * */
	public int getDaysByYearMonth(int year, int month) {
		Calendar a = Calendar.getInstance();
		a.set(Calendar.YEAR, year);
		a.set(Calendar.MONTH, month - 1);
		a.set(Calendar.DATE, 1);
		a.roll(Calendar.DATE, -1);
		int maxDate = a.get(Calendar.DATE);
		return maxDate;
	}

	/**
	 * 获取星期几
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @return 0为星期日
	 */
	public int StringData(int year, int month, int day) {
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
	 * 判断今天以前还是最后（之后包括今天）
	 * 
	 * @return true：之后，false：之前
	 */
	public boolean isCompareTo(Calendar _Calendar) {
		// 0相当-1之前 1之后
		if (mCalendar.compareTo(_Calendar) != -1) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 获取某月最后一天
	 * @param year
	 * @param month
	 * @return
	 */
	public int getLastDayofMonth(int year,int month){
		Calendar cal = Calendar.getInstance();
		//设置年份
		cal.set(Calendar.YEAR,year);
		//设置月份
		cal.set(Calendar.MONTH, month-1);
		//获取某月最大天数
		int lastDay = cal.getActualMaximum(Calendar.DATE);
		Log.i("log", lastDay+ "lastDay");
		//设置日历中月份的最大天数
		cal.set(Calendar.DAY_OF_MONTH, lastDay);
		//格式化日期
		SimpleDateFormat sdf = new SimpleDateFormat("dd");
		String lastDayOfMonth = sdf.format(cal.getTime());

		return Integer.parseInt(lastDayOfMonth);
	}
}
