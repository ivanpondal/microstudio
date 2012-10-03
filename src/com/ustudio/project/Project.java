package com.ustudio.project;

import java.util.Hashtable;

public class Project {
	private String mName;
	private Hashtable<String,Track> mTracks;
	
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
	
	public void setName(String n)
	{
		this.mName=n;
	}
	
	public void setTracks(Hashtable<String,Track> t)
	{
		this.mTracks=t;
	}
	
	public String getName()
	{
		return this.mName;
	}
	
	public Hashtable<String,Track> getTracks()
	{
		return this.mTracks;
	}
	
}
