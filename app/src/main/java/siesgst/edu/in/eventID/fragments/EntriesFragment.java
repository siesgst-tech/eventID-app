package siesgst.edu.in.eventID.fragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import siesgst.edu.in.eventID.R;
import siesgst.edu.in.eventID.adapters.EntriesAdapter;
import siesgst.edu.in.eventID.adapters.HomeTabLayoutAdapter;
import siesgst.edu.in.eventID.database.DatabaseManager;
import siesgst.edu.in.eventID.model.EntriesModel;
import siesgst.edu.in.eventID.utils.Constants;
import siesgst.edu.in.eventID.utils.SessionManager;

/**
 * Created by rohitramaswamy on 23/07/17.
 */

public class EntriesFragment extends Fragment
{
	private static final String LOG_TAG = EntriesFragment.class.getSimpleName();
	List<EntriesModel> entriesModelList = new ArrayList<>();
	RecyclerView recyclerView;
	String event_id, id, uid, cost;
	String name, event_status,receipt_no,email,contact,status1;
	SessionManager session;
	MaterialSearchView searchView;
	EntriesAdapter entriesAdapter;
	DatabaseManager databaseManager;
	ConnectivityManager connectivityManager;
	NetworkInfo activeNetwork;
	ProgressBar progressBar;
	View view;
	HomeTabLayoutAdapter adapter;
	SwipeRefreshLayout swipeRefreshLayout;

	/*
	
	status:
		0: can play (not played)
		1: cannot play (played)
	
	status change karne: uid and event id
	POST:
	http://cognition.siesgst.ac.in/api/eventhead/play
	
	*/
	
	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
	{

		if(view==null) {
			view = inflater.inflate(R.layout.fragment_entries, container, false);
			progressBar = (ProgressBar)view.findViewById(R.id.entries_progress);
			session = new SessionManager(getActivity());
			//getEntries();
		}
		view = inflater.inflate(R.layout.fragment_entries, container, false);
		session = new SessionManager(getActivity());
		databaseManager = new DatabaseManager(getActivity());
		progressBar = (ProgressBar)view.findViewById(R.id.entries_progress);
		swipeRefreshLayout = view.findViewById(R.id.swipeRefreshEntries);
		connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
		activeNetwork = connectivityManager.getActiveNetworkInfo();
		recyclerView = (RecyclerView) view.findViewById(R.id.entries_recycler);
		recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
		entriesAdapter=new EntriesAdapter(entriesModelList, 1, getActivity());
		recyclerView.setAdapter(entriesAdapter);
		//setHasOptionsMenu(true);
		searchView = (MaterialSearchView) getActivity().findViewById(R.id.search_view);
		if (searchView.isSearchOpen())
		{
			searchView.closeSearch();
		}
		searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener()
		{
			@Override
			public boolean onQueryTextSubmit(String query)
			{
				entriesAdapter.filter(query);
				searchView.clearFocus();
				return true;
			}
			
			@Override
			public boolean onQueryTextChange(String newText)
			{
				entriesAdapter.filter(newText);
				return false;
			}
		});

		//getEntries();
//		recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
		new getEntriesFromDb().execute();

		if (connectivityManager.getActiveNetworkInfo() != null) {
			getEntries();
		}

		swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				if(connectivityManager.getActiveNetworkInfo()!=null) {
					getEntries();
				}
				else {
					Toast.makeText(getActivity(), "You are OFFLINE !", Toast.LENGTH_SHORT).show();
				}
			}
		});
		return view;
	}
	
	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		
	}
	
	private void getEntries()
	{
		//progressBar.setVisibility(View.VISIBLE);
		swipeRefreshLayout.setRefreshing(true);
		//entriesModelList = new ArrayList<EntriesModel>();
		//Volley
		final RequestQueue queue = Volley.newRequestQueue(getActivity());
		//Volley JsonObjectRequest
		String jsonUrl = getString(R.string.LIVE_URL)+ session.getEventId() + "/entries";
		JsonObjectRequest jsObjRequest = new JsonObjectRequest
				(Request.Method.GET, jsonUrl, null, new Response.Listener<JSONObject>()
				{
					
					@Override
					public void onResponse(JSONObject response)
					{
					//	Log.v("entries",response.toString());
						try
						{
							JSONObject status = response.optJSONObject("status");
							JSONArray messageArray = response.optJSONArray("response");
							for (int i = 0; i < messageArray.length(); i++)
							{
								JSONObject messageObject = messageArray.optJSONObject(i);
								name = messageObject.optString("name");
								uid = messageObject.optString("uid");
								id = messageObject.optString("id");
								receipt_no = messageObject.optString("receipt_no");
								email = messageObject.optString("email");
								contact = messageObject.optString("contact");
								status1 = messageObject.optString("status");
								//entriesModelList.add(new EntriesModel(name,uid,id,receipt_no,email,contact,status1));

								HashMap<String, String> map = new HashMap<>();
								map.put(Constants.ENTRIES_ID, id);
								map.put(Constants.ENTRIES_USER_ID, uid);
								map.put(Constants.ENTRIES_NAME, name);
								map.put(Constants.ENTRIES_STATUS, status1);

								databaseManager.insertEntries(map);
							}
							//settingAdapter();
							adapter.setEntriesCount(databaseManager.getEntriesCount());
							adapter.notifyDataSetChanged();
							new getEntriesFromDb().execute();
						}
						catch (Exception e)
						{
							e.printStackTrace();
						}
					}
				}, new Response.ErrorListener()
				{
					@Override
					public void onErrorResponse(VolleyError error)
					{
						new getEntriesFromDb().execute();

					}
				});
		
		jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(
				10000,
				DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
		queue.add(jsObjRequest);
	}
	
	public void settingAdapter()
	{
		entriesAdapter=new EntriesAdapter(entriesModelList, 1, getActivity());
		recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
		recyclerView.setAdapter(entriesAdapter);
	}

	public class getEntriesFromDb extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			//progressBar.setVisibility(View.VISIBLE);
		}

		@Override
		protected Void doInBackground(Void... params) {
			entriesModelList = databaseManager.getAllEntries();

			return null;
		}

		@Override
		protected void onPostExecute(Void aVoid) {
			super.onPostExecute(aVoid);
			if (entriesModelList.size() == 0) {
				//errorLayout.setVisibility(View.VISIBLE);
				//errorText.setText("Messages from events you participate in\nwill appear here.");
				//errorTextView.setText(R.string.fa_bell_o);
			} else {
				//errorLayout.setVisibility(View.GONE);
				entriesAdapter = new EntriesAdapter(entriesModelList, 1, getActivity());
				recyclerView.setAdapter(entriesAdapter);
				entriesAdapter.notifyDataSetChanged();
			}
			//progressBar.setVisibility(View.GONE);
			swipeRefreshLayout.setRefreshing(false);
		}
	}
	
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		getActivity().getMenuInflater().inflate(R.menu.menu_fragments, menu);
		MenuItem item = menu.findItem(R.id.action_search);
		searchView.setMenuItem(item);
		
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		int id = item.getItemId();
		
		switch (id)
		{
			case R.id.action_search:
				break;
		}
		return super.onOptionsItemSelected(item);
	}

	public void setPagerAdapter(HomeTabLayoutAdapter pagerAdapter){
		adapter=pagerAdapter;
	}
}
