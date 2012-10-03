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
	private Project mProject;
	private long mPlayTime;
	
	
	public PlayThread(Project p)
	{
		this.mProject=p;
		this.mStop_thread=false;
	}
	
	public void stopthread()
	{
		this.mStop_thread=true;
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
		
		startPlayTimer();
		for(int i=0;i<vecMIDI.size();i++) 
		{ 
			Long key = vecMIDI.get(i); 
			MIDIMessage tmpMIDI = tmpHashMIDI.get(key); 
			while(timePlayTimer()<key)
			{
				//espero
			}
			tmpTrack.getInstr().processMIDI(tmpMIDI);
		} 
	}
	
	private long timePlayTimer()
	{
		return System.currentTimeMillis()-mPlayTime;
	}
	
	private void startPlayTimer()
	{
		mPlayTime=System.currentTimeMillis();
	}
}