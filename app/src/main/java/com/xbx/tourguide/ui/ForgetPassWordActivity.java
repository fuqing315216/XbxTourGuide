package com.xbx.tourguide.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.xbx.tourguide.R;
import com.xbx.tourguide.base.BaseActivity;

/**
 * Created by shuzhen on 2016/3/31.
 *
 * 忘记密码
 */
public class ForgetPassWordActivity extends BaseActivity implements View.OnClickListener{

    private ImageButton returnIbtn;
    private Button verificationBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_forgetpassword);

        initView();

    }

    private void initView(){
        returnIbtn=(ImageButton)findViewById(R.id.ibtn_return);
        verificationBtn=(Button)findViewById(R.id.btn_verification);

        returnIbtn.setOnClickListener(this);
        verificationBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ibtn_return:
                finish();
                break;
            case R.id.btn_verification:


                break;
            default:
                break;
        }
    }
}
