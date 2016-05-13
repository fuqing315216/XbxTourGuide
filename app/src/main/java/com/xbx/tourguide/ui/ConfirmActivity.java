package com.xbx.tourguide.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.xbx.tourguide.R;
import com.xbx.tourguide.base.BaseActivity;
import com.xbx.tourguide.util.ActivityManager;
import com.xbx.tourguide.util.VerifyUtil;

/**
 * Created by xbx on 2016/4/28.
 */
public class ConfirmActivity extends BaseActivity implements View.OnClickListener {
    private String title = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);
        title = getIntent().getStringExtra("title");
        initView();
    }

    private void initView() {
        if ("服务开始".equals(title)) {
            findViewById(R.id.llyt_confirm).setVisibility(View.GONE);
            findViewById(R.id.btn_confirm_ok2).setVisibility(View.VISIBLE);
        }

        ((TextView) findViewById(R.id.tv_confirm_title)).setText(title);
        ((TextView) findViewById(R.id.tv_confirm_content)).setText(getIntent().getStringExtra("content"));
        findViewById(R.id.btn_confirm_ok).setOnClickListener(this);
        findViewById(R.id.btn_confirm_ok2).setOnClickListener(this);
        findViewById(R.id.btn_confirm_cancel).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_confirm_ok:
            case R.id.btn_confirm_ok2:
                setResult(RESULT_OK, null);
                finish();
                break;
            case R.id.btn_confirm_cancel:
                finish();
                break;
            default:
                finish();
                break;
        }
    }
}
