package com.xbx.tourguide.util;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xbx.tourguide.R;

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
        if (star.contains(".")) {
            int i = Integer.valueOf(star.charAt(0));
            int j = Integer.valueOf(star.charAt(2));
            LogUtils.i(i + j + "");
            if (j > 5) {
                LogUtils.i(Float.valueOf(i + 1) + "");
                return Float.valueOf(i + 1);
            }
            LogUtils.i(Float.valueOf(i + "." + 5) + "");
            return Float.valueOf(i + "." + 5);
        } else {
            LogUtils.i(Float.valueOf(Integer.valueOf(star) / 2) + "");
            return Float.valueOf(Integer.valueOf(star) / 2);
        }
    }

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
}
