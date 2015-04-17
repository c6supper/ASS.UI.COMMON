package com.ass.ui.Fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;

import com.ass.ui.common.R;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

public class FrameworkActivity extends SlidingFragmentActivity {

	private int mTitleRes;
	protected LeftFrameFragment mLeftFrame;
	protected RightFrameFragment mRightFrame;
	
	public FrameworkActivity() {
	}
	public FrameworkActivity(int titleRes) {
		mTitleRes = titleRes;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setTitle(mTitleRes);
		
		// customize the SlidingMenu
		SlidingMenu sm = getSlidingMenu();
		sm.setShadowWidthRes(R.dimen.shadow_width);
		sm.setShadowDrawable(R.drawable.shadow);
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		sm.setFadeDegree(0.35f);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		sm.setMode(SlidingMenu.LEFT_RIGHT);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
				
		setBehindContentView(R.layout.left_frame);
		if (savedInstanceState == null) {
			FragmentTransaction t = this.getSupportFragmentManager().beginTransaction();
			mLeftFrame = new LeftFrameFragment();
			t.replace(R.id.left_frame, mLeftFrame);
			t.commit();
		} else {
			mLeftFrame = (LeftFrameFragment) this.getSupportFragmentManager().findFragmentById(R.id.left_frame);
		}

		setContentView(R.layout.center_frame);
		getSupportFragmentManager()
		.beginTransaction()
		.replace(R.id.center_frame, new CenterFrameFragment())
		.commit();
		
		getSlidingMenu().setSecondaryMenu(R.layout.right_frame);
		getSlidingMenu().setSecondaryShadowDrawable(R.drawable.shadowright);
		getSupportFragmentManager()
		.beginTransaction()
		.replace(R.id.right_frame, mRightFrame = new RightFrameFragment())
		.commit();
	}
}

