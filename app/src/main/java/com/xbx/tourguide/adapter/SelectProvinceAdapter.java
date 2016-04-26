package com.xbx.tourguide.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xbx.tourguide.R;
import com.xbx.tourguide.base.BaseMyAdapter;
import com.xbx.tourguide.beans.ProvinceBeans;

import java.util.List;

/**
 * Created by shuzhen on 2016/4/14.
 * <p/>
 * 选择城市adapter
 */
public class SelectProvinceAdapter extends BaseMyAdapter<ProvinceBeans> {

    public SelectProvinceAdapter(Context con, List<ProvinceBeans> list) {
        super(con, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_selectcity, null);
        }

        TextView cityTv = (TextView) convertView.findViewById(R.id.tv_city);

        cityTv.setText(mList.get(position).getName());

        return convertView;
    }
}
