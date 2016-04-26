package com.xbx.tourguide.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbOpenHelper extends SQLiteOpenHelper {
	static String DbName = "xbxguide.db";
	static int Version = 1;
	
	public DbOpenHelper(Context context){
		super(context,DbName, null, Version);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		//create table
		db.execSQL("CREATE TABLE IF NOT EXISTS order_number(_id INTEGER PRIMARY KEY AUTOINCREMENT,num VARCHAR(100))");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		Log.i("TAG", "db.onUpgrade()");
		//当版本变化时系统会调用该回调方法
		db.execSQL("DROP TABLE IF EXISTS order_number");
		//此处是删除数据表，在实际业务中数据是要备份的
		onCreate(db);
		//调用oncreat（db）重新创建数据表 也可以根据自己的业务需要创建新的数据表
	}
}
