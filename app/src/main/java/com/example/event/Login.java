package com.example.event;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Login extends AppCompatActivity
{
	private static final String TAG = Login.class.getSimpleName();
	EditText email, password;
	Button login;
	TextView forgot;
	String email_entered, password_entered;
	RelativeLayout login_layout, forgot_layout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		email = (EditText) findViewById(R.id.email);
		password = (EditText) findViewById(R.id.password);
		login = (Button) findViewById(R.id.login_btn);
		forgot = (TextView) findViewById(R.id.forgot_textView);
		login_layout = (RelativeLayout) findViewById(R.id.login_layout);
		forgot_layout = (RelativeLayout) findViewById(R.id.forgot_layout);
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
					Snackbar.make(findViewById(R.id.activity_login), "Please enter your password.", Snackbar.LENGTH_SHORT).show();
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
	}
	
}
