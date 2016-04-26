package com.xbx.tourguide.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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
    public String selectFirst() {
        String orderNum = "";
        SQLiteDatabase database = null;
        try {
            database = dbOpenHelper.getReadableDatabase();
            String sql = "select num from order_number where _id = (select max(_id) from order_number)";
            Cursor cursor = database.rawQuery(sql, new String[]{});
            while (cursor.moveToNext()) {
                orderNum = cursor.getString(0);
            }

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            if (database != null) {
                database.close();
            }
        }
        LogUtils.e("---selectFirst_orderNum:" + orderNum);
        return orderNum;
    }

    @Override
    public void deleteFirst() {
        SQLiteDatabase database = null;
        try {
            database = dbOpenHelper.getWritableDatabase();
            database.execSQL("delete from order_number where _id = (select max(_id) from order_number)");
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
    public List<String> selectAll() {
        SQLiteDatabase database = null;
        Cursor cursor = null;
        List<String> list = new ArrayList<>();

        try {
            database = dbOpenHelper.getWritableDatabase();
            cursor = database.rawQuery("select num from order_number", new String[]{});

            while (cursor.moveToNext()) {
                list.add(cursor.getString(0));
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
        LogUtils.e("---list:" + list);
        return list;
    }
}
