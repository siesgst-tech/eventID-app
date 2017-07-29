package siesgst.edu.in.eventID.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import siesgst.edu.in.eventID.R;
import siesgst.edu.in.eventID.model.EventsModel;

/**
 * Created by rohitramaswamy on 27/07/17.
 */

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ViewHolder>
{
	List<EventsModel> eventsModelList = new ArrayList<EventsModel>();
	Context context;
	Typeface ttf;
	
	public EventsAdapter(List<EventsModel> eventsModelList, Context context, Typeface ttf)
	{
		this.eventsModelList = eventsModelList;
		this.context = context;
		this.ttf = ttf;
	}
	
	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
	{
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_item,parent,false);
		return new ViewHolder(view);
	}
	
	@Override
	public void onBindViewHolder(ViewHolder holder, int position)
	{
		holder.name.setText(eventsModelList.get(position).getEventName());
		holder.inr.setTypeface(ttf);
		holder.inr.setText(context.getString(R.string.fa_inr));
		holder.cost.setText(" "+eventsModelList.get(position).getCost());
		holder.description.setText(eventsModelList.get(position).getDescription());
	}
	
	@Override
	public int getItemCount()
	{
		if(eventsModelList==null)
		return 0;
		else
			return (int) eventsModelList.size();
	}
	
	class ViewHolder extends RecyclerView.ViewHolder
	{
		TextView name,cost,description,inr;
		
		public ViewHolder(View itemView)
		{
			super(itemView);
			name = (TextView) itemView.findViewById(R.id.event_name);
			cost = (TextView) itemView.findViewById(R.id.event_cost);
			inr = (TextView) itemView.findViewById(R.id.inr_fa);
			description = (TextView) itemView.findViewById(R.id.event_description);
		}
	}
	
}
