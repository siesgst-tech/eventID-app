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
import com.example.event.adapters.EntriesAdapter;
import com.example.event.model.EntriesModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rohitramaswamy on 23/07/17.
 */

public class EntriesFragment extends Fragment
{
	private static final String LOG_TAG = EntriesFragment.class.getSimpleName();
	List<EntriesModel> entriesModelList = new ArrayList<EntriesModel>();
	RecyclerView recyclerView;
	
	
	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_entries, container, false);
		recyclerView = (RecyclerView) view.findViewById(R.id.entries_recycler);
		init();
		recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
		recyclerView.setAdapter(new EntriesAdapter(entriesModelList));
		return view;
	}
	
	
	private void init()
	{
		entriesModelList.add(new EntriesModel("Rohit Ramaswamy","115A1018"));
		entriesModelList.add(new EntriesModel("Omkar Prabhu","115A1077"));
		entriesModelList.add(new EntriesModel("Aditya Nair","115A1067"));
		entriesModelList.add(new EntriesModel("Vinay Ambre","115A1031"));
		entriesModelList.add(new EntriesModel("Aditya Kulkarni","115A1061"));
		entriesModelList.add(new EntriesModel("Shivshankar Ravi","115A1019"));
		entriesModelList.add(new EntriesModel("Siddhesh Shinde","115A1091"));
		entriesModelList.add(new EntriesModel("Pradyumna Bapat","115A1038"));
		entriesModelList.add(new EntriesModel("Abhinandan Gupta","115A1049"));
		entriesModelList.add(new EntriesModel("Vipul Singh Raghuvanshi","115A1099"));
	}
}
