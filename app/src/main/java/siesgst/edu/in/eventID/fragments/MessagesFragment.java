package siesgst.edu.in.eventID.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
	
	List<MessagesModel> messages = new ArrayList<>();
	RecyclerView recyclerView;
	
	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_messages, container, false);
		recyclerView = (RecyclerView) view.findViewById(R.id.messages_recycler);
		recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
		init();
		recyclerView.setAdapter(new MessagesAdapter(messages));
		return view;
	}
	
	public void init()
	{
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
