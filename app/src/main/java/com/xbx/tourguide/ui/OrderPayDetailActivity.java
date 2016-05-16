package com.xbx.tourguide.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.xbx.tourguide.R;
import com.xbx.tourguide.base.BaseActivity;
import com.xbx.tourguide.beans.OrderDetailBeans;
import com.xbx.tourguide.view.TitleBarView;

/**
 * 订单详情页面 支付详情
 * Created by xbx on 2016/5/16.
 */
public class OrderPayDetailActivity extends BaseActivity {
    private TextView payMoneyTv, orderMoneyTv, tipTv, rebateTv;
    private OrderDetailBeans result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_paydetail);
        result = (OrderDetailBeans) getIntent().getSerializableExtra("orderDetailBeans");
        initView();
    }

    private void initView() {
        TitleBarView titleBarView = (TitleBarView) findViewById(R.id.titlebar);
        titleBarView.setTitle(getString(R.string.pay_details));
        titleBarView.setLeftImageButtonOnClickListener(new TitleBarView.OnLeftImageButtonClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        payMoneyTv = (TextView) findViewById(R.id.tv_order_pay_paymoney);
        orderMoneyTv = (TextView) findViewById(R.id.tv_order_pay_order_money);
        tipTv = (TextView) findViewById(R.id.tv_order_pay_tip);
        rebateTv = (TextView) findViewById(R.id.tv_order_pay_rebate);

        initData();
    }

    private void initData() {
        payMoneyTv.setText("￥" + result.getPay_money());
        orderMoneyTv.setText("￥" + result.getOrder_money());
        tipTv.setText("￥" + result.getTip_money());
        rebateTv.setText("￥" + result.getRebate_money());
    }
}
