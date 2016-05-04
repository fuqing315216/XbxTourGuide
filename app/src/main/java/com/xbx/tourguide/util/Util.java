package com.xbx.tourguide.util;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.SystemClock;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xbx.tourguide.R;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by xbx on 2016/4/21.
 */
public class Util {

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
        textView.setPadding(20, 8, 20, 8);
        params.rightMargin = 10;
        params.bottomMargin = 8;
        textView.setBackgroundResource(R.drawable.guide_tag_bg);
        textView.setLayoutParams(params);
        return textView;
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

}
