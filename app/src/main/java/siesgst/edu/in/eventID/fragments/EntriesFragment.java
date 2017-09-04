package siesgst.edu.in.eventID.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
	int event_id,id,user_id,cost;
	String name,event_status;
	SessionManager session;
	
	
	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_entries, container, false);
		session = new SessionManager(getActivity());
		recyclerView = (RecyclerView) view.findViewById(R.id.entries_recycler);
		init();
//		getEntries();
		recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

		return view;
	}
	
	
	private void init()
	{
		entriesModelList.add(new EntriesModel("Rohit Ramaswamy", "115A1018",1));
		entriesModelList.add(new EntriesModel("Omkar Prabhu", "115A1077",1));
		entriesModelList.add(new EntriesModel("Aditya Nair", "115A1067",2));
		entriesModelList.add(new EntriesModel("Vinay Ambre", "115A1031",2));
		entriesModelList.add(new EntriesModel("Aditya Kulkarni", "115A1061",1));
		entriesModelList.add(new EntriesModel("Shivshankar Ravi", "115A1019",1));
		entriesModelList.add(new EntriesModel("Siddhesh Shinde", "115A1091",2));
		entriesModelList.add(new EntriesModel("Pradyumna Bapat", "115A1038",1));
		entriesModelList.add(new EntriesModel("Abhinandan Gupta", "115A1049",2));
		entriesModelList.add(new EntriesModel("Vipul Singh Raghuvanshi", "115A1099",1));
		settingAdapter();
	}

	private void getEntries() {
		entriesModelList = new ArrayList<EntriesModel>();
		//Volley
		final RequestQueue queue = Volley.newRequestQueue(getActivity());
		//Volley JsonObjectRequest
		String jsonUrl = getString(R.string.LOCAL_URL)+"/api/event/"+session.getEventId()+"/entries";
		Log.v("eventurl",jsonUrl);
		JsonObjectRequest jsObjRequest = new JsonObjectRequest
				(Request.Method.GET, jsonUrl, null, new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						try {
							Log.v("TAG", String.valueOf(response));
							JSONObject status = response.optJSONObject("status");
							JSONArray messageArray = response.optJSONArray("response");
							for(int i=0;i<messageArray.length();i++){
								JSONObject messageObject = messageArray.optJSONObject(i);
								name = messageObject.optString("name");
								id = messageObject.optInt("id");
								user_id = messageObject.optInt("user_id");
								cost = messageObject.optInt("cost");
								event_status = messageObject.optString("status");
								entriesModelList.add(new EntriesModel(name,event_status,id,user_id,cost));
							}
							settingAdapter();
						}
						catch (Exception e) {
							e.printStackTrace();
						}
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Log.v("TAG", String.valueOf(error));
					}
				});

		jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(
				10000,
				DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
		queue.add(jsObjRequest);


	}

	public void settingAdapter(){
		recyclerView.setAdapter(new EntriesAdapter(entriesModelList,1,getActivity()));
	}
}
