package com.xbx.tourguide.ui;

import android.os.Bundle;
import android.view.View;

import com.xbx.tourguide.R;
import com.xbx.tourguide.base.BaseActivity;

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

        findViewById(R.id.ibtn_return).setOnClickListener(this);
        findViewById(R.id.tv_pay_details).setOnClickListener(this);
        findViewById(R.id.btn_deposit).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ibtn_return:
                finish();
                break;

            case R.id.tv_pay_details:
                startIntent(PayDetailActivity.class,false);
                break;

            case R.id.btn_deposit:
                startIntent(DepositActivity.class,false);
                break;
            default:
                break;
        }
    }
}
