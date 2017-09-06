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
	Button login;
	String email_entered, password_entered, first_name, last_name;
	String name, prn, branch, year, role;
	int id, event_id;
	RelativeLayout login_layout;
	SessionManager sessionManager;
	ProgressBar progressBar;
	private RequestQueue queue;
	private StringRequest stringRequest;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		sessionManager = new SessionManager(this);
		email = (EditText) findViewById(R.id.email);
		password = (EditText) findViewById(R.id.password);
		login = (Button) findViewById(R.id.login_btn);
		login_layout = (RelativeLayout) findViewById(R.id.login_layout);
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
						String url = getResources().getString(R.string.LIVE_URL) + "login?email=" + email_entered + "&password=" + password_entered;
						Log.v("url", url);
						stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>()
						{
							@Override
							public void onResponse(String response)
							{
								Log.v(TAG + "response", response);
								if (response.contains("success"))
								{
									// parsing the response
									try
									{
										JSONObject root = new JSONObject(response);
										JSONObject content = root.optJSONObject("response");
										id = content.optInt("id");
										name = content.optString("name");
										email_entered = content.optString("email");
										event_id = content.optInt("event_id");
										Log.v("details", id + " " + name + "  " + email_entered + "  " + String.valueOf(event_id));
									}
									catch (JSONException e)
									{
										e.printStackTrace();
									}
									
//									first_name = email_entered.substring(0, email_entered.indexOf("."));
//									last_name = email_entered.substring((email_entered.indexOf(".") + 1), email_entered.indexOf("1"));
									sessionManager.createLoginSession(email_entered, name, "Maze bot", event_id);
									Log.v(TAG, "email: " + email_entered);
									Log.v(TAG, "password: " + password_entered);
									progressBar.setVisibility(View.INVISIBLE);
									finish();
									startActivity(new Intent(LoginActivity.this, HomeActivity.class));
								}
								else if (response.contains("fail"))
								{
									progressBar.setVisibility(View.INVISIBLE);
									if (response.contains("email"))
									{
										Snackbar.make(findViewById(R.id.activity_login), "This user does not exist", Snackbar.LENGTH_SHORT).show();
										email.setText("");
										password.setText("");
									}
									else if (response.contains("Password"))
									{
										Snackbar.make(findViewById(R.id.activity_login), "Incorrect password", Snackbar.LENGTH_SHORT).show();
										password.setText("");
									}
								}
								
							}
						}, new Response.ErrorListener()
						{
							@Override
							public void onErrorResponse(VolleyError error)
							{
								progressBar.setVisibility(View.INVISIBLE);
								Snackbar.make(findViewById(R.id.activity_login), "Please check your credentials", Snackbar.LENGTH_SHORT).show();
								Log.v(TAG, error.toString());
							}
						});
						
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
	}
	
	@Override
	public void onBackPressed()
	{
		super.onBackPressed();
	}
}





/*
 
 
{
    "status": "success",
    "response": {
        "id": 1,
        "name": "Vinay",
        "email": "vinayambre7@gmail.com",
        "event_id": 2,
        "created_at": null,
        "updated_at": null,
        "event_name": "Tanks"
    }
}
 
 */
