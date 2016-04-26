package com.xbx.tourguide.ui;

import android.os.Bundle;
import android.view.View;

import com.xbx.tourguide.R;
import com.xbx.tourguide.base.BaseActivity;

/**
 * 提现
 * Created by shuzhen on 2016/4/15.
 */
public class DepositActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit);
        initView();
    }

    private void initView(){
        findViewById(R.id.ibtn_return).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ibtn_return:
                finish();
                break;
            default:
                break;
        }
    }
}
