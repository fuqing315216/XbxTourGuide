package com.xbx.tourguide.base;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Adapter基础类
 * 
 * @author Alan
 *
 * @param <T>
 */
public abstract class BaseMyAdapter<T> extends BaseAdapter {
	protected LayoutInflater mInflater;
	protected List<T> mList;
	protected Context mContext;

	public BaseMyAdapter(Context con, List<T> list) {
		this.mInflater = LayoutInflater.from(con);
		this.mContext = con;
		this.mList = list;
	}

	public void update(List<T> list) {
		this.mList = list;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mList == null ? 0 : mList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public abstract View getView(int position, View convertView,
			ViewGroup parent);

}
