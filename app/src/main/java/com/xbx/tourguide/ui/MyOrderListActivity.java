package com.xbx.tourguide.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;

import com.alibaba.fastjson.JSON;
import com.xbx.tourguide.R;
import com.xbx.tourguide.adapter.MyOrderListAdapter;
import com.xbx.tourguide.api.ServiceApi;
import com.xbx.tourguide.api.TaskFlag;
import com.xbx.tourguide.base.BaseActivity;
import com.xbx.tourguide.beans.MyOrderBeans;
import com.xbx.tourguide.beans.OrderDetailBeans;
import com.xbx.tourguide.util.Constant;
import com.xbx.tourguide.util.JsonUtils;
import com.xbx.tourguide.util.SPUtils;
import com.xbx.tourguide.view.pullview.PullToRefreshLayout;
import com.xbx.tourguide.view.pullview.PullableListView;
import com.xbx.tourguide.view.TitleBarView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shuzhen on 2016/4/1.
 * <p>
 * 我的订单列表
 */
public class MyOrderListActivity extends BaseActivity implements AdapterView.OnItemClickListener, PullToRefreshLayout.OnRefreshListener {

    private PullableListView myOrderLv;
    private MyOrderListAdapter adapter;
    private List<MyOrderBeans> myOrderBeansList = null;//显示list
    private List<MyOrderBeans> cashMyOrderBeansList = null;//返回list
    private PullToRefreshLayout pullToRefreshLayout;
    private String uid = "";
    private int nowPage = 1;
    private boolean isRefresh = false;
    private boolean isLoadMore = false;

    private ServiceApi serviceApi = null;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case TaskFlag.REQUESTSUCCESS://初始化 和下拉刷新
                    myOrderBeansList = new ArrayList<>();
                    cashMyOrderBeansList = JSON.parseArray((String) msg.obj, MyOrderBeans.class);
                    if (isRefresh) {
                        isRefresh = false;
                        if (cashMyOrderBeansList.size() == 0) {
                            pullToRefreshLayout.refreshFinish(pullToRefreshLayout.FAIL);
                        } else {
                            pullToRefreshLayout.refreshFinish(pullToRefreshLayout.SUCCEED);
                        }
                    }

                    myOrderBeansList.addAll(cashMyOrderBeansList);
                    if (myOrderBeansList != null && myOrderBeansList.size() > 0) {
                        adapter = new MyOrderListAdapter(MyOrderListActivity.this, myOrderBeansList);
                        myOrderLv.setAdapter(adapter);
                    }

                    break;
                case TaskFlag.REQUESTLOADMORE://加载更多
                    if (isLoadMore) {
                        isLoadMore = false;
                        cashMyOrderBeansList = JSON.parseArray((String) msg.obj, MyOrderBeans.class);
                        if (cashMyOrderBeansList.size() == 0 || cashMyOrderBeansList == null) {
                            nowPage--;
                            pullToRefreshLayout.loadmoreFinish(pullToRefreshLayout.NOMORE);
                        } else {
                            pullToRefreshLayout.refreshFinish(pullToRefreshLayout.SUCCEED);
                            myOrderBeansList.addAll(cashMyOrderBeansList);
                            adapter.notifyDataSetChanged();
                        }
                    }
                    break;
                case TaskFlag.REQUESTERROR://刷新 或加载失败
                    if (isRefresh) {
                        isRefresh = false;
                        pullToRefreshLayout.refreshFinish(pullToRefreshLayout.FAIL);
                    }
                    if (isLoadMore) {
                        isLoadMore = false;
                        pullToRefreshLayout.loadmoreFinish(pullToRefreshLayout.FAIL);
                    }
                    break;
                case TaskFlag.PAGEREQUESTWO:
                    final OrderDetailBeans result = JsonUtils.object((String) msg.obj, OrderDetailBeans.class);
                    String order_status = result.getOrder_status();
                    String server_type = result.getServer_type();
                    Intent intent = new Intent();
                    if ("0".equals(server_type)) {//即时服务
                        if ("2".equals(order_status) || "1".equals(order_status)) {//进行中
                            intent.setClass(MyOrderListActivity.this, StartServiceActivity.class);
                            intent.putExtra("orderId", result.getOrder_number());
                        } else {
                            intent.putExtra("orderDetailBeans", result);
                            intent.setClass(MyOrderListActivity.this, MyOrderDetailActivity.class);
                        }
                    } else if ("1".equals(server_type)) {//预约服务
                        intent.putExtra("orderDetailBeans", result);
                        intent.setClass(MyOrderListActivity.this, MyOrderDetailActivity.class);
                    }
                    startActivity(intent);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myorder_list);

        initView();
        initData();
    }

    private void initView() {
        TitleBarView titleBarView = (TitleBarView) findViewById(R.id.titlebar);
        titleBarView.setTitle(getString(R.string.myorder));
        titleBarView.setLeftImageButtonOnClickListener(new TitleBarView.OnLeftImageButtonClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        pullToRefreshLayout = (PullToRefreshLayout) findViewById(R.id.pulllayout_myorder);
        myOrderLv = (PullableListView) findViewById(R.id.pulllv_myorder);
        myOrderLv.setOnItemClickListener(this);
        pullToRefreshLayout.setOnRefreshListener(this);
    }

    private void initData() {
        myOrderBeansList = new ArrayList<>();
        uid = (String) SPUtils.get(this, Constant.UID, "");
        nowPage = 1;
        serviceApi = new ServiceApi(this, handler);
        serviceApi.getMyOrderData(uid, nowPage, Constant.PAGE_NUMBER, TaskFlag.REQUESTSUCCESS);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        MyOrderBeans bean = (MyOrderBeans) parent.getItemAtPosition(position);
        serviceApi.getOrderStatusDetail(bean.getOrder_number());
    }

    @Override
    public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
        // 下拉刷新操作
        nowPage = 1;
        isRefresh = true;
        serviceApi.getMyOrderData(uid, nowPage, Constant.PAGE_NUMBER, TaskFlag.REQUESTSUCCESS);
    }

    @Override
    public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
        // 加载操作
        if (myOrderBeansList.size() > 0) {
            nowPage++;
        }
        isLoadMore = true;
        serviceApi.getMyOrderData(uid, nowPage, Constant.PAGE_NUMBER, TaskFlag.REQUESTLOADMORE);
    }

}
