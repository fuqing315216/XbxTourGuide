package com.xbx.tourguide.ui;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.xbx.tourguide.R;
import com.xbx.tourguide.base.BaseActivity;

/**
 * Created by shuzhen on 2016/3/30.
 *
 * 导游注册下一步
 */
public class RegisterNextActivity extends BaseActivity implements View.OnClickListener{

    private LinearLayout touristLlyt;
    private ImageButton returnIbtn;
    private TextView finishTv;
    private ImageView frontIv,otherIv,touristIv,personalIv;
    private int flag;
    private ImageLoader loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_next);
        loader=ImageLoader.getInstance();
        flag=getIntent().getIntExtra("flag",0);
        initView();
    }

    private void initView(){
        returnIbtn=(ImageButton)findViewById(R.id.ibtn_return);
        finishTv=(TextView)findViewById(R.id.tv_finish);
        touristLlyt=(LinearLayout)findViewById(R.id.llyt_tourist);
        frontIv=(ImageView)findViewById(R.id.iv_card_front);
        otherIv=(ImageView)findViewById(R.id.iv_card_other);
        touristIv=(ImageView)findViewById(R.id.iv_card_tourist);
        personalIv=(ImageView)findViewById(R.id.iv_card_personal);
        if (flag==0){
            touristLlyt.setVisibility(View.VISIBLE);
        }else{
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
        switch (v.getId()){
            case R.id.ibtn_return:
                finish();
                break;
            case R.id.tv_finish:

                break;

            case R.id.iv_card_front:
                Intent intentFront = new Intent(this, CameraDialogActivity.class);
                intentFront.putExtra("isPic", true);
                intentFront.putExtra("isCrop",false);
                startActivityForResult(intentFront, 102);
                break;
            case R.id.iv_card_other:
                Intent intentOther = new Intent(this, CameraDialogActivity.class);
                intentOther.putExtra("isPic", true);
                intentOther.putExtra("isCrop",false);
                startActivityForResult(intentOther, 103);
                break;
            case R.id.iv_card_tourist:
                Intent intentTourist = new Intent(this, CameraDialogActivity.class);
                intentTourist.putExtra("isPic", true);
                intentTourist.putExtra("isCrop",false);
                startActivityForResult(intentTourist, 104);
                break;
            case R.id.iv_card_personal:
                Intent intentPersonal = new Intent(this, CameraDialogActivity.class);
                intentPersonal.putExtra("isPic", true);
                intentPersonal.putExtra("isCrop",false);
                startActivityForResult(intentPersonal, 105);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==100){
            String url = data.getStringExtra("url");
            if (requestCode==102){
                loader.displayImage("file://" + url, frontIv);
            }else if(requestCode==103){
                loader.displayImage("file://" + url, otherIv);
            }else if(requestCode==104){
                loader.displayImage("file://" + url, touristIv);
            }else if(requestCode==105){
                loader.displayImage("file://" + url, personalIv);
            }
        }
    }
}
