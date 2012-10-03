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
	
	private Texture mTexture1;
	private TextureRegion mFaceTextureRegion1;
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
	
	public Track (int w, int h){
		
		this.mTexture1 = new BitmapTextureAtlas(512, 512, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mFaceTextureRegion1 = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture1, MainActivity.getInstance().getApplicationContext(), "gfx/Track/volumeTrack.png", 0, 0);
		MainActivity.getInstance().getEngine().getTextureManager().loadTexture(this.mTexture1);
		
		this.mTexture2 = new BitmapTextureAtlas(256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mFaceTextureRegion2 = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture2, MainActivity.getInstance().getApplicationContext(), "gfx/Track/muteTrack.png", 0, 0);
		MainActivity.getInstance().getEngine().getTextureManager().loadTexture(this.mTexture2);
		
		this.mTexture3 = new BitmapTextureAtlas(256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mFaceTextureRegion3 = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture3, MainActivity.getInstance().getApplicationContext(), "gfx/Track/soloTrack.png", 0, 0);
		MainActivity.getInstance().getEngine().getTextureManager().loadTexture(this.mTexture3);
		
		this.mTexture4 = new BitmapTextureAtlas(1024, 1024, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mFaceTextureRegion4 = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture4, MainActivity.getInstance().getApplicationContext(), "gfx/Track/separatorTrack.png", 0, 0);
		MainActivity.getInstance().getEngine().getTextureManager().loadTexture(this.mTexture4);
		
		this.mTexture5 = new BitmapTextureAtlas(256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mFaceTextureRegion5 = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture5, MainActivity.getInstance().getApplicationContext(), "gfx/Track/pianoTrack.png", 0, 0);
		MainActivity.getInstance().getEngine().getTextureManager().loadTexture(this.mTexture5);
		
		this.mTexture6 = new BitmapTextureAtlas(256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mFaceTextureRegion6 = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture6, MainActivity.getInstance().getApplicationContext(), "gfx/Track/confTrack.png", 0, 0);
		MainActivity.getInstance().getEngine().getTextureManager().loadTexture(this.mTexture6);
		
		this.mTexture7 = new BitmapTextureAtlas(256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mFaceTextureRegion7 = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture7, MainActivity.getInstance().getApplicationContext(), "gfx/Track/labelTrack.png", 0, 0);
		MainActivity.getInstance().getEngine().getTextureManager().loadTexture(this.mTexture7);
		
		Sprite label = new Sprite(w/17 ,h/2 + h/4 - h/16, mFaceTextureRegion7);
		label.setWidth(w/14);
		label.setHeight(h/5);
		//volume.setRotation(-90);
	    this.attachChild(label);
		
		Sprite volume = new Sprite(w/17 ,h/2, mFaceTextureRegion1);
		volume.setWidth(w/14);
		volume.setHeight(h/5);
		//volume.setRotation(-90);
	    this.attachChild(volume);
	    
	    Sprite mute = new Sprite(w/20, h/2 - h/8, mFaceTextureRegion2);
	    mute.setWidth(w/28f);
	    mute.setHeight(h/13);
	    //mute.setRotation(-90);
	    this.attachChild(mute);
	    
	    Sprite solo = new Sprite(w/10 , h/2 - h/8, mFaceTextureRegion3);
	    solo.setWidth(w/28f);
	    solo.setHeight(h/13);
	    //mute.setRotation(-90);
	    this.attachChild(solo);
	    
	    Sprite separator = new Sprite(w/6 ,  h/8 - h/32, mFaceTextureRegion4);
	    separator.setWidth(w/100f);
	    separator.setHeight(h*0.8f);
	    //mute.setRotation(-90);
	    this.attachChild(separator);
	    
	    Sprite piano = new Sprite(w/13 , h/8 + h/8 , mFaceTextureRegion5);
	    piano.setWidth(w/25f);
	    piano.setHeight(h/13);
	    //mute.setRotation(-90);
	    this.attachChild(piano);
	    
	    Sprite conf = new Sprite(w/13 , h/8  , mFaceTextureRegion6);
	    conf.setWidth(w/25f);
	    conf.setHeight(h/13);
	    //mute.setRotation(-90);
	    this.attachChild(conf);
	}
}
