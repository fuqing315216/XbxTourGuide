package com.xbx.tourguide.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.xbx.tourguide.R;
import com.xbx.tourguide.base.BaseStatusActivity;

/**
 * Created by shuzhen on 2016/3/31.
 *
 * 首页
 */
public class HomeActivity extends BaseStatusActivity implements View.OnClickListener{

    private RelativeLayout myOrderRlyt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initView();
    }

    private void initView(){
        myOrderRlyt=(RelativeLayout)findViewById(R.id.rlyt_myorder);

        myOrderRlyt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rlyt_myorder:
                startIntent(MyOrderListActivity.class,false);
                break;
            default:
                break;
        }
    }
}
