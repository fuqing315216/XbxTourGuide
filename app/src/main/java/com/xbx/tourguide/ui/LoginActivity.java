package com.xbx.tourguide.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.xbx.tourguide.R;
import com.xbx.tourguide.api.LoginApi;
import com.xbx.tourguide.api.TaskFlag;
import com.xbx.tourguide.base.BaseActivity;
import com.xbx.tourguide.http.HttpUrl;
import com.xbx.tourguide.http.IRequest;
import com.xbx.tourguide.http.RequestBackListener;
import com.xbx.tourguide.http.RequestParams;
import com.xbx.tourguide.jsonparse.UserInfoParse;
import com.xbx.tourguide.jsonparse.UtilParse;
import com.xbx.tourguide.util.ActivityManager;
import com.xbx.tourguide.util.Cookie;
import com.xbx.tourguide.util.LogUtils;
import com.xbx.tourguide.util.ToastUtils;
import com.xbx.tourguide.util.VerifyUtil;
import com.xbx.tourguide.view.TitleBarView;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by shuzhen on 2016/3/29.
 * <p/>
 * 登录页
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {

    public static boolean isForeground = false;
    private TextView forgetPwTv;
    private Button loginBtn;
    private EditText phoneEt, pwEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ActivityManager.getInstance().pushOneActivity(this);
        initView();
//        Cookie.putUserInfo(this, "");
        isFirstLogin();
    }

    private void isFirstLogin() {
        if (Cookie.getUserInfo(this) == null) {
            return;
        }
        String mobile = UserInfoParse.getMobile(Cookie.getUserInfo(this));
        String token = UserInfoParse.getLogToken(Cookie.getUserInfo(this));
        if (!VerifyUtil.isNullOrEmpty(mobile) && !VerifyUtil.isNullOrEmpty(token)) {
            RequestParams params = new RequestParams();
            params.put("mobile", mobile);
            params.put("password", token);
            params.put("push_id", JPushInterface.getRegistrationID(this));
            IRequest.post(this, HttpUrl.LOGIN, params, this.getString(R.string.loding), new RequestBackListener(this) {
                @Override
                public void requestSuccess(String json) {
                    if (UtilParse.getRequestCode(json) == 0) {
                        ToastUtils.showShort(LoginActivity.this, "自动登录已过期，请重新登录");
                    } else if (UtilParse.getRequestCode(json) == 1) {
                        ToastUtils.showShort(LoginActivity.this, "自动登录成功");
                        Cookie.putUserInfo(LoginActivity.this, UtilParse.getRequestData(json));
                        startIntent(HomeActivity.class, true);
                    }
                }
            });
        }
    }

    private void initView() {
        TitleBarView titleBarView = (TitleBarView) findViewById(R.id.titlebar);
        titleBarView.hideLeftImageButton();
        titleBarView.setTitle(getString(R.string.phone_login));
        titleBarView.setTextRightTextView(getString(R.string.register));
        titleBarView.setRightTextViewOnClickListener(new TitleBarView.OnRightTextViewClickListener() {
            @Override
            public void onClick(View v) {
                startIntent(RegisterActivity.class, false);
            }
        });

        forgetPwTv = (TextView) findViewById(R.id.tv_forgetpass);
        loginBtn = (Button) findViewById(R.id.btn_login);
        phoneEt = (EditText) findViewById(R.id.et_phone);
        pwEt = (EditText) findViewById(R.id.et_code);

        forgetPwTv.setOnClickListener(this);
        loginBtn.setOnClickListener(this);

        phoneEt.setText("13982932283");
        pwEt.setText("XBX123456");
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.tv_forgetpass:
                startIntent(ForgetPassWordActivity.class, false);
                break;

            case R.id.btn_login:
                login();
                break;
            default:
                break;
        }

    }

    /**
     * 登录
     */
    private void login() {

        if (VerifyUtil.isNullOrEmpty(phoneEt.getText().toString())) {
            ToastUtils.showShort(this, "请输入手机号码");
            return;
        }

        if (VerifyUtil.isNullOrEmpty(pwEt.getText().toString())) {
            ToastUtils.showShort(this, "请输入密码");
            return;
        }

        if (VerifyUtil.isNullOrEmpty(JPushInterface.getRegistrationID(this))) {
            ToastUtils.showShort(this, "登录失败，请稍候重试");
        }

        RequestParams params = new RequestParams();
        params.put("mobile", phoneEt.getText().toString());
        params.put("password", pwEt.getText().toString());
        params.put("push_id", JPushInterface.getRegistrationID(this));
        IRequest.post(this, HttpUrl.LOGIN, params, this.getString(R.string.loding), new RequestBackListener(this) {
            @Override
            public void requestSuccess(String json) {
                LogUtils.i("------login-json:" + json);
                if (UtilParse.getRequestCode(json) == 2) {
                    startIntent(RegisterGuideTypeActivity.class, true);
                } else if (UtilParse.getRequestCode(json) == 1) {
                    Cookie.putUserInfo(LoginActivity.this, UtilParse.getRequestData(json));
                    startIntent(HomeActivity.class, true);
                } else {
                    ToastUtils.showShort(LoginActivity.this, UtilParse.getRequestMsg(json));
                }
            }
        });
    }

    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                ToastUtils.showShort(this, "再按一次退出程序");
                exitTime = System.currentTimeMillis();
            } else {
                ActivityManager.getInstance().finishAllActivity();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
