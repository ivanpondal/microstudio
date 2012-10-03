package com.ustudio.scenes;


import org.anddev.andengine.entity.Entity;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.background.ColorBackground;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TextureRegion;

import com.ustudio.main.MainActivity;
import com.ustudio.track.Track;

public class RecordScene extends Scene {

	static int CAMERA_WIDTH;
	static int CAMERA_HEIGHT;

	private Texture mTexture;
	private TextureRegion mFaceTextureRegion;
	private Texture mTexture2;
	private TextureRegion mFaceTextureRegion2;
	private Texture mTexture3;
	private TextureRegion mFaceTextureRegion3;
	private Texture mTexture4;
	private TextureRegion mFaceTextureRegion4;
	private Texture mTexture5;
	private TextureRegion mFaceTextureRegion5;
	private Texture mTexture6;
	private TextureRegion mFaceTextureRegion6;
	private Texture mTexture7;
	private TextureRegion mFaceTextureRegion7;
	private Texture mTexture8;
	private TextureRegion mFaceTextureRegion8;
	private Texture mTexture9;
	private TextureRegion mFaceTextureRegion9;
	private Sprite menuP;
	private Sprite playP;
	private Sprite pauseP;
	private Sprite stopP;
	
	public RecordScene(int w ,int h) {

		CAMERA_WIDTH = w;
		CAMERA_HEIGHT = h;
		//this.mMainActivity = pMainActivity;
        
        this.mTexture = new BitmapTextureAtlas(2048, 2048, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mFaceTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture, MainActivity.getInstance().getApplicationContext(), "gfx/tracks/backgroundTracks.png", 0, 0);
		MainActivity.getInstance().getEngine().getTextureManager().loadTexture(this.mTexture);
		
		this.mTexture2 = new BitmapTextureAtlas(1024, 1024, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mFaceTextureRegion2 = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture2, MainActivity.getInstance().getApplicationContext(), "gfx/tracks/menuTracks.png", 0, 0);
		MainActivity.getInstance().getEngine().getTextureManager().loadTexture(this.mTexture2);
		
		this.mTexture3 = new BitmapTextureAtlas(1024, 1024, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mFaceTextureRegion3 = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture3, MainActivity.getInstance().getApplicationContext(), "gfx/tracks/playTracks.png", 0, 0);
		MainActivity.getInstance().getEngine().getTextureManager().loadTexture(this.mTexture3);
		
		this.mTexture4 = new BitmapTextureAtlas(1024, 1024, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mFaceTextureRegion4 = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture4, MainActivity.getInstance().getApplicationContext(), "gfx/tracks/pauseTracks.png", 0, 0);
		MainActivity.getInstance().getEngine().getTextureManager().loadTexture(this.mTexture4);
		
		this.mTexture5 = new BitmapTextureAtlas(1024, 1024, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mFaceTextureRegion5 = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture5, MainActivity.getInstance().getApplicationContext(), "gfx/tracks/stopTracks.png", 0, 0);
		MainActivity.getInstance().getEngine().getTextureManager().loadTexture(this.mTexture5);
		
		this.mTexture6 = new BitmapTextureAtlas(1024, 1024, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mFaceTextureRegion6 = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture6, MainActivity.getInstance().getApplicationContext(), "gfx/Track/menuPressed.png", 0, 0);
		MainActivity.getInstance().getEngine().getTextureManager().loadTexture(this.mTexture6);
		
		this.mTexture7 = new BitmapTextureAtlas(1024, 1024, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mFaceTextureRegion7 = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture7, MainActivity.getInstance().getApplicationContext(), "gfx/Track/playPressed.png", 0, 0);
		MainActivity.getInstance().getEngine().getTextureManager().loadTexture(this.mTexture7);
		
		this.mTexture8 = new BitmapTextureAtlas(1024, 1024, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mFaceTextureRegion8 = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture8, MainActivity.getInstance().getApplicationContext(), "gfx/Track/pausePressed.png", 0, 0);
		MainActivity.getInstance().getEngine().getTextureManager().loadTexture(this.mTexture8);
		
		this.mTexture9 = new BitmapTextureAtlas(1024, 1024, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mFaceTextureRegion9 = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture9, MainActivity.getInstance().getApplicationContext(), "gfx/Track/stopPressed.png", 0, 0);
		MainActivity.getInstance().getEngine().getTextureManager().loadTexture(this.mTexture9);
	
		Sprite backGround = new Sprite(0,0, mFaceTextureRegion);
	    //backGround.setRotation(-90);
	    backGround.setWidth(CAMERA_WIDTH);
	    backGround.setHeight(CAMERA_HEIGHT);
	    this.attachChild(backGround);
	    
	    Entity menuBar = new Entity();
	    
	    menuP = new Sprite((CAMERA_WIDTH/13)*12, ((CAMERA_HEIGHT/4.6f)/4 + (CAMERA_HEIGHT/4.6f))*3 - (CAMERA_HEIGHT/4.6f)/4 - (CAMERA_HEIGHT/4.6f)/16, mFaceTextureRegion6);
	    //menu.setRotation(-90);
	    menuP.setWidth(CAMERA_WIDTH/13);
	    menuP.setHeight(CAMERA_HEIGHT/4.6f);
	    menuBar.attachChild(menuP);
	    
	    Sprite menu = new Sprite((CAMERA_WIDTH/13)*12, ((CAMERA_HEIGHT/4.6f)/4 + (CAMERA_HEIGHT/4.6f))*3 - (CAMERA_HEIGHT/4.6f)/4 - (CAMERA_HEIGHT/4.6f)/16, mFaceTextureRegion2){
	    	@Override
            public boolean onAreaTouched(final TouchEvent pAreaTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
                switch(pAreaTouchEvent.getAction()) {
                	case TouchEvent.ACTION_DOWN:                    
                		this.setVisible(false);
                		break;
                	case TouchEvent.ACTION_UP:  
                		this.setVisible(true);
                		break;
                }
                
                        return true;
            }
	    };
	    //menu.setRotation(-90);
	    menu.setWidth(CAMERA_WIDTH/13);
	    menu.setHeight(CAMERA_HEIGHT/4.6f);
	    menuBar.attachChild(menu);
	    this.registerTouchArea(menu);
	    
	    playP = new Sprite((CAMERA_WIDTH/13)*12, ((CAMERA_HEIGHT/4.6f)/4 + (CAMERA_HEIGHT/4.6f))*2 - (CAMERA_HEIGHT/4.6f)/8, mFaceTextureRegion7);
	    //play.setRotation(-90);
	    playP.setWidth(CAMERA_WIDTH/13);
	    playP.setHeight(CAMERA_HEIGHT/4.6f);
	    menuBar.attachChild(playP);
	    
	    Sprite play = new Sprite((CAMERA_WIDTH/13)*12, ((CAMERA_HEIGHT/4.6f)/4 + (CAMERA_HEIGHT/4.6f))*2 - (CAMERA_HEIGHT/4.6f)/8, mFaceTextureRegion3){
	    	@Override
            public boolean onAreaTouched(final TouchEvent pAreaTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
                switch(pAreaTouchEvent.getAction()) {
                	case TouchEvent.ACTION_DOWN:                    
                		this.setVisible(false);
                		break;
                	case TouchEvent.ACTION_UP:  
                		this.setVisible(true);
                		break;
                }
                
                        return true;
            }
	    };
	    //play.setRotation(-90);
	    play.setWidth(CAMERA_WIDTH/13-1);
	    play.setHeight(CAMERA_HEIGHT/4.6f);
	    menuBar.attachChild(play);
	    this.registerTouchArea(play);
	    
	    pauseP = new Sprite((CAMERA_WIDTH/13)*12, CAMERA_HEIGHT/4.6f  + (CAMERA_HEIGHT/4.6f)/4 + (CAMERA_HEIGHT/4.6f)/16, mFaceTextureRegion8);
	    //pause.setRotation(-90);
	    pauseP.setWidth(CAMERA_WIDTH/13);
	    pauseP.setHeight(CAMERA_HEIGHT/4.6f);
	    menuBar.attachChild(pauseP);
	    
	    Sprite pause = new Sprite((CAMERA_WIDTH/13)*12, CAMERA_HEIGHT/4.6f  + (CAMERA_HEIGHT/4.6f)/4 + (CAMERA_HEIGHT/4.6f)/16, mFaceTextureRegion4){
	    	@Override
            public boolean onAreaTouched(final TouchEvent pAreaTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
                switch(pAreaTouchEvent.getAction()) {
                	case TouchEvent.ACTION_DOWN:                    
                		this.setVisible(false);
                		break;
                	case TouchEvent.ACTION_UP:  
                		this.setVisible(true);
                		break;
                }
                
                        return true;
            }
	    };
	    //pause.setRotation(-90);
	    pause.setWidth(CAMERA_WIDTH/13);
	    pause.setHeight(CAMERA_HEIGHT/4.6f);
	    menuBar.attachChild(pause);
	    this.registerTouchArea(pause);
	    
	    stopP = new Sprite((CAMERA_WIDTH/13)*12, (CAMERA_HEIGHT/4.6f)/4, mFaceTextureRegion9);
	    //stop.setRotation(-90);
	    stopP.setWidth(CAMERA_WIDTH/13);
	    stopP.setHeight(CAMERA_HEIGHT/4.6f);
	    menuBar.attachChild(stopP);
	    
	    Sprite stop = new Sprite((CAMERA_WIDTH/13)*12, (CAMERA_HEIGHT/4.6f)/4, mFaceTextureRegion5){
	    	@Override
            public boolean onAreaTouched(final TouchEvent pAreaTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
                switch(pAreaTouchEvent.getAction()) {
                    case TouchEvent.ACTION_DOWN:                    
                    	this.setVisible(false);
                        break;
                    case TouchEvent.ACTION_UP:  
                    	this.setVisible(true);
                        break;
                        }
                
                        return true;
            }
	    };
	    //stop.setRotation(-90);
	    stop.setWidth(CAMERA_WIDTH/13);
	    stop.setHeight(CAMERA_HEIGHT/4.6f);
	    menuBar.attachChild(stop);
	    this.registerTouchArea(stop);

	    this.attachChild(menuBar);
	    
	    Track track1 = new Track(CAMERA_WIDTH, CAMERA_HEIGHT);
	    //track1.setRotation(-90);
	    //track1.setPosition(0, 300);
	    this.attachChild(track1);
	}
	public void menuItemPressed(int id){
		
		MainActivity.getInstance().getSceneManager().setMenuScene(CAMERA_WIDTH, CAMERA_HEIGHT);
	}
}
