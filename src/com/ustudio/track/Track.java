package com.ustudio.track;

import org.anddev.andengine.entity.Entity;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TextureRegion;

import com.ustudio.main.MainActivity;

public class Track extends Entity{
	
	static int CAMERA_WIDTH;
	static int CAMERA_HEIGHT;

	private Texture	mTexture;
	
	private TextureRegion mKnob;
	private TextureRegion mMuteRelased;
	private TextureRegion mMutePressed;
	private TextureRegion mSoloRelased;
	private TextureRegion mSoloPressed;
	
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
	
	public Track (int w, int h){

	}
	private void loadGUITextures(){
		this.mTexture = new BitmapTextureAtlas(2048, 2048, TextureOptions.BILINEAR);
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/Buttons/");
		this.mKnob = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture, MainActivity.getInstance().getApplicationContext(), "knob.png", 0, 0);
		this.mMuteRelased = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture, MainActivity.getInstance().getApplicationContext(), "mute_relased.png", 0, 115);
		this.mMutePressed = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture, MainActivity.getInstance().getApplicationContext(), "mute_pressed.png", 0, 162);
		this.mSoloRelased = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture, MainActivity.getInstance().getApplicationContext(), "solo_relased.png", 53, 115);
		this.mSoloPressed = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture, MainActivity.getInstance().getApplicationContext(), "solo_pressed.png", 53, 162);
		
		MainActivity.getInstance().getEngine().getTextureManager().loadTexture(this.mTexture);
	}
	private void drawButtons(){
		
	}
	private void loadSizes(){
		this.knobHeight=CAMERA_HEIGHT/14.15f;
		this.knobWidth=CAMERA_WIDTH/5.78f;
		this.knobY=CAMERA_HEIGHT-(this.knobHeight+(CAMERA_HEIGHT/14.67f));
		this.knobX=CAMERA_WIDTH-(this.knobWidth+(CAMERA_WIDTH/3.01f));
		
		this.muteHeight=CAMERA_HEIGHT/34.78f;
		this.muteWidth=CAMERA_WIDTH/18.46f;
		this.muteY=CAMERA_HEIGHT-(this.muteHeight+(CAMERA_HEIGHT/14.95f));
		this.muteX=CAMERA_WIDTH-(this.muteWidth+(CAMERA_WIDTH/1.8045f));
		
		this.soloHeight=CAMERA_HEIGHT/34.78f;
		this.soloWidth=CAMERA_WIDTH/18.46f;
		this.soloY=CAMERA_HEIGHT-(this.soloHeight+(CAMERA_HEIGHT/8.69f));
		this.soloX=CAMERA_WIDTH-(this.soloWidth+(CAMERA_WIDTH/1.8045f));
		
	}
}
