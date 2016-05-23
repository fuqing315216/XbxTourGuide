package com.xbx.tourguide.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.xbx.tourguide.R;
import com.xbx.tourguide.api.LoginApi;
import com.xbx.tourguide.api.TaskFlag;
import com.xbx.tourguide.base.BaseActivity;
import com.xbx.tourguide.beans.VerifyBeans;
import com.xbx.tourguide.http.RequestParams;
import com.xbx.tourguide.jsonparse.UserInfoParse;
import com.xbx.tourguide.util.Constant;
import com.xbx.tourguide.util.JsonUtils;
import com.xbx.tourguide.util.SPUtils;
import com.xbx.tourguide.util.ToastUtils;
import com.xbx.tourguide.util.VerifyUtil;
import com.xbx.tourguide.view.RegisterStepView;
import com.xbx.tourguide.view.TitleBarView;


/**
 * Created by shuzhen on 2016/4/1.
 * <p>
 * 注册页
 */
public class RegisterActivity extends BaseActivity implements View.OnClickListener {

    private TextView zackTv;
    private Button verificationBtn;
    private EditText phoneEt, verifyEt, pwEt, repwEt, inviteEt;
    private int time = 60;
    private String code = "";

    private LoginApi loginApi = null;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case TaskFlag.REQUESTSUCCESS:
                    SPUtils.put(RegisterActivity.this, Constant.UID, UserInfoParse.getUid((String) msg.obj));
                    startActivity(new Intent(RegisterActivity.this, RegisterGuideTypeActivity.class));
                    break;

                case TaskFlag.PAGEREQUESTWO:
                    VerifyBeans result = JsonUtils.object((String) msg.obj, VerifyBeans.class);
                    code = result.getVierfy_code();
                    time = 60;
                    handler.postDelayed(runnable, 1000);
                    break;
            }
        }
    };

    private Runnable runnable = new Runnable() {
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
                verificationBtn.setTextColor(ContextCompat.getColor(RegisterActivity.this,R.color.head_text_color));
                verificationBtn.setBackgroundResource(R.drawable.bg_roundbtn_verification_style);
                verificationBtn.setText(getResources().getString(R.string.get_verification));
                verificationBtn.setEnabled(true);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        loginApi = new LoginApi(this, handler);
        initView();
    }

    private void initView() {
        TitleBarView titleBarView = (TitleBarView) findViewById(R.id.titlebar);
        titleBarView.setTitle(getString(R.string.register));
        titleBarView.setLeftImageButtonOnClickListener(new TitleBarView.OnLeftImageButtonClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        RegisterStepView stepView = (RegisterStepView) findViewById(R.id.step_view);
        stepView.setStep(1);

        zackTv = (TextView) findViewById(R.id.tv_register_zack);
        verificationBtn = (Button) findViewById(R.id.btn_verification);
        phoneEt = (EditText) findViewById(R.id.et_phone);
        verifyEt = (EditText) findViewById(R.id.et_verification);
        pwEt = (EditText) findViewById(R.id.et_pw);
        repwEt = (EditText) findViewById(R.id.et_confirm_pw);
        inviteEt = (EditText) findViewById(R.id.et_invite_code);

        findViewById(R.id.btn_register_next).setOnClickListener(this);
        zackTv.setOnClickListener(this);
        verificationBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_register_zack://注册协议

                break;

            case R.id.btn_verification://获取验证码
                String phone = phoneEt.getText().toString().trim();
                if (VerifyUtil.isNullOrEmpty(phone)) {
                    Toast.makeText(this, "请输入手机号", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!VerifyUtil.isTelPhoneNumber(phone)) {
                    Toast.makeText(this, "您输入的手机号码有误，请重新输入", Toast.LENGTH_SHORT).show();
                    return;
                }
                loginApi.getVerifyCode(phone, "0");
                break;
            case R.id.btn_register_next:
                String mobile = phoneEt.getText().toString().trim();
                String verify = verifyEt.getText().toString();
                String pw = pwEt.getText().toString();
                String repw = repwEt.getText().toString();

                if (VerifyUtil.isNullOrEmpty(mobile)) {
                    Toast.makeText(RegisterActivity.this, "请输入手机号码", Toast.LENGTH_SHORT).show();
                    return;
                } else if (!VerifyUtil.isTelPhoneNumber(mobile)) {
                    Toast.makeText(RegisterActivity.this, "手机号码格式有误，请重新输入", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (VerifyUtil.isNullOrEmpty(verify)) {
                    Toast.makeText(RegisterActivity.this, "请输入验证码", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (VerifyUtil.isNullOrEmpty(pw)) {
                    Toast.makeText(RegisterActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                } else if (pw.length() < 6 || pw.length() > 20) {
                    Toast.makeText(RegisterActivity.this, "密码长度为8-20位，请重新输入", Toast.LENGTH_SHORT).show();
                    return;
                } else if (!VerifyUtil.isPassWord(pw)) {
                    Toast.makeText(RegisterActivity.this, "密码输入格式有误，请重新输入", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (VerifyUtil.isNullOrEmpty(repw)) {
                    Toast.makeText(RegisterActivity.this, "请输入确认密码", Toast.LENGTH_SHORT).show();
                    return;
                } else if (!pw.equals(repw)) {
                    Toast.makeText(RegisterActivity.this, "两次密码输入不一致，请重新输入", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!code.equals(verify)) {
                    ToastUtils.showShort(RegisterActivity.this, "验证码错误");
                    return;
                }

                RequestParams params = new RequestParams();
                params.put("mobile", mobile);
                params.put("password", pw);
                params.put("repassword", repw);
                params.put("verify_code", verify);
                params.put("invite_code", inviteEt.getText().toString());

                loginApi.register(params);
                break;
            default:
                break;

        }
    }
}
