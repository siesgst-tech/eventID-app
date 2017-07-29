package siesgst.edu.in.eventID.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import siesgst.edu.in.eventID.activities.LoginActivity;

/**
 * Created by rohitramaswamy on 23/07/17.
 */

public class SessionManager
{
	private static final String LOG_TAG = SessionManager.class.getSimpleName();
	
	// defining keys
	private static final String IS_LOGIN = "false";
	private static final String EMAIL = "ANDROID.STUDIO@ANDROID.COM";
	private static final String FIRST_NAME = "ANDROID";
	private static final String LAST_NAME = "STUDIO";
	private static final String FULL_NAME = "ANDROID STUDIO";
	public static final String PREF_NAME = "EVENT_ID";
	private static final String EVENT_NAME = "EVENT_NAME";
	public static final int PRIVATE_MODE = 0;
	
	Context context;
	
	SharedPreferences sharedPreferences;
	
	SharedPreferences.Editor editor;
	
	public SessionManager(Context context)
	{
		this.context = context;
		sharedPreferences = context.getSharedPreferences(PREF_NAME,PRIVATE_MODE);
		editor = sharedPreferences.edit();
	}
	
	// method to initialise login
	public void createLoginSession(String email, String Fname, String Lname,String event_name)
	{
		String name;
		editor.putString(EMAIL, email);
		editor.putString(FIRST_NAME, Fname);
		editor.putString(LAST_NAME, Lname);
		editor.putString(EVENT_NAME,event_name);
		name = Fname.substring(0, 1).toUpperCase() + Fname.substring(1, Fname.length()) + " " + Lname.substring(0, 1).toUpperCase() + Lname.substring(1, Lname.length());
		editor.putString(FULL_NAME, name);
		editor.putBoolean(IS_LOGIN, true);
		editor.apply();
	}
	
	public String getEmail()
	{
		return sharedPreferences.getString(EMAIL, "android.studio@android.com");
	}
	
	public String getFullName()
	{
		return sharedPreferences.getString(FULL_NAME, "Android Studio");
	}
	
	// method to logout user
	public void logoutUser()
	{
		editor.clear();
		editor.commit();
		// After logout redirect user to Main Activity
		Intent i = new Intent(context, LoginActivity.class);
		// Closing all the Activities
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		
		// Add new Flag to start new Activity
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		
		// Staring LoginActivity Activity
		context.startActivity(i);
		
	}
	
	// getter method for getting event_name
	public String getEventName()
	{
		return sharedPreferences.getString(EVENT_NAME,"event_name_else");
	}
	
	
	
	// getter method to check if the user is logged in.
	public boolean isLoggedIn()
	{
		return sharedPreferences.getBoolean(IS_LOGIN, false);
	}
}
