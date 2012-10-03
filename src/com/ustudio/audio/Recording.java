package com.ustudio.audio;

import com.ustudio.project.Track;
import com.ustudio.midi.MIDIMessage;

public class Recording {
	private static boolean mIsRecording;
	private static long mRecTimer;
	
	//PUBLIC
	public static void startRecTimer()
	{
		mRecTimer=System.currentTimeMillis();
	}

	public static long timeRecTimer()
	{
		return System.currentTimeMillis()-mRecTimer;
	}
	
	public static void stopRecTimer()
	{
		mRecTimer=0;
		mIsRecording=false;
	}
	
	public static void saveMIDI(Track activetrack, MIDIMessage midi)
	{
		long tmptime;
		tmptime=timeRecTimer();
		midi.setTime(tmptime);
		activetrack.getMIDInotes().put(tmptime, midi);
	}
	
	//SET
	public static void setIsRecording(boolean r)
	{
		mIsRecording=r;
	}
	
	public static void setRecTimer(long t)
	{
		mRecTimer=t;
	}
	
	//GET
	public static boolean getIsRecording()
	{
		return mIsRecording;
	}
	
	public static long getRecTimer()
	{
		return mRecTimer;
	}
}
