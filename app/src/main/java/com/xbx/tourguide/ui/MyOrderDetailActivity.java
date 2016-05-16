package com.xbx.tourguide.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xbx.tourguide.R;
import com.xbx.tourguide.base.BaseActivity;
import com.xbx.tourguide.beans.OrderDetailBeans;
import com.xbx.tourguide.http.HttpUrl;
import com.xbx.tourguide.http.IRequest;
import com.xbx.tourguide.http.RequestBackListener;
import com.xbx.tourguide.http.RequestParams;
import com.xbx.tourguide.util.Cookie;
import com.xbx.tourguide.util.Util;
import com.xbx.tourguide.util.VerifyUtil;
import com.xbx.tourguide.view.FlowLayout;
import com.xbx.tourguide.view.TitleBarView;

/**
 * Created by shuzhen on 2016/4/5.
 * <p/>
 * 订单详情
 */
public class MyOrderDetailActivity extends BaseActivity implements View.OnClickListener {

    private String orderNum = "";
    private ImageLoader loader;
    private RoundedImageView headPicRiv;
    private TextView nickNameTv, addressTv, address2Tv, totalTv, numberTv, startTv, endTv, durationTv, commentConTv;
    private TextView cancelTv;
    private LinearLayout payDetailLlyt, commentLlyt, cancelLlyt, buttonLlyt;
    private FlowLayout tagFlyt;
    private RatingBar starRab;
    private ImageView phoneIv;
    private OrderDetailBeans result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        result = (OrderDetailBeans) getIntent().getSerializableExtra("orderDetailBeans");
        loader = ImageLoader.getInstance();

        initView();
    }

    private void initView() {

        TitleBarView titleBarView = (TitleBarView) findViewById(R.id.titlebar);
        titleBarView.setTitle(getString(R.string.order_detail));
        titleBarView.setLeftImageButtonOnClickListener(new TitleBarView.OnLeftImageButtonClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        headPicRiv = (RoundedImageView) findViewById(R.id.riv_order_detail_headpic);
        nickNameTv = (TextView) findViewById(R.id.tv_order_detail_name);
        addressTv = (TextView) findViewById(R.id.tv_order_detail_address);
        address2Tv = (TextView) findViewById(R.id.tv_order_detail_address2);
        totalTv = (TextView) findViewById(R.id.tv_order_detail_total);
        numberTv = (TextView) findViewById(R.id.tv_order_detail_order_number);
        startTv = (TextView) findViewById(R.id.tv_order_detail_start);
        endTv = (TextView) findViewById(R.id.tv_order_detail_end);
        durationTv = (TextView) findViewById(R.id.tv_order_detail_duration);
        commentConTv = (TextView) findViewById(R.id.tv_order_detail_content);
        tagFlyt = (FlowLayout) findViewById(R.id.flyt_order_detail_tag);
        starRab = (RatingBar) findViewById(R.id.rab_order_detail);
        cancelTv = (TextView) findViewById(R.id.tv_order_detail_cancel);

        payDetailLlyt = (LinearLayout) findViewById(R.id.llyt_order_detail);
        commentLlyt = (LinearLayout) findViewById(R.id.llyt_order_detail_comment);
        cancelLlyt = (LinearLayout) findViewById(R.id.llyt_order_detail_cancel);
        buttonLlyt = (LinearLayout) findViewById(R.id.llyt_order_detail_button);

        phoneIv = (ImageView) findViewById(R.id.iv_order_detail_phone);
        findViewById(R.id.rlyt_order_detail_pay_detail).setOnClickListener(this);
        findViewById(R.id.btn_order_detail_refuse).setOnClickListener(this);
        findViewById(R.id.btn_order_detail_accept).setOnClickListener(this);

        initData();
    }

    private void initData() {
        loader.displayImage(result.getHead_image(), headPicRiv);
        nickNameTv.setText(result.getNickname());
        addressTv.setText(result.getEnd_addr());

        server_type = result.getServer_type();
        order_status = result.getOrder_status();

        //0-即时服务：0-待处理订单；1-已接单，未开始；2-服务已开始；3-服务已结束，未付款；4-已支付，未评论；5-订单已结束；6-已取消，未支付；7-已关闭（已取消并支付违约金）
        //1-预约服务：0-待支付；1-待处理订单；2-已接单，未开始；3-服务已开始；4-服务已结束,未评论；5-已完成；6-已取消，退款进行中；7-已关闭（已取消并退款完成）
        switch (Integer.valueOf(order_status)) {
            case 0:
                stateNormal(result);
                if ("1".equals(server_type)) {//待支付
                    setPhoneOn(result.getMobile());
                } else if ("0".equals(server_type)) {
                    buttonLlyt.setVisibility(View.VISIBLE);
                    payDetailLlyt.setOnClickListener(null);
                }
                break;
            case 1://待确认
                if ("1".equals(server_type)) {
                    stateNormal(result);
                    buttonLlyt.setVisibility(View.VISIBLE);
                    setPhoneOn(result.getMobile());
                } else if ("0".equals(server_type)) {
                    startActivity(new Intent(MyOrderDetailActivity.this, StartServiceActivity.class)
                            .putExtra("orderId", result.getOrder_number()));
                }
                break;
            case 2://已预约
                if ("1".equals(server_type)) {
                    stateNormal(result);
                    setPhoneOn(result.getMobile());
                } else if ("0".equals(server_type)) {
                    startActivity(new Intent(MyOrderDetailActivity.this, StartServiceActivity.class)
                            .putExtra("orderId", result.getOrder_number()));
                }
                break;
            case 3:
            case 4://未评论
                stateNormal(result);
                break;
            case 5://已完成
                stateNormal(result);
                commentLlyt.setVisibility(View.VISIBLE);

                if (!VerifyUtil.isNullOrEmpty(result.getContent())) {
                    commentConTv.setText(result.getContent());
                } else {
                    commentConTv.setVisibility(View.GONE);
                }

                if (!VerifyUtil.isNullOrEmpty(result.getStar())) {
                    starRab.setRating(Util.getStar(result.getStar()) / 2);
                } else {
                    starRab.setVisibility(View.GONE);
                }

                String[] tags = result.getTag();
                if (tags.length > 0) {
                    for (int i = 0; i < tags.length; i++) {
                        tagFlyt.addView(Util.addTextView(MyOrderDetailActivity.this, tags[i]));
                    }
                } else {
                    tagFlyt.setVisibility(View.GONE);
                }

                break;
            case 6://已关闭
                payDetailLlyt.setVisibility(View.GONE);
                cancelLlyt.setVisibility(View.VISIBLE);
                if ("0".equals(server_type)) {//未付违约金
                    cancelTv.setText("未付违约金");
                } else if ("1".equals(server_type)) {//退款中
                    cancelTv.setText("退款中");
                }
                break;
            case 7://已关闭
                payDetailLlyt.setVisibility(View.GONE);
                cancelLlyt.setVisibility(View.VISIBLE);
                if ("0".equals(server_type)) {//已付违约金
                    cancelTv.setText("已付违约金");
                    if (!"0.00".equals(result.getPay_money())) {
                        stateNormal(result);
                    }
                } else if ("1".equals(server_type)) {//已退款
                    cancelTv.setText("已退款");
                }
                break;
            case 8://已拒接，退款进行中
                payDetailLlyt.setVisibility(View.GONE);
                cancelLlyt.setVisibility(View.VISIBLE);
                cancelTv.setText("已拒接-退款中");
                break;
            case 9://已关闭（拒单并退款成功）
                payDetailLlyt.setVisibility(View.GONE);
                cancelLlyt.setVisibility(View.VISIBLE);
                cancelTv.setText("已拒接-已退款");
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rlyt_order_detail_pay_detail:
                startActivity(new Intent(MyOrderDetailActivity.this, OrderPayDetailActivity.class)
                        .putExtra("orderDetailBeans", result));
                break;
            case R.id.btn_order_detail_refuse:
                confirmOrder(0 + "");
                break;
            case R.id.btn_order_detail_accept:
                confirmOrder(1 + "");
                break;
        }
    }


    private String server_type = "";
    private String order_status = "";

    /**
     * 即时服务：3-未付款 4-未评论
     * 预约服务：0-待支付 2-已预约 3-进行中 4-未评论
     */
    private void stateNormal(OrderDetailBeans result) {
        totalTv.setText(result.getPay_money());
        address2Tv.setText(result.getEnd_addr());
        numberTv.setText(result.getOrder_number());
        startTv.setText(result.getServer_start_time());
        endTv.setText(result.getServer_end_time());
    }

    /**
     * 设置电话可拨打
     *
     * @param mobile
     */
    private void setPhoneOn(final String mobile) {
        phoneIv.setVisibility(View.VISIBLE);
        phoneIv.setImageDrawable(getResources().getDrawable(R.drawable.ic_phone_ok));
        phoneIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //用intent启动拨打电话
                Intent intent = new Intent(Intent.ACTION_DIAL);
                Uri data = Uri.parse("tel:" + mobile);
                intent.setData(data);
                startActivity(intent);
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
        params.put("uid", Cookie.getUid(this));
        params.put("order_number", orderNum);
        params.put("confirm", tag);
        IRequest.post(this, HttpUrl.CONFIRM_ORDER, params, "", new RequestBackListener(this) {
            @Override
            public void requestSuccess(String json) {
                if ("0".equals(tag)) {//拒单
                    finish();
                } else if ("1".equals(tag)) {//接单
                    finish();
                }
            }
        });
    }
}
