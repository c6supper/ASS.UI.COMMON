package com.ass.ui.Adaptor;

import java.util.List;

import com.ass.ui.Fragment.ContentFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.ListFragment;

public class ContentFragmentPagerAdapter extends FragmentPagerAdapter {

	private List<ContentFragment> mFragList;
	public ContentFragmentPagerAdapter(FragmentManager fm) {
		super(fm);
	}
	
	public ContentFragmentPagerAdapter(FragmentManager fm,List<ContentFragment> list) {
		super(fm);
		this.mFragList = list;
	}

	@Override
	public Fragment getItem(int arg0) {
		return mFragList.get(arg0);
	}

	@Override
	public int getCount() {
		return mFragList.size();
	}
	
	@Override
	public CharSequence getPageTitle(int position) {
		// TODO Auto-generated method stub
		return mFragList.get(position).getTitle();
	}

}
