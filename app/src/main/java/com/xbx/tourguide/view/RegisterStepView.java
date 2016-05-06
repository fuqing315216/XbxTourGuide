package com.xbx.tourguide.view;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.xbx.tourguide.R;

public class RegisterStepView extends FrameLayout {

    private ImageView registerIv, typeIv, infoIv, loadIv;
    private TextView registerTv, typeTv, infoTv, loadTv;
    private Context mContext;

    public RegisterStepView(Context context) {
        super(context);
        this.mContext = context;
        initWidgets();
    }

    public RegisterStepView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initWidgets();
    }

    public RegisterStepView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mContext = context;
        initWidgets();
    }

    // initial widgets
    private void initWidgets() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_register_step, this, true);
        registerIv = (ImageView) findViewById(R.id.iv_step_register);
        typeIv = (ImageView) findViewById(R.id.iv_step_guide_type);
        infoIv = (ImageView) findViewById(R.id.iv_step_info);
        loadIv = (ImageView) findViewById(R.id.iv_step_upload);

        registerTv = (TextView) findViewById(R.id.tv_step_register);
        typeTv = (TextView) findViewById(R.id.tv_step_guide_type);
        infoTv = (TextView) findViewById(R.id.tv_step_info);
        loadTv = (TextView) findViewById(R.id.tv_step_upload);
    }

    public void setStep(int step) {
        switch (step) {
            case 1:
                break;
            case 2:
                registerIv.setImageResource(R.drawable.ic_launcher);
                typeIv.setImageResource(R.drawable.ic_launcher);
                infoIv.setImageResource(R.drawable.ic_launcher);
                loadIv.setImageResource(R.drawable.ic_launcher);

                setSelectedColor(registerTv, false);
                setSelectedColor(typeTv, true);
                setSelectedColor(infoTv, false);
                setSelectedColor(loadTv, false);
                break;
            case 3:
                registerIv.setImageResource(R.drawable.ic_launcher);
                typeIv.setImageResource(R.drawable.ic_launcher);
                infoIv.setImageResource(R.drawable.ic_launcher);
                loadIv.setImageResource(R.drawable.ic_launcher);

                setSelectedColor(registerTv, false);
                setSelectedColor(typeTv, false);
                setSelectedColor(infoTv, true);
                setSelectedColor(loadTv, false);
                break;
            case 4:
                registerIv.setImageResource(R.drawable.ic_launcher);
                typeIv.setImageResource(R.drawable.ic_launcher);
                infoIv.setImageResource(R.drawable.ic_launcher);
                loadIv.setImageResource(R.drawable.ic_launcher);

                setSelectedColor(registerTv, false);
                setSelectedColor(typeTv, false);
                setSelectedColor(infoTv, false);
                setSelectedColor(loadTv, true);
                break;
        }
    }

    private void setSelectedColor(TextView text, boolean isSelected) {
        if (isSelected) {
            text.setTextColor(ContextCompat.getColor(mContext, R.color.head_bg_color));
        } else {
            text.setTextColor(ContextCompat.getColor(mContext, R.color.gray_color));
        }
    }
}
