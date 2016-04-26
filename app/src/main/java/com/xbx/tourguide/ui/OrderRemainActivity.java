package com.xbx.tourguide.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xbx.tourguide.R;
import com.xbx.tourguide.api.ServerApi;
import com.xbx.tourguide.api.TaskFlag;
import com.xbx.tourguide.base.BaseActivity;
import com.xbx.tourguide.beans.OrderDetailBeans;
import com.xbx.tourguide.beans.SQLiteOrderBean;
import com.xbx.tourguide.db.OrderNumberDao;
import com.xbx.tourguide.http.HttpUrl;
import com.xbx.tourguide.http.IRequest;
import com.xbx.tourguide.http.RequestBackListener;
import com.xbx.tourguide.http.RequestParams;
import com.xbx.tourguide.jsonparse.UtilParse;
import com.xbx.tourguide.util.Cookie;
import com.xbx.tourguide.util.JsonUtils;
import com.xbx.tourguide.util.LogUtils;
import com.xbx.tourguide.util.ToastUtils;
import com.xbx.tourguide.util.VerifyUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by shuzhen on 2016/4/13.
 * <p/>
 * 订单提醒弹框
 */
public class OrderRemainActivity extends BaseActivity {
    private RelativeLayout remainRlyt;
    private TextView titleTv, contentTv, OKTv, cancelTv;
    private String orderNum = "";
    private String orderType = "";
    private OrderNumberDao orderNumberDao;

    private ServerApi serverApi = null;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case TaskFlag.REQUESTSUCCESS:
                    OrderDetailBeans result = JsonUtils.object((String)msg.obj, OrderDetailBeans.class);
                    orderType = result.getServer_type();
                    if ("0".equals(orderType)) {//及时服务
                        titleTv.setText(getResources().getString(R.string.fast_remain));
                        contentTv.setText(getResources().getString(R.string.remain_content1) + result.getEnd_addr() + getResources().getString(R.string.remain_content2) + result.getNumbers() + getResources().getString(R.string.remain_content3));
                        cancelTv.setVisibility(View.VISIBLE);
                        remainRlyt.setVisibility(View.VISIBLE);
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_remain);
        orderNum = getIntent().getStringExtra("orderNumber");
        orderType = getIntent().getStringExtra("serverType");
        orderNumberDao = new OrderNumberDao(this);
        serverApi = new ServerApi(this,handler);
        LogUtils.e(orderNum);
        initView();
    }


    private void initView() {
        remainRlyt = (RelativeLayout) findViewById(R.id.rlyt_order_remain);
        titleTv = (TextView) findViewById(R.id.tv_dialog_title);
        contentTv = (TextView) findViewById(R.id.tv_remain_content);
        OKTv = (TextView) findViewById(R.id.tv_ok);
        cancelTv = (TextView) findViewById(R.id.tv_cancel);

        OKTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmOrder(1 + "");//接单
                remainRlyt.setVisibility(View.GONE);
            }
        });

        cancelTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmOrder(0 + "");//拒单
                remainRlyt.setVisibility(View.GONE);
            }
        });

        if ("0".equals(orderType)) {//及时服务
            serverApi.getDetail(orderNum);
        } else if ("1".equals(orderType)) {//预约服务
            titleTv.setText(getResources().getString(R.string.order_remain));
            contentTv.setText(getResources().getString(R.string.remain_content));
            cancelTv.setVisibility(View.GONE);
            remainRlyt.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 确认/拒绝订单
     *
     * @param tag
     */
    private void confirmOrder(final String tag) {

        RequestParams params = new RequestParams();
        params.put("uid", Cookie.getUid(this));
        params.put("order_number", orderNum);
        params.put("confirm", tag);
        IRequest.post(this, HttpUrl.CONFIRM_ORDER, params, getString(R.string.loding), new RequestBackListener(this) {
                    @Override
                    public void requestSuccess(String json) {
//                            findViewById(R.id.llyt_button).setVisibility(View.GONE);
                        LogUtils.e("---confirmOrder:" + json);
                        if (UtilParse.getRequestCode(json) == 0) {
                            if ("0".equals(orderType)) {
                                //订单被抢走
                                orderNumberDao.deleteFirst();
//                              orderNumberDao.selectAll();
                                isNext();
                            }else if("1".equals(orderType)){

                            }
                            ToastUtils.showShort(OrderRemainActivity.this, UtilParse.getRequestMsg(json));
                        } else if (UtilParse.getRequestCode(json) == 1) {
                            if ("0".equals(tag)) {//拒单
                                orderNumberDao.deleteFirst();
//                                orderNumberDao.selectAll();
                                isNext();
                            } else if ("1".equals(tag)) {//接单
                                if ("0".equals(orderType)) {//及时服务
                                    orderNumberDao.clear();
//                                    orderNumberDao.selectAll();
                                    Intent intent = new Intent(OrderRemainActivity.this, StartServiceActivity.class);
                                    intent.putExtra("isgoing", false);
                                    intent.putExtra("orderId", orderNum);
                                    startActivity(intent);
                                    finish();
                                } else if ("1".equals(orderType)) {//预约服务
                                    Cookie.putAppointmentOrder(OrderRemainActivity.this,false);
                                    startIntent(MyOrderListActivity.class, true);
                                }
                            }
                        }
                    }
                }
        );
    }

    /**
     * 即时订单被抢走或取消 判断是否还有即时订单或预约订单
     */
    private void isNext() {
        ToastUtils.showShort(this, "取消及时服务");
        SQLiteOrderBean sqLiteOrderBean = orderNumberDao.selectFirst();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            long getMillionSeconds = sdf.parse(sqLiteOrderBean.getDate()).getTime();
            long nowMillionSeconds = new Date().getTime();
            if (nowMillionSeconds - getMillionSeconds > 60 * 60 * 1000) {//时间超过一个小时
                return;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String nextNum = sqLiteOrderBean.getNum();

        LogUtils.e("---nextNum:" + nextNum);
        if (VerifyUtil.isNullOrEmpty(nextNum)) {//即时服务没有了
            if (Cookie.getAppointmentOrder(this)) {//有预约服务
                titleTv.setText(getResources().getString(R.string.order_remain));
                contentTv.setText(getResources().getString(R.string.remain_content));
                cancelTv.setVisibility(View.GONE);
                remainRlyt.setVisibility(View.VISIBLE);
            } else {
                Cookie.putIsDialog(this, false);
                OrderRemainActivity.this.finish();
            }
        } else {
            orderNum = nextNum;
            serverApi.getDetail(orderNum);
        }
    }
}

