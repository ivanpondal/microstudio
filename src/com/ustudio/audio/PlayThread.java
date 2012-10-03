package com.ustudio.audio;

import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import android.util.Log;

import com.ustudio.midi.MIDIMessage;
import com.ustudio.project.Project;

class PlayThread extends Thread {
	private boolean mStop_thread;
	private Project mProject;
	
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
		Vector<Long> vecMIDI = new Vector(); 
		Hashtable<Long,MIDIMessage> tmpHashMIDI;
		
		tmpHashMIDI=mProject.getTracks().get("Piano").getMIDInotes();
		
		Enumeration<Long> enume = tmpHashMIDI.keys(); 
		
		while (enume.hasMoreElements()) 
		{ 
			vecMIDI.add(enume.nextElement()); 
		} 
		
		Collections.sort(vecMIDI); 
		         
		for(int i=0;i<vecMIDI.size();i++) 
		{ 
			Long key = vecMIDI.get(i); 
			String value = tmpHashMIDI.get(key).toString(); 
			Log.d("Piano","Key:"+key);
			Log.d("Piano",value);
		} 
	}
}