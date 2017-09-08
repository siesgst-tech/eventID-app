package siesgst.edu.in.eventID.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import siesgst.edu.in.eventID.R;
import siesgst.edu.in.eventID.adapters.HomeTabLayoutAdapter;
import siesgst.edu.in.eventID.fragments.EntriesFragment;
import siesgst.edu.in.eventID.fragments.InterestedFragment;
import siesgst.edu.in.eventID.fragments.MessagesFragment;
import siesgst.edu.in.eventID.utils.SessionManager;

public class HomeActivity extends AppCompatActivity
{
	private static final String LOG_TAG = HomeActivity.class.getSimpleName();
	SessionManager sessionManager;
	Fragment entriesFragment;
	Fragment interestedFragment;
	Fragment messagesFragment;
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_activity);

		sessionManager = new SessionManager(this);
		Toolbar toolbar = (Toolbar) findViewById(R.id.app_main);
		setSupportActionBar(toolbar);
		getSupportActionBar().setTitle("");
		
		TabLayout tabLayout = (TabLayout) findViewById(R.id.home_tab_layout);
		ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
		
		setUpViewPager(viewPager);
		tabLayout.setupWithViewPager(viewPager);
	}
	
	private void setUpViewPager(ViewPager viewPager)
	{
		HomeTabLayoutAdapter adapter = new HomeTabLayoutAdapter(getSupportFragmentManager());
		entriesFragment= new EntriesFragment();
		adapter.addFrag(entriesFragment, "Entries");
		interestedFragment = new InterestedFragment();
		adapter.addFrag(interestedFragment, "Interested");
		messagesFragment= new MessagesFragment();
		adapter.addFrag(messagesFragment, "Messages");
		viewPager.setAdapter(adapter);
	}

	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.menu_home, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		int id = item.getItemId();

		if (id == R.id.action_logout)
		{
			new AlertDialog.Builder(HomeActivity.this)
					.setMessage("Do you want to Log out ?")
					.setCancelable(false)
					.setPositiveButton("Yes", new DialogInterface.OnClickListener()
					{
						public void onClick(DialogInterface dialog, int id)
						{
							sessionManager.logoutUser();
							finish();
						}
					})
					.setNegativeButton("No", null)
					.show();

			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
}
