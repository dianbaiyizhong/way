package org.hhm.way.db.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hhm.way.db.DatabaseHelper;
import org.hhm.way.db.pojo.Log;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class LogImpl {
	SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public boolean Insert(String title, String content, Context context) {
		DatabaseHelper database = new DatabaseHelper(context);
		SQLiteDatabase db = null;

		db = database.getReadableDatabase();

		ContentValues cv = new ContentValues();
		cv.put("title", title);
		cv.put("content", content);
		cv.put("date", sm.format(new Date()));
		try {
			db.insert("log", null, cv);

			return true;

		} catch (Exception e) {
			e.printStackTrace();

			return false;

		}

	}

	public List<Log> get(Context context) {
		DatabaseHelper database = new DatabaseHelper(context);

		SQLiteDatabase db = database.getReadableDatabase();
		List<Log> list = new ArrayList<Log>();
		Cursor c = db.query("log", null, null, null, null, null, null);// 查询并获得游标
		if (c.moveToFirst()) {// 判断游标是否为空
			for (int i = 0; i < c.getCount(); i++) {
				c.moveToPosition(i);// 移动到指定记录
				Log log = new Log();
				String title = c.getString(c.getColumnIndex("title"));
				String content = c.getString(c.getColumnIndex("content"));
				String date = c.getString(c.getColumnIndex("date"));
				log.setTitle(title);
				log.setContent(content);
				log.setDate(date);
				list.add(log);

			}
		}

		return list;

	}

	public void Delete(Context context) {
		DatabaseHelper database = new DatabaseHelper(context);

		SQLiteDatabase db = database.getReadableDatabase();
		db.delete("log", null, null);
	}

}
