package com.ustudio.track;

import org.anddev.andengine.entity.Entity;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.entity.text.ChangeableText;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.font.Font;
import org.anddev.andengine.opengl.font.FontFactory;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.util.HorizontalAlign;

import android.graphics.Color;
import android.util.Log;

import com.ustudio.main.MainActivity;
import com.ustudio.scenes.RecordScene;

public class TrackGUI extends Entity{
	
	static int CAMERA_WIDTH;
	static int CAMERA_HEIGHT;
	static RecordScene scene;

	private Texture	mTexture;
	private Texture mFontTexture;
	
	private TextureRegion mTransparent;
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
	
	private ChangeableText mName;
	
	private float mTrackHeight;
	private float mTrackWidth;
	
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
	
	private float mFontSize;
	private float mFontY;
	private float mFontX;
	
	private Sprite knob_sprite;
	
	private Font mFont;
	
	public TrackGUI (int w, int h, RecordScene s){
		CAMERA_WIDTH = w;
		CAMERA_HEIGHT = h;
		scene = s;
		loadSizes();
		loadGUITextures();
		drawButtons();
		drawText();
	}
	private void loadGUITextures(){
		FontFactory.setAssetBasePath("fonts/");
		
		this.mFontTexture = new BitmapTextureAtlas(256, 256,TextureOptions.BILINEAR);

		this.mFont = FontFactory.createFromAsset((BitmapTextureAtlas) this.mFontTexture,MainActivity.getInstance().getApplicationContext(), "cambria.ttf", this.mFontSize, true,Color.WHITE);
		
		MainActivity.getInstance().getEngine().getTextureManager().loadTexture(this.mFontTexture);
		MainActivity.getInstance().getEngine().getFontManager().loadFont(this.mFont);
		
		this.mTexture = new BitmapTextureAtlas(1024, 256, TextureOptions.BILINEAR);
		
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/Backgrounds/");
		this.mTransparent = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture, MainActivity.getInstance().getApplicationContext(), "1024x256.png", 0, 0);
		
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/Buttons/");
		this.mKnob = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture, MainActivity.getInstance().getApplicationContext(), "knob_rotate.png", 0, 0);
		this.mMuteRelased = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture, MainActivity.getInstance().getApplicationContext(), "mute_relased.png", 104, 0);
		this.mMutePressed = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture, MainActivity.getInstance().getApplicationContext(), "mute_pressed.png", 104, 46);
		this.mSoloRelased = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture, MainActivity.getInstance().getApplicationContext(), "solo_relased.png", 156, 0);
		this.mSoloPressed = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture, MainActivity.getInstance().getApplicationContext(), "solo_pressed.png", 156, 45);
		this.mPianoRelased = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture, MainActivity.getInstance().getApplicationContext(), "piano_relased.png", 208, 0);
		this.mPianoPressed = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture, MainActivity.getInstance().getApplicationContext(), "piano_pressed.png", 208, 85);
		this.mConfigRelased = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture, MainActivity.getInstance().getApplicationContext(), "config_relased.png", 292, 0);
		this.mConfigPressed = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture, MainActivity.getInstance().getApplicationContext(), "config_pressed.png", 292, 85);
		this.mOpenTrack = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture, MainActivity.getInstance().getApplicationContext(), "open_track.png", 362, 50);
		this.mVolumeIndicators = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture, MainActivity.getInstance().getApplicationContext(), "volume_indicators_03.png", 391, 0);
		this.mSeparator = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture, MainActivity.getInstance().getApplicationContext(), "separator.png", 0, 245);
		
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
				    		Log.d("ROTATE",pX + "�");
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
				    	scene.loadPiano();
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
	
	private void drawText()
	{
		this.mName = new ChangeableText(0, 0, this.mFont,"Piano 1",HorizontalAlign.CENTER,20);
		this.mName.setColor(0.82f, 0.73f, 0.6f);
		this.mName.setPosition(this.mFontX, this.mFontY);

		this.attachChild(this.mName);
	}
	
	private void loadSizes(){
		this.mTrackHeight=CAMERA_HEIGHT/(800/117.0f);
		this.mTrackWidth=CAMERA_WIDTH/(480/421.0f);
		
		this.knobHeight=CAMERA_WIDTH/(120/13.0f);
		this.knobWidth=this.knobHeight;
		this.knobY=this.mTrackHeight/2-this.knobHeight/2;
		this.knobX=CAMERA_WIDTH/(64/19.0f);
		
		this.muteHeight=CAMERA_HEIGHT/34.78f;
		this.muteWidth=CAMERA_WIDTH/18.46f;
		this.muteY=this.mTrackHeight/4-this.muteHeight/2;
		this.muteX=CAMERA_WIDTH/(120/59.0f);
		
		this.soloHeight=this.muteHeight;
		this.soloWidth=this.muteWidth;
		this.soloY=this.mTrackHeight*0.75f-this.soloHeight/2;
		this.soloX=this.muteX;
		
		this.pianoHeight=CAMERA_HEIGHT/(320/17.0f);
		this.pianoWidth=CAMERA_WIDTH/(80/7.0f);
		this.pianoY=this.mTrackHeight/2-this.pianoHeight/2;
		this.pianoX=CAMERA_WIDTH/(96/55.0f);
		
		this.configHeight=CAMERA_HEIGHT/(160f/7f);
		this.configWidth=this.configHeight;
		this.configY=this.mTrackHeight/2-(this.configHeight/2);
		this.configX=CAMERA_WIDTH/(96/67.0f);
		
		this.openHeight=CAMERA_HEIGHT/32.00f;
		this.openWidth=CAMERA_WIDTH/(960/29.0f);
		this.openY=this.mTrackHeight/2-(this.openHeight/2);
		this.openX=CAMERA_WIDTH/(60/48.0f);
		
		this.indicatorHeight=CAMERA_HEIGHT/(1600.0f/14.0f);
		this.indicatorWidth=CAMERA_WIDTH/(960.0f/171.0f);
		this.indicatorY=this.mTrackHeight/2+this.knobHeight/2-this.indicatorHeight;
		this.indicatorX=this.knobX+(this.knobWidth/2-this.indicatorWidth/2);
		
		this.separatorHeight=CAMERA_HEIGHT/(1600/6.0f);
		this.separatorWidth=CAMERA_WIDTH/(960/843.0f);
		this.separatorY=this.mTrackHeight-this.separatorHeight;
		this.separatorX=0f;
		
		this.mFontSize=CAMERA_HEIGHT/38.0f;
		this.mFontY=this.mTrackHeight/2-this.mFontSize*0.75f;
		this.mFontX=CAMERA_WIDTH/20f;
			
	}
	
	//SET
	public void setTrackWidth(float w)
	{
		this.mTrackWidth=w;
	}
	
	public void setTrackHeight(float h)
	{
		this.mTrackHeight=h;
	}
	
	//GET
	public float getTrackWidth()
	{
		return this.mTrackWidth;
	}
	
	public float getTrackHeight()
	{
		return this.mTrackHeight;
	}
}
