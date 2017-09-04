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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import siesgst.edu.in.eventID.R;
import siesgst.edu.in.eventID.adapters.EntriesAdapter;
import siesgst.edu.in.eventID.model.EntriesModel;
import siesgst.edu.in.eventID.utils.SessionManager;

/**
 * Created by rohitramaswamy on 23/07/17.
 */

public class EntriesFragment extends Fragment
{
	private static final String LOG_TAG = EntriesFragment.class.getSimpleName();
	List<EntriesModel> entriesModelList = new ArrayList<EntriesModel>();
	RecyclerView recyclerView;
	String event_id, id, uid, cost;
	String name, event_status,receipt_no,email,contact,paid;
	SessionManager session;
	
	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_entries, container, false);
		session = new SessionManager(getActivity());
		recyclerView = (RecyclerView) view.findViewById(R.id.entries_recycler);
		getEntries();
		recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
		
		return view;
	}

	private void getEntries()
	{
		entriesModelList = new ArrayList<EntriesModel>();
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
								paid = messageObject.optString("paid");
								entriesModelList.add(new EntriesModel(name,uid,id,receipt_no,email,contact,paid));
							}
							settingAdapter();
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
		recyclerView.setAdapter(new EntriesAdapter(entriesModelList, 1, getActivity()));
	}
}
