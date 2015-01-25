package com.example.ex08;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DB extends SQLiteOpenHelper {
	
	private final static String DATABASE_NAME = "phone.db";
	private final static int DATABASE_VERSION = 1;
	
	
	private final static String TABLE_NAME = "phone_number";
	public final static String ID = "task_id";
	public final static String COL1 = "name";
	public final static String COL2 = "phone";
	public final static String COL3 = "sex";
	public final static String COL4 = "remak";
	
	

	public DB(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
//		String sql = "CREATE TABLE " + TABLE_NAME + "(" + BOOK_ID + " INTEGER primary key autoincrement, "
//				+ BOOK_NAME + " text, " + BOOK_AUTHOR + " text);";
		String sql = "CREATE TABLE " + TABLE_NAME + "(" + ID + " INTEGER primary key autoincrement, "
				+ COL1 + " text, " + COL2 + " text, "+ COL3 + " text, " + COL4 + " text);";
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
		db.execSQL(sql);
		onCreate(db);
	}
	
	public Cursor select() {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
		return cursor;
	}
	
	// task
	public Cursor select(String table_name) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(table_name, null, null, null, null, null, null);
		return cursor;
	}
	
	
	// task
	public long insert(String name, String sex, String phone, String remark) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put(COL1, name);
		cv.put(COL2, sex);
		cv.put(COL3, phone);
		cv.put(COL4, remark);
		long row = db.insert(TABLE_NAME, null, cv);
		return row;
	}
	
	public void delete(int id) {
		SQLiteDatabase db = this.getWritableDatabase();
		String where = ID + " =?";
		String[] whereValue = {Integer.toString(id)};
		db.delete(TABLE_NAME, where, whereValue);
	}
	
	// task
	public void delete(String table_name, int id) {
		SQLiteDatabase db = this.getWritableDatabase();
		String where = ID + " =?";
		String[] whereValue = {Integer.toString(id)};
		db.delete(table_name, where, whereValue);
	}
	
	// task
	public void update (int id, String name, String sex, String phone, String remark) {
		SQLiteDatabase db = this.getWritableDatabase();
		String where = ID + " =?";
		String[] whereValue = {Integer.toString(id)};
		
		ContentValues cv = new ContentValues();
		cv.put(COL1, name);
		cv.put(COL2, sex);
		cv.put(COL3, phone);
		cv.put(COL4, remark);
		db.update(TABLE_NAME, cv, where, whereValue);
	}
	
	public Cursor select(String table_name, String value) {
		String sql = "select * from " + table_name + " where task_value like ?";
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(sql, new String[]{"%"+value+"%"});
		return cursor;
	}

}