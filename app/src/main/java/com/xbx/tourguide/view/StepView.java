package com.xbx.tourguide.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.xbx.tourguide.R;

/**
 * Created by xbx on 2016/5/9.
 */
public class StepView extends View {
    private boolean is_selected = false;
    private String text;
    private int width;
    private int height;
    private Paint mPaint;
    private Context mContext;

    public StepView(Context context) {
        this(context, null);
    }

    public StepView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StepView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.StepView, defStyleAttr, 0);

        is_selected = a.getBoolean(R.styleable.StepView_is_selected, false);
        text = a.getString(R.styleable.StepView_text);

        a.recycle();

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void setSelected(boolean is_selected) {
        this.is_selected = is_selected;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        width = getMeasuredWidth();
        height = getMeasuredHeight();

        if (is_selected) {
            mPaint.setColor(ContextCompat.getColor(mContext, R.color.head_bg_color));
        } else {
            mPaint.setColor(ContextCompat.getColor(mContext, android.R.color.white));
        }
        //画矩形
        canvas.drawRect(new RectF(0, height / 2f - 5, width, height / 2f + 5), mPaint);
        //画圆
        canvas.drawCircle(width / 2f, height / 2f, height / 2f, mPaint);
        //
        if (is_selected) {
            mPaint.setColor(ContextCompat.getColor(mContext, android.R.color.white));
        } else {
            mPaint.setColor(ContextCompat.getColor(mContext, R.color.gray_color));
        }

        mPaint.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10, getResources().getDisplayMetrics()));
        mPaint.setTextAlign(Paint.Align.LEFT);
        Rect bounds = new Rect(0, 0, width, height);
        mPaint.getTextBounds(text, 0, text.length(), bounds);
        Paint.FontMetricsInt fontMetrics = mPaint.getFontMetricsInt();
        int baseline = (getMeasuredHeight() - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top;
        if ("1".equals(text)) {
            canvas.drawText(text, getMeasuredWidth() / 2f - bounds.width() / 2f - 4, baseline, mPaint);
        } else {
            canvas.drawText(text, getMeasuredWidth() / 2f - bounds.width() / 2f, baseline, mPaint);
        }

    }
}
