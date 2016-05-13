package com.xbx.tourguide.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.xbx.tourguide.R;
import com.xbx.tourguide.adapter.PayDetailListAdapter;
import com.xbx.tourguide.base.BaseActivity;
import com.xbx.tourguide.util.ActivityManager;
import com.xbx.tourguide.view.TitleBarView;
import com.xbx.tourguide.view.UnScrollListView;

import java.util.ArrayList;
import java.util.List;

/**
 * 余额
 * Created by shuzhen on 2016/4/15.
 */
public class BalanceActivity extends BaseActivity implements View.OnClickListener {
    private TextView totalTv, depositTv, otherTv;
    private UnScrollListView USListView;
    private PayDetailListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance);
        initView();
    }

    private void initView() {
        TitleBarView titleBarView = (TitleBarView) findViewById(R.id.titlebar);
        titleBarView.setTitle(getString(R.string.my_balance));
        titleBarView.setLeftImageButtonOnClickListener(new TitleBarView.OnLeftImageButtonClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        titleBarView.setTextRightTextView(getString(R.string.deposit));
        titleBarView.setRightTextViewOnClickListener(new TitleBarView.OnRightTextViewClickListener() {
            @Override
            public void onClick(View v) {
                startIntent(DepositActivity.class, false);
            }
        });

        totalTv = (TextView) findViewById(R.id.tv_balance_total);
        depositTv = (TextView) findViewById(R.id.tv_balance_deposit);
        otherTv = (TextView) findViewById(R.id.tv_balance_other);
        USListView = (UnScrollListView) findViewById(R.id.uslv_balance);

        findViewById(R.id.tv_balance_more).setOnClickListener(this);

        List<String> list = new ArrayList<>();
        list.add("0");
        list.add("0");
        list.add("0");
        list.add("0");
        list.add("0");
        list.add("0");
        list.add("0");
        list.add("0");
        list.add("0");
        list.add("0");
        adapter = new PayDetailListAdapter(this, list);
        USListView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_balance_more:
                startIntent(PayDetailActivity.class, false);
                break;
        }
    }
}
