package com.xbx.tourguide.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.xbx.tourguide.R;
import com.xbx.tourguide.base.BaseActivity;
import com.xbx.tourguide.beans.VerifyBeans;
import com.xbx.tourguide.http.HttpUrl;
import com.xbx.tourguide.http.IRequest;
import com.xbx.tourguide.http.RequestJsonListener;
import com.xbx.tourguide.http.RequestListener;
import com.xbx.tourguide.http.RequestParams;
import com.xbx.tourguide.util.Cookie;
import com.xbx.tourguide.util.JsonUtils;
import com.xbx.tourguide.util.VerifyUtil;

import java.util.List;

/**
 * Created by shuzhen on 2016/3/31.
 * <p/>
 * 忘记密码
 */
public class ForgetPassWordActivity extends BaseActivity implements View.OnClickListener {

    private ImageButton returnIbtn;
    private Button verificationBtn,loginBtn;
    private EditText phoneEt, pwEt,codeEt;
    private int time = 60;
    private String code;

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (time > 0) {
                time--;
                verificationBtn.setTextColor(Color.WHITE);
                verificationBtn.setBackgroundResource(R.drawable.bg_verification_style);
                if (time > 9) {
                    verificationBtn.setText(getResources().getString(R.string.reset_verification) + "(" + time + "s)");
                } else {
                    verificationBtn.setText(getResources().getString(R.string.reset_verification) + "(0" + time + "s)");
                }
                verificationBtn.setEnabled(false);
                handler.postDelayed(this, 1000);
            } else {
                verificationBtn.setTextColor(getResources().getColor(R.color.head_text_color));
                verificationBtn.setBackgroundResource(R.drawable.bg_roundbtn_verification_style);
                verificationBtn.setText(getResources().getString(R.string.get_verification));
                verificationBtn.setEnabled(true);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_forgetpassword);

        initView();

    }

    private void initView() {
        returnIbtn = (ImageButton) findViewById(R.id.ibtn_return);
        verificationBtn = (Button) findViewById(R.id.btn_verification);
        phoneEt = (EditText) findViewById(R.id.et_phone);
        pwEt = (EditText) findViewById(R.id.et_code);
        loginBtn=(Button)findViewById(R.id.btn_login);
        codeEt=(EditText)findViewById(R.id.et_verification);

        returnIbtn.setOnClickListener(this);
        verificationBtn.setOnClickListener(this);
        loginBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibtn_return:
                finish();
                break;
            case R.id.btn_verification:
                String phone = phoneEt.getText().toString().trim();
                if (VerifyUtil.isNullOrEmpty(phone)) {
                    Toast.makeText(this, "请输入手机号", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!VerifyUtil.isTelPhoneNumber(phone)) {
                    Toast.makeText(this, "您输入的手机号码有误，请重新输入", Toast.LENGTH_SHORT).show();
                    return;
                }
                time = 60;
                handler.postDelayed(runnable, 1000);
                getVerifyCode(phone);
                break;

            case R.id.btn_login:
                updatePw();
                break;
            default:
                break;
        }
    }

    /**
     * 获取验证码
     */
    private void getVerifyCode(String phone) {

        String url = HttpUrl.GET_VERIFYCODE + "?mobile=" + phone + "&check_regester=" + 1;

        IRequest.get(this, url, VerifyBeans.class, "验证码已发送", true, new RequestJsonListener<VerifyBeans>() {
            @Override
            public void requestSuccess(VerifyBeans result) {
                Log.i("log", result.getVierfy_code().toString());
                code = result.getVierfy_code();
            }

            @Override
            public void requestSuccess(List<VerifyBeans> list) {

            }

            @Override
            public void requestError(VolleyError e) {

            }
        });
    }

    /**
     * 找回密码
     */
    private void updatePw() {
        RequestParams params = new RequestParams();
        params.put("mobile", phoneEt.getText().toString().trim());
        if (code.equals(codeEt.getText().toString())) {
            params.put("verify_code", code);
        } else {
            Toast.makeText(this, "验证码错误", Toast.LENGTH_SHORT).show();
        }

        if (VerifyUtil.isNullOrEmpty(pwEt.getText().toString())){
            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
        }else{
            params.put("password",pwEt.getText().toString());
        }

        IRequest.post(this, HttpUrl.UPDATE_PW, params, new RequestListener() {
            @Override
            public void requestSuccess(String json) {
                login();
            }

            @Override
            public void requestError(VolleyError e) {

            }
        });
    }

    /**
     * 登录
     */
    private void login() {
        RequestParams params = new RequestParams();
        params.put("mobile", phoneEt.getText().toString());
        params.put("password", pwEt.getText().toString());
        params.put("user_type", "1");

        IRequest.post(this, HttpUrl.LOGIN, String.class, params, "请稍候...", true, new RequestJsonListener<String>() {
            @Override
            public void requestSuccess(String result) {
                Cookie.putUserInfo(ForgetPassWordActivity.this, JsonUtils.toJson(result.getClass()));
                startIntent(HomeActivity.class, true);
            }

            @Override
            public void requestSuccess(List<String> list) {

            }

            @Override
            public void requestError(VolleyError e) {

            }
        });
    }
}
