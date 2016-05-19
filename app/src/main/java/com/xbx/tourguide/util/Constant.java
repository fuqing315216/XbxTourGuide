package com.xbx.tourguide.util;

import android.os.Environment;

import java.io.File;

/**
 * Created by xbx on 2016/5/10.
 * 常量定义
 */
public class Constant {
    /**
     * 存储根目录
     */
    public static final String APP_ROOT_PATH = Environment.getExternalStorageDirectory().toString();
    /**
     * 图片缓存路径
     */
    public static final String PICTURE_ALBUM_PATH = APP_ROOT_PATH + "/XbxTravel/";
    public static final String PATH_PIC = PICTURE_ALBUM_PATH + File.separator + "Photo";
    /**
     * 系统图片存储路径
     */
    public static final String PHOTO_SYS_PATH = APP_ROOT_PATH + "/DCIM/Camera/";
    /**
     * 从Intent获取图片路径的KEY
     */
    public static final String KEY_PHOTO_PATH = "com.xbb.la.client.photo_path";

    public static String ROOT_PATH = Environment.getExternalStorageDirectory()
            .getAbsolutePath();
    /**
     * 更新apk的存放位置
     */
    public static String APK_PATH = ROOT_PATH + "/tutu/apk/xbx.apk";

    /**
     * Jpush处理广播action
     */
    public static String BROADCAST = "order_broadcast";
    /**
     * 分段加载number
     */
    public final static int PAGE_NUMBER = 10;


    /**
     *  userbean 用户基本信息
     *  uid
     *  online 是否上线
     *  deviceid
     *  lonAndlat 经纬度坐标用逗号组合
     *  appointOrder 预约订单num
     *  isDialog 接单对话框是否显示
     *  isJPush 是否能接收JPush
     *  loginOut 用户基本信息
     */
    public final static String USER_INFO = "userbean";
    public final static String UID = "uid";
    public final static String ONLINE = "online";
    public final static String DEVICEID = "deviceid";
    public final static String LON_LAT = "lonAndlat";
    public final static String APPOINT_ORDER = "appointOrder";
    public final static String IS_DIALOG = "isDialog";
    public final static String IS_JPUSH = "isJPush";
    public final static String LOGIN_OUT = "loginOut";
}
