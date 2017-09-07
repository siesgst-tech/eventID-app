package siesgst.edu.in.eventID.adapters;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import siesgst.edu.in.eventID.R;
import siesgst.edu.in.eventID.activities.RequestPermissionActivity;
import siesgst.edu.in.eventID.database.DatabaseManager;
import siesgst.edu.in.eventID.model.EntriesModel;
import siesgst.edu.in.eventID.utils.SessionManager;

/**
 * Created by rohitramaswamy on 27/07/17.
 */

public class EntriesAdapter extends RecyclerView.Adapter<EntriesAdapter.EntriesViewHolder>
{
	
	// type: 1 = entries; 2 = interested
	// status: 1 = played; 2 = not played
	public static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1232;
	List<EntriesModel> entriesModelList,entriesModelListCopy;
	int type;
	Context context;
	SessionManager session;
	StringRequest stringRequest;
	RequestQueue requestQueue;

	public EntriesAdapter(List<EntriesModel> entriesModelList, int type, Context context)
	{
		this.entriesModelList = entriesModelList;
		entriesModelListCopy = new ArrayList<EntriesModel>();
		entriesModelListCopy.addAll(entriesModelList);
		this.type = type;
		this.context = context;
		session = new SessionManager(context);
		requestQueue = Volley.newRequestQueue(context);
	}
	
	
	@Override
	public EntriesViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
	{
		View view;
		if (type == 1)
		{
			view = LayoutInflater.from(parent.getContext()).inflate(R.layout.entries_item, parent, false);
		}
		else
		{
			view = LayoutInflater.from(parent.getContext()).inflate(R.layout.interested_item, parent, false);
		}
		
		return new EntriesViewHolder(view);
	}
	
	@Override
	public void onBindViewHolder(EntriesViewHolder holder, int position)
	{
		
		if (type == 1)
		{
			holder.entry_tick.setTypeface(Typeface.createFromAsset(context.getAssets(), context.getString(R.string.font_fontawesome)));
			holder.entry_name.setText(entriesModelList.get(position).getName());
			holder.entry_prn.setText(entriesModelList.get(position).getUid());
			if (entriesModelList.get(position).getStatus1().equalsIgnoreCase("1"))
			{
				// played
				Log.d("PlayStatusCheck",entriesModelList.get(position).getName()+" check");
				holder.entry_tick.setText(context.getString(R.string.fa_check_square_o));
			}
			else if (entriesModelList.get(position).getStatus1().equalsIgnoreCase("0"))
			{
				// not played
				Log.d("PlayStatusCheck",entriesModelList.get(position).getName()+" uncheck");

				holder.entry_tick.setText(context.getString(R.string.fa_square_o));
			}
		}
		else
		{
			holder.interested_name.setText(entriesModelList.get(position).getName());
			holder.interested_prn.setText(entriesModelList.get(position).getContact());
			holder.interested_phone.setTypeface(Typeface.createFromAsset(context.getAssets(), context.getString(R.string.font_fontawesome)));
		}
	}
	
	@Override
	public int getItemCount()
	{
		if (entriesModelList == null)
		{ return 0; }
		else
		{ return (int) entriesModelList.size(); }
	}

	public void filter(String query)
	{
		/*
		entries :1
		interested :2
		 */
		Log.d("MaterialSearchView", "filter  " + query);
		Log.d("MaterialSearchView",String.valueOf(type));
		query = query.toLowerCase(Locale.getDefault());
		entriesModelList.clear();

		if (query.length() == 0)
		{
			entriesModelList.addAll(entriesModelListCopy);
		}
		else
		{
			for (int i = 0; i < entriesModelListCopy.size(); i++)
			{
				Log.d("inside for",entriesModelListCopy.get(i).getName().toLowerCase(Locale.getDefault()));
//				if (type == 1)
//				{
				Log.d("EntriesAdapter", "entries search=" + query);
				//for entries
				Log.d("inside if type 1",entriesModelListCopy.get(i).getName().toLowerCase(Locale.getDefault()));
				if (entriesModelListCopy.get(i).getName().toLowerCase(Locale.getDefault()).contains(query) |
						entriesModelListCopy.get(i).getUid().toLowerCase(Locale.getDefault()).contains(query))
				{
					entriesModelList.add(entriesModelListCopy.get(i));
				}
//				}
				/*
				else
				{
					//for interested
					Log.d("EntriesAdapter", "interested search=" + query);
					Log.d("inside if type 2",entriesModelListCopy.get(i).getName().toLowerCase(Locale.getDefault()));
					if (entriesModelListCopy.get(i).getName().toLowerCase(Locale.getDefault()).contains(query) |
							entriesModelListCopy.get(i).getContact().toLowerCase(Locale.getDefault()).contains(query))
					{
						entriesModelList.add(entriesModelListCopy.get(i));
					}
				}
				*/
			}
		}

		if (entriesModelList.size() == 0)
		{
			Log.d("MaterialSearchView","no results");
		}
		else
		{
			Log.d("MaterialSearchView","Oops! something went wrong");
		}
		notifyDataSetChanged();
	}
	
	class EntriesViewHolder extends RecyclerView.ViewHolder
	{
		// entries
		private TextView entry_name, entry_prn, entry_tick;
		
		// interested
		private TextView interested_name, interested_prn, interested_phone;
		ProgressBar progressBar;
		DatabaseManager databaseManager = new DatabaseManager(context);
		
		EntriesViewHolder(View itemView)
		{
			super(itemView);
			if (type == 1)
			{
				// entries
				entry_name = (TextView) itemView.findViewById(R.id.entry_name);
				entry_prn = (TextView) itemView.findViewById(R.id.event_prn);
				entry_tick = (TextView) itemView.findViewById(R.id.entry_tick);
				progressBar=(ProgressBar)itemView.findViewById(R.id.entry_progress_bar);
				entry_tick.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view)
					{
						progressBar.setVisibility(View.VISIBLE);
						
						String url = context.getString(R.string.LIVE_URL)+"play?event_id="+session.getEventId()+"&uid="
								+entriesModelList.get(getAdapterPosition()).getUid();
						Log.v("play",url);
						stringRequest = new StringRequest(Request.Method.POST, url
								, new Response.Listener<String>()
						{
							@Override
							public void onResponse(String response)
							{
								progressBar.setVisibility(View.GONE);
								if(response.contains("success"))
								{

									if(entriesModelList.get(getAdapterPosition()).getStatus1().equals("0")) {
										entriesModelList.get(getAdapterPosition()).setStatus1("1");
										databaseManager.toggleStatus(entriesModelList.get(getAdapterPosition()).getUid(),"1");
									}
									else{
										entriesModelList.get(getAdapterPosition()).setStatus1("0");
										databaseManager.toggleStatus(entriesModelList.get(getAdapterPosition()).getUid(),"0");

									}
									Toast.makeText(context,"Status inverted",Toast.LENGTH_SHORT).show();
									notifyDataSetChanged();
								}
							}
						}, new Response.ErrorListener() {
							@Override
							public void onErrorResponse(VolleyError error)
							{
								progressBar.setVisibility(View.GONE);

								Log.v("onError",error.toString());
								notifyDataSetChanged();
							}
						});
						requestQueue.add(stringRequest);
					}
				});
			}
			else if (type == 2)
			{
				// interested
				interested_name = (TextView) itemView.findViewById(R.id.interested_name);
				interested_prn = (TextView) itemView.findViewById(R.id.interested_prn);
				interested_phone = (TextView) itemView.findViewById(R.id.interested_phone);
				interested_phone.setOnClickListener(new View.OnClickListener()
				{
					@Override
					public void onClick(View view)
					{
						if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.CALL_PHONE)
								!= PackageManager.PERMISSION_GRANTED)
						{
							Log.v("perm", "[if]->we do not have permission");
							
							// Should we show an explanation?
							if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context,
									Manifest.permission.CALL_PHONE))
							{
								// we need to show an explanation
								Log.v("perm", "[if[if]]->taking user to request permission activity");
								context.startActivity(new Intent(context, RequestPermissionActivity.class));
							}
							else
							{
								Log.v("perm", "[if[else]]-> asking permission");
								ActivityCompat.requestPermissions((Activity) context,
										new String[]{Manifest.permission.CALL_PHONE},
										MY_PERMISSIONS_REQUEST_CALL_PHONE);
							}
							
						}
						else
						{
							Log.v("perm", "[else]->we have permission");
							String tel = "tel:" + entriesModelList.get(getAdapterPosition()).getContact();
							Intent intent = new Intent();
							intent.setAction(Intent.ACTION_CALL);
							intent.setData(Uri.parse(tel));
							context.startActivity(intent);
						}
					}
				});
			}
		}
	}
}
