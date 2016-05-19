package com.xbx.tourguide.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.xbx.tourguide.R;
import com.xbx.tourguide.base.BaseActivity;
import com.xbx.tourguide.view.WheelView;

import java.util.List;

/**
 * Created by xbx on 2016/5/19.
 */
public class WheelViewDialogActivity extends BaseActivity implements View.OnClickListener {
    private List<String> list;
    private WheelView wheelView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wheelview);

        findViewById(R.id.tv_wheelview_cancel).setOnClickListener(this);
        findViewById(R.id.tv_wheelview_confirm).setOnClickListener(this);
        wheelView = (WheelView) findViewById(R.id.wheelview);
        list = getIntent().getStringArrayListExtra("wheelViewList");
        wheelView.setItems(list);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_wheelview_cancel:
                setResult(RESULT_OK, null);
                finish();
                break;
            case R.id.tv_wheelview_confirm:
                Intent intent = new Intent();
                intent.putExtra("serverLanguage", wheelView.getSeletedIndex() + "");
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }
}
