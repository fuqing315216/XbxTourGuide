package com.xbx.tourguide.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.xbx.tourguide.R;
import com.xbx.tourguide.base.BaseActivity;
import com.xbx.tourguide.view.TitleBarView;

/**
 * Created by xbx on 2016/5/13.
 */
public class SelectOrderActivity extends BaseActivity {
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_order);
        initView();
    }

    private void initView() {
        TitleBarView titleBarView = (TitleBarView) findViewById(R.id.titlebar);
        titleBarView.setTitle(getString(R.string.accept));
        titleBarView.setLeftImageButtonOnClickListener(new TitleBarView.OnLeftImageButtonClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
