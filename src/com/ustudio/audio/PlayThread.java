package com.ustudio.audio;

import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import android.util.Log;

import com.ustudio.midi.MIDIMessage;
import com.ustudio.project.Project;
import com.ustudio.project.Track;

class PlayThread extends Thread {
	private boolean mStop_thread;
	private boolean mPaused;
	private Project mProject;
	private long mPlayTime;
	private long mTimePlaying;
	
	
	public PlayThread(Project p)
	{
		this.mProject=p;
		this.mStop_thread=false;
		this.mPaused=false;
	}
	
	public void stopthread()
	{
		this.mStop_thread=true;
	}
	
	public void pausethread()
	{
		this.mPaused=true;
	}
	
	public void unpausethread()
	{
		this.mPaused=false;
	}
	
	public boolean isPaused()
	{
		return this.mPaused;
	}
	
	public void run() {
		Track tmpTrack;
		Vector<Long> vecMIDI = new Vector<Long>(); 
		Hashtable<Long,MIDIMessage> tmpHashMIDI;
		
		tmpTrack=mProject.getTracks().get("Piano");
		tmpHashMIDI=tmpTrack.getMIDInotes();
		
		Enumeration<Long> enume = tmpHashMIDI.keys(); 
		
		while (enume.hasMoreElements()) 
		{ 
			vecMIDI.add(enume.nextElement()); 
		} 
		
		Collections.sort(vecMIDI); 
		
		startPlayTimer(0);
		int i=0;
		while(i<vecMIDI.size() && !this.mStop_thread)
		{
			Long key = vecMIDI.get(i); 
			MIDIMessage tmpMIDI = tmpHashMIDI.get(key); 
			while(timePlayTimer()<key)
			{
				//espero
			}
			tmpTrack.getInstr().processMIDI(tmpMIDI);
			i++;
			if(this.mPaused)
			{
				this.mTimePlaying=timePlayTimer();
				while(this.mPaused)
				{
					//espero
				}
				startPlayTimer(this.mTimePlaying);
			}
		}
	}
	
	private long timePlayTimer()
	{
		return System.currentTimeMillis()-mPlayTime;
	}
	
	private void startPlayTimer(long offset)
	{
		mPlayTime=System.currentTimeMillis()-offset;
	}
}