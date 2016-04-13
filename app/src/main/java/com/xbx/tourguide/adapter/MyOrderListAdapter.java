package com.xbx.tourguide.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xbx.tourguide.R;
import com.xbx.tourguide.base.BaseMyAdapter;
import com.xbx.tourguide.beans.MyOrderBeans;

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

        Date d = new Date(Long.parseLong(bean.getOrder_time()));
        SimpleDateFormat sf = new SimpleDateFormat("MM月dd日 hh:mm");
        holder.dateTv.setText(sf.format(d));
        if ("0".equals(bean.getServer_type())) {//0-及时服务，1-预约服务
            holder.serviceTypeTv.setText("即时服务");
        } else {
            holder.serviceTypeTv.setText("预约服务");
        }

        if ("0".equals(bean.getServer_status())) {//0-未开始，1-已取消，2-服务进行中，3-服务结束
            holder.statusTv.setText("待处理");
        } else if ("1".equals(bean.getServer_status())) {
            holder.statusTv.setText("已关闭");
        } else if ("2".equals(bean.getServer_status())) {
            holder.statusTv.setText("进行中");
            holder.statusTv.setTextColor(mContext.getResources().getColor(R.color.forgetpass_color));
        } else if ("3".equals(bean.getServer_status())) {
            holder.statusTv.setText("已完成");
        }

        if ("0".equals(bean.getPay_status()) &&!"0.00".equals(bean.getPay_money())) {//0-未支付，1-未评论，2-已完成
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
