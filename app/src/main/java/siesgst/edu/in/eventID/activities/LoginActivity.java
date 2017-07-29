package siesgst.edu.in.eventID.activities;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import siesgst.edu.in.eventID.R;
import siesgst.edu.in.eventID.utils.SessionManager;

public class LoginActivity extends AppCompatActivity
{
	private static final String TAG = LoginActivity.class.getSimpleName();
	EditText email, password;
	Button login, submit_forgot;
	TextView forgot;
	String email_entered, password_entered, first_name, last_name;
	String id,name,prn,branch,year,role;
	RelativeLayout login_layout, forgot_layout;
	SessionManager sessionManager;
	private RequestQueue queue;
	private StringRequest stringRequest;
	ProgressBar progressBar;
	
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
		progressBar = (ProgressBar) findViewById(R.id.login_progress);
		final MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.sike);
		queue = Volley.newRequestQueue(this);
		login.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				email_entered = email.getText().toString();
				password_entered = password.getText().toString();
				if (email_entered.length() == 0)
				{
					// email not entered
					email.setError("Please enter your email");
				}
				else if (password_entered.length() == 0)
				{
					// password not entered
					mediaPlayer.start();
					Snackbar.make(findViewById(R.id.activity_login), "Please enter your password.", Snackbar.LENGTH_SHORT).show();
				}
				else
				{
					// both are entered
					
					if (sessionManager.checkNet())
					{
						// user is connected to the internet
						progressBar.setVisibility(View.VISIBLE);
						String url = "http://192.168.43.221/api/login?email="+email_entered+"&password="+password_entered;
						
						stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>()
						{
							@Override
							public void onResponse(String response)
							{
								Log.v(TAG+"response",response);
								if(response.contains("success"))
								{
									
									// parsing the response
									
									try
									{
										JSONObject root = new JSONObject(response);
										JSONObject content = root.optJSONObject("message");
										id = content.optString("id");
										name = content.optString("name");
										email_entered = content.optString("email");
										prn = content.optString("prn");
										branch = content.optString("branch");
										year = content.optString("year");
										role = content.optString("role");
									}
									catch (JSONException e)
									{
										e.printStackTrace();
									}
									
									first_name = email_entered.substring(0, email_entered.indexOf("."));
									last_name = email_entered.substring((email_entered.indexOf(".") + 1), email_entered.indexOf("1"));
									sessionManager.createLoginSession(email_entered,name,"Maze bot",prn,branch,role,year);
									Log.v(TAG, "email: " + email_entered);
									Log.v(TAG, "password: " + password_entered);
									progressBar.setVisibility(View.INVISIBLE);
									finish();
									startActivity(new Intent(LoginActivity.this, HomeActivity.class));
								}
								else if(response.contains("fail"))
								{
									progressBar.setVisibility(View.INVISIBLE);
									if(response.contains("email"))
									{
										Snackbar.make(findViewById(R.id.activity_login),"This user does not exist",Snackbar.LENGTH_SHORT).show();
										email.setText("");
										password.setText("");
									}
									else if(response.contains("Password"))
									{
										Snackbar.make(findViewById(R.id.activity_login),"Incorrect password",Snackbar.LENGTH_SHORT).show();
										password.setText("");
									}
								}
								
							}
						}, new Response.ErrorListener() {
							@Override
							public void onErrorResponse(VolleyError error)
							{
								progressBar.setVisibility(View.INVISIBLE);
								Snackbar.make(findViewById(R.id.activity_login),"Please check your credentials",Snackbar.LENGTH_SHORT).show();
								Log.v(TAG,error.toString());
							}
						})
						{
							@Override
							protected Response<String> parseNetworkResponse(NetworkResponse response)
							{
								String gg = response.headers.get("Authorization");
								Log.v(TAG+"header","   "+gg);
								sessionManager.setAuth_token(gg);
								Log.v(TAG+"tokenshared","   "+sessionManager.getToken());
								if((sessionManager.getToken().equals("null")))
								{
//									Toast.makeText(LoginActivity.this,"Some error",Toast.LENGTH_SHORT).show();
									Log.v(TAG,"some error");
								}
								else
								{
									Log.v(TAG,"correct password");
								}
								return super.parseNetworkResponse(response);
							}
						};
						
						queue.add(stringRequest);
						
					}
					else
					{
						progressBar.setVisibility(View.INVISIBLE);
						// user doesnt have an internet connection.
						Snackbar.make(findViewById(R.id.activity_login), "Check your internet connection", Snackbar.LENGTH_SHORT).show();
						email.setText("");
						password.setText("");
					}
					
				}
				
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
		submit_forgot.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				Snackbar.make(findViewById(R.id.activity_login), "Please check your inbox as well as your spam", Snackbar.LENGTH_SHORT).show();
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
