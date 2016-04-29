package com.xbx.tourguide.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;

import com.xbx.tourguide.R;
import com.xbx.tourguide.db.DbOpenHelper;
import com.xbx.tourguide.db.OrderNumberDao;
import com.xbx.tourguide.util.Cookie;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by shuzhen on 2016/3/28.
 * <p/>
 * loading页
 */
public class MainActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Cookie.putAppointmentOrder(this, "");
        Cookie.putIsDialog(this, false);
        Cookie.putIsJPush(this, false);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, 3000);
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

}
