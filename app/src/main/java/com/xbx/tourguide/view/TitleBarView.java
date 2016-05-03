package com.xbx.tourguide.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xbx.tourguide.R;

public class TitleBarView extends FrameLayout implements View.OnClickListener {

    private ImageButton mLeftIB, mRightIB;
    private TextView mTitleTV;
    private TextView mRightTV;
    private RelativeLayout titleRlyt;
    private OnLeftImageButtonClickListener mOnLeftImageButtonClickListener;
    private OnRightTextViewClickListener mOnRightTextViewClickListener;
    private OnRightImageButtonClickListener mOnRightImageButtonClickListener;

    /**
     * Left ImageView click listener on TitleBar
     */
    public interface OnLeftImageButtonClickListener {
        void onClick(View v);
    }

    /**
     * Right TextView click listener on TitleBar
     */
    public interface OnRightTextViewClickListener {
        void onClick(View v);
    }

    /**
     * Right ImageView click listener on TitleBar
     */
    public interface OnRightImageButtonClickListener {
        void onClick(View v);
    }


    public TitleBarView(Context context) {
        super(context);
        initWidgets();
    }

    public TitleBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initWidgets();
    }

    public TitleBarView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initWidgets();
    }

    // initial widgets
    private void initWidgets() {
        if (isInEditMode()) {// 如果Eclipse界面编辑器状态
            return;
        }
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_titlebar, this, true);
        mLeftIB = (ImageButton) findViewById(R.id.ib_title_left);
        mTitleTV = (TextView) findViewById(R.id.tv_title_middle);
        mRightIB = (ImageButton) findViewById(R.id.ib_title_right);
        mRightTV = (TextView) findViewById(R.id.tv_title_right);
        titleRlyt = (RelativeLayout) findViewById(R.id.rlyt_title);

        mLeftIB.setOnClickListener(this);
        mRightTV.setOnClickListener(this);
        mRightIB.setOnClickListener(this);

        mTitleTV.setVisibility(View.INVISIBLE);
        mRightTV.setVisibility(View.GONE);
        mRightIB.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_title_left:
                if (this.mOnLeftImageButtonClickListener != null) {
                    mOnLeftImageButtonClickListener.onClick(view);
                }
                break;
            case R.id.tv_title_right:
                if (this.mOnRightTextViewClickListener != null) {
                    mOnRightTextViewClickListener.onClick(view);
                }
                break;
            case R.id.ib_title_right:
                if (this.mOnRightImageButtonClickListener != null) {
                    mOnRightImageButtonClickListener.onClick(view);
                }
                break;
        }
    }

    /**
     * actions on left ImageButton
     */
    // set left image for ImageButton
    public void setLeftImageButton(int imageResourceID) {
        mLeftIB.setVisibility(View.VISIBLE);
        mLeftIB.setImageResource(imageResourceID);
    }

    // set left ImageView
    public void setLeftImageButtonOnClickListener(OnLeftImageButtonClickListener listener) {
        mLeftIB.setVisibility(View.VISIBLE);
        mOnLeftImageButtonClickListener = listener;
    }

    // set right image for ImageView
    public void setRightImageButton(int imageResourceID) {
        mRightIB.setVisibility(View.VISIBLE);
        // mRightTV.setVisibility(View.INVISIBLE);
        mRightIB.setImageResource(imageResourceID);
    }

    // set right ImageView
    public void setRightImageViewOnClickListener(OnRightImageButtonClickListener listener) {
        mRightIB.setVisibility(View.VISIBLE);
        // mRightTV.setVisibility(View.INVISIBLE);
        mOnRightImageButtonClickListener = listener;
    }

    // show left TextView
    public void showLeftImageButton(boolean isShow) {
        if (isShow) {
            mLeftIB.setVisibility(View.VISIBLE);
        } else {
            mLeftIB.setVisibility(View.GONE);
        }
    }

    // show right ImageButton
    public void showRightImageButton(boolean isShow) {
        if (isShow) {
            // 背景重复
            mRightIB.setVisibility(View.VISIBLE);
        } else {
            mRightIB.setVisibility(View.GONE);
        }
    }

    // remove left ImageButton
    public void hideLeftImageButton() {
        mLeftIB.setVisibility(View.GONE);
    }

    public ImageButton getLeftImageButton() {
        return this.mLeftIB;
    }

    // remove right ImageButton
    public void hideRightImageButton() {
        mRightIB.setVisibility(View.GONE);
    }

    public ImageButton getRightImageButton() {
        return this.mRightIB;
    }

    /**
     * actions on right TextView
     */
    public void setTextRightTextView(int stringID) {
        mRightTV.setVisibility(View.VISIBLE);
        mRightTV.setText(stringID);
    }

    public void setTextRightTextView(String string) {
        mRightTV.setVisibility(View.VISIBLE);
        mRightTV.setText(string);
    }

    public void setTextRightTextViewSize(int dpSize) {
        mRightTV.setVisibility(View.VISIBLE);
        mRightTV.setTextSize(dpSize);
    }

    public void setTextRightTextViewColor(int color) {
        mRightTV.setVisibility(View.VISIBLE);
        mRightTV.setTextColor(color);
    }

    // set right TextView
    public void setRightTextViewOnClickListener(OnRightTextViewClickListener listener) {
        mRightTV.setVisibility(View.VISIBLE);
        // mRightIV.setVisibility(View.INVISIBLE);
        mOnRightTextViewClickListener = listener;
    }

    // show right TextView
    public void showRightTextView(boolean isShow) {
        if (isShow) {
            mRightTV.setVisibility(View.VISIBLE);
        } else {
            mRightTV.setVisibility(View.GONE);
        }
    }

    // remove right TextView
    public void removeRightTextView() {
        showRightTextView(false);
        mOnRightTextViewClickListener = null;
    }

    /**
     * actions on middle title
     */
    // set title text
    public void setTitle(String text) {
        mTitleTV.setVisibility(View.VISIBLE);
        mTitleTV.setText(text);
    }

    // set title text
    public void setTitle(int stringID) {
        mTitleTV.setVisibility(View.VISIBLE);
        mTitleTV.setText(stringID);
    }

    public void setTitleSize(int type, int size) {
        mTitleTV.setVisibility(View.VISIBLE);
        mTitleTV.setTextSize(type, size);
    }

    public void setTitleColor(int color) {
        mTitleTV.setVisibility(View.VISIBLE);
        mTitleTV.setTextColor(color);
    }

    public void hideTitle() {
        mTitleTV.setVisibility(View.GONE);
    }

    public void setLayout(int colorResId) {
        titleRlyt.setBackgroundColor(colorResId);
    }
}
