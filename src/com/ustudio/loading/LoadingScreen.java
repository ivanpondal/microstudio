package com.ustudio.loading;

public class LoadingScreen {
	private String mText;
	private int mTotal;
	private int mLoaded;
	
	public LoadingScreen(String s, int t, int l)
	{
		this.mText=s;
		this.mTotal=t;
		this.mLoaded=l;
	}
	
	//SET
	public void setText(String s)
	{
		this.mText=s;
	}
	
	public void setTotal(int t)
	{
		this.mTotal=t;
	}
	
	public void setLoaded(int l)
	{
		this.mLoaded=l;
	}
	
	//GET
	public String getText()
	{
		return this.mText;
	}
	
	public int getTotal()
	{
		return this.mTotal;
	}
	
	public int getLoaded()
	{
		return this.mLoaded;
	}
}
