package com.example.event.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.event.R;
import com.example.event.adapters.EventsAdapter;
import com.example.event.model.EventsModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rohitramaswamy on 23/07/17.
 */

public class EventsFragment extends Fragment
{
	private static final String LOG_TAG = EventsFragment.class.getSimpleName();
	
	List<EventsModel> eventsModelList = new ArrayList<EventsModel>();
	RecyclerView recyclerView;
	
	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_events,container,false);
		recyclerView = (RecyclerView) view.findViewById(R.id.events_recycler);
		init();
		recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
		recyclerView.setAdapter(new EventsAdapter(eventsModelList));
		return view;
	}
	
	
	public void init()
	{
		eventsModelList.add(new EventsModel("Maze bot","40"));
		eventsModelList.add(new EventsModel("Quarantine","50"));
		eventsModelList.add(new EventsModel("Treasure Hunt","80"));
		eventsModelList.add(new EventsModel("Fashion show","500"));
		eventsModelList.add(new EventsModel("Lan gaming","50"));
		eventsModelList.add(new EventsModel("Racebot","50"));
		eventsModelList.add(new EventsModel("Tanks","70"));
		eventsModelList.add(new EventsModel("Aisehi koi toh bada naam daalke dekhna tha","40"));
		eventsModelList.add(new EventsModel("yeh wale ka bhaav zyaada","5000"));
	}
	
}
