package com.xbx.tourguide.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xbx.tourguide.R;
import com.xbx.tourguide.base.BaseMyAdapter;
import com.xbx.tourguide.beans.MyOrderBeans;
import com.xbx.tourguide.beans.TourGuideInfoBeans;
import com.xbx.tourguide.jsonparse.UserInfoParse;
import com.xbx.tourguide.util.Cookie;
import com.xbx.tourguide.util.LogUtils;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by shuzhen on 2016/4/1.
 * <p/>
 * 我的订单adapter
 */
public class MyOrderListAdapter extends BaseMyAdapter<MyOrderBeans> {

    public MyOrderListAdapter(Context con, List<MyOrderBeans> list) {
        super(con, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_listview_myorder, null);
        }

        ViewHolder holder = (ViewHolder) convertView.getTag();
        if (holder == null) {
            holder = new ViewHolder();
            holder.dateTv = (TextView) convertView.findViewById(R.id.tv_order_list_date);
            holder.typeTv = (TextView) convertView.findViewById(R.id.tv_order_list_type);
            holder.guideTypeTv = (TextView) convertView.findViewById(R.id.tv_order_list_guide_type);
            holder.statusTv = (TextView) convertView.findViewById(R.id.tv_order_list_status);
            holder.addressTv = (TextView) convertView.findViewById(R.id.tv_order_list_address);
            holder.serviceTypeTv = (TextView) convertView.findViewById(R.id.tv_order_list_service_type);
            holder.priceTv = (TextView) convertView.findViewById(R.id.tv_order_list_price);

            convertView.setTag(holder);
        }

        MyOrderBeans bean = mList.get(position);
        holder.addressTv.setText(bean.getEnd_addr());
        holder.dateTv.setText(bean.getOrder_time());

        //导游类型
        String userType = UserInfoParse.getUserInfo(Cookie.getUserInfo(mContext)).getGuide_type();
        if ("1".equals(userType)) {//1：导游；2：向导；3：土著
            holder.guideTypeTv.setText("导");
            holder.guideTypeTv.setBackgroundResource(R.drawable.bg_order_list_guide);
        } else if ("2".equals(userType)) {
            holder.guideTypeTv.setText("向");
            holder.guideTypeTv.setBackgroundResource(R.drawable.bg_order_list_guide2);
        }

        //服务类型
        String service_type = bean.getServer_type();
        int order_status = Integer.valueOf(bean.getOrder_status());

        if ("0".equals(service_type)) {
            if ("1".equals(userType)) {//1：导游；2：向导；3：土著
                holder.typeTv.setText("导游即时服务");
            } else if ("2".equals(userType)) {
                holder.typeTv.setText("向导即时服务");
            }
        } else {
            holder.typeTv.setText("导游预约服务");
        }

        if ("0".equals(service_type)) {//0-及时服务，1-预约服务
            holder.serviceTypeTv.setText("即");
            holder.serviceTypeTv.setBackgroundResource(R.drawable.bg_order_list_service_instant);
            //即时服务：0-待处理订单；1-已接单，未开始；2-服务已开始；3-服务已结束，未付款；4-已支付，未评论；5-订单已结束；6-已取消，未支付；7-已关闭（已取消并支付违约金）
            switch (order_status) {
                case 0:
                    holder.statusTv.setText("待处理");
                    holder.statusTv.setTextColor(ContextCompat.getColor(mContext, R.color.text_color));
                    setPrice(holder.priceTv, bean.getPay_money(), false);
                    break;
                case 1:
                    holder.statusTv.setText("进行中");
                    holder.statusTv.setTextColor(ContextCompat.getColor(mContext, R.color.forgetpass_color));
                    setPrice(holder.priceTv, bean.getPay_money(), false);
                    break;
                case 2:
                    holder.statusTv.setText("进行中");
                    holder.statusTv.setTextColor(ContextCompat.getColor(mContext, R.color.forgetpass_color));
                    setPrice(holder.priceTv, bean.getPay_money(), false);
                    break;
                case 3:
                    holder.statusTv.setText("待支付");
                    holder.statusTv.setTextColor(ContextCompat.getColor(mContext, R.color.text_color));
                    setPrice(holder.priceTv, bean.getPay_money(), true);
                    break;
                case 4:
                    holder.statusTv.setText("未评论");
                    holder.statusTv.setTextColor(ContextCompat.getColor(mContext, R.color.text_color));
                    setPrice(holder.priceTv, bean.getPay_money(), true);
                    break;
                case 5:
                    holder.statusTv.setText("已完成");
                    holder.statusTv.setTextColor(ContextCompat.getColor(mContext, R.color.text_color));
                    setPrice(holder.priceTv, bean.getPay_money(), true);
                    break;
                case 6://未付违约金
                    holder.statusTv.setText("已关闭");
                    holder.statusTv.setTextColor(ContextCompat.getColor(mContext, R.color.text_color));
                    setPrice(holder.priceTv, bean.getPay_money(), true);
                    break;
                case 7://已付违约金
                    holder.statusTv.setText("已关闭");
                    holder.statusTv.setTextColor(ContextCompat.getColor(mContext, R.color.text_color));
                    setPrice(holder.priceTv, bean.getPay_money(), true);
                    break;
            }
        } else {
            holder.serviceTypeTv.setText("预");
            holder.serviceTypeTv.setBackgroundResource(R.drawable.bg_order_list_service_appointment);
            //预约服务：0-待支付；1-待处理订单；2-已接单，未开始；3-服务已开始；4-服务已结束,未评论；5-已完成；6-已取消，退款进行中；7-已关闭（已取消并退款完成）
            //8-已拒接，退款进行中；9-已关闭（拒单并退款成功）
            switch (order_status) {
                case 0:
                    holder.statusTv.setText("待支付");
                    holder.statusTv.setTextColor(ContextCompat.getColor(mContext, R.color.text_color));
                    break;
                case 1:
                    holder.statusTv.setText("待处理");
                    holder.statusTv.setTextColor(ContextCompat.getColor(mContext, R.color.text_color));
                    break;
                case 2:
                    holder.statusTv.setText("已预约");
                    holder.statusTv.setTextColor(ContextCompat.getColor(mContext, R.color.text_color));
                    break;
                case 3:
                    holder.statusTv.setText("进行中");
                    holder.statusTv.setTextColor(ContextCompat.getColor(mContext, R.color.forgetpass_color));
                    break;
                case 4:
                    holder.statusTv.setText("未评论");
                    holder.statusTv.setTextColor(ContextCompat.getColor(mContext, R.color.text_color));
                    break;
                case 5:
                    holder.statusTv.setText("已完成");
                    holder.statusTv.setTextColor(ContextCompat.getColor(mContext, R.color.text_color));
                    break;
                case 6://已取消（退款中）
                    holder.statusTv.setText("已关闭");
                    holder.statusTv.setTextColor(ContextCompat.getColor(mContext, R.color.forgetpass_color));
                    break;
                case 7:
                    holder.statusTv.setText("已关闭");
                    holder.statusTv.setTextColor(ContextCompat.getColor(mContext, R.color.text_color));
                    break;
                case 8://已关闭（拒单并退款中）
                    holder.statusTv.setText("已拒接");
                    holder.statusTv.setTextColor(ContextCompat.getColor(mContext, R.color.forgetpass_color));
                    break;
                case 9://已关闭（拒单并退款成功）
                    holder.statusTv.setText("已关闭");
                    holder.statusTv.setTextColor(ContextCompat.getColor(mContext, R.color.text_color));
                    break;
            }
            setPrice(holder.priceTv, bean.getPay_money(), true);
        }

        return convertView;
    }

    private void setPrice(TextView priceTv, String price, boolean isShow) {
        if (isShow) {
            priceTv.setVisibility(View.VISIBLE);
            priceTv.setText("金额 ¥ " + price);
        } else {
            priceTv.setVisibility(View.GONE);
        }
    }

    private static class ViewHolder {
        private TextView dateTv, typeTv, guideTypeTv, statusTv, addressTv, serviceTypeTv, priceTv;
    }
}
