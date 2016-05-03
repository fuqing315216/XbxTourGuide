package com.xbx.tourguide.ui;

import android.os.Bundle;
import android.view.View;

import com.xbx.tourguide.R;
import com.xbx.tourguide.base.BaseActivity;
import com.xbx.tourguide.util.Cookie;

/**
 * 设置
 * Created by shuzhen on 2016/4/15.
 */
public class SettingActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initView();
    }

    private void initView() {
        findViewById(R.id.ibtn_return).setOnClickListener(this);
        findViewById(R.id.rlyt_about_us).setOnClickListener(this);
        findViewById(R.id.rlyt_feedback).setOnClickListener(this);
        findViewById(R.id.rlyt_user_guide).setOnClickListener(this);
        findViewById(R.id.tv_login_out).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibtn_return:
                finish();
                break;

            case R.id.rlyt_about_us:
                startIntent(AboutUsActivity.class, false);
                break;

            case R.id.rlyt_feedback:
                startIntent(FeedBackActivity.class, false);
                break;

            case R.id.rlyt_user_guide:
                startIntent(UserGuideActivity.class, false);
                break;
            case R.id.tv_login_out:
                Cookie.putUserInfo(SettingActivity.this, "");
                Cookie.putLoginOut(SettingActivity.this, true);
                startIntent(LoginActivity.class, false);
                break;
            default:
                break;
        }
    }
}
