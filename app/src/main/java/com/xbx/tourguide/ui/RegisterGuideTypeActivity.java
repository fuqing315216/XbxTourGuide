package com.xbx.tourguide.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.xbx.tourguide.R;
import com.xbx.tourguide.base.BaseActivity;
import com.xbx.tourguide.util.ActivityManager;
import com.xbx.tourguide.view.RegisterStepView;
import com.xbx.tourguide.view.TitleBarView;

/**
 * Created by xbx on 2016/5/6.
 */
public class RegisterGuideTypeActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_guide_type);
        ActivityManager.getInstance().pushOneActivity(this);
        TitleBarView titleBarView = (TitleBarView) findViewById(R.id.titlebar);
        titleBarView.setTitle(getString(R.string.register));
        titleBarView.setLeftImageButtonOnClickListener(new TitleBarView.OnLeftImageButtonClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        RegisterStepView stepView = (RegisterStepView) findViewById(R.id.step_view);
        stepView.setStep(2);
        findViewById(R.id.llyt_guide_type1).setOnClickListener(this);
        findViewById(R.id.llyt_guide_type2).setOnClickListener(this);
        findViewById(R.id.llyt_guide_type3).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llyt_guide_type1:
                startActivity(new Intent(RegisterGuideTypeActivity.this, RegisterGuideInfoActivity.class).putExtra("guide_type", 1));
                break;
            case R.id.llyt_guide_type2:
                startActivity(new Intent(RegisterGuideTypeActivity.this, RegisterGuideInfoActivity.class).putExtra("guide_type", 2));
                break;
            case R.id.llyt_guide_type3:
                startActivity(new Intent(RegisterGuideTypeActivity.this, RegisterGuideInfoActivity.class).putExtra("guide_type", 3));
                break;
        }
    }
}
