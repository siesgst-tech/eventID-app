package com.example.event.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.event.R;
import com.example.event.utils.SessionManager;

public class SplashActivity extends AppCompatActivity
{
	private static final String TAG = SplashActivity.class.getSimpleName();
	private static final int SPLASH_TIME_OUT = 1000;
	SessionManager sessionManager;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		sessionManager = new SessionManager(this);
		TextView splash = (TextView) findViewById(R.id.splash_txt);
		Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/OpenSans-Light.ttf");
		splash.setTypeface(typeface);
		View decorView = getWindow().getDecorView();
		int visibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
		decorView.setSystemUiVisibility(visibility);
		new Handler().postDelayed(new Runnable()
		{
			@Override
			public void run()
			{
				if(sessionManager.isLoggedIn())
				{
					startActivity(new Intent(SplashActivity.this, HomeActivity.class));
					finish();
				}
				else
				{
					startActivity(new Intent(SplashActivity.this, LoginActivity.class));
					finish();
				}
			}
		}, SPLASH_TIME_OUT);
	}
}
