package com.xbx.tourguide.ui;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xbx.tourguide.R;
import com.xbx.tourguide.base.BaseActivity;
import com.xbx.tourguide.fragment.AccompanyRegisterFragment;
import com.xbx.tourguide.fragment.GuideRegisterFragment;
import com.xbx.tourguide.fragment.NativeRegisterFragment;

/**
 * Created by shuzhen on 2016/3/30.
 * <p/>
 * 注册页
 */
public class RegisterActivity extends BaseActivity implements View.OnClickListener {

    private ImageButton returnIbtn;
    private RelativeLayout guideRlyt, nativeRlyt, accompanyRlyt;
    private TextView guideTv, nativeTv, accompanyTv, nextTv;
    private View guideV, nativeV, accompanyV;
    public LinearLayout registerLlyt;
    private FragmentManager fm;
    private FragmentTransaction transaction;
    private int flag = 0;//0为导游，1为土著，2为随游


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        fm = getFragmentManager();
        transaction = fm.beginTransaction();

        initView();


    }


    private void initView() {
        registerLlyt=(LinearLayout)findViewById(R.id.llyt_register);
        returnIbtn = (ImageButton) findViewById(R.id.ibtn_return);

        guideRlyt = (RelativeLayout) findViewById(R.id.rlyt_guide);
        nativeRlyt = (RelativeLayout) findViewById(R.id.rlyt_native);
        accompanyRlyt = (RelativeLayout) findViewById(R.id.rlyt_accompany);

        guideTv = (TextView) findViewById(R.id.tv_guide);
        nativeTv = (TextView) findViewById(R.id.tv_native);
        accompanyTv = (TextView) findViewById(R.id.tv_accompany);

        guideV = findViewById(R.id.line_guide);
        nativeV = findViewById(R.id.line_native);
        accompanyV = findViewById(R.id.line_accompany);

        nextTv = (TextView) findViewById(R.id.tv_next);

        returnIbtn.setOnClickListener(this);
        guideRlyt.setOnClickListener(this);
        nativeRlyt.setOnClickListener(this);
        accompanyRlyt.setOnClickListener(this);
        nextTv.setOnClickListener(this);

        transaction.add(R.id.fragment_register, new GuideRegisterFragment());
        transaction.commit();
    }

    @Override
    public void onClick(View v) {
        fm = getFragmentManager();
        transaction = fm.beginTransaction();
        switch (v.getId()) {
            case R.id.ibtn_return:
                finish();
                break;

            case R.id.rlyt_guide:
                flag = 0;
                guideTv.setTextColor(getResources().getColor(R.color.head_bg_color));
                nativeTv.setTextColor(getResources().getColor(R.color.gray_color));
                accompanyTv.setTextColor(getResources().getColor(R.color.gray_color));

                guideV.setVisibility(View.VISIBLE);
                nativeV.setVisibility(View.GONE);
                accompanyV.setVisibility(View.GONE);

                transaction.replace(R.id.fragment_register, new GuideRegisterFragment());
                transaction.commit();

                break;

            case R.id.rlyt_native:
                flag = 1;
                guideTv.setTextColor(getResources().getColor(R.color.gray_color));
                nativeTv.setTextColor(getResources().getColor(R.color.head_bg_color));
                accompanyTv.setTextColor(getResources().getColor(R.color.gray_color));

                guideV.setVisibility(View.GONE);
                nativeV.setVisibility(View.VISIBLE);
                accompanyV.setVisibility(View.GONE);

                transaction.replace(R.id.fragment_register, new NativeRegisterFragment());
                transaction.commit();
                break;

            case R.id.rlyt_accompany:
                flag = 2;
                guideTv.setTextColor(getResources().getColor(R.color.gray_color));
                nativeTv.setTextColor(getResources().getColor(R.color.gray_color));
                accompanyTv.setTextColor(getResources().getColor(R.color.head_bg_color));

                guideV.setVisibility(View.GONE);
                nativeV.setVisibility(View.GONE);
                accompanyV.setVisibility(View.VISIBLE);

                transaction.replace(R.id.fragment_register, new AccompanyRegisterFragment());
                transaction.commit();
                break;

            case R.id.tv_next:

                Intent intent = new Intent(this, RegisterNextActivity.class);
                intent.putExtra("flag", flag);
                startActivity(intent);

                break;
            default:
                break;
        }
    }
}
