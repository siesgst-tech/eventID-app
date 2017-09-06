package siesgst.edu.in.eventID.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import siesgst.edu.in.eventID.activities.LoginActivity;

import static siesgst.edu.in.eventID.utils.Constants.EVENT_NAME;
import static siesgst.edu.in.eventID.utils.Constants.FULL_NAME;
import static siesgst.edu.in.eventID.utils.Constants.IS_LOGIN;

/**
 * Created by rohitramaswamy on 23/07/17.
 */

public class SessionManager
{
	public static final String PREF_NAME = "EVENT_ID";
	public static final int PRIVATE_MODE = 0;
	public static final String LOG_TAG = SessionManager.class.getSimpleName();
	
	Context context;
	
	SharedPreferences sharedPreferences;
	
	SharedPreferences.Editor editor;
	
	public SessionManager(Context context)
	{
		this.context = context;
		sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
		editor = sharedPreferences.edit();
	}
	
	// method to initialise login
	public void createLoginSession(String email, String name, String event_name, int event_id)
	{
		editor.putBoolean(IS_LOGIN, true);
		editor.putString(EVENT_NAME, event_name);
		editor.putString(FULL_NAME, name);
		editor.putString(Constants.EMAIL, email);
		editor.putInt(Constants.EVENT_ID, event_id);
		editor.apply();
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
	
	public boolean checkNet()
	{
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		
		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		
		return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
	}
	
	public String getEmail()
	{
		return sharedPreferences.getString(Constants.EMAIL, "android.studio@android.com");
	}
	
	public String getFullName()
	{
		return sharedPreferences.getString(FULL_NAME, "Android Studio");
	}
	
	public int getEventId()
	{
		return sharedPreferences.getInt(Constants.EVENT_ID, 1);
	}
	
	public String getEventName()
	{
		return sharedPreferences.getString(EVENT_NAME, "event_name_else");
	}
	
	
	public boolean isLoggedIn()
	{
		return sharedPreferences.getBoolean(IS_LOGIN, false);
	}
}
