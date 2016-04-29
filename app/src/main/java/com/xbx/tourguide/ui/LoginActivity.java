package com.xbx.tourguide.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.xbx.tourguide.R;
import com.xbx.tourguide.api.LoginApi;
import com.xbx.tourguide.api.TaskFlag;
import com.xbx.tourguide.base.BaseActivity;
import com.xbx.tourguide.beans.TourGuideBeans;
import com.xbx.tourguide.http.HttpUrl;
import com.xbx.tourguide.http.IRequest;
import com.xbx.tourguide.http.RequestBackListener;
import com.xbx.tourguide.http.RequestParams;
import com.xbx.tourguide.jsonparse.UserInfoParse;
import com.xbx.tourguide.jsonparse.UtilParse;
import com.xbx.tourguide.util.Cookie;
import com.xbx.tourguide.util.JsonUtils;
import com.xbx.tourguide.util.LogUtils;
import com.xbx.tourguide.util.ToastUtils;
import com.xbx.tourguide.util.VerifyUtil;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by shuzhen on 2016/3/29.
 * <p/>
 * 登录页
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {

    public static boolean isForeground = false;
    private TextView registerTv, forgetPwTv;
    private Button loginBtn;
    private EditText phoneEt, pwEt;
    private LoginApi loginApi = null;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case TaskFlag.REQUESTSUCCESS:
                    String data = (String) msg.obj;
//                    LogUtils.i("---REQUESTSUCCESS:" + data);
                    Cookie.putUserInfo(LoginActivity.this, data);
                    startIntent(HomeActivity.class, true);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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
        LogUtils.i("---mobile and token--" + mobile + "  " + token);
        if (!VerifyUtil.isNullOrEmpty(mobile) && !VerifyUtil.isNullOrEmpty(token)) {
            RequestParams params = new RequestParams();
            params.put("mobile", mobile);
            params.put("password", token);
            params.put("user_type", "1");
            params.put("push_id", JPushInterface.getRegistrationID(this));
            IRequest.post(this, HttpUrl.LOGIN, params, this.getString(R.string.loding), new RequestBackListener(this) {
                @Override
                public void requestSuccess(String json) {
                    LogUtils.i("---isFirstLogin:" + json);
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

        registerTv = (TextView) findViewById(R.id.tv_register);
        forgetPwTv = (TextView) findViewById(R.id.tv_forgetpass);
        loginBtn = (Button) findViewById(R.id.btn_login);
        phoneEt = (EditText) findViewById(R.id.et_phone);
        pwEt = (EditText) findViewById(R.id.et_code);

        registerTv.setOnClickListener(this);
        forgetPwTv.setOnClickListener(this);
        loginBtn.setOnClickListener(this);

        phoneEt.setText("15802808105");
        pwEt.setText("123456");

        String phone = Cookie.getPhone(this);
        if (!VerifyUtil.isNullOrEmpty(phone)) {
            phoneEt.setText(phone);
            phoneEt.setSelection(phone.length());
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.tv_register:
                startIntent(RegisterActivity.class, false);
                break;

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

        loginApi = new LoginApi(this, handler);
        loginApi.Login(phoneEt.getText().toString(), pwEt.getText().toString(), "1", JPushInterface.getRegistrationID(this));
    }


}
