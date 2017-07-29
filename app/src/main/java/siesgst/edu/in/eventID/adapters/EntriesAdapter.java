package siesgst.edu.in.eventID.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import siesgst.edu.in.eventID.R;
import siesgst.edu.in.eventID.model.EntriesModel;

/**
 * Created by rohitramaswamy on 27/07/17.
 */

public class EntriesAdapter extends RecyclerView.Adapter<EntriesAdapter.EntriesViewHolder>
{
	List<EntriesModel> entriesModelList = new ArrayList<EntriesModel>();
	
	public EntriesAdapter(List<EntriesModel> entriesModelList)
	{
		this.entriesModelList = entriesModelList;
	}
	
	@Override
	public EntriesViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
	{
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.entries_item,parent,false);
		return new EntriesViewHolder(view);
	}
	
	@Override
	public void onBindViewHolder(EntriesViewHolder holder, int position)
	{
		holder.name.setText(entriesModelList.get(position).getName());
		holder.prn.setText(entriesModelList.get(position).getPrn());
	}
	
	@Override
	public int getItemCount()
	{
		if (entriesModelList == null)
		{ return 0; }
		else
		{ return (int)entriesModelList.size(); }
	}
	
	class EntriesViewHolder extends RecyclerView.ViewHolder
	{
		private TextView name, prn;
		
		public EntriesViewHolder(View itemView)
		{
			super(itemView);
			name = (TextView) itemView.findViewById(R.id.entry_name);
			prn = (TextView) itemView.findViewById(R.id.entry_prn);
		}
	}
}
