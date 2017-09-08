package siesgst.edu.in.eventID.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import siesgst.edu.in.eventID.fragments.EntriesFragment;

/**
 * Created by rohitramaswamy on 23/07/17.
 */

public class HomeTabLayoutAdapter extends FragmentPagerAdapter
{
	private final List<Fragment> mFragmentList = new ArrayList<>();
	private final List<String> mFragmentTitleList = new ArrayList<>();
	
	public HomeTabLayoutAdapter(FragmentManager fm)
	{
		super(fm);
	}
	
	@Override
	public Fragment getItem(int position)
	{
		return mFragmentList.get(position);
	}
	
	@Override
	public int getCount()
	{
		return mFragmentList.size();
	}
	
	public void addFrag(Fragment fragment, String title)
	{
		mFragmentList.add(fragment);
		mFragmentTitleList.add(title);
	}
	
	@Override
	public CharSequence getPageTitle(int position)
	{
		return mFragmentTitleList.get(position);
	}

	public void setEntriesCount(int count){
		mFragmentTitleList.set(0,"Entries ("+count+")");
	}
	public void setInterestedCount(int count){
		mFragmentTitleList.set(1,"Interested ("+count+")");

	}
}
