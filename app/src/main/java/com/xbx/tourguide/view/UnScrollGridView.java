package com.xbx.tourguide.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;
/**
 * 没有滚动条的GridView
 * @author Administrator
 *
 */
public class UnScrollGridView extends GridView {

	public UnScrollGridView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public UnScrollGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);

	}

}
