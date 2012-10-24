package com.ustudio.track;

import org.anddev.andengine.entity.Entity;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TextureRegion;

import com.ustudio.main.MainActivity;

public class TrackV extends Entity{
	
	static int CAMERA_WIDTH;
	static int CAMERA_HEIGHT;
	static Scene scene;

	private Texture	mTexture;
	
	private TextureRegion mKnob;
	private TextureRegion mMuteRelased;
	private TextureRegion mMutePressed;
	private TextureRegion mSoloRelased;
	private TextureRegion mSoloPressed;
	private TextureRegion mPianoRelased;
	private TextureRegion mPianoPressed;
	private TextureRegion mConfigRelased;
	private TextureRegion mConfigPressed;
	private TextureRegion mOpenTrack;
	
	private float knobHeight;
	private float knobWidth;
	private float knobY;
	private float knobX;
	
	private float muteHeight;
	private float muteWidth;
	private float muteY;
	private float muteX;
	
	private float soloHeight;
	private float soloWidth;
	private float soloY;
	private float soloX;
	
	private float pianoHeight;
	private float pianoWidth;
	private float pianoY;
	private float pianoX;
	
	private float configHeight;
	private float configWidth;
	private float configY;
	private float configX;
	
	private float openHeight;
	private float openWidth;
	private float openY;
	private float openX;
	
	public TrackV (int w, int h, Scene s){
		CAMERA_WIDTH = w;
		CAMERA_HEIGHT = h;
		scene = s;
		loadGUITextures();
		loadSizes();
		drawButtons();
	}
	private void loadGUITextures(){
		this.mTexture = new BitmapTextureAtlas(2048, 2048, TextureOptions.BILINEAR);
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/Buttons/");
		this.mKnob = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture, MainActivity.getInstance().getApplicationContext(), "knob.png", 0, 0);
		this.mMuteRelased = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture, MainActivity.getInstance().getApplicationContext(), "mute_relased.png", 0, 115);
		this.mMutePressed = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture, MainActivity.getInstance().getApplicationContext(), "mute_pressed.png", 0, 162);
		this.mSoloRelased = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture, MainActivity.getInstance().getApplicationContext(), "solo_relased.png", 0, 208);
		this.mSoloPressed = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture, MainActivity.getInstance().getApplicationContext(), "solo_pressed.png", 0, 254);
		this.mPianoRelased = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture, MainActivity.getInstance().getApplicationContext(), "piano_relased.png", 0, 300);
		this.mPianoPressed = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture, MainActivity.getInstance().getApplicationContext(), "piano_pressed.png", 0, 385);
		this.mConfigRelased = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture, MainActivity.getInstance().getApplicationContext(), "config_relased.png", 0, 470);
		this.mConfigPressed = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture, MainActivity.getInstance().getApplicationContext(), "config_pressed.png", 0, 541);
		this.mOpenTrack = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture, MainActivity.getInstance().getApplicationContext(), "open_track.png", 0, 612);
		
		MainActivity.getInstance().getEngine().getTextureManager().loadTexture(this.mTexture);
	}
	private void drawButtons(){
		Sprite knob_sprite = new Sprite(0,0,this.mKnob);
		knob_sprite.setWidth(this.knobWidth);
		knob_sprite.setHeight(this.knobHeight);
		
		knob_sprite.setPosition(this.knobX,this.knobY);
		
		this.attachChild(knob_sprite);
		
		//----------------------------------------------------------------------------------------
		
		Sprite mutep_sprite = new Sprite(0,0,this.mMutePressed);
		mutep_sprite.setWidth(this.muteWidth);
		mutep_sprite.setHeight(this.muteHeight);
		
		mutep_sprite.setPosition(this.muteX,this.muteY);
		
		this.attachChild(mutep_sprite);
		
		Sprite muter_sprite = new Sprite(0,0,this.mMuteRelased)
		{
            public boolean onAreaTouched(final TouchEvent pAreaTouchEvent, final float pX, final float pY) {
            	switch(pAreaTouchEvent.getAction()) {
				    case TouchEvent.ACTION_DOWN:   
				    	this.setVisible(false);
				        break;
				    case TouchEvent.ACTION_UP:  
				    	this.setVisible(true);
				        break;
				    case TouchEvent.ACTION_MOVE:
				    	this.setVisible(true);
				        break;
				}
				return true;
            }
        };
        
        muter_sprite.setWidth(this.muteWidth);
        muter_sprite.setHeight(this.muteHeight);
		
        muter_sprite.setPosition(this.muteX,this.muteY);
		
		this.attachChild(muter_sprite);
		
		scene.registerTouchArea(muter_sprite);
		
		//----------------------------------------------------------------------------------------
		
		Sprite solop_sprite = new Sprite(0,0,this.mSoloPressed);
		solop_sprite.setWidth(this.soloWidth);
		solop_sprite.setHeight(this.soloHeight);
		
		solop_sprite.setPosition(this.soloX,this.soloY);
		
		this.attachChild(solop_sprite);
		
		Sprite solor_sprite = new Sprite(0,0,this.mSoloRelased)
		{
            public boolean onAreaTouched(final TouchEvent pAreaTouchEvent, final float pX, final float pY) {
            	switch(pAreaTouchEvent.getAction()) {
				    case TouchEvent.ACTION_DOWN:   
				    	this.setVisible(false);
				        break;
				    case TouchEvent.ACTION_UP:  
				    	this.setVisible(true);
				        break;
				    case TouchEvent.ACTION_MOVE:
				    	this.setVisible(true);
				        break;
				}
				return true;
            }
        };
        
        solor_sprite.setWidth(this.soloWidth);
        solor_sprite.setHeight(this.soloHeight);
		
        solor_sprite.setPosition(this.soloX,this.soloY);
		
		this.attachChild(solor_sprite);
		
		scene.registerTouchArea(solor_sprite);
		
		//----------------------------------------------------------------------------------------
		
		Sprite pianop_sprite = new Sprite(0,0,this.mPianoPressed);
		pianop_sprite.setWidth(this.pianoWidth);
		pianop_sprite.setHeight(this.pianoHeight);
		
		pianop_sprite.setPosition(this.pianoX,this.pianoY);
		
		this.attachChild(pianop_sprite);
		
		Sprite pianor_sprite = new Sprite(0,0,this.mPianoRelased)
		{
            public boolean onAreaTouched(final TouchEvent pAreaTouchEvent, final float pX, final float pY) {
            	switch(pAreaTouchEvent.getAction()) {
				    case TouchEvent.ACTION_DOWN:   
				    	this.setVisible(false);
				        break;
				    case TouchEvent.ACTION_UP:  
				    	this.setVisible(true);
				        break;
				    case TouchEvent.ACTION_MOVE:
				    	this.setVisible(true);
				        break;
				}
				return true;
            }
        };
        
        pianor_sprite.setWidth(this.pianoWidth);
        pianor_sprite.setHeight(this.pianoHeight);
		
        pianor_sprite.setPosition(this.pianoX,this.pianoY);
		
		this.attachChild(pianor_sprite);
		
		scene.registerTouchArea(pianor_sprite);
		
		//----------------------------------------------------------------------------------------
		
		Sprite configp_sprite = new Sprite(0,0,this.mConfigPressed);
		configp_sprite.setWidth(this.configWidth);
		configp_sprite.setHeight(this.configHeight);
		
		configp_sprite.setPosition(this.configX,this.configY);
		
		this.attachChild(configp_sprite);
		
		Sprite configr_sprite = new Sprite(0,0,this.mConfigRelased)
		{
            public boolean onAreaTouched(final TouchEvent pAreaTouchEvent, final float pX, final float pY) {
            	switch(pAreaTouchEvent.getAction()) {
				    case TouchEvent.ACTION_DOWN:   
				    	this.setVisible(false);
				        break;
				    case TouchEvent.ACTION_UP:  
				    	this.setVisible(true);
				        break;
				    case TouchEvent.ACTION_MOVE:
				    	this.setVisible(true);
				        break;
				}
				return true;
            }
        };
        
        configr_sprite.setWidth(this.configWidth);
        configr_sprite.setHeight(this.configHeight);
		
        configr_sprite.setPosition(this.configX,this.configY);
		
		this.attachChild(configr_sprite);
		
		scene.registerTouchArea(configr_sprite);
		
		//----------------------------------------------------------------------------------------
		
		
	}
	private void loadSizes(){
		this.knobHeight=CAMERA_HEIGHT/14.15f;
		this.knobWidth=CAMERA_WIDTH/5.78f;
		this.knobY=((CAMERA_HEIGHT/14.67f));
		this.knobX=((CAMERA_WIDTH/3.01f));
		
		this.muteHeight=CAMERA_HEIGHT/34.78f;
		this.muteWidth=CAMERA_WIDTH/18.46f;
		this.muteY=((CAMERA_HEIGHT/14.95f));
		this.muteX=((CAMERA_WIDTH/1.8045f));
		
		this.soloHeight=CAMERA_HEIGHT/34.78f;
		this.soloWidth=CAMERA_WIDTH/18.46f;
		this.soloY=((CAMERA_HEIGHT/8.69f));
		this.soloX=((CAMERA_WIDTH/1.8045f));
		
		this.pianoHeight=CAMERA_HEIGHT/18.82f;
		this.pianoWidth=CAMERA_WIDTH/11.42f;
		this.pianoY=((CAMERA_HEIGHT/12.69f));
		this.pianoX=((CAMERA_WIDTH/1.5311f));
		
		this.configHeight=CAMERA_HEIGHT/22.53f;
		this.configWidth=CAMERA_WIDTH/14.54f;
		this.configY=((CAMERA_HEIGHT/12.03f));
		this.configX=((CAMERA_WIDTH/1.2834f));
		
		this.openHeight=CAMERA_HEIGHT/32.00f;
		this.openWidth=CAMERA_WIDTH/34.28f;
		this.openY=((CAMERA_HEIGHT/11.34f));
		this.openX=((CAMERA_WIDTH/1.1136f));
		
	}
}
