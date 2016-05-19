package com.xbx.tourguide.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xbx.tourguide.R;
import com.xbx.tourguide.api.SettingApi;
import com.xbx.tourguide.api.TaskFlag;
import com.xbx.tourguide.base.BaseActivity;
import com.xbx.tourguide.beans.CityBeans;
import com.xbx.tourguide.beans.TourGuideInfoBeans;
import com.xbx.tourguide.jsonparse.UserInfoParse;
import com.xbx.tourguide.util.Constant;
import com.xbx.tourguide.util.JsonUtils;
import com.xbx.tourguide.util.SPUtils;
import com.xbx.tourguide.util.ToastUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 个人信息
 * Created by shuzhen on 2016/4/15.
 */
public class PersonalInfoActivity extends BaseActivity implements View.OnClickListener {
    private TextView titleRightTv;
    private RoundedImageView headPicRiv;
    private TextView nameTv, sexTv, birthdayTv, phoneTv, idTv, guideTv;
    private TextView locationTv, languageTv;

    private ImageLoader loader;
    private TourGuideInfoBeans beans = null;
    private String userInfo = "";
    private String cityId = "";
    private int rightType = 1;//1-主页 2-确认修改

    private SettingApi settingApi = null;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case TaskFlag.REQUESTSUCCESS:
//                    rightType = 1;
//                    titleRightTv.setText(getString(R.string.personal_main));
                    UserInfoParse.putUserInfo(PersonalInfoActivity.this, userInfo, JsonUtils.object((String) msg.obj, TourGuideInfoBeans.class));
                    ToastUtils.showShort(PersonalInfoActivity.this, "修改成功");
                    finish();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personalinfo);

        loader = ImageLoader.getInstance();
        userInfo = (String) SPUtils.get(this, Constant.USER_INFO, "");
        beans = UserInfoParse.getUserInfo(userInfo);
        initView();
    }

    private void initView() {
        titleRightTv = (TextView) findViewById(R.id.tv_confirm_update);
        headPicRiv = (RoundedImageView) findViewById(R.id.riv_info_headpic);
        nameTv = (TextView) findViewById(R.id.tv_name);
        sexTv = (TextView) findViewById(R.id.tv_sex);
        birthdayTv = (TextView) findViewById(R.id.tv_birthday);
        phoneTv = (TextView) findViewById(R.id.tv_phone);
        idTv = (TextView) findViewById(R.id.tv_id);
        guideTv = (TextView) findViewById(R.id.tv_guide);
        locationTv = (TextView) findViewById(R.id.tv_persioninfo_location);
        languageTv = (TextView) findViewById(R.id.tv_persioninfo_language);

        if (!"1".equals(UserInfoParse.getUserInfo(userInfo).getGuide_type())) {
            findViewById(R.id.rlyt_personalinfo_guide).setVisibility(View.GONE);
        }

        initData();

        titleRightTv.setOnClickListener(this);
        findViewById(R.id.rlyt_info_headpic).setOnClickListener(this);
        findViewById(R.id.rlyt_persioninfo_location).setOnClickListener(this);
        findViewById(R.id.rlyt_persioninfo_language).setOnClickListener(this);
        findViewById(R.id.ibtn_return).setOnClickListener(this);
    }

    private void initData() {
        loader.displayImage(beans.getHead_image(), headPicRiv);
        nameTv.setText(beans.getRealname());
        if (beans.getSex().equals("1")) {
            sexTv.setText("女");
        } else {
            sexTv.setText("男");
        }
        birthdayTv.setText(beans.getBirthday());
        phoneTv.setText(beans.getMobile());
        idTv.setText(beans.getIdcard());
        guideTv.setText(beans.getGuide_card_number());
        locationTv.setText(beans.getNow_address_name());
        languageTv.setText(getLanguage(beans.getServer_language()));
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
                    settingApi.updateInfo((String) SPUtils.get(this, Constant.UID, ""), new File(beans.getHead_image()),
                            beans.getBirthday(), cityId, beans.getServer_language());
                } else {//导游个人主页
                    startIntent(SelfMainActivity.class, false);
                }
                break;
            case R.id.rlyt_info_headpic:
                Intent intent = new Intent(PersonalInfoActivity.this, CameraDialogActivity.class);
                intent.putExtra("isPic", true);
                intent.putExtra("isCrop", true);
                startActivityForResult(intent, 100);
                break;
            case R.id.rlyt_persioninfo_location:
                Intent cityIntent = new Intent(PersonalInfoActivity.this, SelectProvinceActivity.class);
                startActivityForResult(cityIntent, 200);
                break;
            case R.id.rlyt_persioninfo_language:
                Intent languageIntent = new Intent(PersonalInfoActivity.this, WheelViewDialogActivity.class);
                List<String> list = new ArrayList<>();
                list.add(getString(R.string.chinese));
                list.add(getString(R.string.english));
                list.add(getString(R.string.all));
                languageIntent.putStringArrayListExtra("wheelViewList", (ArrayList<String>) list);
                startActivityForResult(languageIntent, 300);
                break;
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
            loader.displayImage("file://" + url, headPicRiv);
            beans.setHead_image(url);
            setUpdate();
        }

        if (requestCode == 200 && resultCode == 200) {
            CityBeans city = (CityBeans) data.getSerializableExtra("bean");
            locationTv.setText(city.getName());
            beans.setNow_address_name(city.getName());
            cityId = city.getId();
            setUpdate();
        }

        if (requestCode == 300) {
            if (data != null) {
                String serverLanguage = data.getStringExtra("serverLanguage");
                beans.setServer_language(serverLanguage);
                languageTv.setText(getLanguage(serverLanguage));
                setUpdate();
            }
        }
    }

    private String getLanguage(String serverLanguage) {
        if ("1".equals(serverLanguage)) {
            return getString(R.string.english);
        }
        if ("2".equals(serverLanguage)) {
            return getString(R.string.all);
        }
        return getString(R.string.chinese);
    }
}
