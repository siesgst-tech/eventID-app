package siesgst.edu.in.eventID.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import siesgst.edu.in.eventID.R;
import siesgst.edu.in.eventID.model.EntriesModel;

import static android.view.View.GONE;

/**
 * Created by rohitramaswamy on 27/07/17.
 */

public class EntriesAdapter extends RecyclerView.Adapter<EntriesAdapter.EntriesViewHolder>
{
	
	// type: 1 = entries; 2 = interested
	// status: 1 = played; 2 = not played
	
	List<EntriesModel> entriesModelList = new ArrayList<EntriesModel>();
	int type;
	Context context;
	
	public EntriesAdapter(List<EntriesModel> entriesModelList, int type, Context context)
	{
		this.entriesModelList = entriesModelList;
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
			holder.entry_prn.setText(entriesModelList.get(position).getPrn());
			if (entriesModelList.get(position).getStatus() == 1)
			{
				// played
				holder.entry_check.setVisibility(GONE);
				holder.entry_tick.setVisibility(View.VISIBLE);
			}
			else if (entriesModelList.get(position).getStatus() == 2)
			{
				// not played
				holder.entry_tick.setVisibility(GONE);
				holder.entry_check.setVisibility(View.VISIBLE);
			}
		}
		else
		{
			holder.interested_name.setText(entriesModelList.get(position).getName());
			holder.interested_prn.setText(entriesModelList.get(position).getPrn());
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
		
		
		public EntriesViewHolder(View itemView)
		{
			super(itemView);
			// entries
			entry_name = (TextView) itemView.findViewById(R.id.entry_name);
			entry_prn = (TextView) itemView.findViewById(R.id.event_prn);
			entry_tick = (TextView) itemView.findViewById(R.id.entry_tick);
			entry_check = (CheckBox) itemView.findViewById(R.id.entry_check);
			
			// interested
			interested_name = (TextView) itemView.findViewById(R.id.interested_name);
			interested_prn = (TextView) itemView.findViewById(R.id.interested_prn);
			interested_phone = (TextView) itemView.findViewById(R.id.interested_phone);
		}
	}
}
