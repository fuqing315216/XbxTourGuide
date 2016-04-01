package com.xbx.tourguide.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.xbx.tourguide.R;
import com.xbx.tourguide.adapter.MyOrderListAdapter;
import com.xbx.tourguide.base.BaseActivity;
import com.xbx.tourguide.beans.MyOrderBeans;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shuzhen on 2016/4/1.
 * <p/>
 * 我的订单列表
 */
public class MyOrderListActivity extends BaseActivity implements View.OnClickListener,AdapterView.OnItemClickListener{

    private ImageButton returnIbtn;
    private ListView myOrderLv;
    private MyOrderListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myorder_list);
        initView();
    }

    private void initView(){
        returnIbtn=(ImageButton)findViewById(R.id.ibtn_return);
        myOrderLv=(ListView)findViewById(R.id.lv_myorder);

        returnIbtn.setOnClickListener(this);
        myOrderLv.setOnItemClickListener(this);
        setData();
    }


    private void setData(){
        ArrayList<MyOrderBeans> orderList=new ArrayList<>();
        for (int i=0;i<5;i++){
            MyOrderBeans bean=new MyOrderBeans();
            bean.setOrderId(1+"");
            bean.setOrderDate("4月" + (1 + i) + "日 11：00");
            bean.setOrderStatus(i % 4);
            bean.setTouristType(i % 3);
            bean.setOrderAddress("锦里-宽窄巷子");

            orderList.add(bean);
        }

        adapter=new MyOrderListAdapter(this,orderList);
        myOrderLv.setAdapter(adapter);
    }
    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.ibtn_return:
            finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        MyOrderBeans bean=(MyOrderBeans)parent.getItemAtPosition(position);
        Toast.makeText(this,bean.getOrderDate(),Toast.LENGTH_SHORT).show();
    }
}
