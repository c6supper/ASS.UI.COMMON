package com.ass.ui.Fragment;  
  
import java.util.ArrayList;
import java.util.List;  

import com.ass.ui.Adaptor.ContentFragmentPagerAdapter;
import com.ass.ui.Fragment.ContentFragment;
import com.ass.ui.View.SlidingTabLayout;
import com.ass.ui.common.R;
  
import android.os.Bundle;  
import android.support.v4.app.Fragment;  
import android.support.v4.view.ViewPager;  
import android.view.LayoutInflater;  
import android.view.View;  
import android.view.ViewGroup;  
  
public class CenterFrameFragment extends Fragment {  
      
    private ViewPager mViewPager;  
    private static final String[] titles = {"One","Two","Three","Four","Five"};  
    private List<ContentFragment> mFragList = new ArrayList<ContentFragment>();  
    private ContentFragmentPagerAdapter mAdapter;  
    private SlidingTabLayout mSlidingTab;
      
    public CenterFrameFragment(){}  
    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
    }
      
    @Override  
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  
            Bundle savedInstanceState) {  
   
        View rootView = inflater.inflate(R.layout.center_frame, container, false);  
          
        for(int i = 0;i < titles.length; i++){  
        	ContentFragment frag = new ContentFragment(titles[i]);
            mFragList.add(frag);  
        }  
        
        findView(rootView);  
          
        return rootView;  
    }  

    private void findView(View rootView) {  
          
        mViewPager = (ViewPager) rootView.findViewById(R.id.view_pager);  
        mAdapter = new ContentFragmentPagerAdapter(getActivity().getSupportFragmentManager(),mFragList);  
        mViewPager.setAdapter(mAdapter);
        mSlidingTab = (SlidingTabLayout)rootView.findViewById(R.id.sliding_tab); 
        mSlidingTab.setDistributeEvenly(true);
        mSlidingTab.setViewPager(mViewPager);
    }  
      
    @Override  
    public void onStart() {  
          
        if(mAdapter!=null){  
            mAdapter.notifyDataSetChanged();  
        }  
          
        super.onStart();  
    }  
    
    @Override
    public void onPause()
    {
        // TODO Auto-generated method stub
        super.onPause();
    }
}   