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
import com.ustudio.piano.*;

public class FreePlay extends Scene {

	static int CAMERA_WIDTH;
	static int CAMERA_HEIGHT;
	
	private MiniPiano mMiniPiano;
	
	private Piano mTouchPiano;
	
	private Instrument mInsPiano;
	
	private Texture mTexture;
	
	private TextureRegion mBackground;
	private TextureRegion[] mButton_N;
	private TextureRegion[] mButton_P;
	private TextureRegion mStep;
	
	private Entity mToolBar;
	private Entity mWholeStep;
	private Entity mSingleStep;
	
	private float ButtonWidth;
	private float ButtonHeight;
	private float BGHeight;
	private float ToolbarY;
	private float ToolbarX;
	private float PianoY;
	private float PianoX;
	private float MiniPianoY;
	private float MiniPianoX;
	private float StepHeight;
	private float StepWidth;
	private float WholeStepBwX;
	private float WholeStepBwY;
	private float WholeStepFwX;
	private float WholeStepFwY;
	private float StepBwX;
	private float StepBwY;
	private float StepFwX;
	private float StepFwY;
	
	public FreePlay(int w, int h) {
		CAMERA_WIDTH = w;
		CAMERA_HEIGHT = h;
		
		loadSizes();
		loadGUITextures();
		drawBG();
		drawToolBar();
		drawMiniPiano();
		drawPiano();
	}
	
	private void loadSizes()
	{
		this.BGHeight=CAMERA_HEIGHT/2.5f;
		this.ButtonWidth=CAMERA_WIDTH/7.6f;
		this.ButtonHeight=CAMERA_HEIGHT/6.9f;
		this.ToolbarY=CAMERA_HEIGHT/24;
		this.ToolbarX=(CAMERA_WIDTH-this.ButtonWidth*6)/2;
		this.PianoX=(CAMERA_WIDTH-CAMERA_WIDTH/8)*-3;//hago que empieze desde el middle C
		this.MiniPianoY=CAMERA_HEIGHT/4.4f;
		this.StepWidth=CAMERA_WIDTH/50;
		this.StepHeight=CAMERA_HEIGHT/15;
		this.WholeStepFwX=CAMERA_WIDTH-(CAMERA_WIDTH/6f);
		this.WholeStepFwY=CAMERA_HEIGHT/4f;
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
		
		this.mStep = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture, MainActivity.getInstance().getApplicationContext(), "Buttons/step.png", 1600, 834);
		
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
	
	private void drawMiniPiano()
	{
		this.mWholeStep=new Entity();
		Sprite step1_sprite = new Sprite(0,0,this.mStep);
		Sprite step2_sprite = new Sprite(this.StepWidth/1.5f,0,this.mStep);
		
		step1_sprite.setWidth(this.StepWidth);
		step1_sprite.setHeight(this.StepHeight);
		
		step2_sprite.setWidth(this.StepWidth);
		step2_sprite.setHeight(this.StepHeight);
		
		this.mWholeStep.attachChild(step1_sprite);
		this.mWholeStep.attachChild(step2_sprite);
		
		this.mWholeStep.setPosition(this.WholeStepFwX, this.WholeStepFwY);
		
		this.attachChild(this.mWholeStep);
		
		this.mMiniPiano = new MiniPiano(this, CAMERA_WIDTH, CAMERA_HEIGHT);
		this.MiniPianoX=(CAMERA_WIDTH-this.mMiniPiano.getKeyboardWidth())/2;
		this.mMiniPiano.setPosition(this.MiniPianoX, this.MiniPianoY);
		this.attachChild(this.mMiniPiano);	
	}
	
	private void drawPiano()
	{
		this.mInsPiano=new Instrument("Piano",(byte)1,400,(byte)60,(byte)73);
		this.mTouchPiano = new Piano(this, CAMERA_WIDTH, CAMERA_HEIGHT, this.mInsPiano);
		this.PianoY=CAMERA_HEIGHT-this.mTouchPiano.getKeyboardHeight();
		this.mTouchPiano.setPosition(this.PianoX, this.PianoY);
		this.attachChild(this.mTouchPiano);		
	}
}
