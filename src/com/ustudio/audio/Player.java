package com.ustudio.audio;

import com.ustudio.project.Project;;

public class Player {
	private static PlayThread mPlayer;
	
	public static boolean isPlaying()
	{
		if(mPlayer!=null)
		{
			if(mPlayer.isAlive())
			{
				return true;
			}
		}
		return false;
	}
	
	public static boolean isPaused()
	{
		if(isPlaying())
		{
			if(mPlayer.isPaused())
			{
				return true;
			}
		}
		return false;
	}
	
	//PUBLIC
	public static void PlayProject(Project p)
	{
		if(!isPlaying())
		{
			mPlayer=new PlayThread(p);
			mPlayer.start();
		}
	}	
	
	public static void Pause()
	{
		if(isPlaying())
		{
			if(!isPaused())
			{
				mPlayer.pausethread();
				while(!mPlayer.isPaused())
				{
					//wait;
				}
			}
		}
	}
	
	public static void UnPause()
	{
		if(isPaused())
		{
			mPlayer.unpausethread();
			while(mPlayer.isPaused())
			{
				//wait;
			}
		}
	}
	
	public static void Stop()
	{
		if(isPlaying())
		{
			mPlayer.stopthread();
			while(mPlayer.isAlive())
			{
				//wait;
			}
		}
	}
	
	//SET

	
	//GET

}
