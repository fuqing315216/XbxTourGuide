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
import com.xbx.tourguide.http.HttpUrl;
import com.xbx.tourguide.http.IRequest;
import com.xbx.tourguide.http.RequestBackListener;
import com.xbx.tourguide.http.RequestParams;
import com.xbx.tourguide.jsonparse.UserInfoParse;
import com.xbx.tourguide.jsonparse.UtilParse;
import com.xbx.tourguide.util.Cookie;
import com.xbx.tourguide.util.ToastUtils;
import com.xbx.tourguide.util.VerifyUtil;

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
        feedbackEt = (EditText) findViewById(R.id.et_feedback);
        findViewById(R.id.ibtn_return).setOnClickListener(this);
        findViewById(R.id.btn_feedback).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibtn_return:
                finish();
                break;
            case R.id.btn_feedback:
                if (VerifyUtil.isNullOrEmpty(feedbackEt.getText().toString())) {
                    ToastUtils.showShort(this, "反馈意见不能为空");
                    return;
                }
                settingApi = new SettingApi(FeedBackActivity.this,handler);
                settingApi.feedBack(UserInfoParse.getUid(Cookie.getUserInfo(this)),feedbackEt.getText().toString());
                break;
        }
    }
}
