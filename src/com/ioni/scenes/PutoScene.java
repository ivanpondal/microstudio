package com.ioni.scenes;


import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.background.ColorBackground;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TextureRegion;

import com.main.MainActivity;

public class PutoScene extends Scene {

	static int CAMERA_WIDTH ;
	static int CAMERA_HEIGHT ;

	private Texture mTexture;
	private TextureRegion mFaceTextureRegion;
	private Sprite back;
	
	private MainActivity mMainActivity;
	
	public PutoScene(MainActivity pMainActivity, int w, int h) {

		CAMERA_WIDTH = w;
		CAMERA_HEIGHT = h;
		this.mMainActivity = pMainActivity;
        this.setBackground(new ColorBackground(0.8784f, 0, 0));
        
        this.mTexture = new BitmapTextureAtlas(512, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mFaceTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture, this.mMainActivity.getApplicationContext(), "gfx/Botones/R-U R-D.png", 0, 0);
		this.mMainActivity.getEngine().getTextureManager().loadTexture(this.mTexture);
		
		final int centerX = (10);
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
        back.setWidth(780);
        back.setHeight(75);
        this.attachChild(back);
        this.registerTouchArea(back);
        
	}
	public void menuItemPressed(int id){
		
		this.mMainActivity.getSceneManager().setMenuScene(CAMERA_WIDTH, CAMERA_HEIGHT);
	}
}
