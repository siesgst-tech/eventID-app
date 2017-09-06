package siesgst.edu.in.eventID.fragments;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import siesgst.edu.in.eventID.R;
import siesgst.edu.in.eventID.adapters.MessagesAdapter;
import siesgst.edu.in.eventID.model.MessagesModel;

/**
 * Created by rohitramaswamy on 01/08/17.
 */

public class MessagesFragment extends Fragment
{
	FloatingActionButton fab;
	List<MessagesModel> messages;
	RecyclerView recyclerView;
	
	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_messages, container, false);
		recyclerView = (RecyclerView) view.findViewById(R.id.messages_recycler);
		fab = (FloatingActionButton) view.findViewById(R.id.new_message);
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
					}
				});
			}
		});
		init();
		recyclerView.setAdapter(new MessagesAdapter(messages));
		return view;
	}
	
	public void init()
	{
		messages = new ArrayList<MessagesModel>();
		messages.add(new MessagesModel("Maze Bot", "Title goes here", "Description goes here. Description goes here. Description goes here. Description goes here. " +
				"Description goes here. Description goes here. Description goes here"));
		messages.add(new MessagesModel("Maze Bot", "Title goes here", "Description goes here. Description goes here. Description goes here. Description goes here. " +
				"Description goes here. Description goes here. Description goes here"));
		messages.add(new MessagesModel("Maze Bot", "Title goes here", "Description goes here. Description goes here. Description goes here. Description goes here. " +
				"Description goes here. Description goes here. Description goes here"));
		messages.add(new MessagesModel("Maze Bot", "Title goes here", "Description goes here. Description goes here. Description goes here. Description goes here. " +
				"Description goes here. Description goes here. Description goes here"));
		messages.add(new MessagesModel("Maze Bot", "Title goes here", "Description goes here. Description goes here. Description goes here. Description goes here. " +
				"Description goes here. Description goes here. Description goes here"));
		messages.add(new MessagesModel("Maze Bot", "Title goes here", "Description goes here. Description goes here. Description goes here. Description goes here. " +
				"Description goes here. Description goes here. Description goes here"));
		messages.add(new MessagesModel("Maze Bot", "Title goes here", "Description goes here. Description goes here. Description goes here. Description goes here. " +
				"Description goes here. Description goes here. Description goes here"));
		messages.add(new MessagesModel("Maze Bot", "Title goes here", "Description goes here. Description goes here. Description goes here. Description goes here. " +
				"Description goes here. Description goes here. Description goes here"));
		messages.add(new MessagesModel("Maze Bot", "Title goes here", "Description goes here. Description goes here. Description goes here. Description goes here. " +
				"Description goes here. Description goes here. Description goes here"));
		messages.add(new MessagesModel("Maze Bot", "Title goes here", "Description goes here. Description goes here. Description goes here. Description goes here. " +
				"Description goes here. Description goes here. Description goes here"));
		messages.add(new MessagesModel("Maze Bot", "Title goes here", "Description goes here. Description goes here. Description goes here. Description goes here. " +
				"Description goes here. Description goes here. Description goes here"));
		messages.add(new MessagesModel("Maze Bot", "Title goes here", "Description goes here. Description goes here. Description goes here. Description goes here. " +
				"Description goes here. Description goes here. Description goes here"));
		messages.add(new MessagesModel("Maze Bot", "Title goes here", "Description goes here. Description goes here. Description goes here. Description goes here. " +
				"Description goes here. Description goes here. Description goes here"));
		
	}
}



/*

/api/eventhead/id/messages

*/