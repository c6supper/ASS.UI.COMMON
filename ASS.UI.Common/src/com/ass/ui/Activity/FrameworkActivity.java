package com.ass.ui.Activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;

import com.ass.ui.Fragment.CenterFrameFragment;
import com.ass.ui.Fragment.LeftFrameFragment;
import com.ass.ui.Fragment.RightFrameFragment;
import com.ass.ui.common.R;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

public class FrameworkActivity extends SlidingFragmentActivity {

	private int mTitleRes = -1;
	private SlidingMenu mSlidingMenu;
	protected LeftFrameFragment mLeftFrame;
	protected RightFrameFragment mRightFrame;
	protected CenterFrameFragment mCenterFrame;
	
	public FrameworkActivity() {
	}
	public FrameworkActivity(int titleRes) {
		mTitleRes = titleRes;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if(mTitleRes != -1)
			setTitle(mTitleRes);
		
		setContentView(R.layout.center_frame);
		setBehindContentView(R.layout.left_frame);
		
		// customize the SlidingMenu
		mSlidingMenu = getSlidingMenu();
		mSlidingMenu.setMode(SlidingMenu.LEFT_RIGHT);
		mSlidingMenu.setSecondaryMenu(R.layout.right_frame);
		mSlidingMenu.setSecondaryShadowDrawable(R.drawable.shadowright);
		mSlidingMenu.setShadowWidthRes(R.dimen.shadow_width);
		mSlidingMenu.setShadowDrawable(R.drawable.shadow);
		mSlidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		mSlidingMenu.setFadeDegree(0.35f);
		mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

		if (savedInstanceState == null) {
			getSupportFragmentManager()
			.beginTransaction()
			.replace(R.id.center_frame, mCenterFrame = new CenterFrameFragment())
			.replace(R.id.left_frame, mLeftFrame = new LeftFrameFragment())
			.replace(R.id.right_frame, mRightFrame = new RightFrameFragment())
			.commit();
		} else {
			mCenterFrame = (CenterFrameFragment) this.getSupportFragmentManager().findFragmentById(R.id.center_frame);
			mLeftFrame = (LeftFrameFragment) this.getSupportFragmentManager().findFragmentById(R.id.left_frame);
			mRightFrame = (RightFrameFragment) this.getSupportFragmentManager().findFragmentById(R.id.right_frame);
		}

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}
}

