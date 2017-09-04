package siesgst.edu.in.eventID.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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
	
	
	private static final String CREATE_MESSAGES_TABLE = "CREATE TABLE " + Constants.MESSAGES_TABLE_NAME + "(  " +
			Constants.EVENT_ID + " int(11) NOT NULL, " +
			Constants.MESSAGE_TITLE + " varchar(255) NOT NULL, " +
			Constants.MESSAGE_MESSAGE + " varchar(255) NOT NULL )";
	
	private static String CREATE_INTERESTED_TABLE;
	
	
	public DatabaseManager(Context context)
	{
		super(context, DB_NAME, null, DB_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db)
	{
		db.execSQL(CREATE_ENTRIES_TABLE);
		Log.v(LOG_TAG, "CREATE_EVENTS_TABLE=" + CREATE_ENTRIES_TABLE);
		db.execSQL(CREATE_MESSAGES_TABLE);
		Log.v(LOG_TAG, "CREATE_EVENTS_TABLE=" + CREATE_MESSAGES_TABLE);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int i, int i1)
	{
		
	}
	
}
