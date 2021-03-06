package com.ass.ui.AsyncTask;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

import android.os.AsyncTask;
import android.widget.ProgressBar;
import android.widget.TextView;

public class FileCopyTask extends AsyncTask<File, Integer, Integer> {
	
	private ProgressBar 		m_progressBar = null;
	private TextView			m_textView = null;
	private ArrayList<File>		m_srcFileList = new ArrayList<File>();
	
	public FileCopyTask(ProgressBar progressBar,TextView testView) {
		m_progressBar = progressBar;
		m_textView = testView;		
	}
	
	@SuppressWarnings("resource")
	public boolean copy(final File srcFile,final File destFile)
	{
		try
		{
			if(destFile.exists() && !destFile.equals(srcFile))
			{
				if(!destFile.delete())
					return false;
			}
			destFile.mkdirs();
			
			if(!srcFile.exists() || srcFile.isFile())
				return false;
			
			FileChannel inChannel = new FileInputStream(srcFile).getChannel();
		    FileChannel outChannel = new FileOutputStream(destFile).getChannel();
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
	
	private boolean prepareFileList(File srcFile)
	{
		m_srcFileList.clear();
		boolean ret = false;
		
		try
		{			
			if(srcFile.isDirectory())
			{
				for(File file:srcFile.listFiles())
				{
					if(file.isFile())
						ret = m_srcFileList.add(file);
					
					if(file.isDirectory())
						ret = prepareFileList(srcFile); 
					
					if(!ret)
						break;
				}
			}
			
			if(srcFile.isFile())
				ret = m_srcFileList.add(srcFile);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
		
		return ret;
	}
	
	@Override
	protected Integer doInBackground(File... params) 
	{
		// TODO Auto-generated method stub
		if(params.length < 2)
			return null;
		
		File srcFile = params[0];
		File destFile = params[1];
		
		if(prepareFileList(srcFile))
		{
			if(m_textView != null && m_srcFileList.size() > 0)
				m_textView.setText("0/" + m_srcFileList.size());
			
			if(m_progressBar != null && m_srcFileList.size() > 0)
				m_progressBar.setMax(m_srcFileList.size());
			
			for(int fileIndex = 0; fileIndex < m_srcFileList.size(); fileIndex++)
			{
				File src = m_srcFileList.get(fileIndex);
				File dest = new File(src.getPath().replace(srcFile.getPath(), destFile.getPath()));
				
				if(copy(src,dest))
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
