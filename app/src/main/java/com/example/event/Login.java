package com.example.event;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Login extends AppCompatActivity
{
	private static final String TAG = Login.class.getSimpleName();
	EditText email, password;
	Button login;
	String email_entered, password_entered;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		email = (EditText) findViewById(R.id.email);
		password = (EditText) findViewById(R.id.password);
		login = (Button) findViewById(R.id.login_btn);
		login.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				email_entered = email.getText().toString();
				password_entered = password.getText().toString();
				Log.v(TAG, "email: " + email_entered);
				Log.v(TAG, "password: " + password_entered);
				email.setText("");
				password.setText("");
			}
		});
	}
}
