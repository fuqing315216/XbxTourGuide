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
 * <p/>
 * 我的订单adapter
 */
public class PayDetailListAdapter extends BaseMyAdapter<MyOrderBeans> {

    public PayDetailListAdapter(Context con, List<MyOrderBeans> list) {
        super(con, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_listview_paydetail, null);
        }

        ViewHolder holder = (ViewHolder) convertView.getTag();
        if (holder == null) {
            holder = new ViewHolder();
            holder.dateTv = (TextView) convertView.findViewById(R.id.tv_pay_detail_date);
            holder.typeTv = (TextView) convertView.findViewById(R.id.tv_pay_detail_type);
            holder.priceTv = (TextView) convertView.findViewById(R.id.tv_pay_detail_price);

            convertView.setTag(holder);
        }
        return convertView;
    }

    private static class ViewHolder {
        private TextView dateTv, typeTv, priceTv;
    }
}
