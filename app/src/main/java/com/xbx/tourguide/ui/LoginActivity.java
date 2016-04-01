package com.xbx.tourguide.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.xbx.tourguide.R;
import com.xbx.tourguide.base.BaseActivity;

/**
 * Created by shuzhen on 2016/3/29.
 * <p/>
 * 登录页
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private TextView registerTv, forgetPwTv;
    private Button loginBtn;

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

        registerTv.setOnClickListener(this);
        forgetPwTv.setOnClickListener(this);
        loginBtn.setOnClickListener(this);
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
                startIntent(HomeActivity.class, false);
                break;
            default:
                break;
        }

    }
}
