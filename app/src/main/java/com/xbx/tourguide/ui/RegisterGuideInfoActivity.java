package com.xbx.tourguide.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.makeramen.roundedimageview.RoundedImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xbx.tourguide.R;
import com.xbx.tourguide.base.BaseActivity;
import com.xbx.tourguide.beans.CityBeans;
import com.xbx.tourguide.beans.RegisterInfoBeans;
import com.xbx.tourguide.util.VerifyUtil;
import com.xbx.tourguide.view.RegisterStepView;
import com.xbx.tourguide.view.TitleBarView;

/**
 * Created by shuzhen on 2016/3/30.
 * <p/>
 * 注册页-下一步
 */
public class RegisterGuideInfoActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout touristLlyt;
    private RelativeLayout touristTypeRlyt;
    private RadioButton femaleRb, maleRb, chRb, enRb, allRb;
    private RoundedImageView headPicRiv;
    private TextView typeEt, locationTv;
    private EditText nameEt, idEt, guideIdEt;
    private ImageLoader loader;
    private int guide_type = 1;//1-导游；2-伴游；3-向导
    private RegisterInfoBeans beans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_guide_info);
        loader = ImageLoader.getInstance();
        beans = new RegisterInfoBeans();
        guide_type = getIntent().getIntExtra("guide_type", 1);
        beans.setGuide_type(guide_type);//设置guide_type
        initView();
    }


    private void initView() {
        TitleBarView titleBarView = (TitleBarView) findViewById(R.id.titlebar);
        titleBarView.setTitle(getString(R.string.fill_info));
        titleBarView.setLeftImageButtonOnClickListener(new TitleBarView.OnLeftImageButtonClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        RegisterStepView stepView = (RegisterStepView) findViewById(R.id.step_view);
        stepView.setStep(3);

        touristTypeRlyt = (RelativeLayout) findViewById(R.id.rlyt_tourist_type);
        touristLlyt = (LinearLayout) findViewById(R.id.llyt_tourist);

        femaleRb = (RadioButton) findViewById(R.id.rb_register_famale);
        maleRb = (RadioButton) findViewById(R.id.rb_register_male);
        headPicRiv = (RoundedImageView) findViewById(R.id.riv_register_headimg);
        nameEt = (EditText) findViewById(R.id.et_register_realname);
        idEt = (EditText) findViewById(R.id.et_register_card);
        typeEt = (TextView) findViewById(R.id.et_toursit_type);
        guideIdEt = (EditText) findViewById(R.id.et_tourist_num);
        chRb = (RadioButton) findViewById(R.id.rb_register_chinese);
        enRb = (RadioButton) findViewById(R.id.rb_register_english);
        allRb = (RadioButton) findViewById(R.id.rb_register_all);
        locationTv = (TextView) findViewById(R.id.et_register_location);

        if (guide_type != 1) {
            touristLlyt.setVisibility(View.GONE);
        }

        beans.setSex(1);//设置性别

        femaleRb.setOnClickListener(this);
        maleRb.setOnClickListener(this);
        headPicRiv.setOnClickListener(this);
        locationTv.setOnClickListener(this);
        chRb.setOnClickListener(this);
        enRb.setOnClickListener(this);
        allRb.setOnClickListener(this);
        touristTypeRlyt.setOnClickListener(this);

        findViewById(R.id.btn_register_guide_info_next).setOnClickListener(this);

        femaleRb.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_gender_selected, 0, 0, 0);
        maleRb.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_gender_normal, 0, 0, 0);
        chRb.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_gender_selected, 0, 0, 0);
        enRb.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_gender_normal, 0, 0, 0);
        allRb.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_gender_normal, 0, 0, 0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rb_register_famale:
                femaleRb.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_gender_selected, 0, 0, 0);
                maleRb.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_gender_normal, 0, 0, 0);
                beans.setSex(1);

                break;

            case R.id.rb_register_male:
                femaleRb.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_gender_normal, 0, 0, 0);
                maleRb.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_gender_selected, 0, 0, 0);
                beans.setSex(0);

                break;

            case R.id.riv_register_headimg:
                Intent intent = new Intent(RegisterGuideInfoActivity.this, CameraDialogActivity.class);
                intent.putExtra("isPic", true);
                intent.putExtra("isCrop", true);
                startActivityForResult(intent, 100);
                break;
            case R.id.rlyt_tourist_type:
                startActivityForResult(new Intent(RegisterGuideInfoActivity.this, CameraDialogActivity.class)
                        .putExtra("isPic", false), 101);
                break;
            case R.id.et_register_location:
                Intent cityIntent = new Intent(RegisterGuideInfoActivity.this, SelectProvinceActivity.class);
                startActivityForResult(cityIntent, 200);
                break;
            case R.id.rb_register_chinese:
                beans.setServer_language(0);
                chRb.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_gender_selected, 0, 0, 0);
                enRb.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_gender_normal, 0, 0, 0);
                allRb.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_gender_normal, 0, 0, 0);
                break;

            case R.id.rb_register_english:
                beans.setServer_language(1);
                chRb.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_gender_normal, 0, 0, 0);
                enRb.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_gender_selected, 0, 0, 0);
                allRb.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_gender_normal, 0, 0, 0);
                break;

            case R.id.rb_register_all:
                chRb.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_gender_normal, 0, 0, 0);
                enRb.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_gender_normal, 0, 0, 0);
                allRb.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_gender_selected, 0, 0, 0);
                break;
            case R.id.btn_register_guide_info_next:
                clickNext();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 100) {

            String url = data.getStringExtra("url");
            loader.displayImage("file://" + url, headPicRiv);
            beans.setHead_image(url);

        } else if (resultCode == 101) {
            String type = data.getStringExtra("type");
            typeEt.setText(type);
            typeEt.setTextColor(ContextCompat.getColor(this, R.color.text_color));
            if (getResources().getString(R.string.guide).equals(type)) {
                beans.setGuide_card_type(0);
            } else if (getResources().getString(R.string.leader).equals(type)) {
                beans.setGuide_card_type(1);
            }
        }

        if (requestCode == 200 && resultCode == 200) {
            CityBeans city = (CityBeans) data.getSerializableExtra("bean");
            locationTv.setText(city.getName());
            beans.setCity(city);
        }
    }

    private void clickNext() {

        if (VerifyUtil.isNullOrEmpty(beans.getHead_image())) {
            Toast.makeText(this, "请上传头像", Toast.LENGTH_SHORT).show();
            return;
        }
        String name = nameEt.getText().toString();
        if (VerifyUtil.isNullOrEmpty(name)) {
            Toast.makeText(this, "请输入姓名", Toast.LENGTH_SHORT).show();
            return;
        } else if (!VerifyUtil.checkNameChese(name)
                || name.length() < 2) {
            Toast.makeText(this, "请输入正确的中文姓名", Toast.LENGTH_SHORT).show();
            return;
        } else {
            beans.setRealname(name);
        }

        String idCard = idEt.getText().toString();
        if (VerifyUtil.isNullOrEmpty(idCard)) {
            Toast.makeText(this, "请输入身份证号", Toast.LENGTH_SHORT).show();
            return;
        } else if (!VerifyUtil.isCardID(idCard)) {
            Toast.makeText(this, "身份证号输入有误，请重新输入", Toast.LENGTH_SHORT).show();
            return;
        } else {
            beans.setIdcard(idCard);
        }

        if (guide_type == 1) {
            if (VerifyUtil.isNullOrEmpty(typeEt.getText().toString())) {
                Toast.makeText(this, "请选择证件类型", Toast.LENGTH_SHORT).show();
                return;
            }

            if (VerifyUtil.isNullOrEmpty(guideIdEt.getText().toString())) {
                Toast.makeText(this, "请输入导游证号", Toast.LENGTH_SHORT).show();
                return;
            } else {
                beans.setGuide_card_number(guideIdEt.getText().toString());
            }
        }


        if (VerifyUtil.isNullOrEmpty(locationTv.getText().toString())) {
            Toast.makeText(this, "请选择所在地", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(this, RegisterFinalActivity.class);
        intent.putExtra("guide_type", guide_type);
        intent.putExtra("bean", beans);
        startActivity(intent);
    }

}
