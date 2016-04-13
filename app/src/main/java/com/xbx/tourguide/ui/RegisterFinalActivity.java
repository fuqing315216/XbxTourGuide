package com.xbx.tourguide.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xbx.tourguide.R;
import com.xbx.tourguide.base.BaseActivity;
import com.xbx.tourguide.beans.RegisterBeans;
import com.xbx.tourguide.beans.TourGuideBeans;
import com.xbx.tourguide.beans.TourGuideInfoBeans;
import com.xbx.tourguide.http.HttpUrl;
import com.xbx.tourguide.http.IRequest;
import com.xbx.tourguide.http.RequestJsonListener;
import com.xbx.tourguide.http.RequestParams;
import com.xbx.tourguide.util.Cookie;
import com.xbx.tourguide.util.JsonUtils;
import com.xbx.tourguide.util.VerifyUtil;

import java.io.File;
import java.util.List;

/**
 * Created by shuzhen on 2016/3/30.
 * <p>
 * 导游注册下一步
 */
public class RegisterFinalActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout touristLlyt;
    private ImageButton returnIbtn;
    private TextView finishTv;
    private ImageView frontIv, otherIv, touristIv, personalIv;
    private int flag;
    private ImageLoader loader;
    private RegisterBeans beans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_final);
        loader = ImageLoader.getInstance();
        flag = getIntent().getIntExtra("flag", 0);
        beans = (RegisterBeans) getIntent().getSerializableExtra("bean");
        initView();
    }

    private void initView() {
        returnIbtn = (ImageButton) findViewById(R.id.ibtn_return);
        finishTv = (TextView) findViewById(R.id.tv_finish);
        touristLlyt = (LinearLayout) findViewById(R.id.llyt_tourist);
        frontIv = (ImageView) findViewById(R.id.iv_card_front);
        otherIv = (ImageView) findViewById(R.id.iv_card_other);
        touristIv = (ImageView) findViewById(R.id.iv_card_tourist);
        personalIv = (ImageView) findViewById(R.id.iv_card_personal);
        if (flag == 1) {
            touristLlyt.setVisibility(View.VISIBLE);
        } else {
            touristLlyt.setVisibility(View.GONE);
        }

        returnIbtn.setOnClickListener(this);
        finishTv.setOnClickListener(this);
        frontIv.setOnClickListener(this);
        otherIv.setOnClickListener(this);
        touristIv.setOnClickListener(this);
        personalIv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibtn_return:
                finish();
                break;
            case R.id.tv_finish:
                if (VerifyUtil.isNullOrEmpty(beans.getIdcard_front())) {
                    Toast.makeText(this, "请上传身份证正面照片", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (VerifyUtil.isNullOrEmpty(beans.getIdcard_back())) {
                    Toast.makeText(this, "请上传身份证背面照片", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (flag == 1) {

                    if (VerifyUtil.isNullOrEmpty(beans.getGuide_card())) {
                        Toast.makeText(this, "请上传导游证照片", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (VerifyUtil.isNullOrEmpty(beans.getGuide_idcard())) {
                        Toast.makeText(this, "请上传本人手持身份证照片", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                register();

                break;

            case R.id.iv_card_front:
                Intent intentFront = new Intent(this, CameraDialogActivity.class);
                intentFront.putExtra("isPic", true);
                intentFront.putExtra("isCrop", false);
                startActivityForResult(intentFront, 102);
                break;
            case R.id.iv_card_other:
                Intent intentOther = new Intent(this, CameraDialogActivity.class);
                intentOther.putExtra("isPic", true);
                intentOther.putExtra("isCrop", false);
                startActivityForResult(intentOther, 103);
                break;
            case R.id.iv_card_tourist:
                Intent intentTourist = new Intent(this, CameraDialogActivity.class);
                intentTourist.putExtra("isPic", true);
                intentTourist.putExtra("isCrop", false);
                startActivityForResult(intentTourist, 104);
                break;
            case R.id.iv_card_personal:
                Intent intentPersonal = new Intent(this, CameraDialogActivity.class);
                intentPersonal.putExtra("isPic", true);
                intentPersonal.putExtra("isCrop", false);
                startActivityForResult(intentPersonal, 105);
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
    private void register() {
        RequestParams params = new RequestParams();
        params.put("mobile", beans.getMobile());
        params.put("password", beans.getPassword());
        params.put("repassword", beans.getRepassword());
        params.put("verify_code", beans.getVerify_code());
        params.put("realname", beans.getRealname());
        params.put("sex", beans.getSex() + "");
        params.put("idcard", beans.getIdcard());
        params.put("guide_type", beans.getGuide_type() + "");
        params.put("guide_number", beans.getGuide_number());
        params.put("user_type", beans.getUser_type() + "");
        params.put("head_image", new File(beans.getHead_image()));
        params.put("idcard_front", new File(beans.getIdcard_front()));
        params.put("idcard_back", new File(beans.getIdcard_back()));
        params.put("guide_card", new File(beans.getGuide_card()));
        params.put("guide_idcard", new File(beans.getGuide_idcard()));

        Log.i("log", "mobile=" + beans.getMobile() + "" +
                        "\npassword=" + beans.getPassword()
                        + "\nrepassword=" + beans.getRepassword()
                        + "\nverify_code=" + beans.getVerify_code()
                        + "\nrealname=" + beans.getRealname()
                        + "\nsex=" + beans.getSex()
                        + "\nidcard=" + beans.getIdcard()
                        + "\nguide_type=" + beans.getGuide_type()
                        + "\nguide_number=" + beans.getGuide_number()
                        + "\nuser_type=" + beans.getUser_type()
                        + "\nhead_image=" + beans.getHead_image()
                        + "\nidcard_front=" + beans.getIdcard_front()
                        + "\nidcard_back=" + beans.getIdcard_back()
                        + "\nguide_card=" + beans.getGuide_card()
                        + "\nguide_idcard=" + beans.getGuide_idcard()
        );

        IRequest.post(this, HttpUrl.REGISTER, String.class, params, "请稍后...", true, new RequestJsonListener<String>() {
            @Override
            public void requestSuccess(String result) {

                TourGuideInfoBeans infoBeans=new TourGuideInfoBeans();
                TourGuideBeans bean=new TourGuideBeans();
                infoBeans.setMobile(beans.getMobile());
                bean.setUser_info(infoBeans);

                Cookie.putUserInfo(RegisterFinalActivity.this, JsonUtils.toJson(bean));

                startIntent(LoginActivity.class, true);
            }

            @Override
            public void requestSuccess(List<String> list) {

            }

            @Override
            public void requestError(VolleyError e) {

            }
        });

    }
}
