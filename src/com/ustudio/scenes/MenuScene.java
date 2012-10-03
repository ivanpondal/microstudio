package com.ustudio.scenes;



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

import android.util.Log;

import com.ustudio.main.MainActivity;
import com.ustudio.track.Track;

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
		
        this.setBackground(new ColorBackground(0, 0, 0));

		
		this.mTexture = new BitmapTextureAtlas(512, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mFaceTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture, MainActivity.getInstance().getApplicationContext(), "gfx/Botones/R-U S-D.png", 0, 0);
		MainActivity.getInstance().getEngine().getTextureManager().loadTexture(this.mTexture);
		
		this.mTexture2 = new BitmapTextureAtlas(2048, 2048, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mFaceTextureRegion2 = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture2, MainActivity.getInstance().getApplicationContext(), "gfx/menu/fondoMenu.png", 0, 0);
		MainActivity.getInstance().getEngine().getTextureManager().loadTexture(this.mTexture2);
		
		this.mTexture3 = new BitmapTextureAtlas(512, 512, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mFaceTextureRegion3 = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture3, MainActivity.getInstance().getApplicationContext(), "gfx/menu/newButtonMenu.png", 0, 0);
		MainActivity.getInstance().getEngine().getTextureManager().loadTexture(this.mTexture3);
		        
        this.mScrollDetector = new SurfaceScrollDetector(this);
        this.mScrollDetector.setEnabled(true);

        final int centerX = (CAMERA_WIDTH/100);
        final int centerY = (10);
        
        Log.d("", "w: " + CAMERA_WIDTH +  "h: " + CAMERA_HEIGHT);
        
       Sprite backGround = new Sprite(0,0 , mFaceTextureRegion2);
       //backGround.setRotation(-90);
       backGround.setWidth(CAMERA_WIDTH);
       backGround.setHeight(CAMERA_HEIGHT);
       this.attachChild(backGround);
        
        ball = new Sprite((CAMERA_WIDTH/13)*12, CAMERA_WIDTH/13, this.mFaceTextureRegion3){
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
        ball.setWidth(CAMERA_WIDTH/13); //h
        ball.setHeight(CAMERA_HEIGHT/3); //w
        //ball.setRotation(-90);
        this.attachChild(ball);
        this.registerTouchArea(ball);
       
        this.setOnSceneTouchListener(this);
        this.setTouchAreaBindingEnabled(true);
	}
	


	public void menuItemPressed(int id){
		switch (id){
			case 1:
				MainActivity.getInstance().getSceneManager().setRecordScene(CAMERA_WIDTH, CAMERA_HEIGHT);
				//Track newTrack = new Track();
				//this.attachChild(newTrack);
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
		
		
		//MainActivity.getInstance().getCamera().offsetCenter(-pDistanceX , -pDistanceY);
		
	 }
	 
	 @Override
	 public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		 this.mScrollDetector.onTouchEvent(pSceneTouchEvent);
		 return true;
	 }
}
