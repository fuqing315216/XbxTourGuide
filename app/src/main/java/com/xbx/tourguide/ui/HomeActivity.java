package com.xbx.tourguide.ui;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xbx.tourguide.R;
import com.xbx.tourguide.app.XbxTGApplication;
import com.xbx.tourguide.base.BaseStatusActivity;

/**
 * Created by shuzhen on 2016/3/31.
 *
 * 首页
 */
public class HomeActivity extends BaseStatusActivity implements View.OnClickListener{

    private RelativeLayout myOrderRlyt;
    private TextView startTv,serviceTimeTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initView();
    }

    private void initView(){
        myOrderRlyt=(RelativeLayout)findViewById(R.id.rlyt_myorder);
        startTv=(TextView)findViewById(R.id.tv_start_order);
        serviceTimeTv=(TextView)findViewById(R.id.tv_service_time);

        myOrderRlyt.setOnClickListener(this);
        startTv.setOnClickListener(this);
        serviceTimeTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rlyt_myorder:
                startIntent(MyOrderListActivity.class,false);
                break;

            case R.id.tv_start_order:
               XbxTGApplication.getInstance().showNotification();
                break;

            case R.id.tv_service_time:
                startIntent(ServiceTimeActivity.class,false);
                break;
            default:
                break;
        }
    }

}
