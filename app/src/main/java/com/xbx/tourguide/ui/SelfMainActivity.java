package com.xbx.tourguide.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.xbx.tourguide.R;
import com.xbx.tourguide.api.SettingApi;
import com.xbx.tourguide.api.TaskFlag;
import com.xbx.tourguide.base.BaseActivity;
import com.xbx.tourguide.beans.GuideDetailBeans;
import com.xbx.tourguide.beans.TagBeans;
import com.xbx.tourguide.http.HttpUrl;
import com.xbx.tourguide.http.IRequest;
import com.xbx.tourguide.http.RequestBackListener;
import com.xbx.tourguide.http.RequestParams;
import com.xbx.tourguide.jsonparse.UserInfoParse;
import com.xbx.tourguide.jsonparse.UtilParse;
import com.xbx.tourguide.util.Cookie;
import com.xbx.tourguide.util.JsonUtils;
import com.xbx.tourguide.util.LogUtils;
import com.xbx.tourguide.util.ToastUtils;
import com.xbx.tourguide.util.Util;
import com.xbx.tourguide.util.VerifyUtil;
import com.xbx.tourguide.view.CircleImageView;
import com.xbx.tourguide.view.FlowLayout;

import java.util.List;

/**
 * Created by xbx on 2016/4/21.
 */
public class SelfMainActivity extends BaseActivity implements View.OnClickListener {
    private CircleImageView headCiv;
    private TextView nameTv, guideIdTv, scoreTv, pricehTv, pricedTv, userTypeTv, countTv;
    private RatingBar startRab;
    private FlowLayout tagFlyt;
    private EditText introduceEt, serviceEt;

    private SettingApi settingApi = null;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case TaskFlag.REQUESTSUCCESS:
                    GuideDetailBeans result = JsonUtils.object((String) msg.obj, GuideDetailBeans.class);
                    LogUtils.i("---getGuideDetail:" + result);
                    ImageLoader.getInstance().displayImage(result.getHead_image(), headCiv);
                    nameTv.setText(result.getRealname());
                    guideIdTv.setText(result.getGuide_number());

                    scoreTv.setText(Util.getStar(result.getStars()) + "分");
                    if ("0.0".equals(Util.getStar(result.getStars()))) {
                        startRab.setVisibility(View.GONE);
                    } else {
                        startRab.setRating(Util.getStar(result.getStars()) / 2);
                    }

                    pricehTv.setText(result.getGuide_instant_price());
                    pricedTv.setText(result.getGuide_reserve_price());
                    int userType = Integer.valueOf(UserInfoParse.getUserInfo(Cookie.getUserInfo(SelfMainActivity.this)).getUser_type());
                    switch (userType) {
                        case 1:
                            userTypeTv.setText("导游");
                            break;
                        case 2:
                            userTypeTv.setText("随游");
                            break;
                        case 3:
                            userTypeTv.setText("土著");
                            break;
                    }
                    countTv.setText(result.getServer_times() + "次");
                    //设置tag
                    if (result.getComment_tag_times() == null) {
                        tagFlyt.setVisibility(View.GONE);
                    } else {
                        List<TagBeans> tagBeanses = result.getComment_tag_times();
                        for (int i = 0; i < tagBeanses.size(); i++) {
                            tagFlyt.addView(Util.addTextView(SelfMainActivity.this, tagBeanses.get(i).getTag_name()));
                        }
                    }

                    introduceEt.setText(result.getSelf_introduce());
                    serviceEt.setText(result.getServer_introduce());
                    break;

                case TaskFlag.PAGEREQUESTWO://修改
                    ToastUtils.showShort(SelfMainActivity.this, (String) msg.obj);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self_main);
        settingApi = new SettingApi(this, handler);
        initViews();
    }

    private void initViews() {
        headCiv = (CircleImageView) findViewById(R.id.civ_self_main);
        nameTv = (TextView) findViewById(R.id.tv_self_main_name);
        guideIdTv = (TextView) findViewById(R.id.tv_self_main_id);
        scoreTv = (TextView) findViewById(R.id.tv_self_main_score);
        pricehTv = (TextView) findViewById(R.id.tv_self_main_price_h);
        pricedTv = (TextView) findViewById(R.id.tv_self_main_price_d);
        userTypeTv = (TextView) findViewById(R.id.tv_self_main_user_type);
        countTv = (TextView) findViewById(R.id.tv_self_main_count);
        startRab = (RatingBar) findViewById(R.id.rab_self_main);
        tagFlyt = (FlowLayout) findViewById(R.id.flyt_self_main_tag);
        introduceEt = (EditText) findViewById(R.id.et_self_main_introduce);
        serviceEt = (EditText) findViewById(R.id.et_self_main_service);

        findViewById(R.id.btn_self_main_back).setOnClickListener(this);
        findViewById(R.id.btn_self_main_confirm).setOnClickListener(this);

        settingApi.getGuideDetail(Cookie.getUid(this));
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_self_main_back:
                finish();
                break;
            case R.id.btn_self_main_confirm:
                if (VerifyUtil.isNullOrEmpty(introduceEt.getText().toString())) {
                    ToastUtils.showShort(this, "请填写自我介绍");
                    return;
                }

                if (VerifyUtil.isNullOrEmpty(serviceEt.getText().toString())) {
                    ToastUtils.showShort(this, "请填写服务标准");
                    return;
                }

                settingApi.updateGuideDetail(UserInfoParse.getUid(Cookie.getUserInfo(this)), introduceEt.getText().toString(), serviceEt.getText().toString());
                break;
        }
    }
}
