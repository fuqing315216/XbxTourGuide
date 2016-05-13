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
}
