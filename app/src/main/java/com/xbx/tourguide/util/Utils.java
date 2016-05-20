package com.xbx.tourguide.util;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xbx.tourguide.R;
import com.xbx.tourguide.beans.SQLiteOrderBean;
import com.xbx.tourguide.db.OrderNumberDao;
import com.xbx.tourguide.ui.OrderRemainActivity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xbx on 2016/4/21.
 */
public class Utils {
    private static final double EARTH_RADIUS = 6378137.0;

    /**
     * 根据返回的star分数计算ratingbar显示的星数
     *
     * @param star
     * @return
     */
    public static float getStar(String star) {
        return new BigDecimal(Float.valueOf(star)).setScale(1, BigDecimal.ROUND_HALF_UP).floatValue();
    }

    /**
     * Flowlayout add Text
     *
     * @param context
     * @param txt
     * @return
     */
    public static TextView addTextView(Context context, String txt) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        TextView textView = new TextView(context);
        textView.setText(txt);
        textView.setPadding(30, 12, 30, 12);
        params.rightMargin = 15;
        params.bottomMargin = 12;
        textView.setTextColor(ContextCompat.getColor(context, R.color.head_bg_color));
        textView.setBackgroundResource(R.drawable.guide_tag_bg);
        textView.setLayoutParams(params);
        return textView;
    }

    /**
     * 检查是否有订单要提示
     * @param context
     */
    public static void isShowDialog(Context context) {
        if (!(Boolean) SPUtils.get(context, Constant.LOGIN_OUT, false)) {
            OrderNumberDao orderNumberDao = new OrderNumberDao(context);
            SQLiteOrderBean sqLiteOrderBean = orderNumberDao.selectFirst();
            if (sqLiteOrderBean.getNum() != null) {//有缓存
                if (Utils.isOverTime(Long.valueOf(sqLiteOrderBean.getDate()))) {
                    orderNumberDao.clear();
                    return;
                }
                SPUtils.put(context, Constant.LOGIN_OUT, true);
                Intent orderIntent = new Intent(context, OrderRemainActivity.class);
                orderIntent.putExtra("serverType", "0");
                orderIntent.putExtra("orderNumber", sqLiteOrderBean.getNum());
                orderIntent.putExtra("_id", sqLiteOrderBean.get_id());
                orderIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(orderIntent);
            } else {//查看是否有预约服务
                String appointOrder = (String) SPUtils.get(context, Constant.APPOINT_ORDER, "");
                if (appointOrder != null && !VerifyUtil.isNullOrEmpty(appointOrder)) {
                    SPUtils.put(context, "isDialog", true);
                    Intent orderIntent = new Intent(context, OrderRemainActivity.class);
                    orderIntent.putExtra("serverType", "1");
                    orderIntent.putExtra("orderNumber", appointOrder);
                    orderIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(orderIntent);
                }
            }
        }
    }

    public static boolean isOverTime(long getMillionSeconds) {
        if (System.currentTimeMillis() - getMillionSeconds > 60 * 60 * 1000) {//时间超过一个小时
            return true;
        } else {
            return false;
        }
    }

    /**
     * list倒序
     *
     * @param list
     * @param <T>
     * @return
     */
    public static <T> List<T> reverseList(List<T> list) {
        List<T> relist = new ArrayList<>();
        for (int i = list.size() - 1; i >= 0; i--) {
            relist.add(list.get(i));
        }
        return relist;
    }

    /**
     * 是否在当前应用
     *
     * @param context
     * @return
     */
    public static boolean isAction(final Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (topActivity.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }

    public static boolean isTopActiviy(String cmdName, Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> runningTaskInfos = manager.getRunningTasks(Integer.MAX_VALUE);
        String cmpNameTemp = null;
        if (null != runningTaskInfos) {
            cmpNameTemp = (runningTaskInfos.get(0).topActivity).getClassName();
        }

        if (null == cmpNameTemp) {
            return false;
        }
        return cmpNameTemp.equals(cmdName);
    }

    /**
     * 毫秒转换
     *
     * @param ms
     * @return
     */
    public static String formatTime(long ms) {

        ms = ms * 1000;

        int ss = 1000;
        int mi = ss * 60;
        int hh = mi * 60;
        int dd = hh * 24;

        long day = ms / dd;
        long hour = (ms - day * dd) / hh;
        long minute = (ms - day * dd - hour * hh) / mi;
        long second = (ms - day * dd - hour * hh - minute * mi) / ss;
        long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;

        String strDay = day < 10 ? "0" + day : "" + day; //天
        String strHour = hour < 10 ? "0" + hour : "" + hour;//小时
        String strMinute = minute < 10 ? "0" + minute : "" + minute;//分钟
        String strSecond = second < 10 ? "0" + second : "" + second;//秒
        String strMilliSecond = milliSecond < 10 ? "0" + milliSecond : "" + milliSecond;//毫秒
        strMilliSecond = milliSecond < 100 ? "0" + strMilliSecond : "" + strMilliSecond;

        return strHour + "小时" + strMinute + "分" + strSecond + "秒";
    }

    // 返回单位是米
    public static double getDistance(double longitude1, double latitude1,
                                     double longitude2, double latitude2) {
        double Lat1 = rad(latitude1);
        double Lat2 = rad(latitude2);
        double a = Lat1 - Lat2;
        double b = rad(longitude1) - rad(longitude2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                + Math.cos(Lat1) * Math.cos(Lat2)
                * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000) / 10000;
        return s;
    }

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }
}
