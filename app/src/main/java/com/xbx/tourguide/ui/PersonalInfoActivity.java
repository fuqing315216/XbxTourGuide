package com.xbx.tourguide.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.xbx.tourguide.R;
import com.xbx.tourguide.api.SettingApi;
import com.xbx.tourguide.api.TaskFlag;
import com.xbx.tourguide.base.BaseActivity;
import com.xbx.tourguide.beans.CityBeans;
import com.xbx.tourguide.beans.TourGuideInfoBeans;
import com.xbx.tourguide.jsonparse.UserInfoParse;
import com.xbx.tourguide.util.Cookie;
import com.xbx.tourguide.util.JsonUtils;
import com.xbx.tourguide.view.CircleImageView;

import java.io.File;

/**
 * 个人信息
 * Created by shuzhen on 2016/4/15.
 */
public class PersonalInfoActivity extends BaseActivity implements View.OnClickListener {
    private TextView titleRightTv;
    private CircleImageView headPicCiv;
    private TextView nameTv, sexTv, birthdayTv, phoneTv, idTv, guideTv;
    private RelativeLayout locationRlyt;
    private TextView locationTv;
    private RadioButton chineseRb, englishRb, allRb;

    private ImageLoader loader;
    private TourGuideInfoBeans beans = null;
    private String userInfo = "";
    private int rightType = 1;//1-主页 2-确认修改

    private SettingApi settingApi = null;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case TaskFlag.REQUESTSUCCESS:
                    rightType = 1;
                    titleRightTv.setText(getString(R.string.personal_main));
//                    beans.setHead_image(JsonUtils.object((String) msg.obj, TourGuideInfoBeans.class).getHead_image());
//                    Cookie.putUserInfo(PersonalInfoActivity.this, JsonUtils.toJson(tourGuideBean));
                    UserInfoParse.putUserInfo(PersonalInfoActivity.this, userInfo, JsonUtils.object((String) msg.obj, TourGuideInfoBeans.class));
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personalinfo);
        loader = ImageLoader.getInstance();
        userInfo = Cookie.getUserInfo(this);
        beans = UserInfoParse.getUserInfo(userInfo);
        initView();
    }

    private void initView() {
        titleRightTv = (TextView) findViewById(R.id.tv_confirm_update);
        headPicCiv = (CircleImageView) findViewById(R.id.circle_head);
        nameTv = (TextView) findViewById(R.id.tv_name);
        sexTv = (TextView) findViewById(R.id.tv_sex);
        birthdayTv = (TextView) findViewById(R.id.tv_birthday);
        phoneTv = (TextView) findViewById(R.id.tv_phone);
        idTv = (TextView) findViewById(R.id.tv_id);
        guideTv = (TextView) findViewById(R.id.tv_guide);
        locationRlyt = (RelativeLayout) findViewById(R.id.rlyt_location);
        locationTv = (TextView) findViewById(R.id.tv_location);
        chineseRb = (RadioButton) findViewById(R.id.rb_chinese);
        englishRb = (RadioButton) findViewById(R.id.rb_english);
        allRb = (RadioButton) findViewById(R.id.rb_all);

        if (!"1".equals(UserInfoParse.getUserInfo(Cookie.getUserInfo(this)).getUser_type())) {
            findViewById(R.id.rlyt_personalinfo_guide).setVisibility(View.GONE);
        }

        initData();

        titleRightTv.setOnClickListener(this);
        headPicCiv.setOnClickListener(this);
        locationRlyt.setOnClickListener(this);
        chineseRb.setOnClickListener(this);
        englishRb.setOnClickListener(this);
        allRb.setOnClickListener(this);
        findViewById(R.id.ibtn_return).setOnClickListener(this);
    }

    private void initData() {
        loader.displayImage(beans.getHead_image(), headPicCiv);
        nameTv.setText(beans.getRealname());
        if (beans.getSex().equals("1")) {
            sexTv.setText("女");
        } else {
            sexTv.setText("男");
        }
        birthdayTv.setText(beans.getBirthday());
        phoneTv.setText(beans.getMobile());
        idTv.setText(beans.getIdcard());
        guideTv.setText(beans.getGuide_number());
        locationTv.setText(beans.getNow_address_name());
        setRb(beans.getServer_language());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(RESULT_OK, null);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.ibtn_return:
                setResult(RESULT_OK, null);
                finish();
                break;
            case R.id.tv_confirm_update:
                if (rightType == 2) {//确认修改
                    settingApi = new SettingApi(this, handler);
                    settingApi.updateInfo(UserInfoParse.getUid(userInfo), new File(beans.getHead_image())
                            , beans.getNow_address(), beans.getServer_language());
                } else {//导游个人主页
                    startIntent(SelfMainActivity.class, false);
                }
                break;
            case R.id.circle_head:
                Intent intent = new Intent(PersonalInfoActivity.this, CameraDialogActivity.class);
                intent.putExtra("isPic", true);
                intent.putExtra("isCrop", true);
                startActivityForResult(intent, 100);
                break;
            case R.id.rlyt_location:
                Intent cityIntent = new Intent(PersonalInfoActivity.this, SelectProvinceActivity.class);
                startActivityForResult(cityIntent, 200);
                break;
            case R.id.rb_chinese:
                setRb("0");
                setUpdate();
                break;
            case R.id.rb_english:
                setRb("1");
                setUpdate();
                break;
            case R.id.rb_all:
                setRb("2");
                setUpdate();
                break;
            default:
                break;
        }
    }

    private void setRb(String languageType) {
        if (languageType.equals("1")) {
            beans.setServer_language("1");
            chineseRb.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_gender_normal, 0, 0, 0);
            englishRb.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_gender_selected, 0, 0, 0);
            allRb.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_gender_normal, 0, 0, 0);
        } else if (languageType.equals("2")) {
            beans.setServer_language("2");
            chineseRb.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_gender_normal, 0, 0, 0);
            englishRb.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_gender_normal, 0, 0, 0);
            allRb.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_gender_selected, 0, 0, 0);
        } else {
            beans.setServer_language("0");
            chineseRb.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_gender_selected, 0, 0, 0);
            englishRb.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_gender_normal, 0, 0, 0);
            allRb.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_gender_normal, 0, 0, 0);
        }
    }

    private void setUpdate() {
        titleRightTv.setText(getString(R.string.confirm_update));
        rightType = 2;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == 100) {
            String url = data.getStringExtra("url");
            loader.displayImage("file://" + url, headPicCiv);
            beans.setHead_image(url);
            setUpdate();
        }

        if (requestCode == 200 && resultCode == 200) {
            CityBeans city = (CityBeans) data.getSerializableExtra("bean");
            locationTv.setText(city.getName());
            beans.setNow_address(city.getId());
            beans.setNow_address_name(city.getName());
            setUpdate();
        }
    }
}
