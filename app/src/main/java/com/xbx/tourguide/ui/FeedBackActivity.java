package com.xbx.tourguide.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;

import com.xbx.tourguide.R;
import com.xbx.tourguide.api.SettingApi;
import com.xbx.tourguide.api.TaskFlag;
import com.xbx.tourguide.base.BaseActivity;
import com.xbx.tourguide.util.Constant;
import com.xbx.tourguide.util.SPUtils;
import com.xbx.tourguide.util.ToastUtils;
import com.xbx.tourguide.util.VerifyUtil;
import com.xbx.tourguide.view.TitleBarView;

/**
 * 意见反馈
 * Created by shuzhen on 2016/4/15.
 */
public class FeedBackActivity extends BaseActivity implements View.OnClickListener {
    private EditText feedbackEt;
    private SettingApi settingApi = null;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case TaskFlag.REQUESTSUCCESS:
                    ToastUtils.showShort(FeedBackActivity.this, (String) msg.obj);
                    FeedBackActivity.this.finish();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        initView();
    }

    private void initView() {
        TitleBarView titleBarView = (TitleBarView) findViewById(R.id.titlebar);
        titleBarView.setTitle(getString(R.string.feedback));
        titleBarView.setLeftImageButtonOnClickListener(new TitleBarView.OnLeftImageButtonClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        feedbackEt = (EditText) findViewById(R.id.et_feedback);
        findViewById(R.id.btn_feedback).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_feedback:
                if (VerifyUtil.isNullOrEmpty(feedbackEt.getText().toString())) {
                    ToastUtils.showShort(this, "反馈意见不能为空");
                    return;
                }
                settingApi = new SettingApi(FeedBackActivity.this, handler);
                settingApi.feedBack((String) SPUtils.get(this, Constant.UID, ""),feedbackEt.getText().toString());
                break;
        }
    }
}
