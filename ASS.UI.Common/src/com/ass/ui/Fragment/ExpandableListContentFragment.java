package com.ass.ui.Fragment;

import com.ass.ui.common.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshExpandableListView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;
import com.ass.ui.Fragment.ContentFragment;

public class ExpandableListContentFragment extends ContentFragment implements OnGroupClickListener, OnRefreshListener2<ExpandableListView> {
	
	private PullToRefreshExpandableListView mExpandList = null;
	
	public ExpandableListContentFragment(final String title)
	{
		super(title);
	}
	
	@Override  
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  
            Bundle savedInstanceState) {  
   
        View rootView = inflater.inflate(R.layout.expandable_list_content_frame, container, false);  
        
        mExpandList = (PullToRefreshExpandableListView) rootView.findViewById(R.id.expand_list); 
        mExpandList.getRefreshableView().setGroupIndicator(null);  
        mExpandList.getRefreshableView().setDivider(null);  
        mExpandList.getRefreshableView().setSelector(android.R.color.transparent);  
        mExpandList.getRefreshableView().setOnGroupClickListener(this);  
        mExpandList.setOnRefreshListener(this); 
        mExpandList.setMode(Mode.BOTH);  
        mExpandList.getLoadingLayoutProxy(false, true).setPullLabel(getString(R.string.pull_to_refresh));  
        mExpandList.getLoadingLayoutProxy(false, true).setRefreshingLabel(getString(R.string.loading));  
        mExpandList.getLoadingLayoutProxy(false, true).setReleaseLabel(getString(R.string.release_to_load));  
          
        return rootView;  
    }  
	
	@Override  
    public void onStart() {  
        super.onStart();  
    }

	@Override
	public boolean onGroupClick(ExpandableListView parent, View v,
			int groupPosition, long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onPullDownToRefresh(
			PullToRefreshBase<ExpandableListView> refreshView) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPullUpToRefresh(
			PullToRefreshBase<ExpandableListView> refreshView) {
		// TODO Auto-generated method stub
		
	}
}
