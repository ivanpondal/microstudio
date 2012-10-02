package com.ustudio.scenes;

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

import com.ustudio.audio.Instrument;
import com.ustudio.main.MainActivity;
import com.ustudio.piano.Piano;

public class FreePlay extends Scene {

	static int CAMERA_WIDTH;
	static int CAMERA_HEIGHT;
	
	private Piano mTouchPiano;
	private Instrument mInsPiano;
	
	private Texture mTexture;
	
	private TextureRegion mBackground;
	private TextureRegion[] mButton_N;
	private TextureRegion[] mButton_P;

	
	private Entity mToolBar;
	
	private float ButtonWidth;
	private float ButtonHeight;
	private float BGHeight;
	private float ToolbarY;
	private float ToolbarX;
	
	public FreePlay(int w, int h) {
		CAMERA_WIDTH = w;
		CAMERA_HEIGHT = h;
		
		loadSizes();
		loadGUITextures();
		drawBG();
		drawToolBar();
		drawPiano();
	}
	
	private void loadSizes()
	{
		this.BGHeight=CAMERA_HEIGHT/2.5f;
		this.ButtonWidth=CAMERA_WIDTH/7.6f;
		this.ButtonHeight=CAMERA_HEIGHT/6.9f;
		this.ToolbarY=CAMERA_HEIGHT/24;
		this.ToolbarX=(CAMERA_WIDTH-this.ButtonWidth*6)/2;
	}
	
	private void loadGUITextures()
	{
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/Piano/");
		this.mTexture = new BitmapTextureAtlas(2048, 1024, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		
		this.mBackground = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture, MainActivity.getInstance().getApplicationContext(), "Background/bg.png", 0, 0);
		
		this.mButton_N=new TextureRegion[6];
		this.mButton_P=new TextureRegion[6];
		
		for(byte i=0;i<6;i++)
		{
			this.mButton_N[i] = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture, MainActivity.getInstance().getApplicationContext(), "Buttons/"+i+"_released.png", 1600, i*139);
			this.mButton_P[i] = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture, MainActivity.getInstance().getApplicationContext(), "Buttons/"+i+"_pressed.png", 1811, i*139);
		}
		
		MainActivity.getInstance().getEngine().getTextureManager().loadTexture(this.mTexture);
	}
	
	private void drawBG()
	{
		
		Sprite bg_sprite = new Sprite(0,0,this.mBackground);
		bg_sprite.setWidth(CAMERA_WIDTH);
		bg_sprite.setHeight(this.BGHeight);
		this.attachChild(bg_sprite);
	}
	
	private void drawToolBar()
	{

		this.mToolBar=new Entity();
		for(byte i=0;i<6;i++)
		{
			Sprite sp_pressed = new Sprite(i*this.ButtonWidth,0,this.mButton_P[i]);
			sp_pressed.setWidth(this.ButtonWidth);
			sp_pressed.setHeight(this.ButtonHeight);
			
			this.mToolBar.attachChild(sp_pressed);
			
			Sprite sp_released = new Sprite(i*this.ButtonWidth,0,this.mButton_N[i])
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
	        sp_released.setWidth(this.ButtonWidth);
	        sp_released.setHeight(this.ButtonHeight);
			
			this.mToolBar.attachChild(sp_released);
			
			this.registerTouchArea(sp_released);
		}
		
		this.mToolBar.setPosition(this.ToolbarX,this.ToolbarY);
		
		this.attachChild(this.mToolBar);
	}
	
	private void drawPiano()
	{
		this.mInsPiano=new Instrument("Piano",(byte)1,400,(byte)60,(byte)73);
		this.mTouchPiano = new Piano(this, CAMERA_WIDTH, CAMERA_HEIGHT, this.mInsPiano);
		this.attachChild(this.mTouchPiano);
		this.mTouchPiano.setPosition((CAMERA_WIDTH-CAMERA_WIDTH/8)*-3, 0.0f);//hago que empieze desde el middle C
	}
}
