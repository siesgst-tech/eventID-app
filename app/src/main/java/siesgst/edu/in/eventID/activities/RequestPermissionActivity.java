package siesgst.edu.in.eventID.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import siesgst.edu.in.eventID.R;


public class RequestPermissionActivity extends AppCompatActivity
{
	public static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1232;
	CardView cardView;
	
	public static void startInstalledAppDetailsActivity(final Activity context)
	{
		if (context == null)
		{
			return;
		}
		final Intent i = new Intent();
		i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
		i.addCategory(Intent.CATEGORY_DEFAULT);
		i.setData(Uri.parse("package:" + context.getPackageName()));
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
		i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
		context.startActivity(i);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_request_permission);
		
		TextView permission_msg = (TextView) findViewById(R.id.permission_message);
		Typeface typeface = Typeface.createFromAsset(getAssets(), getString(R.string.press_start_2p));
		permission_msg.setTypeface(typeface);
		
		cardView = (CardView) findViewById(R.id.permission_card);
		cardView.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				checkPermissionNew();
			}
		});
	}
	
	
	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
	{
		
		
		switch (requestCode)
		{
			case MY_PERMISSIONS_REQUEST_CALL_PHONE:
				// if result is cancelled the array would be empty
				if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
				{
					doThisOnPermissionGranted();
				}
				else
				{
					String permission = Manifest.permission.CALL_PHONE;
					
					if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M)
					{
						boolean showRationale = shouldShowRequestPermissionRationale(permission);
						if (!showRationale)
						{
							cardView.setOnClickListener(new View.OnClickListener()
							{
								@Override
								public void onClick(View v)
								{
									startInstalledAppDetailsActivity(RequestPermissionActivity.this);
								}
							});
						}
					}
					
				}
		}
		
		
	}
	
	@Override
	public void onBackPressed()
	{
		//KEEP SHOWING THE SAME SCREEN UNTIL USER GIVES PERMISSION
		//super.onBackPressed();
		setResult(Activity.RESULT_CANCELED);
		finish();
	}
	
	@Override
	protected void onResume()
	{
		super.onResume();
		if (ContextCompat.checkSelfPermission(RequestPermissionActivity.this,
				Manifest.permission.CALL_PHONE)
				== PackageManager.PERMISSION_GRANTED)
		{
			setResult(Activity.RESULT_OK);
			finish();
		}
	}
	
	private void doThisOnPermissionGranted()
	{
		setResult(Activity.RESULT_OK);
		finish();
	}
	
	
	private void checkPermissionNew()
	{
		
		// Here, thisActivity is the current activity
		if (ContextCompat.checkSelfPermission(RequestPermissionActivity.this,
				Manifest.permission.CALL_PHONE)
				!= PackageManager.PERMISSION_GRANTED)
		{
			
			
			//shouldShowRequestPermissionRationale(). This method returns true if the app has
			// requested this permission previously and the user denied the request.
			
			// Should we show an explanation?
			if (ActivityCompat.shouldShowRequestPermissionRationale(RequestPermissionActivity.this,
					Manifest.permission.CALL_PHONE))
			{
				// Show an explanation to the user *asynchronously* -- don't block
				// this thread waiting for the user's response! After the user
				// sees the explanation, try again to request the permission.
				
				
				ActivityCompat.requestPermissions(RequestPermissionActivity.this,
						new String[]{Manifest.permission.CALL_PHONE},
						MY_PERMISSIONS_REQUEST_CALL_PHONE);
				
			}
			else
			{
				
				// No explanation needed, we can request the permission.
				ActivityCompat.requestPermissions(RequestPermissionActivity.this,
						new String[]{Manifest.permission.CALL_PHONE},
						MY_PERMISSIONS_REQUEST_CALL_PHONE);
				
				// MY_PERMISSIONS_REQUEST_WRITE_STORAGE is an
				// app-defined int constant. The callback method gets the
				// result of the request.
			}
		}
		else
		{
			doThisOnPermissionGranted();
		}
	}
}