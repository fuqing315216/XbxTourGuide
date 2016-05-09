package com.xbx.tourguide.ui;

import android.os.Bundle;
import android.view.View;

import com.xbx.tourguide.R;
import com.xbx.tourguide.base.BaseActivity;
import com.xbx.tourguide.util.ActivityManager;
import com.xbx.tourguide.util.Cookie;
import com.xbx.tourguide.view.TitleBarView;

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
        TitleBarView titleBarView = (TitleBarView) findViewById(R.id.titlebar);
        titleBarView.setTitle(getString(R.string.setting));
        titleBarView.setLeftImageButtonOnClickListener(new TitleBarView.OnLeftImageButtonClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.rlyt_setting_user_info).setOnClickListener(this);
        findViewById(R.id.rlyt_setting_invite_code).setOnClickListener(this);
        findViewById(R.id.rlyt_about_us).setOnClickListener(this);
        findViewById(R.id.rlyt_feedback).setOnClickListener(this);
        findViewById(R.id.rlyt_user_guide).setOnClickListener(this);
        findViewById(R.id.tv_login_out).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rlyt_setting_user_info:
                startIntent(PersonalInfoActivity.class, false);
                break;
            case R.id.rlyt_setting_invite_code:

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
                startIntent(LoginActivity.class, true);
                break;
            default:
                break;
        }
    }
}
