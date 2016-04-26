package com.xbx.tourguide.ui;

import android.os.Bundle;
import android.view.View;

import com.xbx.tourguide.R;
import com.xbx.tourguide.base.BaseActivity;

/**
 * 我的钱包
 * Created by shuzhen on 2016/4/15.
 */
public class MyWalletActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mywallet);
        initView();
    }

    private void initView() {
        findViewById(R.id.ibtn_return).setOnClickListener(this);
        findViewById(R.id.rlyt_balance).setOnClickListener(this);
        findViewById(R.id.rlyt_bank_card).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibtn_return:
                finish();
                break;
            case R.id.rlyt_balance:
                startIntent(BalanceActivity.class,false);
                break;

            case R.id.rlyt_bank_card:

                break;
            default:
                break;
        }
    }
}
