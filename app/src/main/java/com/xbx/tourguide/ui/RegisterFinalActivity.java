package com.xbx.tourguide.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.xbx.tourguide.R;
import com.xbx.tourguide.base.BaseActivity;
import com.xbx.tourguide.beans.RegisterInfoBeans;
import com.xbx.tourguide.http.HttpUrl;
import com.xbx.tourguide.http.IRequest;
import com.xbx.tourguide.http.RequestBackListener;
import com.xbx.tourguide.http.RequestParams;
import com.xbx.tourguide.jsonparse.UtilParse;
import com.xbx.tourguide.util.ActivityManager;
import com.xbx.tourguide.util.Cookie;
import com.xbx.tourguide.util.LogUtils;
import com.xbx.tourguide.util.ToastUtils;
import com.xbx.tourguide.util.VerifyUtil;
import com.xbx.tourguide.view.RegisterStepView;
import com.xbx.tourguide.view.TitleBarView;

import java.io.File;

/**
 * Created by shuzhen on 2016/3/30.
 * <p/>
 * 导游注册-完成
 */
public class RegisterFinalActivity extends BaseActivity implements View.OnClickListener {

    private int guide_type;
    private ImageView frontIv, otherIv, touristIv, personalIv;
    private ImageLoader loader;
    private RegisterInfoBeans beans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_final);
        ActivityManager.getInstance().pushOneActivity(this);
        loader = ImageLoader.getInstance();
        guide_type = getIntent().getIntExtra("guide_type", 1);
        beans = (RegisterInfoBeans) getIntent().getSerializableExtra("bean");
        initView();
    }

    private void initView() {
        TitleBarView titleBarView = (TitleBarView) findViewById(R.id.titlebar);
        titleBarView.setTitle(getString(R.string.post_info));
        titleBarView.setLeftImageButtonOnClickListener(new TitleBarView.OnLeftImageButtonClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        RegisterStepView stepView = (RegisterStepView) findViewById(R.id.step_view);
        stepView.setStep(4);

        frontIv = (ImageView) findViewById(R.id.iv_card_front);
        otherIv = (ImageView) findViewById(R.id.iv_card_other);
        touristIv = (ImageView) findViewById(R.id.iv_card_tourist);
        personalIv = (ImageView) findViewById(R.id.iv_card_personal);

        if (guide_type != 1) {
            findViewById(R.id.rlyt_upload_tourist).setVisibility(View.GONE);
        }

        findViewById(R.id.btn_upload_card_front).setOnClickListener(this);
        findViewById(R.id.btn_upload_card_other).setOnClickListener(this);
        findViewById(R.id.btn_upload_tourist).setOnClickListener(this);
        findViewById(R.id.btn_upload_card_personal).setOnClickListener(this);
        findViewById(R.id.btn_register_finish).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_upload_card_front:
                Intent intentFront = new Intent(this, CameraDialogActivity.class);
                intentFront.putExtra("isPic", true);
                intentFront.putExtra("isCrop", false);
                startActivityForResult(intentFront, 102);
                break;
            case R.id.btn_upload_card_other:
                Intent intentOther = new Intent(this, CameraDialogActivity.class);
                intentOther.putExtra("isPic", true);
                intentOther.putExtra("isCrop", false);
                startActivityForResult(intentOther, 103);
                break;
            case R.id.btn_upload_tourist:
                Intent intentTourist = new Intent(this, CameraDialogActivity.class);
                intentTourist.putExtra("isPic", true);
                intentTourist.putExtra("isCrop", false);
                startActivityForResult(intentTourist, 104);
                break;
            case R.id.btn_upload_card_personal:
                Intent intentPersonal = new Intent(this, CameraDialogActivity.class);
                intentPersonal.putExtra("isPic", true);
                intentPersonal.putExtra("isCrop", false);
                startActivityForResult(intentPersonal, 105);
                break;
            case R.id.btn_register_finish:
                if (VerifyUtil.isNullOrEmpty(beans.getIdcard_front())) {
                    Toast.makeText(RegisterFinalActivity.this, "请上传身份证正面照片", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (VerifyUtil.isNullOrEmpty(beans.getIdcard_back())) {
                    Toast.makeText(RegisterFinalActivity.this, "请上传身份证背面照片", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (guide_type == 1) {
                    if (VerifyUtil.isNullOrEmpty(beans.getGuide_card())) {
                        Toast.makeText(RegisterFinalActivity.this, "请上传导游证照片", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                if (VerifyUtil.isNullOrEmpty(beans.getGuide_idcard())) {
                    Toast.makeText(RegisterFinalActivity.this, "请上传本人手持身份证照片", Toast.LENGTH_SHORT).show();
                    return;
                }

                registerGuideInfo();
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
            if (requestCode == 102) {
                loader.displayImage("file://" + url, frontIv);
//                ImageSize imageSize = new ImageSize(ScreenUtils.px2dip(this, 115), ScreenUtils.px2dip(this, 76), 0);
//                frontIv.setImageBitmap(loader.loadImageSync("file://" + url, imageSize));
                beans.setIdcard_front(url);
            } else if (requestCode == 103) {
                loader.displayImage("file://" + url, otherIv);
                beans.setIdcard_back(url);
            } else if (requestCode == 104) {
                loader.displayImage("file://" + url, touristIv);
                beans.setGuide_card(url);
            } else if (requestCode == 105) {
                loader.displayImage("file://" + url, personalIv);
                beans.setGuide_idcard(url);
            }
        }
    }

    /**
     * 注册
     */
    private void registerGuideInfo() {
        RequestParams params = new RequestParams();
        params.put("uid", Cookie.getUid(this));
        params.put("realname", beans.getRealname());
        params.put("sex", beans.getSex() + "");
        params.put("idcard", beans.getIdcard());
        params.put("guide_type", beans.getGuide_type() + "");
        params.put("guide_card_number", beans.getGuide_card_number() + "");
        params.put("guide_card_type", beans.getGuide_card_type() + "");
        params.put("head_image", new File(beans.getHead_image()));
        params.put("idcard_front", new File(beans.getIdcard_front()));
        params.put("idcard_back", new File(beans.getIdcard_back()));
        params.put("guide_card", new File(beans.getGuide_card()));
        params.put("guide_idcard", new File(beans.getGuide_idcard()));
        params.put("server_language", beans.getServer_language() + "");
        params.put("now_address", beans.getCity().getId());

        LogUtils.i("--------uid=" + Cookie.getUid(this)
                + "\nrealname=" + beans.getRealname()
                + "\nsex=" + beans.getSex()
                + "\nidcard=" + beans.getIdcard()
                + "\nguide_type=" + beans.getGuide_type()
                + "\nguide_card_number=" + beans.getGuide_card_number()
                + "\nguide_card_type=" + beans.getGuide_card_type()
                + "\nhead_image=" + beans.getHead_image()
                + "\nidcard_front=" + beans.getIdcard_front()
                + "\nidcard_back=" + beans.getIdcard_back()
                + "\nguide_card=" + beans.getGuide_card()
                + "\nguide_idcard=" + beans.getGuide_idcard()
                + "\nserver_language=" + beans.getServer_language()
                + "\nnow_address=" + beans.getCity().getId());

        IRequest.post(this, HttpUrl.REGISTER_GUIDE_INFO, params, getString(R.string.loding), new RequestBackListener(this) {
            @Override
            public void requestSuccess(String json) {
                LogUtils.i("-----registerGuideInfo:" + json);
                if (UtilParse.getRequestCode(json) != 0) {
                    ToastUtils.showShort(RegisterFinalActivity.this, "註冊成功");
                    startIntent(LoginActivity.class, true);
                } else {
                    ToastUtils.showShort(RegisterFinalActivity.this, UtilParse.getRequestMsg(json));
                }
            }
        });
    }
}
