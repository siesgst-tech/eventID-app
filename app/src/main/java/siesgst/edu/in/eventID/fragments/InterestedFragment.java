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
import siesgst.edu.in.eventID.adapters.EntriesAdapter;
import siesgst.edu.in.eventID.model.EntriesModel;
import siesgst.edu.in.eventID.utils.SessionManager;

/**
 * Created by rohitramaswamy on 30/08/17.
 */

public class InterestedFragment extends Fragment
{
	private final String LOG_TAG = InterestedFragment.class.getSimpleName();
	List<EntriesModel> entriesModels = new ArrayList<EntriesModel>();
	RecyclerView recyclerView;
	SessionManager session;
	String name, prn;
	
	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_interested, container, false);
		session = new SessionManager(getActivity());
		recyclerView = (RecyclerView) view.findViewById(R.id.interested_recycler);
		init();
		settingAdapter();
		recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
		return view;
	}
	
	
	public void init()
	{
		entriesModels.add(new EntriesModel("I am interested", "my prn"));
		entriesModels.add(new EntriesModel("Aee merko bhi le na", "mera bhi prn lele"));
	}
	
	public void settingAdapter()
	{
		recyclerView.setAdapter(new EntriesAdapter(entriesModels, 2, getActivity()));
	}
}
