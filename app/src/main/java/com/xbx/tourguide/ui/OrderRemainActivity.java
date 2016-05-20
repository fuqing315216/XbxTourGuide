package com.xbx.tourguide.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xbx.tourguide.R;
import com.xbx.tourguide.base.BaseActivity;
import com.xbx.tourguide.beans.OrderDetailBeans;
import com.xbx.tourguide.beans.SQLiteOrderBean;
import com.xbx.tourguide.db.OrderNumberDao;
import com.xbx.tourguide.http.HttpUrl;
import com.xbx.tourguide.http.IRequest;
import com.xbx.tourguide.http.RequestBackListener;
import com.xbx.tourguide.http.RequestParams;
import com.xbx.tourguide.jsonparse.UtilParse;
import com.xbx.tourguide.util.Constant;
import com.xbx.tourguide.util.JsonUtils;
import com.xbx.tourguide.util.LogUtils;
import com.xbx.tourguide.util.SPUtils;
import com.xbx.tourguide.util.ToastUtils;
import com.xbx.tourguide.util.Utils;
import com.xbx.tourguide.util.VerifyUtil;

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
    private String _id;
    private OrderNumberDao orderNumberDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_remain);
        orderNum = getIntent().getStringExtra("orderNumber");
        orderType = getIntent().getStringExtra("serverType");
        orderNumberDao = new OrderNumberDao(this);
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
                if ("0".equals(orderType)) {//及时服务
                    OKTv.setEnabled(false);
                    confirmOrder(1 + "");//接单
                } else if ("1".equals(orderType)) {//预约服务
                    SPUtils.put(OrderRemainActivity.this, Constant.APPOINT_ORDER, "");
                    putIsDialog();
                    startIntent(MyOrderListActivity.class, true);
                    finish();
                }
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
            _id = getIntent().getStringExtra("_id");
            getDetail();
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


    private void getDetail() {
        String url = HttpUrl.MY_ORDER_DETAIL + "?order_number=" + orderNum;
        IRequest.get(this, url, getString(R.string.waitting), new RequestBackListener(this) {
            @Override
            public void requestSuccess(String json) {
                if (UtilParse.getRequestCode(json) == 0) {
                    ToastUtils.showShort(OrderRemainActivity.this, UtilParse.getRequestMsg(json));
                    putIsDialog();
                    orderNumberDao.deleteFirst(_id);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            finish();
                        }
                    }, 1500);

                } else if (UtilParse.getRequestCode(json) == 1) {
                    OrderDetailBeans result = JsonUtils.object(UtilParse.getRequestData(json), OrderDetailBeans.class);
                    orderType = result.getServer_type();
                    if ("0".equals(orderType)) {//及时服务
                        titleTv.setText(getResources().getString(R.string.fast_remain));
                        contentTv.setText(getResources().getString(R.string.remain_content1) + result.getEnd_addr() + getResources().getString(R.string.remain_content2) + result.getNumbers() + getResources().getString(R.string.remain_content3));
                        cancelTv.setVisibility(View.VISIBLE);
                        remainRlyt.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
    }

    /**
     * 确认/拒绝订单
     *
     * @param tag
     */
    private void confirmOrder(final String tag) {
        RequestParams params = new RequestParams();
        params.put("uid", (String) SPUtils.get(this, Constant.UID, ""));
        params.put("order_number", orderNum);
        params.put("confirm", tag);
        IRequest.post(this, HttpUrl.CONFIRM_ORDER, params, getString(R.string.waitting), new RequestBackListener(this) {
                    @Override
                    public void requestSuccess(String json) {
                        OKTv.setEnabled(true);
                        ToastUtils.showShort(OrderRemainActivity.this, UtilParse.getRequestMsg(json));
                        if (UtilParse.getRequestCode(json) == 0) {
                            if ("0".equals(orderType)) {
                                //订单被抢走
                                orderNumberDao.deleteFirst(_id);
//                              orderNumberDao.selectAll();
                                isNext();
                            }
                        } else if (UtilParse.getRequestCode(json) == 1) {
                            LogUtils.i("----" + UtilParse.getRequestMsg(json));
                            if ("0".equals(tag)) {//拒单
                                orderNumberDao.deleteFirst(_id);
//                                orderNumberDao.selectAll();
                                isNext();
                            } else if ("1".equals(tag)) {//接单
                                putIsDialog();
                                if ("0".equals(orderType)) {//及时服务
                                    orderNumberDao.clear();
//                                    orderNumberDao.selectAll();
                                    Intent intent = new Intent(OrderRemainActivity.this, StartServiceActivity.class);
                                    intent.putExtra("isgoing", false);
                                    intent.putExtra("orderId", orderNum);
                                    startActivity(intent);
                                    finish();
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
        SQLiteOrderBean sqLiteOrderBean = orderNumberDao.selectFirst();
        if (sqLiteOrderBean.getNum() == null) {//即时服务没有了
            String appointOrder = (String) SPUtils.get(this, Constant.APPOINT_ORDER, "");
            if (appointOrder != null && !VerifyUtil.isNullOrEmpty(appointOrder)) {//有预约服务
                orderType = "1";
                titleTv.setText(getResources().getString(R.string.order_remain));
                contentTv.setText(getResources().getString(R.string.remain_content));
                cancelTv.setVisibility(View.GONE);
                remainRlyt.setVisibility(View.VISIBLE);
            } else {
                putIsDialog();
                finish();
            }
        } else {
            if (Utils.isOverTime(Long.valueOf(sqLiteOrderBean.getDate()))) {
                orderNumberDao.clear();
                putIsDialog();
                finish();
            } else {
                orderNum = sqLiteOrderBean.getNum();
                _id = sqLiteOrderBean.get_id();
                getDetail();
            }
        }
    }

    private void putIsDialog() {
        SPUtils.put(OrderRemainActivity.this, Constant.IS_DIALOG, false);
    }
}

