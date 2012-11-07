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
	
	public LoadingScreen(Scene pScene, int w, int h)
	{
		CAMERA_WIDTH = w;
		CAMERA_HEIGHT = h;
		scene = pScene;

		this.mLoader=new Loader(CAMERA_WIDTH, CAMERA_HEIGHT);
		this.mPosX=(CAMERA_WIDTH/2)-this.mLoader.getWidth()/2;
		this.mPosY=(CAMERA_HEIGHT/2)-this.mLoader.getHeight()/2;
		this.mLoader.setPosition(this.mPosX, this.mPosY);
		
		scene.attachChild(this.mLoader);
	}
	
	//PUBLIC
	
	public void updateProgress()
	{
		if(this.mLoaded<this.mTotal)
			this.refreshText();
		this.mLoader.updatePercent(((this.mLoaded*100)/this.mTotal)+"%");
		this.mLoader.getProgress().setWidth((this.mLoaded*this.mLoader.getPBWidth())/this.mTotal);
	}
	
	public void refreshText()
	{
		String result;
		result=this.mText;
		result=result.replaceAll("#loaded", String.valueOf(this.mLoaded+1));
		result=result.replaceAll("#total", String.valueOf(this.mTotal));
		this.mLoader.updateText(result);
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
