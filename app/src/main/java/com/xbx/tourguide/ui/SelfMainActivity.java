package com.xbx.tourguide.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xbx.tourguide.R;
import com.xbx.tourguide.api.SettingApi;
import com.xbx.tourguide.api.TaskFlag;
import com.xbx.tourguide.base.BaseActivity;
import com.xbx.tourguide.beans.GuideDetailBeans;
import com.xbx.tourguide.beans.TagBeans;
import com.xbx.tourguide.util.Constant;
import com.xbx.tourguide.util.JsonUtils;
import com.xbx.tourguide.util.LogUtils;
import com.xbx.tourguide.util.SPUtils;
import com.xbx.tourguide.util.ToastUtils;
import com.xbx.tourguide.util.Util;
import com.xbx.tourguide.util.VerifyUtil;
import com.xbx.tourguide.view.FlowLayout;
import com.xbx.tourguide.view.TitleBarView;

import java.util.List;

/**
 * Created by xbx on 2016/4/21.
 */
public class SelfMainActivity extends BaseActivity implements View.OnClickListener {
    private RoundedImageView headRiv;
    private TextView nameTv, guideIdTv, scoreTv, priceTv, durationTv, countTv;
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
                    ImageLoader.getInstance().displayImage(result.getHead_image(), headRiv);
                    nameTv.setText(result.getRealname());
                    guideIdTv.setText(result.getGuide_card_number());

                    scoreTv.setText(Util.getStar(result.getStars()) + "分");
                    if ("0.0".equals(Util.getStar(result.getStars()))) {
                        startRab.setVisibility(View.GONE);
                    } else {
                        startRab.setRating(Util.getStar(result.getStars()) / 2);
                    }

                    priceTv.setText(result.getGuide_reserve_price());
                    countTv.setText(result.getServer_times());

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
                    ToastUtils.showShort(SelfMainActivity.this, "修改成功");
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
        TitleBarView titleBarView = (TitleBarView) findViewById(R.id.titlebar);
        titleBarView.setTitle(getString(R.string.self_title));
        titleBarView.setTitleColor(ContextCompat.getColor(this, R.color.head_bg_color));
        titleBarView.setLeftImageButton(R.drawable.ic_return2);
        titleBarView.setLeftImageButtonOnClickListener(new TitleBarView.OnLeftImageButtonClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        titleBarView.setLayout(ContextCompat.getColor(this, android.R.color.white));

        headRiv = (RoundedImageView) findViewById(R.id.riv_self_main);
        nameTv = (TextView) findViewById(R.id.tv_self_name);
        guideIdTv = (TextView) findViewById(R.id.tv_self_id);
        scoreTv = (TextView) findViewById(R.id.tv_self_score);
        priceTv = (TextView) findViewById(R.id.tv_self_price);
        durationTv = (TextView) findViewById(R.id.tv_self_duration);
        countTv = (TextView) findViewById(R.id.tv_self_count);
        startRab = (RatingBar) findViewById(R.id.rab_self);
        tagFlyt = (FlowLayout) findViewById(R.id.flyt_self_tag);
        introduceEt = (EditText) findViewById(R.id.et_self_introduce);
        serviceEt = (EditText) findViewById(R.id.et_self_service);

        findViewById(R.id.btn_self_save).setOnClickListener(this);

        settingApi.getGuideDetail((String) SPUtils.get(this, Constant.UID, ""));
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_self_save:
                if (VerifyUtil.isNullOrEmpty(introduceEt.getText().toString())) {
                    ToastUtils.showShort(this, "请填写自我介绍");
                    return;
                }

                if (VerifyUtil.isNullOrEmpty(serviceEt.getText().toString())) {
                    ToastUtils.showShort(this, "请填写服务标准");
                    return;
                }

                settingApi.updateGuideDetail((String) SPUtils.get(this, Constant.UID, ""), introduceEt.getText().toString(), serviceEt.getText().toString());
                break;
        }
    }
}
