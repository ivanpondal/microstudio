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
	
		Sprite backGround = new Sprite(0,0, mFaceTextureRegion);
	    //backGround.setRotation(-90);
	    backGround.setWidth(CAMERA_WIDTH);
	    backGround.setHeight(CAMERA_HEIGHT);
	    this.attachChild(backGround);
	    
	    Entity menuBar = new Entity();
	    
	    Sprite menu = new Sprite((CAMERA_WIDTH/13)*12, ((CAMERA_HEIGHT/4.6f)/4 + (CAMERA_HEIGHT/4.6f))*3 - (CAMERA_HEIGHT/4.6f)/4 - (CAMERA_HEIGHT/4.6f)/16, mFaceTextureRegion2);
	    //menu.setRotation(-90);
	    menu.setWidth(CAMERA_WIDTH/13);
	    menu.setHeight(CAMERA_HEIGHT/4.6f);
	    menuBar.attachChild(menu);
	    
	    Sprite play = new Sprite((CAMERA_WIDTH/13)*12, ((CAMERA_HEIGHT/4.6f)/4 + (CAMERA_HEIGHT/4.6f))*2 - (CAMERA_HEIGHT/4.6f)/8, mFaceTextureRegion3);
	    //play.setRotation(-90);
	    play.setWidth(CAMERA_WIDTH/13);
	    play.setHeight(CAMERA_HEIGHT/4.6f);
	    menuBar.attachChild(play);
	    
	    Sprite pause = new Sprite((CAMERA_WIDTH/13)*12, CAMERA_HEIGHT/4.6f  + (CAMERA_HEIGHT/4.6f)/4 + (CAMERA_HEIGHT/4.6f)/16, mFaceTextureRegion4);
	    //pause.setRotation(-90);
	    pause.setWidth(CAMERA_WIDTH/13);
	    pause.setHeight(CAMERA_HEIGHT/4.6f);
	    menuBar.attachChild(pause);
	    
	    Sprite stop = new Sprite((CAMERA_WIDTH/13)*12, (CAMERA_HEIGHT/4.6f)/4, mFaceTextureRegion5);
	    //stop.setRotation(-90);
	    stop.setWidth(CAMERA_WIDTH/13);
	    stop.setHeight(CAMERA_HEIGHT/4.6f);
	    menuBar.attachChild(stop);

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
