package com.ustudio.scenes;


import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.background.ColorBackground;


import com.ustudio.piano.Piano;
import com.ustudio.audio.*;

public class FreePlay extends Scene {

	static int CAMERA_WIDTH;
	static int CAMERA_HEIGHT;

	private Piano mTouchPiano;
	private Instrument mInsPiano;
	
	public FreePlay(int w, int h) {
		
		CAMERA_WIDTH = w;
		CAMERA_HEIGHT = h;
	
		//this.mMainActivity = pMainActivity;
        this.setBackground(new ColorBackground(0, 0.8784f, 0));
        this.mInsPiano=new Instrument("Piano",(byte)3,400,(byte)60,(byte)73);
        
		this.mTouchPiano = new Piano(this, CAMERA_WIDTH, CAMERA_HEIGHT, this.mInsPiano);
		this.attachChild(this.mTouchPiano);
		this.mTouchPiano.setPosition((CAMERA_WIDTH-CAMERA_WIDTH/8)*-3, 0.0f);//hago que empieze desde el middle C
	}
}
