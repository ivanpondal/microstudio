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

import android.util.Log;

import com.ustudio.main.MainActivity;

public class TrackV extends Entity{
	
	static int CAMERA_WIDTH;
	static int CAMERA_HEIGHT;
	static Scene scene;

	private Texture	mTexture;
	
	private TextureRegion mKnob;
	private TextureRegion mVolumeIndicators;
	private TextureRegion mMuteRelased;
	private TextureRegion mMutePressed;
	private TextureRegion mSoloRelased;
	private TextureRegion mSoloPressed;
	private TextureRegion mPianoRelased;
	private TextureRegion mPianoPressed;
	private TextureRegion mConfigRelased;
	private TextureRegion mConfigPressed;
	private TextureRegion mOpenTrack;
	private TextureRegion mSeparator;
	
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
	
	private float indicatorHeight;
	private float indicatorWidth;
	private float indicatorY;
	private float indicatorX;
	
	private float separatorHeight;
	private float separatorWidth;
	private float separatorY;
	private float separatorX;
	
	private Sprite knob_sprite;
	
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
		this.mKnob = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture, MainActivity.getInstance().getApplicationContext(), "knob-rotate.png", 0, 0);
		this.mMuteRelased = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture, MainActivity.getInstance().getApplicationContext(), "mute_relased.png", 0, 115);
		this.mMutePressed = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture, MainActivity.getInstance().getApplicationContext(), "mute_pressed.png", 0, 162);
		this.mSoloRelased = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture, MainActivity.getInstance().getApplicationContext(), "solo_relased.png", 0, 208);
		this.mSoloPressed = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture, MainActivity.getInstance().getApplicationContext(), "solo_pressed.png", 0, 254);
		this.mPianoRelased = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture, MainActivity.getInstance().getApplicationContext(), "piano_relased.png", 0, 300);
		this.mPianoPressed = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture, MainActivity.getInstance().getApplicationContext(), "piano_pressed.png", 0, 385);
		this.mConfigRelased = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture, MainActivity.getInstance().getApplicationContext(), "config_relased.png", 0, 470);
		this.mConfigPressed = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture, MainActivity.getInstance().getApplicationContext(), "config_pressed.png", 0, 541);
		this.mOpenTrack = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture, MainActivity.getInstance().getApplicationContext(), "open_track.png", 0, 612);
		this.mVolumeIndicators = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture, MainActivity.getInstance().getApplicationContext(), "volume_indicators_03.png", 0, 663);
		this.mSeparator = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture, MainActivity.getInstance().getApplicationContext(), "separator.png", 0, 678);
		
		MainActivity.getInstance().getEngine().getTextureManager().loadTexture(this.mTexture);
	}
	private void drawButtons(){
		knob_sprite = new Sprite(0,0,this.mKnob);
		knob_sprite.setWidth(this.knobWidth);
		knob_sprite.setHeight(this.knobHeight);
		
		knob_sprite.setPosition(this.knobX,this.knobY);
		knob_sprite.setRotationCenter(knobWidth/2, knobHeight/2);
		knob_sprite.setRotation(-22.5f);
		
		this.attachChild(knob_sprite);
		
		//----------------------------------------------------------------------------------------
		
		Sprite knob_adjust = new Sprite(0,0,this.mKnob){
            public boolean onAreaTouched(final TouchEvent pAreaTouchEvent, final float pX, final float pY) {
            	switch(pAreaTouchEvent.getAction()) {
				    case TouchEvent.ACTION_DOWN:  
				        break;
				    case TouchEvent.ACTION_UP:  
				        break;
				    case TouchEvent.ACTION_MOVE:
				    	if (pX<280 && pX>0 ){
				    		knob_sprite.setRotation(pX + 200);
				    		Log.d("ROTATE",pX + "¼");
				    	}
				    	break;
				}
				return true;
            }
        };
        knob_adjust.setWidth(this.knobWidth*4);
        knob_adjust.setHeight(this.knobHeight);
		
        knob_adjust.setPosition(this.knobX - this.knobWidth*2,this.knobY);
        knob_adjust.setRotationCenter(knobWidth/2, knobHeight/2);
		knob_adjust.setAlpha(0.0f);
        
		this.attachChild(knob_adjust);
		
		scene.registerTouchArea(knob_adjust);
		
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
		
		Sprite open_sprite = new Sprite(0,0,this.mOpenTrack);
		open_sprite.setWidth(this.openWidth);
		open_sprite.setHeight(this.openHeight);
		
		open_sprite.setPosition(this.openX,this.openY);
		
		this.attachChild(open_sprite);
		
		//----------------------------------------------------------------------------------------
		
		Sprite indicator_sprite = new Sprite(0,0,this.mVolumeIndicators);
		indicator_sprite.setWidth(this.indicatorWidth);
		indicator_sprite.setHeight(this.indicatorHeight);
		
		indicator_sprite.setPosition(this.indicatorX,this.indicatorY);
		
		this.attachChild(indicator_sprite);
		
		//----------------------------------------------------------------------------------------
		
		Sprite separator_sprite = new Sprite(0,0,this.mSeparator);
		separator_sprite.setWidth(this.separatorWidth);
		separator_sprite.setHeight(this.separatorHeight);
		
		separator_sprite.setPosition(this.separatorX,this.separatorY);
		
		this.attachChild(separator_sprite);
		
		//----------------------------------------------------------------------------------------
		
	}
	private void loadSizes(){
		this.knobHeight=CAMERA_HEIGHT/14.15f;
		this.knobWidth=CAMERA_HEIGHT/14.15f;
		this.knobY=((CAMERA_HEIGHT/15.0f));
		this.knobX=((CAMERA_WIDTH/2.85f));
		
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
		this.openX=((CAMERA_WIDTH/1.135f));
		
		this.indicatorHeight=CAMERA_HEIGHT/(1600.0f/14.71f);
		this.indicatorWidth=CAMERA_WIDTH/(960.0f/171.0f);
		this.indicatorY=((CAMERA_HEIGHT/(1600.0f/210.0f)));
		this.indicatorX=((CAMERA_WIDTH/(960.0f/315.0f)));
		
		this.separatorHeight=CAMERA_HEIGHT/(1600.0f/6.461f);
		this.separatorWidth=CAMERA_WIDTH/(960.0f/843.0f);
		this.separatorY=((CAMERA_HEIGHT/(1600.0f/283.5f)));
		this.separatorX=((CAMERA_WIDTH/(960.0f/59.0f)));
		
		
		
	}
}
