package com.ustudio.project;

import java.util.Hashtable;

public class Project {
	private String mName;
	private Hashtable<String,Track> mTracks;
	private String mActiveTrack;
	
	public Project()
	{
	}
	
	public Project(String n)
	{
		this.mName=n;
		this.mTracks=new Hashtable<String,Track>();
	}
	
	public Project(String n, Track t)
	{
		this.mName=n;
		this.mTracks=new Hashtable<String,Track>();
		this.mTracks.put(t.getName(), t);
	}
	
	public Track findActiveTrack()
	{
		Track tmpTrack;
		tmpTrack=this.mTracks.get(this.mActiveTrack);
		return tmpTrack;
	}
	
	//SET
	public void setName(String n)
	{
		this.mName=n;
	}
	
	public void setTracks(Hashtable<String,Track> t)
	{
		this.mTracks=t;
	}
	
	public void setActiveTrack(String a)
	{
		this.mActiveTrack=a;
	}
	
	//GET
	public String getName()
	{
		return this.mName;
	}
	
	public Hashtable<String,Track> getTracks()
	{
		return this.mTracks;
	}
	
	public String getActiveTrack()
	{
		return this.mActiveTrack;
	}
}
