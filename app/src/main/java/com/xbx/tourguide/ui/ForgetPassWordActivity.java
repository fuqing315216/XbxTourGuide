package com.xbx.tourguide.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.xbx.tourguide.R;
import com.xbx.tourguide.api.LoginApi;
import com.xbx.tourguide.api.TaskFlag;
import com.xbx.tourguide.base.BaseActivity;
import com.xbx.tourguide.util.Cookie;
import com.xbx.tourguide.util.LogUtils;
import com.xbx.tourguide.util.ToastUtils;
import com.xbx.tourguide.util.VerifyUtil;

/**
 * Created by shuzhen on 2016/3/31.
 * <p>
 * 忘记密码
 */
public class ForgetPassWordActivity extends BaseActivity implements View.OnClickListener {

    private ImageButton returnIbtn;
    private Button verificationBtn, loginBtn;
    private EditText phoneEt, pwEt, codeEt;
    private int time = 60;
    private String code;

    private LoginApi loginApi = null;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case TaskFlag.REQUESTSUCCESS:
                    code = (String) msg.obj;
                    LogUtils.i("---runde:"+code);
                    time = 60;
                    handler.postDelayed(runnable, 1000);
                    break;
                case TaskFlag.PAGEREQUESTWO:
                    loginApi.forgetPwLogin(phoneEt.getText().toString(), pwEt.getText().toString(), "1");
                    break;
                case TaskFlag.PAGEREQUESTHREE:
                    Cookie.putUserInfo(ForgetPassWordActivity.this, (String) msg.obj);
                    startIntent(HomeActivity.class, true);
                    break;
            }
        }
    };

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
        loginApi = new LoginApi(this, handler);

    }

    private void initView() {
        returnIbtn = (ImageButton) findViewById(R.id.ibtn_return);
        verificationBtn = (Button) findViewById(R.id.btn_verification);
        phoneEt = (EditText) findViewById(R.id.et_phone);
        pwEt = (EditText) findViewById(R.id.et_code);
        loginBtn = (Button) findViewById(R.id.btn_login);
        codeEt = (EditText) findViewById(R.id.et_verification);

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
                    ToastUtils.showShort(this,"请输入手机号");
                    return;
                }
                if (!VerifyUtil.isTelPhoneNumber(phone)) {
                    ToastUtils.showShort(this,"您输入的手机号码有误，请重新输入");
                    return;
                }

                loginApi.getVerifyCode(phone, "1");
                break;

            case R.id.btn_login:
                updatePw();
                break;
            default:
                break;
        }
    }

    /**
     * 找回密码
     */
    private void updatePw() {

        if (VerifyUtil.isNullOrEmpty(phoneEt.getText().toString())) {
            ToastUtils.showShort(this,"请输入手机号码");
            return;
        }

        if (VerifyUtil.isNullOrEmpty(codeEt.getText().toString())) {
            ToastUtils.showShort(this,"请输入验证码");
            return;
        }

        LogUtils.i("---runde:"+code+"--"+codeEt.getText().toString());
//        if (!code.equals(codeEt.getText().toString())) {
//            ToastUtils.showShort(this,"验证码错误");
//            return;
//        }

        if (VerifyUtil.isNullOrEmpty(pwEt.getText().toString())) {
            ToastUtils.showShort(this,"请输入密码");
            return;
        }

        loginApi.updatePw(phoneEt.getText().toString().trim(),code, pwEt.getText().toString());

    }

}
