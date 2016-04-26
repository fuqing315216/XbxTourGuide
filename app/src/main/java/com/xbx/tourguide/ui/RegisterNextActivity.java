package com.xbx.tourguide.ui;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xbx.tourguide.R;
import com.xbx.tourguide.base.BaseActivity;
import com.xbx.tourguide.beans.RegisterBeans;
import com.xbx.tourguide.fragment.AccompanyRegisterFragment;
import com.xbx.tourguide.fragment.GuideRegisterFragment;
import com.xbx.tourguide.fragment.NativeRegisterFragment;
import com.xbx.tourguide.util.LogUtils;
import com.xbx.tourguide.util.VerifyUtil;

/**
 * Created by shuzhen on 2016/3/30.
 * <p/>
 * 注册页-下一步
 */
public class RegisterNextActivity extends BaseActivity implements View.OnClickListener {

    private ImageButton returnIbtn;
    private RelativeLayout guideRlyt, nativeRlyt, accompanyRlyt;
    private TextView guideTv, nativeTv, accompanyTv, nextTv;
    private View guideV, nativeV, accompanyV;
    public LinearLayout registerLlyt;
    private FragmentManager fm;
    private FragmentTransaction transaction;
    private int flag = 1;//1为导游，3为土著，2为随游
    private RegisterBeans beans;
    private GuideRegisterFragment guideRegisterFragment;
    private AccompanyRegisterFragment accompanyRegisterFragment;
    private NativeRegisterFragment nativeRegisterFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_next);
        beans = (RegisterBeans) getIntent().getSerializableExtra("bean");
        fm = getFragmentManager();
        transaction = fm.beginTransaction();

        initView();
    }


    private void initView() {
        registerLlyt = (LinearLayout) findViewById(R.id.llyt_register);
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

        guideRegisterFragment = new GuideRegisterFragment();
        transaction.add(R.id.fragment_register, guideRegisterFragment);
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
                guideRegisterFragment = new GuideRegisterFragment();
                flag = 1;

                guideTv.setTextColor(getResources().getColor(R.color.head_bg_color));
                nativeTv.setTextColor(getResources().getColor(R.color.gray_color));
                accompanyTv.setTextColor(getResources().getColor(R.color.gray_color));

                guideV.setVisibility(View.VISIBLE);
                nativeV.setVisibility(View.GONE);
                accompanyV.setVisibility(View.GONE);

                transaction.replace(R.id.fragment_register, guideRegisterFragment);
                transaction.commit();

                break;

            case R.id.rlyt_native:
                nativeRegisterFragment = new NativeRegisterFragment();
                flag = 3;
                guideTv.setTextColor(getResources().getColor(R.color.gray_color));
                nativeTv.setTextColor(getResources().getColor(R.color.head_bg_color));
                accompanyTv.setTextColor(getResources().getColor(R.color.gray_color));

                guideV.setVisibility(View.GONE);
                nativeV.setVisibility(View.VISIBLE);
                accompanyV.setVisibility(View.GONE);

                transaction.replace(R.id.fragment_register, nativeRegisterFragment);
                transaction.commit();
                break;

            case R.id.rlyt_accompany:
                accompanyRegisterFragment = new AccompanyRegisterFragment();
                flag = 2;
                guideTv.setTextColor(ContextCompat.getColor(this, R.color.gray_color));
                nativeTv.setTextColor(ContextCompat.getColor(this, R.color.gray_color));
                accompanyTv.setTextColor(ContextCompat.getColor(this, R.color.head_bg_color));

                guideV.setVisibility(View.GONE);
                nativeV.setVisibility(View.GONE);
                accompanyV.setVisibility(View.VISIBLE);

                transaction.replace(R.id.fragment_register, accompanyRegisterFragment);
                transaction.commit();
                break;

            case R.id.tv_next:

                if (flag == 1) {//导游注册
                    if (VerifyUtil.isNullOrEmpty(guideRegisterFragment.beans.getHead_image())) {
                        Toast.makeText(this, "请上传头像", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        beans.setHead_image(guideRegisterFragment.beans.getHead_image());
                    }

                    beans.setSex(guideRegisterFragment.beans.getSex());
                    beans.setGuide_type(guideRegisterFragment.beans.getGuide_type());
                    if (VerifyUtil.isNullOrEmpty(guideRegisterFragment.nameEt.getText().toString())) {
                        Toast.makeText(this, "请输入姓名", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        beans.setRealname(guideRegisterFragment.nameEt.getText().toString());
                    }

                    if (VerifyUtil.isNullOrEmpty(guideRegisterFragment.idEt.getText().toString())) {
                        Toast.makeText(this, "请输入身份证号", Toast.LENGTH_SHORT).show();
                        return;
                    } else if (!VerifyUtil.isCardID(guideRegisterFragment.idEt.getText().toString())) {
                        Toast.makeText(this, "身份证号输入有误，请重新输入", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        beans.setIdcard(guideRegisterFragment.idEt.getText().toString());
                    }

                    if (VerifyUtil.isNullOrEmpty(guideRegisterFragment.typeEt.getText().toString())) {
                        Toast.makeText(this, "请选择证件类型", Toast.LENGTH_SHORT).show();
                        return;
                    }else{
                        beans.setGuide_type(guideRegisterFragment.beans.getGuide_type());
                    }

                    if (VerifyUtil.isNullOrEmpty(guideRegisterFragment.locationTv.getText().toString())) {
                        Toast.makeText(this, "请选择所在地", Toast.LENGTH_SHORT).show();
                        return;
                    }else{
                        beans.setCity(guideRegisterFragment.beans.getCity());
                    }

                    if (VerifyUtil.isNullOrEmpty(guideRegisterFragment.guideIdEt.getText().toString())) {
                        Toast.makeText(this, "请输入导游证号", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        beans.setGuide_number(guideRegisterFragment.guideIdEt.getText().toString());
                    }

                } else if (flag == 2) {//随游注册
                    if (VerifyUtil.isNullOrEmpty(accompanyRegisterFragment.beans.getHead_image())) {
                        Toast.makeText(this, "请上传头像", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        beans.setHead_image(accompanyRegisterFragment.beans.getHead_image());
                    }
                    beans.setSex(accompanyRegisterFragment.beans.getSex());
                    if (VerifyUtil.isNullOrEmpty(accompanyRegisterFragment.nameEt.getText().toString())) {
                        Toast.makeText(this, "请输入姓名", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        beans.setRealname(accompanyRegisterFragment.nameEt.getText().toString());
                    }

                    if (VerifyUtil.isNullOrEmpty(accompanyRegisterFragment.idEt.getText().toString())) {
                        Toast.makeText(this, "请输入身份证号", Toast.LENGTH_SHORT).show();
                        return;
                    } else if (!VerifyUtil.isCardID(accompanyRegisterFragment.idEt.getText().toString())) {
                        Toast.makeText(this, "身份证号输入有误，请重新输入", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        beans.setIdcard(accompanyRegisterFragment.idEt.getText().toString());
                    }

                    if (VerifyUtil.isNullOrEmpty(accompanyRegisterFragment.locationTv.getText().toString())) {
                        Toast.makeText(this, "请选择所在地", Toast.LENGTH_SHORT).show();
                        return;
                    }else{
                        beans.setCity(accompanyRegisterFragment.beans.getCity());
                    }

                } else if (flag == 3) {//土著注册
                    if (VerifyUtil.isNullOrEmpty(nativeRegisterFragment.beans.getHead_image())) {
                        Toast.makeText(this, "请上传头像", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        beans.setHead_image(nativeRegisterFragment.beans.getHead_image());
                    }
                    beans.setSex(nativeRegisterFragment.beans.getSex());
                    if (VerifyUtil.isNullOrEmpty(nativeRegisterFragment.nameEt.getText().toString())) {
                        Toast.makeText(this, "请输入姓名", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        beans.setRealname(nativeRegisterFragment.nameEt.getText().toString());
                    }

                    if (VerifyUtil.isNullOrEmpty(nativeRegisterFragment.idEt.getText().toString())) {
                        Toast.makeText(this, "请输入身份证号", Toast.LENGTH_SHORT).show();
                        return;
                    } else if (!VerifyUtil.isCardID(nativeRegisterFragment.idEt.getText().toString())) {
                        Toast.makeText(this, "身份证号输入有误，请重新输入", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        beans.setIdcard(nativeRegisterFragment.idEt.getText().toString());
                    }

                    if (VerifyUtil.isNullOrEmpty(nativeRegisterFragment.locationTv.getText().toString())) {
                        Toast.makeText(this, "请选择所在地", Toast.LENGTH_SHORT).show();
                        return;
                    }else{
                        beans.setCity(nativeRegisterFragment.beans.getCity());
                    }

                }

                beans.setUser_type(flag);
                Intent intent = new Intent(this, RegisterFinalActivity.class);
                intent.putExtra("flag", flag);
                intent.putExtra("bean", beans);
                LogUtils.e(beans.toString());
                startActivity(intent);

                break;
            default:
                break;
        }
    }
}
