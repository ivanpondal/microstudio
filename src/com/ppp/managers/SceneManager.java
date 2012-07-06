package com.ppp.managers;


import org.anddev.andengine.entity.scene.Scene;

import com.ppp.main.MainActivity;
import com.ppp.scenes.FreePlay;
import com.ppp.scenes.MenuScene;
import com.ppp.scenes.PutoScene;
import com.ppp.scenes.RecordScene;

public class SceneManager {

	// Scene
	public Scene scene;
	
	public void setMenuScene(int w, int h) {
		this.scene = new MenuScene(w, h);
		MainActivity.getInstance().getEngine().setScene(this.scene);
	}
	
	public void setPutoScene(int w, int h) {
		this.scene = new PutoScene(w, h);
		MainActivity.getInstance().getEngine().setScene(this.scene);
	}
	
	public void setFreePlay(int w, int h) {
		this.scene = new FreePlay(w, h);
		MainActivity.getInstance().getEngine().setScene(this.scene);
	}
	
	public void setRecordScene(int w, int h) {
		this.scene = new RecordScene(w, h);
		MainActivity.getInstance().getEngine().setScene(this.scene);
	}
	
	public Scene getCurrentScene() {
		return this.scene;
	}
}
