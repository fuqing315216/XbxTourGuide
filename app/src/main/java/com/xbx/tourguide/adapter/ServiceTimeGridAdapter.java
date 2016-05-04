package com.xbx.tourguide.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xbx.tourguide.R;
import com.xbx.tourguide.base.BaseMyAdapter;
import com.xbx.tourguide.beans.DateBeans;
import com.xbx.tourguide.beans.ServiceTimeBeans;
import com.xbx.tourguide.util.VerifyUtil;

import java.util.Date;
import java.util.List;

/**
 * Created by shuzhen on 2016/4/7.
 * <p>
 * 服务时间设置adapter
 */
public class ServiceTimeGridAdapter extends BaseMyAdapter<ServiceTimeBeans> {

    public ServiceTimeGridAdapter(Context con, List<ServiceTimeBeans> list) {
        super(con, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_gridview_servicetime, null);
        }

        TextView date = (TextView) convertView.findViewById(R.id.tv_date);

        ServiceTimeBeans serviceTimeBeans = mList.get(position);
        DateBeans dateBeans = serviceTimeBeans.getDate();


        if (serviceTimeBeans.isSelected()) {
            date.setTextColor(ContextCompat.getColor(mContext, R.color.head_bg_color));
        } else {
            date.setTextColor(ContextCompat.getColor(mContext, R.color.gray_color));
        }

        if (serviceTimeBeans.isDay()) {
            if (dateBeans != null && !VerifyUtil.isNullOrEmpty(dateBeans.getDate())) {
                date.setText(dateBeans.getDate());
            } else {
                date.setText("");
            }
        } else {
            date.setText("");
        }


        return convertView;
    }
}
