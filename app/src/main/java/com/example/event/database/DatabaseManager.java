package com.example.event.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by rohitramaswamy on 27/07/17.
 */

public class DatabaseManager extends SQLiteOpenHelper
{
	
	private static final String DB_NAME = "Event-id DB";
	private static final int DB_VERSION = 1;
	
	public DatabaseManager(Context context)
	{
		super(context, DB_NAME, null, DB_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase sqLiteDatabase)
	{
		
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
	{
		
	}
}
