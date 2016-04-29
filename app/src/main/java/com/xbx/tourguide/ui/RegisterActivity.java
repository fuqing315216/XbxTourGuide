package com.xbx.tourguide.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.xbx.tourguide.R;
import com.xbx.tourguide.api.LoginApi;
import com.xbx.tourguide.api.TaskFlag;
import com.xbx.tourguide.base.BaseActivity;
import com.xbx.tourguide.beans.RegisterBeans;
import com.xbx.tourguide.beans.VerifyBeans;
import com.xbx.tourguide.http.HttpUrl;
import com.xbx.tourguide.http.IRequest;
import com.xbx.tourguide.http.RequestBackListener;
import com.xbx.tourguide.jsonparse.UtilParse;
import com.xbx.tourguide.util.JsonUtils;
import com.xbx.tourguide.util.ToastUtils;
import com.xbx.tourguide.util.VerifyUtil;


/**
 * Created by shuzhen on 2016/4/1.
 * <p>
 * 注册页
 */
public class RegisterActivity extends BaseActivity implements View.OnClickListener {

    private TextView zackTv, nextTv;
    private ImageButton returnIbtn;
    private Button verificationBtn;
    private EditText phoneEt, verifyEt, pwEt, repwEt;
    private int time = 60;
    private RegisterBeans beans = new RegisterBeans();
    private String code = "";

    private LoginApi loginApi = null;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case TaskFlag.REQUESTSUCCESS:
                    VerifyBeans result = JsonUtils.object((String) msg.obj, VerifyBeans.class);
                    code = result.getVierfy_code();
                    time = 60;
                    handler.postDelayed(runnable, 1000);
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
        setContentView(R.layout.activity_register);
        initView();
    }

    private void initView() {
        returnIbtn = (ImageButton) findViewById(R.id.ibtn_return);
        zackTv = (TextView) findViewById(R.id.tv_register_zack);
        nextTv = (TextView) findViewById(R.id.tv_next);
        verificationBtn = (Button) findViewById(R.id.btn_verification);
        phoneEt = (EditText) findViewById(R.id.et_phone);
        verifyEt = (EditText) findViewById(R.id.et_verification);
        pwEt = (EditText) findViewById(R.id.et_pw);
        repwEt = (EditText) findViewById(R.id.et_confirm_pw);

        returnIbtn.setOnClickListener(this);
        zackTv.setOnClickListener(this);
        nextTv.setOnClickListener(this);
        verificationBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibtn_return:
                finish();
                break;
            case R.id.tv_register_zack://注册协议

                break;

            case R.id.tv_next://下一步

                beans.setMobile(phoneEt.getText().toString().trim());
                String verify = verifyEt.getText().toString();
                String pw = pwEt.getText().toString();
                String repw = repwEt.getText().toString();

                if (VerifyUtil.isNullOrEmpty(verify)) {
                    Toast.makeText(this, "请输入验证码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (VerifyUtil.isNullOrEmpty(pw)) {
                    Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                } else if (pw.length() < 6 || pw.length() > 18) {
                    Toast.makeText(this, "密码长度为6-18位，请重新输入", Toast.LENGTH_SHORT).show();
                    return;
                } else if (!VerifyUtil.isPassWord(pw) && !VerifyUtil.isNumber(pw)
                        && !VerifyUtil.isLetter(pw)) {
                    Toast.makeText(this, "密码输入格式有误，请重新输入", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (VerifyUtil.isNullOrEmpty(repw)) {
                    Toast.makeText(this, "请输入确认密码", Toast.LENGTH_SHORT).show();
                    return;
                } else if (!pw.equals(repw)) {
                    Toast.makeText(this, "两次密码输入不一致，请重新输入", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (code.equals(verify)) {
                    beans.setVerify_code(verify);
                } else {
                    ToastUtils.showShort(this, "验证码错误");
                    return;
                }
                beans.setPassword(pw);
                beans.setRepassword(repw);

                Intent intent = new Intent(this, RegisterNextActivity.class);
                intent.putExtra("bean", beans);
                startActivity(intent);
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
                loginApi = new LoginApi(this,handler);
                loginApi.getVerifyCode(phone, "0");
//                getVerifyCode(phone);
                break;
            default:
                break;

        }
    }

    /**
     * 获取验证码
     */
    private void getVerifyCode(String phone) {

        String url = HttpUrl.GET_VERIFYCODE + "?mobile=" + phone + "&check_regester=" + 0;
        IRequest.get(this, url, "验证码发送中", new RequestBackListener(this) {
            @Override
            public void requestSuccess(String json) {
                if (UtilParse.getRequestCode(json) == 1) {
                    VerifyBeans result = JsonUtils.object(UtilParse.getRequestData(json), VerifyBeans.class);
                    code = result.getVierfy_code();
                    time = 60;
                    handler.postDelayed(runnable, 1000);
                }
            }
        });
    }
}
