package com.xbx.tourguide.ui;

import android.os.Bundle;
import android.view.View;

import com.xbx.tourguide.R;
import com.xbx.tourguide.base.BaseActivity;
import com.xbx.tourguide.util.ActivityManager;
import com.xbx.tourguide.view.TitleBarView;

/**
 * 余额
 * Created by shuzhen on 2016/4/15.
 */
public class BalanceActivity extends BaseActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance);
        initView();
    }

    private void initView(){
        TitleBarView titleBarView = (TitleBarView) findViewById(R.id.titlebar);
        titleBarView.setTitle(getString(R.string.balance));
        titleBarView.setLeftImageButtonOnClickListener(new TitleBarView.OnLeftImageButtonClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        titleBarView.setTextRightTextView(getString(R.string.pay_details));
        titleBarView.setRightTextViewOnClickListener(new TitleBarView.OnRightTextViewClickListener() {
            @Override
            public void onClick(View v) {
                startIntent(PayDetailActivity.class,false);
            }
        });

        findViewById(R.id.btn_deposit).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_deposit:
                startIntent(DepositActivity.class,false);
                break;
        }
    }
}
