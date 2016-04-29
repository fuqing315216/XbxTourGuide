package com.xbx.tourguide.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.android.volley.VolleyError;
import com.xbx.tourguide.R;
import com.xbx.tourguide.adapter.MyOrderListAdapter;
import com.xbx.tourguide.api.ServerApi;
import com.xbx.tourguide.api.TaskFlag;
import com.xbx.tourguide.base.BaseActivity;
import com.xbx.tourguide.beans.MyOrderBeans;
import com.xbx.tourguide.http.HttpUrl;
import com.xbx.tourguide.http.IRequest;
import com.xbx.tourguide.http.RequestBackListener;
import com.xbx.tourguide.http.RequestJsonListener;
import com.xbx.tourguide.jsonparse.UserInfoParse;
import com.xbx.tourguide.jsonparse.UtilParse;
import com.xbx.tourguide.util.Cookie;
import com.xbx.tourguide.util.JsonUtils;
import com.xbx.tourguide.util.LogUtils;
import com.xbx.tourguide.util.ToastUtils;
import com.xbx.tourguide.util.Util;
import com.xbx.tourguide.view.PullToRefreshLayout;
import com.xbx.tourguide.view.PullableListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by shuzhen on 2016/4/1.
 * <p/>
 * 我的订单列表
 */
public class MyOrderListActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener, PullToRefreshLayout.OnRefreshListener {

    private PullableListView myOrderLv;
    private MyOrderListAdapter adapter;
    private List<MyOrderBeans> myOrderBeansList = null;//显示list
    private List<MyOrderBeans> cashMyOrderBeansList = null;//返回list
    private PullToRefreshLayout pullToRefreshLayout;
    private String uid = "";
    private int nowPage = 1;
    private final static int PAGE_NUMBER = 10;
    private boolean isRefresh = false;
    private boolean isLoadMore = false;

    private ServerApi serverApi = null;
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
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myorder_list);
        myOrderBeansList = new ArrayList<>();
        uid = UserInfoParse.getUid(Cookie.getUserInfo(this));
        nowPage = 1;
        serverApi = new ServerApi(this, handler);
        serverApi.getMyOrderData(uid, nowPage, PAGE_NUMBER, TaskFlag.REQUESTSUCCESS, false);
        initView();
    }

    private void initView() {
        pullToRefreshLayout = (PullToRefreshLayout) findViewById(R.id.pulllayout_myorder);
        myOrderLv = (PullableListView) findViewById(R.id.pulllv_myorder);

        findViewById(R.id.ibtn_return).setOnClickListener(this);
        myOrderLv.setOnItemClickListener(this);

        pullToRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.ibtn_return:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        MyOrderBeans bean = (MyOrderBeans) parent.getItemAtPosition(position);
        String order_status = bean.getOrder_status();
        String server_type = bean.getServer_type();
        Intent intent = new Intent();
        LogUtils.i("---server_status:" + order_status + "---server_type：" + server_type);
        if ("0".equals(server_type)) {//即时服务
            if ("2".equals(order_status)) {//进行中
                intent.putExtra("isgoing", true);
                intent.setClass(this, StartServiceActivity.class);
            } else if ("1".equals(order_status)) {//进行中
                intent.putExtra("isgoing", false);
                intent.setClass(this, StartServiceActivity.class);
            } else if ("0".equals(order_status)) {
                intent.putExtra("isgoing", false);
                intent.setClass(this, StartServiceActivity.class);
            } else {
                intent.setClass(this, MyOrderDetailActivity.class);
            }
        } else if ("1".equals(server_type)) {//预约服务
            intent.setClass(this, MyOrderDetailActivity.class);
        }
        intent.putExtra("orderId", bean.getOrder_number());
        startActivity(intent);
    }

    @Override
    public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
        // 下拉刷新操作
        nowPage = 1;
        isRefresh = true;
        serverApi.getMyOrderData(uid, nowPage, PAGE_NUMBER, TaskFlag.REQUESTSUCCESS, true);
    }

    @Override
    public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
        // 加载操作
        nowPage++;
        isLoadMore = true;
        serverApi.getMyOrderData(uid, nowPage, PAGE_NUMBER, TaskFlag.REQUESTLOADMORE, true);
    }
}
