package com.ustudio.scenes;


import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.background.ColorBackground;

import com.ustudio.editor.Editor;

public class PutoScene extends Scene {

	static int CAMERA_WIDTH ;
	static int CAMERA_HEIGHT ;


	private Editor mEditor;
	
	
	
	public PutoScene(int w, int h) {
		
		
		this.mEditor = new Editor(0, 0, this);
		this.attachChild(this.mEditor);

		CAMERA_WIDTH = w;
		CAMERA_HEIGHT = h;
		//this.mMainActivity = pMainActivity;
        this.setBackground(new ColorBackground(0.8784f, 0, 0));
        
	}
	
	
	
}
