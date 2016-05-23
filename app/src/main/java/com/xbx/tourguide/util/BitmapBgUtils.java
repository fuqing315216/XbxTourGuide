package com.xbx.tourguide.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;

/**
 * Created by xbx on 2016/5/23.
 */
public class BitmapBgUtils {

    private static BitmapBgUtils instance;
    //单例模式
    public static BitmapBgUtils getInstance() {
        if (instance == null) {
            synchronized (BitmapBgUtils.class) {
                if (instance == null) {
                    instance = new BitmapBgUtils();
                }
            }
        }
        return instance;
    }

    public static void setBitmapBackground(Context context, View view, int resource){
        Bitmap bm = BitmapFactory.decodeResource(context.getResources(),resource);
        BitmapDrawable bd = new BitmapDrawable(context.getResources(), bm);
        view.setBackgroundDrawable(bd);
    }

    public static void destroyBackground(View view){
        BitmapDrawable bd = (BitmapDrawable) view.getBackground();
        view.setBackgroundResource(0);
        bd.setCallback(null);
        bd.getBitmap().recycle();
    }
}
