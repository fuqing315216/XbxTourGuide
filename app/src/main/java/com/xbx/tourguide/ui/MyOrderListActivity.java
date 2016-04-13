package com.xbx.tourguide.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.android.volley.VolleyError;
import com.xbx.tourguide.R;
import com.xbx.tourguide.adapter.MyOrderListAdapter;
import com.xbx.tourguide.base.BaseActivity;
import com.xbx.tourguide.beans.MyOrderBeans;
import com.xbx.tourguide.http.HttpUrl;
import com.xbx.tourguide.http.IRequest;
import com.xbx.tourguide.http.RequestJsonListener;
import com.xbx.tourguide.util.Cookie;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shuzhen on 2016/4/1.
 * <p/>
 * 我的订单列表
 */
public class MyOrderListActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private ImageButton returnIbtn;
    private ListView myOrderLv;
    private MyOrderListAdapter adapter;
    private List<MyOrderBeans> myOrderBeansList ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myorder_list);
        initView();
    }

    private void initView() {
        returnIbtn = (ImageButton) findViewById(R.id.ibtn_return);
        myOrderLv = (ListView) findViewById(R.id.lv_myorder);

        returnIbtn.setOnClickListener(this);
        myOrderLv.setOnItemClickListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        myOrderData();
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

    /**
     * 获取我的订单
     */
    private void myOrderData() {

        String url = HttpUrl.MY_ORDER + "?uid=" + Cookie.getUserInfo(this).getUid();

        IRequest.get(this, url, MyOrderBeans.class, "请稍候...", new RequestJsonListener<MyOrderBeans>() {
            @Override
            public void requestSuccess(MyOrderBeans result) {
            }

            @Override
            public void requestSuccess(List<MyOrderBeans> list) {

                myOrderBeansList = new ArrayList<>();
                myOrderBeansList.addAll(list);
                if (myOrderBeansList != null && myOrderBeansList.size() > 0) {
                    adapter = new MyOrderListAdapter(MyOrderListActivity.this, myOrderBeansList);
                    myOrderLv.setAdapter(adapter);
                }
            }

            @Override
            public void requestError(VolleyError e) {

            }
        });
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        MyOrderBeans bean = (MyOrderBeans) parent.getItemAtPosition(position);
        Intent intent=new Intent();
        if ("0".equals(bean.getPay_status())){//进行中
            intent.setClass(this,StartServiceActivity.class);
        }else{
            intent.setClass(this,MyOrderDetailActivity.class);
        }

        intent.putExtra("orderId",bean.getOrder_number());
        startActivity(intent);

    }
}
