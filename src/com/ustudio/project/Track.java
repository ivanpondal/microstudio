package com.ustudio.project;

import com.ustudio.audio.Instrument;

public class Track {
	private String mName;
	private Instrument mInstr;
	private boolean mMute;
	private boolean mSolo;
	
	public Track(String n, Instrument i)
	{
		this.mName=n;
		this.mInstr=i;
		this.mMute=false;
		this.mSolo=false;
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
}
