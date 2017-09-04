package siesgst.edu.in.eventID.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import siesgst.edu.in.eventID.activities.LoginActivity;

import static siesgst.edu.in.eventID.utils.Constants.EVENT_ID;
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
	// defining keys
	
	
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
		editor.putBoolean(Constants.IS_LOGIN, true);
		editor.putString(Constants.EVENT_NAME, event_name);
		editor.putString(Constants.FULL_NAME, name);
		editor.putString(Constants.EMAIL, email);
		editor.putInt(EVENT_ID,event_id);
//		editor.putString(Constants.LOGIN_PRN, prn);
//		editor.putString(Constants.LOGIN_BRANCH, branch);
//		editor.putString(Constants.LOGIN_YEAR, year);
//		editor.putString(Constants.LOGIN_ROLE, role);
		editor.apply();
		
		
//		Log.v("sp name", sharedPreferences.getString(Constants.FULL_NAME, "full_name"));
//		Log.v("sp name", sharedPreferences.getString(Constants.EMAIL, "email"));
//		Log.v("sp name", sharedPreferences.getString(Constants.LOGIN_PRN, "prn"));
//		Log.v("sp name", sharedPreferences.getString(Constants.LOGIN_BRANCH, "branch"));
//		Log.v("sp name", sharedPreferences.getString(Constants.LOGIN_YEAR, "year"));
//		Log.v("sp name", sharedPreferences.getString(Constants.LOGIN_ROLE, "role"));
		
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
		return sharedPreferences.getInt(EVENT_ID, 1);
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
		return sharedPreferences.getString(EVENT_NAME, "event_name_else");
	}
	
	
	public boolean checkNet()
	{
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		
		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		
		return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
	}
	
	// getter method to check if the user is logged in.
	public boolean isLoggedIn()
	{
		return sharedPreferences.getBoolean(IS_LOGIN, false);
	}
	
}
