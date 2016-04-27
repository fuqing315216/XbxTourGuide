package com.xbx.tourguide.db;

import android.content.ContentValues;

import com.xbx.tourguide.beans.SQLiteOrderBean;

import java.util.List;

/**
 * Created by xbx on 2016/4/19.
 */
public interface OrderNumberService {
    boolean insertLast(ContentValues values);

    SQLiteOrderBean selectFirst();

    void deleteFirst(String _id);

    void clear();

    List<SQLiteOrderBean> selectAll();
}
