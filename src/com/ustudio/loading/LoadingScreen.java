package com.ustudio.loading;

import org.anddev.andengine.entity.scene.Scene;

public class LoadingScreen {
	static int CAMERA_WIDTH;
	static int CAMERA_HEIGHT;
	static Scene scene;
	private String mText;
	private int mTotal;
	private int mLoaded;
	private Loader mLoader;
	private float mPosX;
	private float mPosY;
	
	public LoadingScreen(Scene pScene, int w, int h, String s, int t)
	{
		CAMERA_WIDTH = w;
		CAMERA_HEIGHT = h;
		scene = pScene;
		this.mText = s;
		this.mTotal = t;
		this.mLoaded = 0;

		this.mLoader=new Loader(CAMERA_WIDTH, CAMERA_HEIGHT);
		this.mPosX=(CAMERA_WIDTH/2)-this.mLoader.getWidth()/2;
		this.mPosY=(CAMERA_HEIGHT/2)-this.mLoader.getHeight()/2;
		this.mLoader.setPosition(this.mPosX, this.mPosY);
		scene.attachChild(this.mLoader);
	}
	
	//SET
	public void setText(String s)
	{
		this.mText = s;
	}
	
	public void setTotal(int t)
	{
		this.mTotal = t;
	}
	
	public void setLoaded(int l)
	{
		this.mLoaded = l;
	}
	
	public void setLoader(Loader l)
	{
		this.mLoader = l;
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
	
	public Loader getLoader()
	{
		return this.mLoader;
	}
}
