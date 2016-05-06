package com.xbx.tourguide.ui;

import android.os.Bundle;
import android.view.View;
import com.xbx.tourguide.R;
import com.xbx.tourguide.base.BaseActivity;
import com.xbx.tourguide.view.TitleBarView;

/**
 * Created by xbx on 2016/5/6.
 */
public class RegisterInfoOkActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_info_ok);
        TitleBarView titleBarView = (TitleBarView) findViewById(R.id.titlebar);
        titleBarView.setTitle(getString(R.string.register));
        titleBarView.setLeftImageButtonOnClickListener(new TitleBarView.OnLeftImageButtonClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.btn_register_info_ok_finish).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startIntent(LoginActivity.class, true);
            }
        });
    }
}
