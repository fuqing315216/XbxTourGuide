package com.xbx.tourguide.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.xbx.tourguide.R;
import com.xbx.tourguide.beans.CityBeans;
import com.xbx.tourguide.beans.RegisterBeans;
import com.xbx.tourguide.ui.CameraDialogActivity;
import com.xbx.tourguide.ui.RegisterActivity;
import com.xbx.tourguide.ui.RegisterNextActivity;
import com.xbx.tourguide.ui.SelectProvinceActivity;
import com.xbx.tourguide.util.ToastUtils;
import com.xbx.tourguide.view.CircleImageView;

/**
 * Created by shuzhen on 2016/3/30.
 * <p/>
 * 导游注册
 */
public class GuideRegisterFragment extends Fragment implements View.OnClickListener {

    private View view;
    private RadioButton femaleRb, maleRb, chRb, enRb, allRb;
    private CircleImageView headPicCiv;
    public TextView typeEt, locationTv;
    public EditText nameEt, idEt, guideIdEt;
    private ImageLoader loader;
    public RegisterBeans beans = new RegisterBeans();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_guide_register, null);
        loader = ImageLoader.getInstance();
        initView(view);
        return view;
    }


    private void initView(View v) {
        femaleRb = (RadioButton) v.findViewById(R.id.rb_famale);
        maleRb = (RadioButton) v.findViewById(R.id.rb_male);
        headPicCiv = (CircleImageView) v.findViewById(R.id.civ_headimg);
        typeEt = (TextView) v.findViewById(R.id.et_toursit_type);
        nameEt = (EditText) v.findViewById(R.id.et_name);
        idEt = (EditText) v.findViewById(R.id.et_card);
        guideIdEt = (EditText) v.findViewById(R.id.et_tourist_certificate);
        locationTv = (TextView) v.findViewById(R.id.et_location);
        chRb = (RadioButton) v.findViewById(R.id.rb_chinese);
        enRb = (RadioButton) v.findViewById(R.id.rb_english);
        allRb = (RadioButton) v.findViewById(R.id.rb_all);

        beans.setSex(1);

        femaleRb.setOnClickListener(this);
        maleRb.setOnClickListener(this);
        headPicCiv.setOnClickListener(this);
        typeEt.setOnClickListener(this);
        locationTv.setOnClickListener(this);
        chRb.setOnClickListener(this);
        enRb.setOnClickListener(this);
        allRb.setOnClickListener(this);

        femaleRb.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_gender_selected, 0, 0, 0);
        maleRb.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_gender_normal, 0, 0, 0);
        chRb.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_gender_selected, 0, 0, 0);
        enRb.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_gender_normal, 0, 0, 0);
        allRb.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_gender_normal, 0, 0, 0);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.rb_famale:
                femaleRb.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_gender_selected, 0, 0, 0);
                maleRb.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_gender_normal, 0, 0, 0);
                beans.setSex(1);
                break;

            case R.id.rb_male:
                femaleRb.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_gender_normal, 0, 0, 0);
                maleRb.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_gender_selected, 0, 0, 0);
                beans.setSex(0);
                break;

            case R.id.civ_headimg:
                Intent intent = new Intent(getActivity(), CameraDialogActivity.class);
                intent.putExtra("isPic", true);
                intent.putExtra("isCrop", true);
                startActivityForResult(intent, 100);
                break;

            case R.id.et_toursit_type:
                Intent intentType = new Intent(getActivity(), CameraDialogActivity.class);
                intentType.putExtra("isPic", false);
                startActivityForResult(intentType, 101);
                break;
            case R.id.et_location:
                Intent cityIntent = new Intent(getActivity(), SelectProvinceActivity.class);
                startActivityForResult(cityIntent, 200);
                break;
            case R.id.rb_chinese:
                beans.setServer_language(0);
                chRb.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_gender_selected, 0, 0, 0);
                enRb.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_gender_normal, 0, 0, 0);
                allRb.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_gender_normal, 0, 0, 0);
                break;

            case R.id.rb_english:
                beans.setServer_language(1);
                chRb.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_gender_normal, 0, 0, 0);
                enRb.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_gender_selected, 0, 0, 0);
                allRb.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_gender_normal, 0, 0, 0);
                break;

            case R.id.rb_all:
                beans.setServer_language(2);
                chRb.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_gender_normal, 0, 0, 0);
                enRb.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_gender_normal, 0, 0, 0);
                allRb.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_gender_selected, 0, 0, 0);
                break;
            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == 100) {

            String url = data.getStringExtra("url");
            loader.displayImage("file://" + url, headPicCiv);
            beans.setHead_image(url);

        } else if (resultCode == 101) {
            String type = data.getStringExtra("type");
            typeEt.setText(type);
            typeEt.setTextColor(ContextCompat.getColor(getActivity(), R.color.text_color));
            if (getResources().getString(R.string.guide).equals(type)) {
                beans.setGuide_type(0);
            } else if (getResources().getString(R.string.leader).equals(type)) {
                beans.setGuide_type(1);
            }
        }

        if (requestCode == 200 && resultCode == 200) {
            CityBeans city = (CityBeans) data.getSerializableExtra("bean");
            locationTv.setText(city.getName());
            beans.setCity(city);
        }

    }
}
