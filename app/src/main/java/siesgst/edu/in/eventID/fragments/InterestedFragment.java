package siesgst.edu.in.eventID.fragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

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
import java.util.HashMap;
import java.util.List;

import siesgst.edu.in.eventID.R;
import siesgst.edu.in.eventID.adapters.EntriesAdapter;
import siesgst.edu.in.eventID.database.DatabaseManager;
import siesgst.edu.in.eventID.model.EntriesModel;
import siesgst.edu.in.eventID.utils.Constants;
import siesgst.edu.in.eventID.utils.SessionManager;

/**
 * Created by rohitramaswamy on 30/08/17.
 */

public class InterestedFragment extends Fragment {
    private final String LOG_TAG = InterestedFragment.class.getSimpleName();
    List<EntriesModel> entriesModels = new ArrayList<EntriesModel>();
    RecyclerView recyclerView;
    SessionManager session;
    String name, prn;
    MaterialSearchView searchView;
    EntriesAdapter entriesAdapter;
    DatabaseManager databaseManager;
    private StringRequest stringRequest;
    private RequestQueue requestQueue;
    ConnectivityManager connectivityManager;
    NetworkInfo activeNetwork;
    ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_interested, container, false);
        session = new SessionManager(getActivity());
        databaseManager = new DatabaseManager(getActivity());

        connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        activeNetwork = connectivityManager.getActiveNetworkInfo();
//		setHasOptionsMenu(true);
        progressBar = (ProgressBar)view.findViewById(R.id.interested_progress);

        recyclerView = (RecyclerView) view.findViewById(R.id.interested_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        entriesAdapter = new EntriesAdapter(entriesModels, 2, getActivity());
        recyclerView.setAdapter(entriesAdapter);

        //getInterestedList();

        new getInterestedFromDb().execute();


		if (connectivityManager.getActiveNetworkInfo() != null) {
            getInterestedList();
        }


        return view;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public void settingAdapter() {
        entriesAdapter = new EntriesAdapter(entriesModels, 2, getActivity());
        recyclerView.setAdapter(entriesAdapter);
    }

    public void getInterestedList() {
        progressBar.setVisibility(View.VISIBLE);
        String url = getString(R.string.LIVE_URL) + session.getEventId() + "/interested";
        stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject root = new JSONObject(response);
                    String status = root.optString("status");
                    JSONArray responses = root.optJSONArray("response");
                    for (int i = 0; i < responses.length(); i++) {
                        JSONObject responseObject = responses.optJSONObject(i);
                        String name = responseObject.optString("name");
                        String uid = responseObject.optString("uid");
                        String id = responseObject.optString("id");
                        String event_id = responseObject.optString("event_id");
                        String email = responseObject.optString("email");
                        String contact = responseObject.optString("contact");

                        //entriesModels.add(new EntriesModel(name, uid, id, email, contact));

                        HashMap<String, String> map = new HashMap<>();
                        map.put(Constants.INTERESTED_ID, String.valueOf(id));
                        map.put(Constants.INTERESTED_USER_ID, uid);
                        map.put(Constants.INTERESTED_NAME, name);
                        map.put(Constants.INTERESTED_EMAIL, email);
                        map.put(Constants.INTERESTED_CONTACT, contact);

                        databaseManager.insertInterested(map);
                    }
                    //settingAdapter();
                    new getInterestedFromDb().execute();


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    public class getInterestedFromDb extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            Log.d("InterestedFragment", "onPreExecute ");
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... params) {
            entriesModels = databaseManager.getAllInterested();

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.d("InterestedFragment", "onPostExecute entriesModels size=" + entriesModels.size());
            if (entriesModels.size() == 0) {
                //errorLayout.setVisibility(View.VISIBLE);
                //errorText.setText("Messages from events you participate in\nwill appear here.");
                //errorTextView.setText(R.string.fa_bell_o);
            } else {
                //errorLayout.setVisibility(View.GONE);
                entriesAdapter = new EntriesAdapter(entriesModels, 2, getActivity());
                recyclerView.setAdapter(entriesAdapter);
                entriesAdapter.notifyDataSetChanged();
            }
            progressBar.setVisibility(View.GONE);
        }
    }


}
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

            /*
    @Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.menu_fragments, menu);
		MenuItem item = menu.findItem(R.id.action_search);
		searchView.setMenuItem(item);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.action_search:
				Log.d("EntriesFragment","action Search");

				Toast.makeText(getActivity(), "search interested", Toast.LENGTH_SHORT).show();
				return true;

			default:
				return super.onOptionsItemSelected(item);
		}
	}
	*/