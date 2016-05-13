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
public class BalanceActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance);
        initView();
    }

    private void initView() {
        TitleBarView titleBarView = (TitleBarView) findViewById(R.id.titlebar);
        titleBarView.setTitle(getString(R.string.my_balance));
        titleBarView.setLeftImageButtonOnClickListener(new TitleBarView.OnLeftImageButtonClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        titleBarView.setTextRightTextView(getString(R.string.deposit));
        titleBarView.setRightTextViewOnClickListener(new TitleBarView.OnRightTextViewClickListener() {
            @Override
            public void onClick(View v) {
                startIntent(DepositActivity.class, false);
            }
        });

        findViewById(R.id.tv_balance_more).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_balance_more:
                startIntent(DepositActivity.class, false);
                break;
        }
    }
}
