package com.ppp.scenes;



import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.Scene.IOnSceneTouchListener;
import org.anddev.andengine.entity.scene.background.ColorBackground;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.input.touch.detector.ScrollDetector;
import org.anddev.andengine.input.touch.detector.ScrollDetector.IScrollDetectorListener;
import org.anddev.andengine.input.touch.detector.SurfaceScrollDetector;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TextureRegion;

import com.ppp.main.MainActivity;

public class MenuScene extends Scene implements IScrollDetectorListener, IOnSceneTouchListener  {

	static int CAMERA_WIDTH;
	static int CAMERA_HEIGHT;
	
	private Texture mTexture;
	private Texture mTexture2;
	private Texture mTexture3;
	private TextureRegion mFaceTextureRegion;
	private TextureRegion mFaceTextureRegion2;
	private TextureRegion mFaceTextureRegion3;
	private SurfaceScrollDetector mScrollDetector;
	private Sprite ball2;
	private Sprite ball;
	private Sprite ball3;
	
	public MenuScene(int w, int h) {
		
		CAMERA_WIDTH = w;
		CAMERA_HEIGHT = h;
		
		this.mTexture = new BitmapTextureAtlas(512, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mFaceTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture, MainActivity.getInstance().getApplicationContext(), "gfx/Botones/R-U S-D.png", 0, 0);
		MainActivity.getInstance().getEngine().getTextureManager().loadTexture(this.mTexture);
		
		this.mTexture2 = new BitmapTextureAtlas(512, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mFaceTextureRegion2 = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture2, MainActivity.getInstance().getApplicationContext(), "gfx/Botones/S-U S-D.png", 0, 0);
		MainActivity.getInstance().getEngine().getTextureManager().loadTexture(this.mTexture2);
		
		this.mTexture3 = new BitmapTextureAtlas(512, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mFaceTextureRegion3 = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture3, MainActivity.getInstance().getApplicationContext(), "gfx/Botones/S-U R-D.png", 0, 0);
		MainActivity.getInstance().getEngine().getTextureManager().loadTexture(this.mTexture3);
		
        this.setBackground(new ColorBackground(0.5f, 0.5f, 0.5f));
        
        this.mScrollDetector = new SurfaceScrollDetector(this);
        this.mScrollDetector.setEnabled(true);

        final int centerX = (CAMERA_WIDTH/100);
        final int centerY = (10);
       
        ball = new Sprite(centerX, centerY, this.mFaceTextureRegion){
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
        ball.setWidth(CAMERA_WIDTH-CAMERA_WIDTH/50);
        ball.setHeight(CAMERA_WIDTH/3);
        this.attachChild(ball);
        this.registerTouchArea(ball);
        
        ball2 = new Sprite(centerX, centerY+(CAMERA_WIDTH/3), this.mFaceTextureRegion2){
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
                        menuItemPressed(2);
                        break;
                        }
                
                        return true;
            }
        };
        ball2.setWidth(CAMERA_WIDTH-CAMERA_WIDTH/50);
        ball2.setHeight(CAMERA_WIDTH/3);
        this.attachChild(ball2);
        this.registerTouchArea(ball2);
        
        ball3 = new Sprite(centerX, centerY+(CAMERA_WIDTH/3)*2, this.mFaceTextureRegion3){
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
                        menuItemPressed(3);
                        break;
                        }
                
                        return true;
            }
        };
        ball3.setWidth(CAMERA_WIDTH-CAMERA_WIDTH/50);
        ball3.setHeight(CAMERA_WIDTH/3);
        this.attachChild(ball3);
        this.registerTouchArea(ball3);
       
        this.setOnSceneTouchListener(this);
        this.setTouchAreaBindingEnabled(true);
	}
	


	public void menuItemPressed(int id){
		switch (id){
			case 1:
				MainActivity.getInstance().getSceneManager().setPutoScene(CAMERA_WIDTH, CAMERA_HEIGHT);
				break;
			case 2:
				MainActivity.getInstance().getSceneManager().setFreePlay(CAMERA_WIDTH, CAMERA_HEIGHT);
				break;
			case 3:
				MainActivity.getInstance().getSceneManager().setRecordScene(CAMERA_WIDTH, CAMERA_HEIGHT);
				break;
		}
	}
	
	@Override
	 public void onScroll(ScrollDetector pScollDetector, TouchEvent pTouchEvent, float pDistanceX, float pDistanceY) {
		
		
		MainActivity.getInstance().getCamera().offsetCenter(0, -pDistanceY);
		
	 }
	 
	 @Override
	 public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		 this.mScrollDetector.onTouchEvent(pSceneTouchEvent);
		 return true;
	 }
}
