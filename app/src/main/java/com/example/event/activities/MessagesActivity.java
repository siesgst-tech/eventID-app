package com.example.event.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.event.R;
import com.example.event.utils.SessionManager;

public class MessagesActivity extends AppCompatActivity
{
	SessionManager sessionManager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_messages);
		sessionManager = new SessionManager(MessagesActivity.this);
		if(getSupportActionBar()!=null)
		{
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
			getSupportActionBar().setDisplayShowHomeEnabled(true);
			getSupportActionBar().setTitle("Messages");
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
