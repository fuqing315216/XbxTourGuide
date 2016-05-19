package com.xbx.tourguide.app;

import android.app.Application;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.NotificationCompat;
import android.telephony.TelephonyManager;

import com.baidu.mapapi.SDKInitializer;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.xbx.tourguide.R;
import com.xbx.tourguide.ui.HomeActivity;
import com.xbx.tourguide.util.Constant;
import com.xbx.tourguide.util.SPUtils;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by shuzhen on 2016/3/28.
 */
public class XbxTGApplication extends Application {

    private static XbxTGApplication instance;
    private static Context mContext;

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        SDKInitializer.initialize(this);
        instance = this;
        initImageLoader(instance);
        mContext = getApplicationContext();
        TelephonyManager manager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        SPUtils.put(mContext, Constant.DEVICEID, manager.getDeviceId());

        JPushInterface.setDebugMode(true);    // 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);            // 初始化 JPush
    }

    public Context getmContext() {
        return mContext;
    }

    public static XbxTGApplication getInstance() {
        return instance;
    }

    /**
     * 初始化ImageLoader
     *
     * @param context
     */
    public static void initImageLoader(Context context) {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
//                .showImageOnLoading(R.drawable.ic_headpic)
//                .showImageForEmptyUri(R.drawable.ic_headpic)
//                .showImageOnFail(R.drawable.ic_headpic)
                .cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565).build();

        // This configuration tuning is custom. You can tune every option, you
        // may tune some of them,
        // or you can create default configuration by
        // ImageLoaderConfiguration.createDefault(this);
        // method.
        ImageLoaderConfiguration.Builder config;
//        if (isWiFi) {
        config = new ImageLoaderConfiguration.Builder(context);
//        } else {// 非WiFi环境下不下载图片
//            config = new ImageLoaderConfiguration.Builder(context)
//                    .imageDownloader(new MyImageDownloader(context));
//        }

        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        config.writeDebugLogs(); // Remove for release app
        config.defaultDisplayImageOptions(options);
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config.build());

    }

    public void showNotification() {
        NotificationCompat.Builder notifyBuilder = new NotificationCompat.Builder(
                this);
        Intent intent = new Intent(this, HomeActivity.class);
        ComponentName componentName = new ComponentName("com.xbx.tourguide.ui",
                "com.xbx.tourguide.ui.HomeActivity");
        intent.setComponent(componentName);
        intent.setAction("Android.intent.action.MAIN");
        intent.addCategory("Android.intent.category.LAUNCHER");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent, 0);
//        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
//        stackBuilder.addParentStack(HomeActivity.class);
//        stackBuilder.addNextIntent(intent);
//        int requestCode = (int) SystemClock.uptimeMillis();
//        PendingIntent contentIntent = stackBuilder.getPendingIntent(
//                requestCode, PendingIntent.FLAG_UPDATE_CURRENT);

        notifyBuilder.setContentTitle("途途导由");
        notifyBuilder.setContentText("服务已开启");
        notifyBuilder.setTicker("途途导由");
        notifyBuilder.setContentIntent(contentIntent);
        notifyBuilder.setAutoCancel(false);
        notifyBuilder.setOngoing(true);
        notifyBuilder.setPriority(NotificationCompat.PRIORITY_MAX);

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mNotificationManager.notify(R.string.app_name, notifyBuilder.build());
    }
}
