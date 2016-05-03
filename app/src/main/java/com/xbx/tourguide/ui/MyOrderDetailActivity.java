package com.xbx.tourguide.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.xbx.tourguide.R;
import com.xbx.tourguide.base.BaseActivity;
import com.xbx.tourguide.beans.OrderDetailBeans;
import com.xbx.tourguide.db.OrderNumberDao;
import com.xbx.tourguide.http.HttpUrl;
import com.xbx.tourguide.http.IRequest;
import com.xbx.tourguide.http.RequestBackListener;
import com.xbx.tourguide.http.RequestParams;
import com.xbx.tourguide.jsonparse.UserInfoParse;
import com.xbx.tourguide.jsonparse.UtilParse;
import com.xbx.tourguide.util.Cookie;
import com.xbx.tourguide.util.JsonUtils;
import com.xbx.tourguide.util.LogUtils;
import com.xbx.tourguide.util.Util;
import com.xbx.tourguide.util.VerifyUtil;
import com.xbx.tourguide.view.CircleImageView;
import com.xbx.tourguide.view.FlowLayout;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by shuzhen on 2016/4/5.
 * <p/>
 * 订单详情
 */
public class MyOrderDetailActivity extends BaseActivity implements View.OnClickListener {

    private ImageButton returnIbtn;
    private String orderNum = "";
    private ImageLoader loader;
    private CircleImageView headPicCiv;
    private TextView nickNameTv, addressTv, orderStatusTv, commentConTv, costSumTv, payTypeTv;
    private FlowLayout tagFlyt;
    private RatingBar starRab;
    private Button refuseBtn, acceptBtn;
    private ImageView phoneIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myorder_detail);
        orderNum = getIntent().getStringExtra("orderId");
        loader = ImageLoader.getInstance();
        initView();
    }

    private void initView() {
        returnIbtn = (ImageButton) findViewById(R.id.ibtn_return);
        headPicCiv = (CircleImageView) findViewById(R.id.civ_headpic);
        nickNameTv = (TextView) findViewById(R.id.tv_username);
        addressTv = (TextView) findViewById(R.id.tv_useradd);
        orderStatusTv = (TextView) findViewById(R.id.tv_order_status);
        commentConTv = (TextView) findViewById(R.id.tv_comment_content);
        tagFlyt = (FlowLayout) findViewById(R.id.flyt_myorder_detail_tag);
        starRab = (RatingBar) findViewById(R.id.rab_myorder_detail);
        refuseBtn = (Button) findViewById(R.id.btn_refuse);
        acceptBtn = (Button) findViewById(R.id.btn_accept);
        costSumTv = (TextView) findViewById(R.id.tv_cost_sum);
        payTypeTv = (TextView) findViewById(R.id.tv_pay_type);
        phoneIv = (ImageView) findViewById(R.id.iv_phone);

        returnIbtn.setOnClickListener(this);
        refuseBtn.setOnClickListener(this);
        acceptBtn.setOnClickListener(this);

        getOrderDetail();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibtn_return:
                finish();
                break;
            case R.id.btn_refuse:
                confirmOrder(0 + "");
                break;
            case R.id.btn_accept:
                confirmOrder(1 + "");
                break;

            default:
                break;
        }
    }

    private String server_type = "";
    private String order_status = "";

    private void getOrderDetail() {
        String url = HttpUrl.MY_ORDER_DETAIL + "?order_number=" + orderNum;
        IRequest.get(this, url, getString(R.string.loding), new RequestBackListener(this) {
            @Override
            public void requestSuccess(String json) {
                LogUtils.i("---getOrderDetail:" + json);
                if (UtilParse.getRequestCode(json) == 1) {
                    final OrderDetailBeans result = JsonUtils.object(UtilParse.getRequestData(json), OrderDetailBeans.class);
                    loader.displayImage(result.getHead_image(), headPicCiv);
                    nickNameTv.setText(result.getNickname());
                    addressTv.setText(result.getEnd_addr());
                    server_type = result.getServer_type();
                    order_status = result.getOrder_status();

                    //0-即时服务：0-待处理订单；1-已接单，未开始；2-服务已开始；3-服务已结束，未付款；4-已支付，未评论；5-订单已结束；6-已取消，未支付；7-已关闭（已取消并支付违约金）
                    //1-预约服务：0-待支付；1-待处理订单；2-已接单，未开始；3-服务已开始；4-服务已结束,未评论；5-已完成；6-已取消，退款进行中；7-已关闭（已取消并退款完成）
                    switch (Integer.valueOf(order_status)) {
                        case 0://待支付
                            if ("1".equals(server_type)) {
                                stateNormal(result);
                            } else if ("0".equals(server_type)) {
                                stateNormal(result);
                                findViewById(R.id.llyt_myorder_detail_button).setVisibility(View.VISIBLE);
                            }
                            break;
                        case 1://待确认
                            if ("1".equals(server_type)) {
                                findViewById(R.id.llyt_person_num).setVisibility(View.VISIBLE);

                                findViewById(R.id.llyt_myorder_detail_deposit).setVisibility(View.VISIBLE);
                                findViewById(R.id.llyt_pay_type).setVisibility(View.VISIBLE);
                                findViewById(R.id.llyt_myorder_detail_button).setVisibility(View.VISIBLE);

                                setText(R.id.tv_order_status, "待确认");
                                setText(R.id.tv_order_time, result.getServer_date());
                                setText(R.id.tv_person_num, result.getNumbers() + "人");
                                setText(R.id.tv_myorder_detail_deposit, result.getOrder_money() + "元");
                                setPayType(result.getPay_type());//1-支付宝，2-微信支付

                                setPhoneOn(result.getMobile());
                            }
                            break;
                        case 2://已预约
                            if ("1".equals(server_type)) {
                                stateNormal(result);
                            }
                            break;
                        case 3:
                            stateNormal(result);
                            if ("0".equals(server_type)) {//未付款
                                setText(R.id.tv_order_status, "未付款");
                            } else if ("1".equals(server_type)) {//进行中
                                setText(R.id.tv_order_status, "进行中");
                            }
                            break;
                        case 4://未评论
                            findViewById(R.id.llyt_cost_sum).setVisibility(View.VISIBLE);
                            findViewById(R.id.llyt_fee).setVisibility(View.VISIBLE);
                            findViewById(R.id.llyt_rebate_money).setVisibility(View.VISIBLE);
                            findViewById(R.id.llyt_cost_total).setVisibility(View.VISIBLE);
                            findViewById(R.id.llyt_pay_type).setVisibility(View.VISIBLE);

                            setText(R.id.tv_order_status, "未评论");
                            setText(R.id.tv_order_time, result.getServer_date());

                            setText(R.id.tv_cost_sum, result.getOrder_money() + "元");
                            setText(R.id.tv_fee, result.getTip_money() + "元");
                            setText(R.id.tv_rebate_money, result.getRebate_money() + "元");
                            setText(R.id.tv_cost_total, result.getPay_money() + "元");
                            setPayType(result.getPay_type());//1-支付宝，2-微信支付

                            break;
                        case 5://已完成
                            findViewById(R.id.llyt_cost_sum).setVisibility(View.VISIBLE);
                            findViewById(R.id.llyt_fee).setVisibility(View.VISIBLE);
                            findViewById(R.id.llyt_rebate_money).setVisibility(View.VISIBLE);
                            findViewById(R.id.llyt_cost_total).setVisibility(View.VISIBLE);
                            findViewById(R.id.llyt_pay_type).setVisibility(View.VISIBLE);
                            findViewById(R.id.llyt_myorder_detail_comment).setVisibility(View.VISIBLE);

                            setText(R.id.tv_order_status, "已完成");
                            setText(R.id.tv_order_time, result.getServer_date());

                            setText(R.id.tv_cost_sum, result.getOrder_money() + "元");
                            setText(R.id.tv_fee, result.getTip_money() + "元");
                            setText(R.id.tv_rebate_money, result.getRebate_money() + "元");
                            setText(R.id.tv_cost_total, result.getPay_money() + "元");
                            setPayType(result.getPay_type());//1-支付宝，2-微信支付

                            if (!VerifyUtil.isNullOrEmpty(result.getContent())) {
                                setText(R.id.tv_comment_content, result.getContent());
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
                            if ("0".equals(server_type)) {//未付违约金
                                stateClose0(result);
                            } else if ("1".equals(server_type)) {//退款中
                                stateClose1(result, "已关闭-退款中");
                            }
                            break;
                        case 7://已关闭
                            if ("0".equals(server_type)) {//已付违约金
                                stateClose0(result);
                            } else if ("1".equals(server_type)) {//已退款
                                stateClose1(result, "已关闭-已退款");
                            }
                            break;
                        case 8://已拒接，退款进行中
                            stateClose1(result, "已拒接-退款中");
                            break;
                        case 9://已关闭（拒单并退款成功）
                            stateClose1(result, "已拒接-已退款");
                            break;
                    }
                }
            }
        });
    }


    /**
     * 即时服务：未付款
     * 预约服务：待支付 已预约 进行中
     */
    private void stateNormal(OrderDetailBeans result) {
        findViewById(R.id.llyt_myorder_detail_deposit).setVisibility(View.VISIBLE);
        findViewById(R.id.llyt_pay_type).setVisibility(View.VISIBLE);

        setText(R.id.tv_order_status, "待支付");
        setText(R.id.tv_order_time, result.getServer_start_time() + "-" + result.getServer_end_time());

        setText(R.id.tv_myorder_detail_deposit, result.getOrder_money() + "元");

        setPayType(result.getPay_type());//1-支付宝，2-微信支付

        setPhoneOn(result.getMobile());
    }

    /**
     * 已关闭
     * 即时服务
     *
     * @param result
     */
    private void stateClose0(OrderDetailBeans result) {
        findViewById(R.id.llyt_order_time).setVisibility(View.GONE);

        findViewById(R.id.llyt_myorder_detail_cancle_money).setVisibility(View.VISIBLE);

        setText(R.id.tv_order_status, "已关闭");
        setText(R.id.tv_cancle_money_name, "违约金");

        if ("0.00".equals(result.getPay_money())) {
            setText(R.id.tv_myorder_detail_cancle_money, "免费");
        } else {
            findViewById(R.id.llyt_pay_type).setVisibility(View.VISIBLE);
            setText(R.id.tv_myorder_detail_cancle_money, result.getPay_money() + "元");
            setPayType(result.getPay_type());//1-支付宝，2-微信支付
        }
    }

    /**
     * 已关闭
     * 预约服务
     *
     * @param result
     */
    private void stateClose1(OrderDetailBeans result, String status) {
        findViewById(R.id.llyt_order_time).setVisibility(View.GONE);

        findViewById(R.id.llyt_myorder_detail_cancle_money).setVisibility(View.VISIBLE);

        setText(R.id.tv_order_status, status);
        setText(R.id.tv_cancle_money_name, "退款金额");
        setText(R.id.tv_myorder_detail_cancle_money, result.getPay_money() + "元");

    }

    /**
     * 设置电话可拨打
     *
     * @param mobile
     */
    private void setPhoneOn(final String mobile) {
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
     * 设置支付方式
     *
     * @param pay_type 1-支付宝，2-微信支付
     */
    private void setPayType(String pay_type) {
        if ("1".equals(pay_type)) {
            payTypeTv.setText(getResources().getString(R.string.alipay));
        } else {
            payTypeTv.setText(getResources().getString(R.string.weixin));
        }
    }

    private void setText(int id, String text) {
        ((TextView) findViewById(id)).setText(text);
    }

    /**
     * 确认/拒绝订单
     *
     * @param tag
     */
    private void confirmOrder(final String tag) {

        RequestParams params = new RequestParams();
        params.put("uid", UserInfoParse.getUid(Cookie.getUserInfo(this)));
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
