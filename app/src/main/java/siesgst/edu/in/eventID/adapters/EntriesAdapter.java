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
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import siesgst.edu.in.eventID.R;
import siesgst.edu.in.eventID.activities.RequestPermissionActivity;
import siesgst.edu.in.eventID.model.EntriesModel;

import static android.view.View.GONE;

/**
 * Created by rohitramaswamy on 27/07/17.
 */

public class EntriesAdapter extends RecyclerView.Adapter<EntriesAdapter.EntriesViewHolder>
{
	
	// type: 1 = entries; 2 = interested
	// status: 1 = played; 2 = not played
	public static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1232;
	List<EntriesModel> entriesModelList = new ArrayList<EntriesModel>();
	List<EntriesModel> entriesModelListCopy;
	int type;
	Context context;
	
	public EntriesAdapter(List<EntriesModel> entriesModelList, int type, Context context)
	{
		this.entriesModelList = entriesModelList;
		entriesModelListCopy=new ArrayList<>();
		entriesModelList.addAll(entriesModelList);
		this.type = type;
		this.context = context;
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
			if (entriesModelList.get(position).getPaid().equalsIgnoreCase("1"))
			{
				// played
				holder.entry_check.setVisibility(GONE);
				holder.entry_tick.setVisibility(View.VISIBLE);
			}
			else if (entriesModelList.get(position).getPaid().equalsIgnoreCase("2"))
			{
				// not played
				holder.entry_tick.setVisibility(GONE);
				holder.entry_check.setVisibility(View.VISIBLE);
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
	
	class EntriesViewHolder extends RecyclerView.ViewHolder
	{
		// entries
		private TextView entry_name, entry_prn, entry_tick;
		private CheckBox entry_check;
		
		// interested
		private TextView interested_name, interested_prn, interested_phone;
		
		EntriesViewHolder(View itemView)
		{
			super(itemView);
			if(type==1)
			{
				// entries
				entry_name = (TextView) itemView.findViewById(R.id.entry_name);
				entry_prn = (TextView) itemView.findViewById(R.id.event_prn);
				entry_tick = (TextView) itemView.findViewById(R.id.entry_tick);
				entry_check = (CheckBox) itemView.findViewById(R.id.entry_check);
			}
			else if(type==2)
			{
				// interested
				interested_name = (TextView) itemView.findViewById(R.id.interested_name);
				interested_prn = (TextView) itemView.findViewById(R.id.interested_prn);
				interested_phone = (TextView) itemView.findViewById(R.id.interested_phone);
				interested_phone.setOnClickListener(new View.OnClickListener() {
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


	public void filter(String query){

			query = query.toLowerCase(Locale.getDefault());
			entriesModelList.clear();
			if (query.length() == 0)
			{
				entriesModelList.addAll(entriesModelListCopy);

			}
			else
			{
				for (int i = 0; i < entriesModelListCopy.size(); i++) {
					if (type == 1){

						Log.d("EntriesAdapter","entries search="+query);
						//for entries
						if (entriesModelListCopy.get(i).getName().toLowerCase(Locale.getDefault()).contains(query) |
								entriesModelListCopy.get(i).getUid().toLowerCase(Locale.getDefault()).contains(query)) {
							entriesModelList.add(entriesModelListCopy.get(i));
						}
					}
					else{
						//for interested
						Log.d("EntriesAdapter","interested search="+query);

						if (entriesModelListCopy.get(i).getName().toLowerCase(Locale.getDefault()).contains(query) |
								entriesModelListCopy.get(i).getContact().toLowerCase(Locale.getDefault()).contains(query)) {
							entriesModelList.add(entriesModelListCopy.get(i));
						}

					}
				}
			}




			notifyDataSetChanged();



	}
}
