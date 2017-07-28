package com.example.event.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.event.R;
import com.example.event.utils.SessionManager;

public class SettingsActivity extends AppCompatActivity
{
	SessionManager sessionManager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		sessionManager = new SessionManager(SettingsActivity.this);
		if(getSupportActionBar()!=null)
		{
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
			getSupportActionBar().setDisplayShowHomeEnabled(true);
			getSupportActionBar().setTitle("Settings");
		}
		
	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		int id = item.getItemId();
		if(id==android.R.id.home)
		{
			onBackPressed();
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
