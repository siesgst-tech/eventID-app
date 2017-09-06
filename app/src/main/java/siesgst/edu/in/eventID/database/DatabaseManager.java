package siesgst.edu.in.eventID.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

import siesgst.edu.in.eventID.model.EntriesModel;
import siesgst.edu.in.eventID.utils.Constants;

/**
 * Created by rohitramaswamy on 27/07/17.
 */

public class DatabaseManager extends SQLiteOpenHelper {
    private static final String LOG_TAG = DatabaseManager.class.getSimpleName();

    private static final String DB_NAME = "Event-id.db";
    private static final int DB_VERSION = 1;

    private static final String CREATE_ENTRIES_TABLE = "CREATE TABLE " + Constants.ENTRIES_TABLE_NAME + " ( " +
            Constants.ENTRIES_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            Constants.ENTRIES_USER_ID + " VARCHAR DEFAULT NULL, " +
            Constants.ENTRIES_EVENT_ID + " VARCHAR DEFAULT NULL, " +
            Constants.ENTRIES_COST + " VARCHAR DEFAULT NULL, " +
            Constants.ENTRIES_STATUS + " VARCHAR DEFAULT NULL )";


    private static final String CREATE_MESSAGES_TABLE = "CREATE TABLE " + Constants.MESSAGES_TABLE_NAME + "(  " +
            Constants.EVENT_ID + " int(11) NOT NULL, " +
            Constants.MESSAGE_TITLE + " varchar(255) NOT NULL, " +
            Constants.MESSAGE_CONTENT + " varchar(255) NOT NULL )";

    private static final String CREATE_INTERESTED_TABLE = "CREATE TABLE " + Constants.INTERESTED_TABLE_NAME + " ( " +
            Constants.INTERESTED_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            Constants.INTERESTED_USER_ID + " VARCHAR DEFAULT NULL, " +
            Constants.INTERESTED_NAME + " VARCHAR DEFAULT NULL, " +
            Constants.INTERESTED_EMAIL + " VARCHAR DEFAULT NULL, " +
            Constants.INTERESTED_CONTACT + " VARCHAR DEFAULT NULL )";


    public DatabaseManager(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_ENTRIES_TABLE);
        Log.v(LOG_TAG, "CREATE_EVENTS_TABLE=" + CREATE_ENTRIES_TABLE);
        db.execSQL(CREATE_MESSAGES_TABLE);
        Log.v(LOG_TAG, "CREATE_EVENTS_TABLE=" + CREATE_MESSAGES_TABLE);
        db.execSQL(CREATE_INTERESTED_TABLE);
        Log.v(LOG_TAG, "CREATE_EVENTS_TABLE=" + CREATE_INTERESTED_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }

    public void insertInterested(HashMap<String, String> map) {

        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Constants.INTERESTED_TABLE_NAME + " WHERE " + Constants.INTERESTED_USER_ID + " LIKE '" + map.get(Constants.INTERESTED_USER_ID) + "'", null);

        int flag = 0;

        if (cursor.getCount() > 0) {
            flag = 1;
        }

        ContentValues values = new ContentValues();
        //values.put(Constants.INTERESTED_ID, map.get(Constants.INTERESTED_ID));
        values.put(Constants.INTERESTED_USER_ID, map.get(Constants.INTERESTED_USER_ID));
        //values.put(Constants.INTERESTED_EVENT_ID, map.get(Constants.INTERESTED_EVENT_ID));
        values.put(Constants.INTERESTED_NAME, map.get(Constants.INTERESTED_NAME));
        values.put(Constants.INTERESTED_EMAIL, map.get(Constants.INTERESTED_EMAIL));
        values.put(Constants.INTERESTED_CONTACT, map.get(Constants.INTERESTED_CONTACT));

        if (flag == 0) {
            db.insert(Constants.INTERESTED_TABLE_NAME, null, values);

        } else {
            db.update(Constants.INTERESTED_TABLE_NAME, values, Constants.INTERESTED_USER_ID + " LIKE '" + map.get(Constants.INTERESTED_USER_ID) + "'", null);
        }

        cursor.close();
    }

    public ArrayList<EntriesModel> getAllInterested() {

        ArrayList<EntriesModel> messagesList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(Constants.INTERESTED_TABLE_NAME, new String[]{"*"}, null, null, null, null, null);
        //Cursorr cursoror=db.rawQuery("SELECT * FROM "+Constants.NOTIFICATIONS_TABLE_NAME+" ORDER BY "+Constants.NOTIFICATION_TIMESTAMP,null);
        //for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext())
        for (cursor.moveToLast(); !cursor.isBeforeFirst(); cursor.moveToPrevious()) {
            EntriesModel notification_data = new EntriesModel();
            notification_data.setName(cursor.getString(cursor.getColumnIndex(Constants.INTERESTED_NAME)));
            notification_data.setContact(cursor.getString(cursor.getColumnIndex(Constants.INTERESTED_CONTACT)));
            messagesList.add(notification_data);
        }
        cursor.close();
        db.close();
        return messagesList;
    }

}
