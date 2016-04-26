package com.xbx.tourguide.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.xbx.tourguide.beans.Result;
import com.xbx.tourguide.beans.TourGuideBeans;

/**
 * Created by shuzhen on 2016/4/6.
 */
public class Cookie {

    private static String filename = "filename";

    /**
     * 获取存储的用户信息
     *
     * @param context
     * @return
     */
//    public static TourGuideBeans getUserInfo(Context context) {
//        SharedPreferences share = context.getSharedPreferences(filename,
//                Context.MODE_WORLD_READABLE);
//        String info = share.getString("userbean", "");
//        if (info.length() > 0) {
//            LogUtils.i("---userbean:"+info);
//            TourGuideBeans temp = JsonUtils.object(info, TourGuideBeans.class);
//            return temp;
//        } else {
//            return null;
//        }
//    }

    public static String getUserInfo(Context context) {
        SharedPreferences share = context.getSharedPreferences(filename,
                Context.MODE_WORLD_READABLE);
        String info = share.getString("userbean", "");
        if (info.length() > 0) {
            LogUtils.i("---userbean:"+info);
            return info;
        } else {
            return null;
        }
    }

    /**
     * 存储临时文件
     *
     * @param context
     * @param info
     */
    public static void putUserInfo(Context context, String info) {
        SharedPreferences share = context.getSharedPreferences(filename,
                Context.MODE_WORLD_WRITEABLE);
        SharedPreferences.Editor editor = share.edit();
        if (info != null) {
            editor.putString("userbean", info);
        }
        editor.commit();
        editor.clear();
    }

    public static void putPhone(Context context, String phone) {
        SharedPreferences share = context.getSharedPreferences(filename,
                Context.MODE_WORLD_WRITEABLE);
        SharedPreferences.Editor editor = share.edit();
        if (phone != null) {
            editor.putString("phone", phone);
        }

        editor.commit();
        editor.clear();

    }

    public static String getPhone(Context context) {
        SharedPreferences share = context.getSharedPreferences(filename,
                Context.MODE_WORLD_READABLE);
        String info = share.getString("phone", "");
        if (info.length() > 0) {
            return info;
        } else {
            return null;
        }
    }

    public static void putDeviceID(Context context, String deviceid) {
        SharedPreferences share = context.getSharedPreferences(filename,
                Context.MODE_WORLD_WRITEABLE);
        SharedPreferences.Editor editor = share.edit();
        if (deviceid != null) {
            editor.putString("deviceid", deviceid);
        }

        editor.commit();
        editor.clear();

    }

    public static String getDeviceID(Context context) {
        SharedPreferences share = context.getSharedPreferences(filename,
                Context.MODE_WORLD_READABLE);
        String info = share.getString("deviceid", "");
        if (info.length() > 0) {
            return info;
        } else {
            return null;
        }
    }

    public static void putLonAndLat(Context context, String lonAndLat) {
        SharedPreferences share = context.getSharedPreferences(filename,
                Context.MODE_WORLD_WRITEABLE);
        SharedPreferences.Editor editor = share.edit();
        if (lonAndLat != null) {
            editor.putString("lonAndlat", lonAndLat);
        }

        editor.commit();
        editor.clear();
    }

    public static String getLonAndLat(Context context) {
        SharedPreferences share = context.getSharedPreferences(filename,
                Context.MODE_WORLD_READABLE);
        String info = share.getString("lonAndlat", "");
        if (info.length() > 0) {
            return info;
        } else {
            return null;
        }
    }

    public static void putUid(Context context, String uid) {
        SharedPreferences share = context.getSharedPreferences(filename,
                Context.MODE_WORLD_WRITEABLE);
        SharedPreferences.Editor editor = share.edit();
        if (uid != null) {
            editor.putString("uid", uid);
        }

        editor.commit();
        editor.clear();

    }

    public static String getUid(Context context) {
        SharedPreferences share = context.getSharedPreferences(filename,
                Context.MODE_WORLD_READABLE);
        String info = share.getString("uid", "");
        if (info.length() > 0) {
            return info;
        } else {
            return null;
        }
    }

    /**
     * 是否有预约订单
     * @param context
     * @param isAppointOrder
     */
    public static void putAppointmentOrder(Context context, Boolean isAppointOrder) {
        SharedPreferences share = context.getSharedPreferences(filename,
                Context.MODE_WORLD_WRITEABLE);
        SharedPreferences.Editor editor = share.edit();
        if (isAppointOrder != null) {
            editor.putBoolean("isAppointOrder", isAppointOrder);
        }

        editor.commit();
        editor.clear();

    }

    public static boolean getAppointmentOrder(Context context) {
        SharedPreferences share = context.getSharedPreferences(filename,
                Context.MODE_WORLD_READABLE);
        boolean info = share.getBoolean("isAppointOrder", false);
        return info;
    }

    //接单对话框是否显示
    public static void putIsDialog(Context context, Boolean isDialog) {
        SharedPreferences share = context.getSharedPreferences(filename,
                Context.MODE_WORLD_WRITEABLE);
        SharedPreferences.Editor editor = share.edit();
        if (isDialog != null) {
            editor.putBoolean("isDialog", isDialog);
        }

        editor.commit();
        editor.clear();

    }

    public static boolean getIsDialog(Context context) {
        SharedPreferences share = context.getSharedPreferences(filename,
                Context.MODE_WORLD_READABLE);
        boolean info = share.getBoolean("isDialog", false);
        return info;
    }

}
