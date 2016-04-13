package com.xbx.tourguide.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.xbx.tourguide.R;
import com.xbx.tourguide.base.BaseActivity;
import com.xbx.tourguide.beans.TourGuideBeans;
import com.xbx.tourguide.http.HttpUrl;
import com.xbx.tourguide.http.IRequest;
import com.xbx.tourguide.http.RequestJsonListener;
import com.xbx.tourguide.http.RequestParams;
import com.xbx.tourguide.util.Cookie;
import com.xbx.tourguide.util.JsonUtils;
import com.xbx.tourguide.util.VerifyUtil;

import java.util.List;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by shuzhen on 2016/3/29.
 * <p>
 * 登录页
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {

    public static boolean isForeground = false;
    private TextView registerTv, forgetPwTv;
    private Button loginBtn;
    private EditText phoneEt, pwEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
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
//                startIntent(HomeActivity.class, true);
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
        RequestParams params = new RequestParams();
        if (VerifyUtil.isNullOrEmpty(phoneEt.getText().toString())) {
            Toast.makeText(this, "请输入手机号码", Toast.LENGTH_SHORT).show();
            return;
        } else {
            params.put("mobile", phoneEt.getText().toString());
        }

        if (VerifyUtil.isNullOrEmpty(pwEt.getText().toString())) {
            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
            return;
        } else {
            params.put("password", pwEt.getText().toString());
        }
        params.put("user_type", "1");
        Log.i("log","registerId>>>>>>>>>>>>"+JPushInterface.getRegistrationID(this));
        params.put("push_id", JPushInterface.getRegistrationID(this));

        IRequest.post(this, HttpUrl.LOGIN, TourGuideBeans.class, params, "请稍候...", true, new RequestJsonListener<TourGuideBeans>() {
            @Override
            public void requestSuccess(TourGuideBeans result) {

                Cookie.putUserInfo(LoginActivity.this, JsonUtils.toJson(result));
                startIntent(HomeActivity.class, true);
            }

            @Override
            public void requestSuccess(List<TourGuideBeans> list) {

            }

            @Override
            public void requestError(VolleyError e) {

            }
        });
    }




}
