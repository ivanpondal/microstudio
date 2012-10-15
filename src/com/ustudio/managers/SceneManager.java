package com.ustudio.managers;


import org.anddev.andengine.entity.scene.Scene;

import android.content.pm.ActivityInfo;

import com.ustudio.main.MainActivity;
import com.ustudio.scenes.FreePlay;
import com.ustudio.scenes.MenuScene;
import com.ustudio.scenes.PutoScene;
import com.ustudio.scenes.RecordScene;

public class SceneManager {

	// Scene
	public Scene scene;
	
	public void setMenuScene(int w, int h) {
		if(MainActivity.getInstance().getRequestedOrientation()!=ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
		{
			MainActivity.getInstance().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		}
		this.scene = new MenuScene(w, h);
		MainActivity.getInstance().getEngine().setScene(this.scene);
	}
	
	public void setPutoScene(int w, int h) {
		this.scene = new PutoScene(w, h);
		MainActivity.getInstance().getEngine().setScene(this.scene);
	}
	
	public void setFreePlay(int w, int h) {
		if(MainActivity.getInstance().getRequestedOrientation()!=ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
		{
			MainActivity.getInstance().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		}
		this.scene = new FreePlay(w, h);
		MainActivity.getInstance().getEngine().setScene(this.scene);
	}
	
	public void setRecordScene(int w, int h) {
		if(MainActivity.getInstance().getRequestedOrientation()!=ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
		{
			MainActivity.getInstance().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		}
		this.scene = new RecordScene(w, h);
		MainActivity.getInstance().getEngine().setScene(this.scene);
	}
	
	public Scene getCurrentScene() {
		return this.scene;
	}
}
