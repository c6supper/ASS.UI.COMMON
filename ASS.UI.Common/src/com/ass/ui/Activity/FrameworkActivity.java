package com.ass.ui.Activity;

import java.io.File;
import java.io.IOException;

import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.ass.ui.AsyncTask.AssetsCopyTask;
import com.ass.ui.AsyncTask.FileCopyTask;
import com.ass.ui.Fragment.CenterFrameFragment;
import com.ass.ui.Fragment.LeftFrameFragment;
import com.ass.ui.Fragment.RightFrameFragment;
import com.ass.ui.common.R;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

public class FrameworkActivity extends SlidingFragmentActivity {

	private int mTitleRes = -1;
	private SlidingMenu mSlidingMenu;
	private android.app.ActionBar	mActionBar;
	protected LeftFrameFragment mLeftFrame;
	protected RightFrameFragment mRightFrame;
	protected CenterFrameFragment mCenterFrame;
	
	public FrameworkActivity() {
	}
	public FrameworkActivity(int titleRes) {
		mTitleRes = titleRes;
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
    	menu.add(R.string.user_center)
    	.setIcon(R.drawable.user_center)  
    	.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS); 
        return true;
    }
	
	private boolean initApp()
	{
		PackageManager manager = getPackageManager();
		try 
		{
			PackageInfo info = manager.getPackageInfo(getPackageName(), 0);
			
			SharedPreferences shared = getSharedPreferences(info.packageName, MODE_PRIVATE);
			String installedVersion = shared.getString("version", "");
			
			if(!installedVersion.equals(info.versionName))
			{
				try 
				{
					for(String directory : getAssets().list(""))
					{
						if(directory.equals("AssetsRes"))
						{
							String[] parameters = {"AssetsRes",getFilesDir().getAbsolutePath()};
							AssetsCopyTask asyncCopy = new AssetsCopyTask(null, null,this);
							asyncCopy.execute(parameters);
							shared.edit()
							.putString("version", info.versionName)
							.apply();
							break;
						}
					}
				} 
				catch (IOException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				return true;
			}
			
		} catch (NameNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		return false;
	}
	
	private void initSlidingMenu()
	{
		// customize the SlidingMenu
		mSlidingMenu = getSlidingMenu();
		mSlidingMenu.setMode(SlidingMenu.LEFT_RIGHT);
		mSlidingMenu.setSecondaryMenu(R.layout.right_frame);
		mSlidingMenu.setSecondaryShadowDrawable(R.drawable.shadowright);
		mSlidingMenu.setShadowWidthRes(R.dimen.shadow_width);
		mSlidingMenu.setShadowDrawable(R.drawable.shadow);
		mSlidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		mSlidingMenu.setFadeDegree(0.35f);
		mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		mSlidingMenu.setBackgroundColor(getResources().getColor(R.color.violet));
	}
	
	private void initActionBar()
	{
		mActionBar = getActionBar();
		mActionBar.setDisplayHomeAsUpEnabled(true);
		mActionBar.setDisplayShowHomeEnabled(true);
		mActionBar.setDisplayShowCustomEnabled(true);
		mActionBar.setDisplayShowTitleEnabled(true);
	}
	
	@Override  
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		if(item.getItemId() ==  android.R.id.home)
		{  
            toggle();  
            return true;
		}
        else if(item.getTitle() == getString(R.string.user_center))
        {
        	if(mSlidingMenu.isSecondaryMenuShowing()){
        		mSlidingMenu.showContent();
        	}else{
        		mSlidingMenu.showSecondaryMenu();
        	}
            return true;
        }
        else
        {
        	return super.onOptionsItemSelected(item);  
        }
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if(mTitleRes != -1)
			setTitle(mTitleRes);
		
		initApp();
		
		setContentView(R.layout.center_frame);
		setBehindContentView(R.layout.left_frame);
		
		initSlidingMenu();
		
		initActionBar();

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
	}
}

