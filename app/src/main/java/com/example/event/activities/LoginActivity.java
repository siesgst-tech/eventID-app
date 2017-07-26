package com.example.event.activities;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.event.R;
import com.example.event.utils.SessionManager;

public class LoginActivity extends AppCompatActivity
{
	private static final String TAG = LoginActivity.class.getSimpleName();
	EditText email, password;
	Button login,submit_forgot;
	TextView forgot;
	String email_entered, password_entered,first_name,last_name;
	RelativeLayout login_layout, forgot_layout;
	SessionManager sessionManager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		sessionManager = new SessionManager(this);
		email = (EditText) findViewById(R.id.email);
		password = (EditText) findViewById(R.id.password);
		login = (Button) findViewById(R.id.login_btn);
		forgot = (TextView) findViewById(R.id.forgot_textView);
		login_layout = (RelativeLayout) findViewById(R.id.login_layout);
		forgot_layout = (RelativeLayout) findViewById(R.id.forgot_layout);
		submit_forgot = (Button) findViewById(R.id.submit_forgot);
		final MediaPlayer mediaPlayer = MediaPlayer.create(this,R.raw.sike);
		login.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				email_entered = email.getText().toString();
				password_entered = password.getText().toString();
				if (email_entered.length() == 0)
				{
					email.setError("Please enter your email");
				}
				else if (password_entered.length() == 0)
				{
					mediaPlayer.start();
					Snackbar.make(findViewById(R.id.activity_login), "Please enter your password.", Snackbar.LENGTH_SHORT).show();
				}
				else
				{
					finish();
					first_name = email_entered.substring(0,email_entered.indexOf("."));
					last_name = email_entered.substring((email_entered.indexOf(".")+1),email_entered.indexOf("1"));
					sessionManager.createLoginSession(email_entered,first_name,last_name);
					startActivity(new Intent(LoginActivity.this,HomeActivity.class));
				}
				Log.v(TAG, "email: " + email_entered);
				Log.v(TAG, "password: " + password_entered);
				email.setText("");
				password.setText("");
			}
		});
		forgot.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				login_layout.setVisibility(View.GONE);
				forgot_layout.setVisibility(View.VISIBLE);
			}
		});
		submit_forgot.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view)
			{
				Snackbar.make(findViewById(R.id.activity_login),"Please check your inbox as well as your spam",Snackbar.LENGTH_SHORT).show();
			}
		});
	}
	
	@Override
	public void onBackPressed()
	{
		if (forgot_layout.getVisibility() == View.VISIBLE)
		{
			forgot_layout.setVisibility(View.GONE);
			login_layout.setVisibility(View.VISIBLE);
		}
		else
		{
			super.onBackPressed();
		}
	}
}
