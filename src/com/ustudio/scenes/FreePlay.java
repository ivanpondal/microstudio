package com.ustudio.scenes;


import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.background.ColorBackground;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TextureRegion;

import android.util.Log;

import com.ustudio.main.MainActivity;
import com.ustudio.piano.Piano;

public class FreePlay extends Scene {

	static int CAMERA_WIDTH;
	static int CAMERA_HEIGHT;


	private Texture mTexture;
	private TextureRegion mFaceTextureRegion;
	private Sprite back;
	private Piano mPiano;
	
	
	
	public FreePlay(int w, int h) {
		

		CAMERA_WIDTH = w;
		CAMERA_HEIGHT = h;
	
		//this.mMainActivity = pMainActivity;
        this.setBackground(new ColorBackground(0, 0.8784f, 0));
        
        this.mTexture = new BitmapTextureAtlas(512, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mFaceTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture, MainActivity.getInstance().getApplicationContext(), "gfx/Botones/R-U R-D.png", 0, 0);
		MainActivity.getInstance().getEngine().getTextureManager().loadTexture(this.mTexture);
		
		final int centerX = (600);
        final int centerY = (10);
       
        back = new Sprite(centerX, centerY, this.mFaceTextureRegion){
        	@Override
            public boolean onAreaTouched(final TouchEvent pAreaTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
                switch(pAreaTouchEvent.getAction()) {
                    case TouchEvent.ACTION_DOWN:                    
                        this.setScale(1.25f);
                        this.setAlpha(0.5f);
                        break;
                    case TouchEvent.ACTION_UP:  
                        this.setScale(1.0f);
                        this.setAlpha(1.0f);
                        menuItemPressed(1);
                        break;
                        }
                
                        return true;
            }
        };
        back.setWidth(100);
        back.setHeight(75);
        this.attachChild(back);
        this.registerTouchArea(back);
        
        this.setTouchAreaBindingEnabled(false);
        
		this.mPiano = new Piano(this, CAMERA_WIDTH, CAMERA_HEIGHT);
		this.attachChild(this.mPiano);
		this.mPiano.setPosition((CAMERA_WIDTH-CAMERA_WIDTH/8)*-3, 0.0f);
		Log.d("Piano", "X:"+this.mPiano.getX());
        
	}

	public void menuItemPressed(int id){
		
		MainActivity.getInstance().getSceneManager().setMenuScene(CAMERA_WIDTH, CAMERA_HEIGHT);
	}
}
