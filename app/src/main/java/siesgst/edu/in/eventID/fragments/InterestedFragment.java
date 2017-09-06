package siesgst.edu.in.eventID.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import siesgst.edu.in.eventID.R;
import siesgst.edu.in.eventID.adapters.EntriesAdapter;
import siesgst.edu.in.eventID.model.EntriesModel;
import siesgst.edu.in.eventID.utils.SessionManager;

/**
 * Created by rohitramaswamy on 30/08/17.
 */

public class InterestedFragment extends Fragment
{
	private final String LOG_TAG = InterestedFragment.class.getSimpleName();
	List<EntriesModel> entriesModels = new ArrayList<EntriesModel>();
	RecyclerView recyclerView;
	SessionManager session;
	String name, prn;
	private StringRequest stringRequest;
	private RequestQueue requestQueue;
	MaterialSearchView searchView;
	EntriesAdapter entriesAdapter;
	
	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_interested, container, false);
		session = new SessionManager(getActivity());
//		setHasOptionsMenu(true);
		/*
		searchView = (MaterialSearchView) getActivity().findViewById(R.id.search_view);
		if (searchView.isSearchOpen())
		{
			searchView.closeSearch();
		}
		searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
			@Override
			public boolean onQueryTextSubmit(String query) {

				entriesAdapter.filter(query);
				searchView.clearFocus();
				return true;
			}

			@Override
			public boolean onQueryTextChange(String newText) {
				entriesAdapter.filter(newText);
				return false;
			}
		});
		searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
			@Override
			public void onSearchViewShown() {

			}

			@Override
			public void onSearchViewClosed() {

			}
		});
		*/
		recyclerView = (RecyclerView) view.findViewById(R.id.interested_recycler);
		getInterestedList();
		recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
		return view;
	}
	
	
	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}
	
	public void settingAdapter()
	{
		entriesAdapter=new EntriesAdapter(entriesModels, 2, getActivity());
		recyclerView.setAdapter(entriesAdapter);
	}
	
	public void getInterestedList()
	{
		String url = getString(R.string.LIVE_URL)+session.getEventId()+"/interested";
		stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>()
		{
			@Override
			public void onResponse(String response)
			{
				try
				{
					JSONObject root = new JSONObject(response);
					String status = root.optString("status");
					JSONArray responses = root.optJSONArray("response");
					for(int i=0;i<responses.length();i++)
					{
						JSONObject responseObject = responses.optJSONObject(i);
						String name = responseObject.optString("name");
						String uid = responseObject.optString("uid");
						String id = responseObject.optString("id");
						String event_id = responseObject.optString("event_id");
						String email = responseObject.optString("email");
						String contact = responseObject.optString("contact");
						entriesModels.add(new EntriesModel(name,uid,id,email,contact));
					}
					settingAdapter();
					
				}
				catch (JSONException e)
				{
					e.printStackTrace();
				}
				
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error)
			{
				
			}
		});
		
		stringRequest.setRetryPolicy(new DefaultRetryPolicy(
				10000,
				DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
		requestQueue = Volley.newRequestQueue(getActivity());
		requestQueue.add(stringRequest);
	}


//	@Override
//	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//		inflater.inflate(R.menu.menu_fragments, menu);
//		MenuItem item = menu.findItem(R.id.action_search);
//		searchView.setMenuItem(item);
//	}
//
//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		switch (item.getItemId()) {
//			case R.id.action_search:
//				Log.d("EntriesFragment","action Search");
//
//				Toast.makeText(getActivity(), "search interested", Toast.LENGTH_SHORT).show();
//				return true;
//
//			default:
//				return super.onOptionsItemSelected(item);
//		}
//	}
}
