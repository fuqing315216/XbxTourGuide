package com.xbx.tourguide.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xbx.tourguide.R;
import com.xbx.tourguide.base.BaseMyAdapter;
import com.xbx.tourguide.beans.MyOrderBeans;

import java.util.List;

/**
 * Created by shuzhen on 2016/4/1.
 *
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

            convertView.setTag(holder);
        }

        MyOrderBeans beans = mList.get(position);

        holder.dateTv.setText(beans.getOrderDate());
        if (beans.getTouristType() == 0) {
            holder.typeTv.setText(mContext.getResources().getString(R.string.guide));
        } else if (beans.getTouristType() == 1) {
            holder.typeTv.setText(mContext.getResources().getString(R.string.accompany));
        } else if (beans.getTouristType() == 2) {
            holder.typeTv.setText(mContext.getResources().getString(R.string.natival));
        }

        if (beans.getOrderStatus() == 0) {
            holder.statusTv.setText(mContext.getResources().getString(R.string.finished));
            holder.statusTv.setTextColor(mContext.getResources().getColor(R.color.text_color));
        } else if (beans.getOrderStatus() == 1) {
            holder.statusTv.setText(mContext.getResources().getString(R.string.ordered));
            holder.statusTv.setTextColor(mContext.getResources().getColor(R.color.color_3cde3c));
        } else if (beans.getOrderStatus() == 2) {
            holder.statusTv.setText(mContext.getResources().getString(R.string.going));
            holder.statusTv.setTextColor(mContext.getResources().getColor(R.color.forgetpass_color));
        } else if (beans.getOrderStatus() == 3) {
            holder.statusTv.setText(mContext.getResources().getString(R.string.closed));
            holder.statusTv.setTextColor(mContext.getResources().getColor(R.color.gray_color));
        }
        holder.addressTv.setText(beans.getOrderAddress());

        return convertView;
    }

    private static class ViewHolder {

        private TextView dateTv, typeTv, statusTv, addressTv;

    }
}
