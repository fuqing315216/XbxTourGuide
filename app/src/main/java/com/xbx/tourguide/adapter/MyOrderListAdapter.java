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
            holder.dateTv = (TextView) convertView.findViewById(R.id.tv_date_time);
            holder.typeTv = (TextView) convertView.findViewById(R.id.tv_tourist_type);
            holder.statusTv = (TextView) convertView.findViewById(R.id.tv_order_status);
            holder.addressTv = (TextView) convertView.findViewById(R.id.tv_order_address);
            holder.serviceTypeTv = (TextView) convertView.findViewById(R.id.tv_service_type);
            holder.priceTv = (TextView) convertView.findViewById(R.id.tv_service_price);

            convertView.setTag(holder);
        }

        MyOrderBeans bean = mList.get(position);
        holder.addressTv.setText(bean.getEnd_addr());

        holder.dateTv.setText(bean.getOrder_time());
        int order_status = Integer.valueOf(bean.getOrder_status());
        if ("0".equals(bean.getServer_type())) {//0-及时服务，1-预约服务
            holder.serviceTypeTv.setText("即时服务");
            //0-待处理订单；1-已接单，未开始；2-服务已开始；3-服务已结束，未付款；4-已支付，未评论；5-订单已结束；6-已取消，未支付；7-已关闭（已取消并支付违约金）
            switch (order_status){
                case 0:
                    holder.statusTv.setText("待处理");
                    holder.statusTv.setTextColor(ContextCompat.getColor(mContext,R.color.text_color));
                    break;
                case 1:
                    holder.statusTv.setText("进行中");
                    holder.statusTv.setTextColor(ContextCompat.getColor(mContext,R.color.color_3cde3c));
                    break;
                case 2:
                    holder.statusTv.setText("进行中");
                    holder.statusTv.setTextColor(ContextCompat.getColor(mContext,R.color.forgetpass_color));
                    break;
                case 3:
                    holder.statusTv.setText("未付款");
                    holder.statusTv.setTextColor(ContextCompat.getColor(mContext,R.color.forgetpass_color));
                    break;
                case 4:
                    holder.statusTv.setText("未评论");
                    holder.statusTv.setTextColor(ContextCompat.getColor(mContext,R.color.forgetpass_color));
                    break;
                case 5:
                    holder.statusTv.setText("已完成");
                    holder.statusTv.setTextColor(ContextCompat.getColor(mContext,R.color.text_color));
                    break;
                case 6:
                    holder.statusTv.setText("已关闭");
                    holder.statusTv.setTextColor(ContextCompat.getColor(mContext,R.color.gray_color));
                    break;
                case 7:
                    holder.statusTv.setText("已关闭");
                    holder.statusTv.setTextColor(ContextCompat.getColor(mContext,R.color.gray_color));
                    break;
            }
        } else {
            holder.serviceTypeTv.setText("预约服务");
            //预约服务：0-待支付；1-待处理订单；2-已接单，未开始；3-服务已开始；4-服务已结束,未评论；5-已完成；6-已取消，退款进行中；7-已关闭（已取消并退款完成）
            switch (order_status){
                case 0:
                    holder.statusTv.setText("待支付");
                    holder.statusTv.setTextColor(ContextCompat.getColor(mContext,R.color.text_color));
                    break;
                case 1:
                    holder.statusTv.setText("待确认");
                    holder.statusTv.setTextColor(ContextCompat.getColor(mContext,R.color.text_color));
                    break;
                case 2:
                    holder.statusTv.setText("已预约");
                    holder.statusTv.setTextColor(ContextCompat.getColor(mContext,R.color.color_3cde3c));
                    break;
                case 3:
                    holder.statusTv.setText("进行中");
                    holder.statusTv.setTextColor(ContextCompat.getColor(mContext,R.color.forgetpass_color));
                    break;
                case 4:
                    holder.statusTv.setText("未评论");
                    holder.statusTv.setTextColor(ContextCompat.getColor(mContext,R.color.forgetpass_color));
                    break;
                case 5:
                    holder.statusTv.setText("已完成");
                    holder.statusTv.setTextColor(ContextCompat.getColor(mContext,R.color.text_color));
                    break;
                case 6:
                    holder.statusTv.setText("已关闭");
                    holder.statusTv.setTextColor(ContextCompat.getColor(mContext,R.color.text_color));
                    break;
                case 7:
                    holder.statusTv.setText("已关闭");
                    holder.statusTv.setTextColor(ContextCompat.getColor(mContext,R.color.gray_color));
                    break;
            }
        }

        if ("0".equals(bean.getPay_status()) && !"0.00".equals(bean.getPay_money())) {//0-未支付，1-未评论，2-已完成
            holder.priceTv.setVisibility(View.VISIBLE);
            holder.priceTv.setText("¥" + bean.getPay_money());
        } else {
            holder.priceTv.setVisibility(View.GONE);
        }
        return convertView;
    }

    private static class ViewHolder {

        private TextView dateTv, typeTv, statusTv, addressTv, serviceTypeTv, priceTv;

    }
}
