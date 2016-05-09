package com.xbx.tourguide.view;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.xbx.tourguide.R;

public class RegisterStepView extends FrameLayout {

    private StepView stepView1, stepView2, stepView3, stepView4;
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
        stepView1 = (StepView) findViewById(R.id.step_view1);
        stepView2 = (StepView) findViewById(R.id.step_view2);
        stepView3 = (StepView) findViewById(R.id.step_view3);
        stepView4 = (StepView) findViewById(R.id.step_view4);

        registerTv = (TextView) findViewById(R.id.tv_step_register);
        typeTv = (TextView) findViewById(R.id.tv_step_guide_type);
        infoTv = (TextView) findViewById(R.id.tv_step_info);
        loadTv = (TextView) findViewById(R.id.tv_step_upload);
    }

    public void setStep(int step) {
        switch (step) {
            case 1:
                stepView1.setSelected(true);
                stepView2.setSelected(false);
                stepView3.setSelected(false);
                stepView4.setSelected(false);

                setSelectedColor(registerTv, true);
                setSelectedColor(typeTv, false);
                setSelectedColor(infoTv, false);
                setSelectedColor(loadTv, false);

                break;
            case 2:
                stepView1.setSelected(false);
                stepView2.setSelected(true);
                stepView3.setSelected(false);
                stepView4.setSelected(false);

                setSelectedColor(registerTv, false);
                setSelectedColor(typeTv, true);
                setSelectedColor(infoTv, false);
                setSelectedColor(loadTv, false);
                break;
            case 3:
                stepView1.setSelected(false);
                stepView2.setSelected(false);
                stepView3.setSelected(true);
                stepView4.setSelected(false);


                setSelectedColor(registerTv, false);
                setSelectedColor(typeTv, false);
                setSelectedColor(infoTv, true);
                setSelectedColor(loadTv, false);
                break;
            case 4:
                stepView1.setSelected(false);
                stepView2.setSelected(false);
                stepView3.setSelected(false);
                stepView4.setSelected(true);

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
