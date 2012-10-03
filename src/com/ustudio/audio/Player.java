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
	
	//PUBLIC
	public static void PlayProject(Project p)
	{
		if(!isPlaying())
		{
			mPlayer=new PlayThread(p);
			mPlayer.start();
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
