package com.ass.ui.Fragment;

import com.ass.ui.common.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class RightFrameFragment extends Fragment {
	@Override
    public void onCreate(Bundle savedInstanceState)
    {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
    }
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.right_frame, container, false);
    }
	
	@Override
    public void onPause()
    {
        // TODO Auto-generated method stub
        super.onPause();
    }
}
