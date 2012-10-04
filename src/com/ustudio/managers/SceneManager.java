package com.ustudio.managers;


import org.anddev.andengine.entity.scene.Scene;

import com.ustudio.main.MainActivity;
import com.ustudio.scenes.FreePlay;
import com.ustudio.scenes.MenuScene2;
import com.ustudio.scenes.PutoScene;
import com.ustudio.scenes.RecordScene;

public class SceneManager {

	// Scene
	public Scene scene;
	
	public void setMenuScene(int w, int h) {
		this.scene = new MenuScene2(w, h);
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
