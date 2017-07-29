package siesgst.edu.in.eventID.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import siesgst.edu.in.eventID.R;
import siesgst.edu.in.eventID.model.MessagesModel;


/**
 * Created by rohitramaswamy on 28/07/17.
 */

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.ViewHolder>
{
	
	List<MessagesModel> messagesModels = new ArrayList<MessagesModel>();
	
	
	public MessagesAdapter(List<MessagesModel> messagesModels)
	{
		this.messagesModels = messagesModels;
	}
	
	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
	{
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_item,parent,false);
		return  new ViewHolder(view);
	}
	
	@Override
	public void onBindViewHolder(ViewHolder holder, int position)
	{
		holder.event_name.setText(messagesModels.get(position).getEvent_name());
		holder.title.setText(messagesModels.get(position).getTitle());
		holder.content.setText(messagesModels.get(position).getDescription());
	}
	
	@Override
	public int getItemCount()
	{
		if(messagesModels==null)
		{
			return 0;
		}
		else
			return (int) messagesModels.size();
	}
	
	class ViewHolder extends RecyclerView.ViewHolder
	{
		
		TextView event_name,title,content;
		
		public ViewHolder(View itemView)
		{
			super(itemView);
			event_name = (TextView) itemView.findViewById(R.id.message_event_name);
			title = (TextView) itemView.findViewById(R.id.message_title);
			content = (TextView) itemView.findViewById(R.id.message_content);
		}
	}
}
