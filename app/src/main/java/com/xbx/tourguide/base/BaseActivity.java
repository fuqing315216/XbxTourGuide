package com.xbx.tourguide.base;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.xbx.tourguide.R;
import com.xbx.tourguide.util.SystemBarTintManager;

/**
 * Created by shuzhen on 2016/3/29.
 */
public class BaseActivity extends Activity {

    protected Intent intent = new Intent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(R.color.head_bg_color);//通知栏所需颜色
        } else {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
        }
        super.onCreate(savedInstanceState);
    }

    /**
     * @param cls
     * @param isFinish true 关闭
     */
    protected void startIntent(Class<?> cls, boolean isFinish) {
        intent.setClass(this, cls);
        startActivity(intent);
        if (isFinish)
            finish();
    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }
}
