package com.ustudio.project;

import java.util.Hashtable;

import com.ustudio.audio.Instrument;
import com.ustudio.midi.MIDIMessage;

public class Track {
	private String mName;
	private Instrument mInstr;
	private boolean mMute;
	private boolean mSolo;
	private boolean mIsRec;
	private Hashtable<Long,MIDIMessage> mMIDInotes;
	
	public Track(String n, Instrument i)
	{
		this.mName=n;
		this.mInstr=i;
		this.mMute=false;
		this.mSolo=false;
		this.mIsRec=false;
	}
	
	//SET
	public void setName(String n)
	{
		this.mName=n;
	}
	
	public void setInstr(Instrument i)
	{
		this.mInstr=i;
	}
	
	public void setMute(boolean m)
	{
		this.mMute=m;
	}
	
	public void setSolo(boolean s)
	{
		this.mSolo=s;
	}
	
	public void setIsRec(boolean r)
	{
		this.mIsRec=r;
	}
	
	public void setMIDInotes(Hashtable<Long,MIDIMessage> m)
	{
		this.mMIDInotes=m;
	}
	
	//GET
	public String getName()
	{
		return this.mName;
	}
	
	public Instrument getInstr(Instrument i)
	{
		return this.mInstr;
	}
	
	public boolean getMute()
	{
		return this.mMute;
	}
	
	public boolean getSolo()
	{
		return this.mSolo;
	}
	
	public boolean getIsRec()
	{
		return this.mIsRec;
	}
	
	public Hashtable<Long,MIDIMessage> getMIDInotes()
	{
		return this.mMIDInotes;
	}
}
