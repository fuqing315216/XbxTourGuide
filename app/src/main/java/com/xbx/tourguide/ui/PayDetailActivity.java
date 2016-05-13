package com.xbx.tourguide.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.xbx.tourguide.R;
import com.xbx.tourguide.adapter.MyOrderListAdapter;
import com.xbx.tourguide.adapter.PayDetailListAdapter;
import com.xbx.tourguide.base.BaseActivity;
import com.xbx.tourguide.beans.MyOrderBeans;
import com.xbx.tourguide.view.PullToRefreshLayout;
import com.xbx.tourguide.view.PullableListView;
import com.xbx.tourguide.view.TitleBarView;

import java.util.List;

/**
 * 支付明细
 * Created by shuzhen on 2016/4/15.
 */
public class PayDetailActivity extends BaseActivity implements AdapterView.OnItemClickListener, PullToRefreshLayout.OnRefreshListener, View.OnClickListener {

    private PullableListView myPayDetailLv;
    private PayDetailListAdapter adapter;
    //    private List<MyOrderBeans> myOrderBeansList = null;//显示list
//    private List<MyOrderBeans> cashMyOrderBeansList = null;//返回list
    private PullToRefreshLayout pullToRefreshLayout;
    private int nowPage = 1;
    private boolean isRefresh = false;
    private boolean isLoadMore = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paydetail);

        initView();
    }

    private void initView() {
        TitleBarView titleBarView = (TitleBarView) findViewById(R.id.titlebar);
        titleBarView.setTitle(getString(R.string.paydetail));
        titleBarView.setLeftImageButtonOnClickListener(new TitleBarView.OnLeftImageButtonClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        pullToRefreshLayout = (PullToRefreshLayout) findViewById(R.id.pulllayout_paydetail);
        myPayDetailLv = (PullableListView) findViewById(R.id.pulllv_paydetail);
        myPayDetailLv.setOnItemClickListener(this);
        pullToRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {

    }

    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {

    }
}
