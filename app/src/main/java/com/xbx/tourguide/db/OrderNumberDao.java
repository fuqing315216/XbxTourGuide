package com.xbx.tourguide.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.xbx.tourguide.beans.SQLiteOrderBean;
import com.xbx.tourguide.util.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xbx on 2016/4/19.
 */
public class OrderNumberDao implements OrderNumberService {
    private DbOpenHelper dbOpenHelper = null;
    private boolean flag = false;
    private long id = -1;

    public OrderNumberDao(Context context) {
        dbOpenHelper = new DbOpenHelper(context);
    }


    @Override
    public boolean insertLast(ContentValues values) {
        SQLiteDatabase database = null;
        try {
            database = dbOpenHelper.getWritableDatabase();
            LogUtils.e("---values:" + values.toString());
            id = database.insert("order_number", null, values);
            flag = (id > 0 ? true : false);
        } catch (Exception e) {
            LogUtils.e("---Exception:" + e.getMessage());
            e.printStackTrace();
        } finally {
            if (database != null) {
                database.close();
            }
        }
        LogUtils.e("---flag:" + flag);
        return flag;
    }

    @Override
    public SQLiteOrderBean selectFirst() {
        SQLiteOrderBean bean = null;
        SQLiteDatabase database = null;
        try {
            database = dbOpenHelper.getReadableDatabase();
            bean = new SQLiteOrderBean();
            String sql = "select * from order_number where _id = (select max(_id) from order_number)";
            Cursor cursor = database.rawQuery(sql, new String[]{});
            while (cursor.moveToNext()) {
                bean.set_id(cursor.getInt(0) + "");
                bean.setNum(cursor.getString(1));
                bean.setDate(cursor.getString(2));
            }

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            if (database != null) {
                database.close();
            }
        }
        LogUtils.e("---selectFirst:" + bean.toString());
        return bean;
    }

    @Override
    public void deleteFirst(String _id) {
        SQLiteDatabase database = null;
        try {
            database = dbOpenHelper.getWritableDatabase();
            database.execSQL("delete from order_number where _id = " + _id);
//            flag = (id > 0 ? true : false);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (database != null) {
                database.close();
            }
        }
//        return flag;
    }

    @Override
    public void clear() {
        SQLiteDatabase database = null;
        try {
            database = dbOpenHelper.getReadableDatabase();
            database.execSQL("delete from order_number");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (database != null) {
                database.close();
            }
        }
    }

    @Override
    public List<SQLiteOrderBean> selectAll() {
        SQLiteDatabase database = null;
        Cursor cursor = null;
        List<SQLiteOrderBean> list = new ArrayList<>();

        try {
            database = dbOpenHelper.getWritableDatabase();
            cursor = database.rawQuery("select * from order_number", new String[]{});

            while (cursor.moveToNext()) {
                SQLiteOrderBean bean = new SQLiteOrderBean();
                bean.set_id(cursor.getInt(0) + "");
                bean.setNum(cursor.getString(1));
                bean.setDate(cursor.getString(2));
                list.add(bean);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (database != null) {
                database.close();
            }

            if (cursor != null) {
                cursor.close();
            }
        }
        LogUtils.e("---list:" + list.toString());
        return list;
    }
}
