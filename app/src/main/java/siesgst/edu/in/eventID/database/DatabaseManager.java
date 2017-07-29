package siesgst.edu.in.eventID.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import siesgst.edu.in.eventID.model.EventsModel;
import siesgst.edu.in.eventID.utils.Constants;

/**
 * Created by rohitramaswamy on 27/07/17.
 */

public class DatabaseManager extends SQLiteOpenHelper
{
	private static final String LOG_TAG = DatabaseManager.class.getSimpleName();
	
	private static final String DB_NAME = "Event-id.db";
	private static final int DB_VERSION = 1;
	
	private static final String CREATE_ENTRIES_TABLE = "CREATE TABLE " + Constants.ENTRIES_TABLE_NAME + " ( " +
			Constants.ENTRIES_ID + " int(10) UNSIGNED NOT NULL, " +
			Constants.ENTRIES_USER_ID + " int(11) NOT NULL, " +
			Constants.EVENT_ID + " int(11) NOT NULL, " +
			Constants.ENTRIES_COST + " int(11) NOT NULL, " +
			Constants.ENTRIES_STATUS + " enum('1','0') NOT NULL )";
	
	
	private static final String CREATE_EVENTS_TABLE = "CREATE TABLE " + Constants.EVENTS_TABLE_NAME + " ( " +
			Constants.EVENT_ID + " int(10) UNSIGNED NOT NULL, " +
			Constants.EVENT_NAME + " varchar(255) NOT NULL, " +
			Constants.EVENT_DESCRIPTION + " varchar(255) NOT NULL, " +
			Constants.EVENT_ABOUT + " longtext NOT NULL, " +
			Constants.EVENT_RULES + " longtext NOT NULL, " +
			Constants.EVENT_GAMEPLAY + " longtext NOT NULL, " +
			Constants.EVENT_HEAD + " mediumtext NOT NULL, " +
			Constants.EVENT_COST + " int(11) NOT NULL ) ";
	
	
	private static final String CREATE_MESSAGES_TABLE = "CREATE TABLE " + Constants.MESSAGES_TABLE_NAME + "(  " +
			Constants.EVENT_ID + " int(11) NOT NULL, " +
			Constants.MESSAGE_TITLE + " varchar(255) NOT NULL, " +
			Constants.MESSAGE_MESSAGE + " varchar(255) NOT NULL )";
	
	
	public DatabaseManager(Context context)
	{
		super(context, DB_NAME, null, DB_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db)
	{
		db.execSQL(CREATE_EVENTS_TABLE);
		Log.v(LOG_TAG, "CREATE_EVENTS_TABLE=" + CREATE_EVENTS_TABLE);
		db.execSQL(CREATE_ENTRIES_TABLE);
		Log.v(LOG_TAG, "CREATE_EVENTS_TABLE=" + CREATE_EVENTS_TABLE);
		db.execSQL(CREATE_MESSAGES_TABLE);
		Log.v(LOG_TAG, "CREATE_EVENTS_TABLE=" + CREATE_EVENTS_TABLE);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int i, int i1)
	{
		
	}
	
	public void insertEvents(HashMap<String, String> events)
	{
		SQLiteDatabase sqLiteDatabase = getWritableDatabase();
		Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + Constants.EVENTS_TABLE_NAME + " WHERE "
				+ Constants.EVENT_ID + " LIKE '" + events.get(Constants.EVENT_ID) + "'", null);
		int flag = 0;
		if (cursor.getCount() > 0)
		{ flag = 1; }
		
		ContentValues contentValues = new ContentValues();
		contentValues.put(Constants.EVENT_ID, events.get(Constants.EVENT_ID));
		contentValues.put(Constants.EVENT_NAME, events.get(Constants.EVENT_NAME));
		contentValues.put(Constants.EVENT_DESCRIPTION, events.get(Constants.EVENT_DESCRIPTION));
		contentValues.put(Constants.EVENT_ABOUT, events.get(Constants.EVENT_ABOUT));
		contentValues.put(Constants.EVENT_RULES, events.get(Constants.EVENT_RULES));
		contentValues.put(Constants.EVENT_GAMEPLAY, events.get(Constants.EVENT_GAMEPLAY));
		contentValues.put(Constants.ENTRIES_COST, events.get(Constants.ENTRIES_COST));
		contentValues.put(Constants.EVENT_HEAD, events.get(Constants.EVENT_HEAD));
		
		
		if (flag == 0)
		{
			sqLiteDatabase.insert(Constants.EVENTS_TABLE_NAME, null, contentValues);
		}
		else
		{
			sqLiteDatabase.update(Constants.EVENTS_TABLE_NAME, contentValues, Constants.EVENT_ID + " LIKE '" +
					events.get(Constants.EVENT_ID) + "'", null);
		}
		
		cursor.close();
	}
	
	
	public List<EventsModel> getAllEvents()
	{
		SQLiteDatabase db = getWritableDatabase();
		List<EventsModel> events = new ArrayList<>();
		Cursor eventCursor = db.rawQuery("SELECT * FROM " + Constants.EVENTS_TABLE_NAME, null);
		
		for (eventCursor.moveToFirst(); !eventCursor.isAfterLast(); eventCursor.moveToNext())
		{
			EventsModel model = new EventsModel();
			model.setEventName(eventCursor.getString(eventCursor.getColumnIndex(Constants.EVENT_NAME)));
			model.setCost(eventCursor.getString(eventCursor.getColumnIndex(Constants.EVENT_COST)));
			model.setDescription(eventCursor.getString(eventCursor.getColumnIndex(Constants.EVENT_DESCRIPTION)));
			events.add(model);
		}
		eventCursor.close();
		return events;
	}
}
