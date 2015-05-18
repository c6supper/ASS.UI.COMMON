package com.ass.ui.AsyncTask;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.widget.ProgressBar;
import android.widget.TextView;

public class AssetsCopyTask extends AsyncTask<String, Integer, Integer> {
	
	private ProgressBar 			m_progressBar = null;
	private TextView				m_textView = null;
	private AssetManager			m_assetManager = null;
	private ArrayList<String>		m_srcFileList = new ArrayList<String>();
	
	public AssetsCopyTask(ProgressBar progressBar,TextView testView,Context context) {
		m_progressBar = progressBar;
		m_textView = testView;
		m_assetManager = context.getAssets();
	}
	
	@SuppressWarnings("resource")
	public boolean copy(final String src,final String dest)
	{
		try
		{
			File destFile = new File(dest);
			if(destFile.exists())
			{
				if(!destFile.delete())
					return false;
			}
			destFile.getParentFile().mkdirs();
			
			destFile = new File(dest);
			if(!destFile.createNewFile()) {
				return false;
			} 

			FileChannel inChannel = m_assetManager.openFd(src).createInputStream().getChannel();
		    FileChannel outChannel = new FileOutputStream(dest).getChannel();
		    try 
		    {
		        inChannel.transferTo(0, inChannel.size(), outChannel);
		    } 
		    catch(Exception e)
		    {
		    	e.printStackTrace();
		    	return false;
		    }
		    finally 
		    {
		        if (inChannel != null)
		            inChannel.close();
		        if (outChannel != null)
		            outChannel.close();
		    }
		}
		catch (Exception e) 
		{    
			e.printStackTrace();   
			return false;
		}   
		
		return true;
	}
	
	private boolean prepareFileList(String src)
	{
		boolean ret = false;

		try
		{	
			String[] assetsFile = m_assetManager.list(src);
			if(assetsFile.length <= 0)
			{
				ret = m_srcFileList.add(src);
			}
			else
			{
				for(String relativeSrc : assetsFile)
				{
					ret = prepareFileList(src + File.separator + relativeSrc);
					
					if(!ret)
						break;
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
		
		return ret;
	}
	
	@Override
	protected Integer doInBackground(String... params) 
	{
		// TODO Auto-generated method stub
		if(params.length < 2)
			return null;
		String srcFile = params[0];
		String destFile = params[1];
		
		if(prepareFileList(srcFile))
		{
			if(m_textView != null && m_srcFileList.size() > 0)
				m_textView.setText("0/" + m_srcFileList.size());
			
			if(m_progressBar != null && m_srcFileList.size() > 0)
				m_progressBar.setMax(m_srcFileList.size());
			
			for(int fileIndex = 0; fileIndex < m_srcFileList.size(); fileIndex++)
			{
				String src = m_srcFileList.get(fileIndex);
				if(copy(src,destFile+File.separator+src))
				{
					if(m_textView != null && m_srcFileList.size() > 0)
						m_textView.setText(fileIndex+"/" + m_srcFileList.size());
					
					publishProgress((int) ((fileIndex / (float) m_srcFileList.size()) * 100));
				}
				else
					return fileIndex;
			}
		}
		
		return m_srcFileList.size();
	}
	
	protected void onProgressUpdate(Integer... progress) 
	{
		if(m_progressBar != null)
			m_progressBar.setProgress(progress[0]);
    }
}
