package com.ass.ui.Fragment;

import android.support.v4.app.Fragment;

public class ContentFragment extends Fragment {

	private String mTitle = "";
	
	public ContentFragment(final String title)
	{
		mTitle = title;
	}
	
	public final String getTitle()
	{
		return mTitle;
	}
}
