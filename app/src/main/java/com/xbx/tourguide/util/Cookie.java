package com.xbx.tourguide.util;

import android.content.Context;
import android.content.SharedPreferences;

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

    public static String getUserInfo(Context context) {
        SharedPreferences share = context.getSharedPreferences(filename,
                Context.MODE_WORLD_READABLE);
        String info = share.getString("userbean", "");
        if (info.length() > 0) {
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

    /**
     * 预约订单num
     *
     * @param context
     * @param appointOrder
     */
    public static void putAppointmentOrder(Context context, String appointOrder) {
        SharedPreferences share = context.getSharedPreferences(filename,
                Context.MODE_WORLD_WRITEABLE);
        SharedPreferences.Editor editor = share.edit();
        if (appointOrder != null) {
            editor.putString("appointOrder", appointOrder);
        }

        editor.commit();
        editor.clear();

    }

    public static String getAppointmentOrder(Context context) {
        SharedPreferences share = context.getSharedPreferences(filename,
                Context.MODE_WORLD_READABLE);
        String info = share.getString("appointOrder", "");
        if (info.length() > 0) {
            return info;
        } else {
            return null;
        }
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

    //是否能接收JPush
    public static void putIsJPush(Context context, Boolean isDialog) {
        SharedPreferences share = context.getSharedPreferences(filename,
                Context.MODE_WORLD_WRITEABLE);
        SharedPreferences.Editor editor = share.edit();
        if (isDialog != null) {
            editor.putBoolean("isJPush", isDialog);
        }

        editor.commit();
        editor.clear();

    }

    public static boolean getIsJPush(Context context) {
        SharedPreferences share = context.getSharedPreferences(filename,
                Context.MODE_WORLD_READABLE);
        boolean info = share.getBoolean("isJPush", false);
        return info;
    }

    public static void putLoginOut(Context context, Boolean isLoginOut) {
        SharedPreferences share = context.getSharedPreferences(filename,
                Context.MODE_WORLD_WRITEABLE);
        SharedPreferences.Editor editor = share.edit();
        if (isLoginOut != null) {
            editor.putBoolean("loginOut", isLoginOut);
        }

        editor.commit();
        editor.clear();

    }

    public static boolean getLoginOut(Context context) {
        SharedPreferences share = context.getSharedPreferences(filename,
                Context.MODE_WORLD_READABLE);
        boolean info = share.getBoolean("loginOut", false);
        return info;
    }

    /**
     * 更新清除信息
     *
     * @param con
     */
    public static void clear(Context con) {
        SharedPreferences sp = con.getSharedPreferences("UpdateState",
                Context.MODE_PRIVATE);
        sp.edit().clear().commit();
    }

    /***
     * 版本更新状态
     ***/
    public static void saveState(Context con, boolean state) {
        SharedPreferences sp = con.getSharedPreferences("UpdateState",
                Context.MODE_PRIVATE);
        sp.edit().putBoolean("state", state).commit();
    }
}
