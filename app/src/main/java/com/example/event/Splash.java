package com.example.event;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class Splash extends AppCompatActivity
{
	private static final String TAG = Splash.class.getSimpleName();
	private static final int SPLASH_TIME_OUT = 1000;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		TextView splash = (TextView) findViewById(R.id.splash_txt);
		Typeface typeface = Typeface.createFromAsset(getAssets(),"fonts/OpenSans-Light.ttf");
		splash.setTypeface(typeface);
		View decorView = getWindow().getDecorView();
		int visibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
		decorView.setSystemUiVisibility(visibility);
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run()
			{
				startActivity(new Intent(Splash.this,Login.class));
				finish();
			}
		},SPLASH_TIME_OUT);
	}
}
