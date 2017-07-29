package siesgst.edu.in.eventID.fragments;

import android.graphics.Typeface;
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
import siesgst.edu.in.eventID.adapters.EventsAdapter;
import siesgst.edu.in.eventID.model.EventsModel;



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
		Typeface typeface_fa = Typeface.createFromAsset(getActivity().getAssets(),getString(R.string.font_fontawesome));
		recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
		recyclerView.setAdapter(new EventsAdapter(eventsModelList,getActivity(),typeface_fa));
		return view;
	}
	
	
	public void init()
	{
		eventsModelList.add(new EventsModel("Maze bot",getString(R.string.description),"40"));
		eventsModelList.add(new EventsModel("Quarantine",getString(R.string.description),"50"));
		eventsModelList.add(new EventsModel("Treasure Hunt",getString(R.string.description),"80"));
		eventsModelList.add(new EventsModel("Fashion show",getString(R.string.description),"500"));
		eventsModelList.add(new EventsModel("Lan gaming",getString(R.string.description),"50"));
		eventsModelList.add(new EventsModel("Racebot",getString(R.string.description),"50"));
		eventsModelList.add(new EventsModel("Tanks",getString(R.string.description),"70"));
		eventsModelList.add(new EventsModel("Aisehi koi toh bada naam daalke dekhna tha",getString(R.string.description),"40"));
		eventsModelList.add(new EventsModel("yeh wale ka bhaav zyaada",getString(R.string.description),"5000"));
	}
	
}
