package com.ustudio.audio;

import org.anddev.andengine.audio.sound.Sound;

class PlayThread extends Thread {
	private boolean stop_thread;
	
	public PlayThread(long d, Sound s)
	{
		this.stop_thread=false;
	}
	
	public void stopthread()
	{
		this.stop_thread=true;
	}
	
	public void run() {
		
		while(this.stop_thread==false)
		{	
			
		}
	}
}