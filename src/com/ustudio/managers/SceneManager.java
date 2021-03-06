package com.ustudio.managers;


import org.anddev.andengine.entity.scene.Scene;

import android.content.pm.ActivityInfo;
import android.util.Log;

import com.ustudio.main.MainActivity;
import com.ustudio.scenes.FreePlay;
import com.ustudio.scenes.MenuScene;
import com.ustudio.scenes.EditorScene;
import com.ustudio.scenes.RecordScene;

public class SceneManager {

	// Scene
	public Scene scene;
	
	public void setMenuScene(int w, int h) {
		this.scene = new MenuScene(w, h);
		MainActivity.getInstance().getEngine().setScene(this.scene);
	}
	
	public void setEditorScene(int w, int h) {
		this.scene = new EditorScene(w, h);
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
