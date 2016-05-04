package com.xbx.tourguide.base;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by shuzhen on 2016/3/29.
 */
public class BaseActivity extends FragmentActivity {

    protected Intent intent = new Intent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

//    @Override
//    protected void onResume() {
//        // TODO Auto-generated method stub
//        super.onResume();
//        JPushInterface.onResume(this);
//    }
//
//    @Override
//    protected void onPause() {
//        // TODO Auto-generated method stub
//        super.onPause();
//        JPushInterface.onPause(this);
//    }

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
}
