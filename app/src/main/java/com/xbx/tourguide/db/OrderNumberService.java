package com.xbx.tourguide.db;

import android.content.ContentValues;

import java.util.List;

/**
 * Created by xbx on 2016/4/19.
 */
public interface OrderNumberService {
    boolean insertLast(ContentValues values);
    String selectFirst();
    void deleteFirst();
    void clear();
    List<String> selectAll();
}
