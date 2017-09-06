package siesgst.edu.in.eventID.fragments;

import android.content.Context;
import android.content.res.ColorStateList;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import siesgst.edu.in.eventID.R;
import siesgst.edu.in.eventID.adapters.EntriesAdapter;
import siesgst.edu.in.eventID.adapters.MessagesAdapter;
import siesgst.edu.in.eventID.database.DatabaseManager;
import siesgst.edu.in.eventID.model.MessagesModel;
import siesgst.edu.in.eventID.utils.Constants;
import siesgst.edu.in.eventID.utils.SessionManager;

/**
 * Created by rohitramaswamy on 01/08/17.
 */

public class MessagesFragment extends Fragment
{
	FloatingActionButton fab;
	List<MessagesModel> messages = new ArrayList<>();
	RecyclerView recyclerView;
	SessionManager session;
	StringRequest stringRequest;
	StringRequest stringRequest_;
	RequestQueue requestQueue;
	ConnectivityManager connectivityManager;
	NetworkInfo activeNetwork;
	ProgressBar progressBar;
	DatabaseManager databaseManager;
	MessagesAdapter messagesAdapter;
	
	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_messages, container, false);
		session = new SessionManager(getActivity());
		databaseManager = new DatabaseManager(getActivity());
		requestQueue = Volley.newRequestQueue(getActivity());

		connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
		activeNetwork = connectivityManager.getActiveNetworkInfo();

		recyclerView = (RecyclerView) view.findViewById(R.id.messages_recycler);
		recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
		recyclerView.setAdapter(new MessagesAdapter(messages));

		fab = (FloatingActionButton) view.findViewById(R.id.new_message);
		progressBar = (ProgressBar)view.findViewById(R.id.messages_progress);
		fab.setImageResource(R.drawable.create);
		fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.green_tick)));
		recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
		fab.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
				LayoutInflater inflater = getActivity().getLayoutInflater();
				final View view_ = inflater.inflate(R.layout.new_message_layout, null);
				final EditText contactEditText = (EditText) view_.findViewById(R.id.message_edit_text);
				Button addButton = view_.findViewById(R.id.add_message);
				// Set up the input
				builder.setView(view_);
				final AlertDialog d = builder.show();
				addButton.setOnClickListener(new Button.OnClickListener()
				{
					
					@Override
					public void onClick(View arg0)
					{
						// call message api here
						String message = contactEditText.getText().toString().trim();
						if(message.length()>0)
						{
							String url = getString(R.string.LIVE_URL)+"message/add?event_id="+session.getEventId()
									+"&title="+session.getEventName()+"&body="+message;
							stringRequest_ = new StringRequest(Request.Method.POST, url, new Response.Listener<String>()
							{
								@Override
								public void onResponse(String response)
								{
									if(response.contains("success"))
									{
										Snackbar.make(getActivity().findViewById(R.id.fragment_messages),"Message sent",Snackbar.LENGTH_SHORT).show();
									}
								}
							}, new Response.ErrorListener() {
								@Override
								public void onErrorResponse(VolleyError error)
								{
									Log.v("onError",error.toString());
									Snackbar.make(getActivity().findViewById(R.id.fragment_messages),"Please try again later",Snackbar.LENGTH_SHORT).show();
								}
							});
						}
					}
				});
			}
		});
		//getMessages();
		//setAdapter();
		new getMessagesFromDb().execute();


		if (connectivityManager.getActiveNetworkInfo() != null) {
			getMessages();
		}
		return view;
	}
	
	public void setAdapter()
	{
		recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
		recyclerView.setAdapter(new MessagesAdapter(messages));
	}
	
	public void getMessages()
	{
		String url = getString(R.string.LIVE_URL) + session.getEventId() + "/messages";
		stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>()
		{
			@Override
			public void onResponse(String response)
			{
				try
				{
					JSONObject root = new JSONObject(response);
					String status = root.optString("status");
					JSONArray responseArray = root.optJSONArray("response");
					for (int i = 0; i < responseArray.length(); i++)
					{
						JSONObject object = responseArray.optJSONObject(i);
						int id = object.optInt("id");
						int event_id = object.optInt("event_id");
						String title = object.optString("title");
						String body = object.optString("body");
						//messages.add(new MessagesModel(title, body));

						HashMap<String, String> map = new HashMap<>();
						map.put(Constants.MESSAGE_ID, String.valueOf(id));
						map.put(Constants.MESSAGE_TITLE, title);
						map.put(Constants.MESSAGE_BODY, body);

						databaseManager.insertMessages(map);
					}
					new getMessagesFromDb().execute();
				}
				catch (JSONException e)
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
		requestQueue.add(stringRequest);
	}
	
	
	public void init()
	{
		messages = new ArrayList<MessagesModel>();
		messages.add(new MessagesModel("Title goes here", "Description goes here. Description goes here. Description goes here. Description goes here. " +
				"Description goes here. Description goes here. Description goes here"));
		messages.add(new MessagesModel("Title goes here", "Description goes here. Description goes here. Description goes here. Description goes here. " +
				"Description goes here. Description goes here. Description goes here"));
		messages.add(new MessagesModel("Title goes here", "Description goes here. Description goes here. Description goes here. Description goes here. " +
				"Description goes here. Description goes here. Description goes here"));
		messages.add(new MessagesModel("Title goes here", "Description goes here. Description goes here. Description goes here. Description goes here. " +
				"Description goes here. Description goes here. Description goes here"));
		messages.add(new MessagesModel("Title goes here", "Description goes here. Description goes here. Description goes here. Description goes here. " +
				"Description goes here. Description goes here. Description goes here"));
		messages.add(new MessagesModel("Title goes here", "Description goes here. Description goes here. Description goes here. Description goes here. " +
				"Description goes here. Description goes here. Description goes here"));
		messages.add(new MessagesModel("Title goes here", "Description goes here. Description goes here. Description goes here. Description goes here. " +
				"Description goes here. Description goes here. Description goes here"));
		
	}

	public class getMessagesFromDb extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			Log.d("MessagesFragment", "onPreExecute ");
			super.onPreExecute();
			progressBar.setVisibility(View.VISIBLE);
		}

		@Override
		protected Void doInBackground(Void... params) {
			messages = databaseManager.getAllMessages();

			return null;
		}

		@Override
		protected void onPostExecute(Void aVoid) {
			super.onPostExecute(aVoid);
			Log.d("MessagesFragment", "onPostExecute entriesModels size=" + messages.size());
			if (messages.size() == 0) {
				//errorLayout.setVisibility(View.VISIBLE);
				//errorText.setText("Messages from events you participate in\nwill appear here.");
				//errorTextView.setText(R.string.fa_bell_o);
			} else {
				//errorLayout.setVisibility(View.GONE);
				messagesAdapter = new MessagesAdapter(messages);
				recyclerView.setAdapter(messagesAdapter);
				messagesAdapter.notifyDataSetChanged();
			}
			progressBar.setVisibility(View.GONE);
		}
	}
}

/*

/api/eventhead/id/messages

{
    "status": "success",
    "response": [
        {
            "id": 1,
            "event_id": 2,
            "title": "test",
            "body": "testing....",
            "created_at": "2017-09-05 00:00:00",
            "updated_at": null
        },
        {
            "id": 2,
            "event_id": 2,
            "title": "asdadadad",
            "body": "asbdadhadajvjvvvfvafs ban c scka scn absckacsn ac",
            "created_at": "2017-09-05 00:00:00",
            "updated_at": null
        }
    ]
}

*/