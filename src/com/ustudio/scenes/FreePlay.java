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

import android.content.pm.ActivityInfo;
import android.util.Log;

import com.ustudio.project.*;
import com.ustudio.audio.Instrument;
import com.ustudio.audio.Player;
import com.ustudio.audio.Record;
import com.ustudio.loading.LoadingScreen;
import com.ustudio.main.MainActivity;
import com.ustudio.piano.*;

public class FreePlay extends Scene {

	static int CAMERA_WIDTH;
	static int CAMERA_HEIGHT;
	
	private MiniPiano mMiniPiano;
	
	private Piano mTouchPiano;
	
	private Track mActiveTrack;
	
	private Texture mTexture;
	
	private TextureRegion mBackground;
	private TextureRegion mTransparent;
	private TextureRegion[] mButton_N;
	private TextureRegion[] mButton_P;
	private TextureRegion mSingleStep;
	private TextureRegion mWholeStep;
	
	private Entity mToolBar;
	private Entity mWholeStepFW;
	private Entity mWholeStepBW;
	private Entity mStepFW;
	private Entity mStepBW;
	
	private float ButtonWidth;
	private float ButtonHeight;
	private float BGHeight;
	private float BGY;
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
	
	private static FreePlay mInstance;
	
	public FreePlay(int w, int h) {
		CAMERA_WIDTH = w;
		CAMERA_HEIGHT = h;
	
		loadActiveTrack();
		loadSizes();
		loadGUITextures();
		drawBG();
		drawToolBar();
		drawPiano();
		drawMiniPiano();
		drawSteps();
		FreePlay.setInstance(this);
	}

	private void loadActiveTrack()
	{
		Project tmpProject;
		tmpProject=MainActivity.getInstance().getProject();
		this.mActiveTrack=tmpProject.findActiveTrack();
	}
	
	private void loadSizes()
	{
		this.BGHeight=CAMERA_WIDTH/2.5f;
		this.BGY=CAMERA_WIDTH-this.BGHeight;
		this.ButtonWidth=CAMERA_HEIGHT/7.6f;
		this.ButtonHeight=CAMERA_WIDTH/6.9f;
		this.ToolbarY=CAMERA_WIDTH-this.ButtonHeight-(CAMERA_WIDTH/24);
		this.ToolbarX=(CAMERA_HEIGHT-this.ButtonWidth*6)/2;
		this.PianoX=0;
		this.StepWidth=CAMERA_HEIGHT/50;
		this.StepHeight=CAMERA_WIDTH/15;
	}
	
	private void loadGUITextures()
	{
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/Backgrounds/");
		this.mTexture = new BitmapTextureAtlas(1024, 2048, TextureOptions.BILINEAR);
		
		this.mTransparent = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture, MainActivity.getInstance().getApplicationContext(), "1024x2048.png", 0, 0);
		this.mBackground = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture, MainActivity.getInstance().getApplicationContext(), "freeplay_bg.png", 0, 0);
		
		this.mButton_N=new TextureRegion[6];
		this.mButton_P=new TextureRegion[6];
		
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/Buttons/");
		
		for(byte i=0;i<6;i++)
		{
			this.mButton_N[i] = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture, MainActivity.getInstance().getApplicationContext(), i+"_released.png",i*139,1600);
			this.mButton_P[i] = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture, MainActivity.getInstance().getApplicationContext(), +i+"_pressed.png",i*139,1811);
		}
		
		this.mSingleStep = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture, MainActivity.getInstance().getApplicationContext(), "single_step.png", 835,1600);
		this.mWholeStep = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture, MainActivity.getInstance().getApplicationContext(), "whole_step.png", 835, 1632);
		
		MainActivity.getInstance().getEngine().getTextureManager().loadTexture(this.mTexture);
	}
	
	private void drawBG()
	{
		
		Sprite bg_sprite = new Sprite(this.BGY,0,this.mBackground);
		bg_sprite.setWidth(this.BGHeight);
		bg_sprite.setHeight(CAMERA_HEIGHT);
		this.attachChild(bg_sprite);
	}
	
	private void drawToolBar()
	{

		this.mToolBar=new Entity();
		for(byte i=0;i<6;i++)
		{
			Sprite sp_pressed = new Sprite(0,i*this.ButtonWidth,this.mButton_P[i]);
			sp_pressed.setWidth(this.ButtonHeight);
			sp_pressed.setHeight(this.ButtonWidth);
			
			this.mToolBar.attachChild(sp_pressed);
			
			Sprite sp_released = new Sprite(0,i*this.ButtonWidth,this.mButton_N[i])
			{
	            public boolean onAreaTouched(final TouchEvent pAreaTouchEvent, final float pX, final float pY) {
	            	switch(pAreaTouchEvent.getAction()) {
					    case TouchEvent.ACTION_DOWN:                    
					        FreePlay.this.ToolbarAction(Byte.parseByte(this.getUserData().toString()), true);
					        break;
					    case TouchEvent.ACTION_UP:  
					    	FreePlay.this.ToolbarAction(Byte.parseByte(this.getUserData().toString()), false);
					        break;
					}
					return true;
	            }
	        };
	        sp_released.setUserData((i*2)+1);
	        sp_released.setWidth(this.ButtonHeight);
	        sp_released.setHeight(this.ButtonWidth);
			
			this.mToolBar.attachChild(sp_released);
			
			this.registerTouchArea(sp_released);
			this.setTouchAreaBindingEnabled(true);
		}
		
		this.mToolBar.setPosition(this.ToolbarY,this.ToolbarX);
		
		this.attachChild(this.mToolBar);
	}
	
	private void drawSteps()
	{
		float rotationX;
		float rotationY;
		
		this.WholeStepFwX=this.mMiniPiano.getKeyboardWidth()+this.mMiniPiano.getY()+(CAMERA_HEIGHT/20);
		this.WholeStepFwY=this.mMiniPiano.getX()+(this.mMiniPiano.getKeyboardHeight()-this.StepHeight)/2.0f;
		
		this.WholeStepBwX=this.mMiniPiano.getY()-(CAMERA_HEIGHT/45)-(CAMERA_HEIGHT/20);
		this.WholeStepBwY=this.WholeStepFwY;
		this.StepFwY=this.WholeStepFwY;
		this.StepFwX=this.mMiniPiano.getKeyboardWidth()+this.mMiniPiano.getY()+(CAMERA_HEIGHT/55);
		this.StepBwY=this.WholeStepFwY;
		this.StepBwX=this.mMiniPiano.getY()-(CAMERA_HEIGHT/48)-(CAMERA_HEIGHT/55);
		
		this.mWholeStepFW=new Entity();
		this.mWholeStepBW=new Entity();
		this.mStepFW=new Entity();
		this.mStepBW=new Entity();
		
		Sprite[] stepFW_sprite=new Sprite[2];
		Sprite[] stepBW_sprite=new Sprite[2];
		
		rotationX=this.StepWidth/2;
		rotationY=this.StepHeight/2;
		
		//Single step forward
		stepFW_sprite[0] = new Sprite(0,0,this.mSingleStep){
			public boolean onAreaTouched(final TouchEvent pAreaTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				byte selTone;
				switch(pAreaTouchEvent.getAction()) 
				{
                    case TouchEvent.ACTION_DOWN:
                    	selTone=FreePlay.this.getActiveTrack().getFirstTone();
                    	selTone++;
                    	FreePlay.this.getMiniPiano().moveViewer(selTone);
                    	break;
				}
                return true;
            }
		};
		stepFW_sprite[0].setWidth(this.StepHeight);
		stepFW_sprite[0].setHeight(this.StepWidth);
		stepFW_sprite[0].setAlpha(0.5f);
		
		//Single step backward
		stepBW_sprite[0] = new Sprite(0,0,this.mSingleStep){
			public boolean onAreaTouched(final TouchEvent pAreaTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				byte selTone;
				switch(pAreaTouchEvent.getAction()) 
				{
                    case TouchEvent.ACTION_DOWN:
                    	selTone=FreePlay.this.getActiveTrack().getFirstTone();
                    	selTone--;
                    	FreePlay.this.getMiniPiano().moveViewer(selTone);
                    	break;
				}
                return true;
            }
		};
		stepBW_sprite[0].setWidth(this.StepHeight);
		stepBW_sprite[0].setHeight(this.StepWidth);
		stepBW_sprite[0].setAlpha(0.5f);
		stepBW_sprite[0].setRotationCenter(rotationY,rotationX);
		stepBW_sprite[0].setRotation(180);
		
		//Whole step forward
		stepFW_sprite[1] = new Sprite(0,0,this.mWholeStep){
			public boolean onAreaTouched(final TouchEvent pAreaTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				byte selTone;
				switch(pAreaTouchEvent.getAction()) 
				{
                    case TouchEvent.ACTION_DOWN:
                    	selTone=FreePlay.this.getActiveTrack().getFirstTone();
                    	selTone+=FreePlay.this.getActiveTrack().getTonesVisible();
                    	FreePlay.this.getMiniPiano().moveViewer(selTone);
                    	break;
				}
                return true;
            }
		};
		stepFW_sprite[1].setHeight(this.StepWidth*2);
		stepFW_sprite[1].setWidth(this.StepHeight);
		stepFW_sprite[1].setAlpha(0.5f);
		
		//Whole step backward
		stepBW_sprite[1] = new Sprite(0,0,this.mWholeStep){
			public boolean onAreaTouched(final TouchEvent pAreaTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				byte selTone;
				switch(pAreaTouchEvent.getAction()) 
				{
                    case TouchEvent.ACTION_DOWN:
                    	selTone=FreePlay.this.getActiveTrack().getFirstTone();
                    	selTone-=FreePlay.this.getActiveTrack().getTonesVisible();
                    	FreePlay.this.getMiniPiano().moveViewer(selTone);
                    	break;
				}
                return true;
            }
		};
		stepBW_sprite[1].setHeight(this.StepWidth*2);
		stepBW_sprite[1].setWidth(this.StepHeight);
		stepBW_sprite[1].setAlpha(0.5f);
		stepBW_sprite[1].setRotationCenter(rotationY,rotationX);
		stepBW_sprite[1].setRotation(180);

		this.mStepFW.attachChild(stepFW_sprite[0]);
		this.mWholeStepFW.attachChild(stepFW_sprite[1]);	
		this.mStepBW.attachChild(stepBW_sprite[0]);
		this.mWholeStepBW.attachChild(stepBW_sprite[1]);
		
		this.mWholeStepFW.setPosition(this.WholeStepFwY,this.WholeStepFwX);
		this.mWholeStepBW.setPosition(this.WholeStepBwY,this.WholeStepBwX);
		this.mStepFW.setPosition(this.StepFwY,this.StepFwX);
		this.mStepBW.setPosition(this.StepBwY,this.StepBwX);
		
		this.attachChild(this.mStepBW);
		this.attachChild(this.mStepFW);
		this.attachChild(this.mWholeStepBW);
		this.attachChild(this.mWholeStepFW);
		
		this.registerTouchArea(stepFW_sprite[0]);
		this.registerTouchArea(stepFW_sprite[1]);
		this.registerTouchArea(stepBW_sprite[0]);
		this.registerTouchArea(stepBW_sprite[1]);		
	}
	
	private void drawMiniPiano()
	{		
		
		this.mMiniPiano = new MiniPiano(this, CAMERA_WIDTH, CAMERA_HEIGHT, this.mActiveTrack, this.mTouchPiano);
		this.MiniPianoX=(CAMERA_HEIGHT-this.mMiniPiano.getKeyboardWidth())/2;
		this.MiniPianoY=CAMERA_WIDTH-this.mMiniPiano.getKeyboardHeight()-(CAMERA_WIDTH/4.4f);
		this.mMiniPiano.setPosition(this.MiniPianoY, this.MiniPianoX);
		this.mMiniPiano.moveViewer(this.mActiveTrack.getFirstTone());
		this.attachChild(this.mMiniPiano);	
		this.mTouchPiano.setMiniPiano(this.mMiniPiano);
	}
	
	private void drawPiano()
	{
		this.mTouchPiano = new Piano(this, CAMERA_WIDTH, CAMERA_HEIGHT, this.mActiveTrack);
		this.PianoY=this.mTouchPiano.getKeyboardHeight();
		this.mTouchPiano.setPosition(this.PianoY, this.PianoX);
		this.mTouchPiano.moveViewer(this.mActiveTrack.getFirstTone());
		this.attachChild(this.mTouchPiano);
	}
	
	public void ToolbarAction(byte selindex, boolean pressed)
	{
		switch(selindex)
		{
			//Menu
			case 1:
				if(pressed)
				{
					this.mToolBar.getChild(selindex).setVisible(false);
				}
				else
				{
					this.mToolBar.getChild(selindex).setVisible(true);
				}
				break;
			//Play
			case 3:
				if(pressed)
				{
					this.mToolBar.getChild(selindex).setVisible(false);
					if(Record.getIsRecording())
					{
						Record.startRecTimer();
					}
					if(Player.isPaused())
					{
						this.mToolBar.getChild(5).setAlpha(1.0f);
						Player.UnPause();
					}
					else
					{
						if(this.mToolBar.getChild(5).getAlpha()==0.0f)
						{
							this.mToolBar.getChild(5).setAlpha(1.0f);
						}
						Player.PlayProject(MainActivity.getInstance().getProject(), this.mTouchPiano);
					}
				}
				else
				{
					this.mToolBar.getChild(selindex).setVisible(true);
				}
				break;
			//Pause
			case 5:
				if(pressed)
				{
					if(this.mToolBar.getChild(selindex).getAlpha()==0.0f)
					{
						this.mToolBar.getChild(selindex).setAlpha(1.0f);
						Player.UnPause();
					}
					else
					{
						this.mToolBar.getChild(selindex).setAlpha(0.0f);
						Player.Pause();
						if(Record.getIsRecording())
						{
							this.mToolBar.getChild(9).setAlpha(1.0f);
							Record.stopRecTimer();
						}
					}
				}
				break;
			//Stop
			case 7:
				if(pressed)
				{
					this.mToolBar.getChild(selindex).setVisible(false);
					if(Record.getIsRecording())
					{
						this.mToolBar.getChild(9).setAlpha(1.0f);
						Record.stopRecTimer();
					}
					if(!Player.isPaused())
					{
						Player.Stop();
					}
					else
					{
						this.mToolBar.getChild(5).setAlpha(1.0f);
						Player.UnPause();
						Player.Stop();
					}
				}
				else
				{
					this.mToolBar.getChild(selindex).setVisible(true);
				}
				break;
			//Rec
			case 9:
				if(pressed)
				{
					if(this.mToolBar.getChild(selindex).getAlpha()==0.0f)
					{
						this.mToolBar.getChild(selindex).setAlpha(1.0f);
						Record.stopRecTimer();
					}
					else
					{
						this.mToolBar.getChild(selindex).setAlpha(0.0f);
						Record.setIsRecording(true);
					}
				}
				break;
			//Back
			case 11:
				if(pressed)
				{
					this.mToolBar.getChild(selindex).setVisible(false);
				}
				else
				{
					this.mToolBar.getChild(selindex).setVisible(true);
				}
				break;
		}
	}

	//SET
	
	public static void setInstance(FreePlay mInstance) {
		FreePlay.mInstance = mInstance;
	}
	
	public void setActiveTrack(Track t)
	{
		this.mActiveTrack=t;
	}
	
	public void setPiano(Piano p)
	{
		this.mTouchPiano=p;
	}
	
	public void setMiniPiano(MiniPiano p)
	{
		this.mMiniPiano=p;
	}

	//GET
	
	public Track getActiveTrack()
	{
		return this.mActiveTrack;
	}

	public Piano getPiano()
	{
		return this.mTouchPiano;
	}
	
	public MiniPiano getMiniPiano()
	{
		return this.mMiniPiano;
	}
	
	public static FreePlay getInstance() {
		return mInstance;
	}
}
