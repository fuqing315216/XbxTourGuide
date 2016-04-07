package com.xbx.tourguide.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.xbx.tourguide.R;
import com.xbx.tourguide.beans.RegisterBeans;
import com.xbx.tourguide.ui.CameraDialogActivity;
import com.xbx.tourguide.view.CircleImageView;

/**
 * Created by shuzhen on 2016/3/30.
 */
public class NativeRegisterFragment extends Fragment implements View.OnClickListener {

    private View view;
    private RadioButton femaleRb, maleRb;
    private CircleImageView headPicCiv;
    public EditText nameEt,idEt;
    private ImageLoader loader;
    public RegisterBeans beans = new RegisterBeans();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_native_register, null);

        loader = ImageLoader.getInstance();
        initView(view);

        return view;
    }

    private void initView(View v) {
        femaleRb = (RadioButton) v.findViewById(R.id.rb_famale);
        maleRb = (RadioButton) v.findViewById(R.id.rb_male);
        headPicCiv = (CircleImageView) v.findViewById(R.id.civ_headimg);
        nameEt=(EditText)v.findViewById(R.id.et_name);
        idEt=(EditText)v.findViewById(R.id.et_card);

        beans.setSex(1);

        femaleRb.setOnClickListener(this);
        maleRb.setOnClickListener(this);
        headPicCiv.setOnClickListener(this);

        femaleRb.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_gender_selected, 0, 0, 0);
        maleRb.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_gender_normal, 0, 0, 0);

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
            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 100) {
            String url =  data.getStringExtra("url");
            loader.displayImage("file://" +url, headPicCiv);
            beans.setHead_image(url);
        }
    }
}
