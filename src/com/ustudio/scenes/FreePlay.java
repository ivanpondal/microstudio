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
	
	private Instrument mInsPiano;
	
	private Project mProject;
	
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
		
		createProject();
		loadSizes();
		loadGUITextures();
		drawBG();
		drawToolBar();
		drawPiano();
		drawMiniPiano();
		drawSteps();
	}
	
	private void createProject()
	{
		Track tmpTrack;
		this.mInsPiano=new Instrument("Piano",(byte)1,400,(byte)60,(byte)60);
		tmpTrack=new Track(this.mInsPiano.getName(),this.mInsPiano);
		this.mProject=new Project("New Project",tmpTrack);
		this.mActiveTrack=this.mProject.getTracks().get("Piano");
	}
	
	private void loadSizes()
	{
		this.BGHeight=CAMERA_HEIGHT/2.5f;
		this.ButtonWidth=CAMERA_WIDTH/7.6f;
		this.ButtonHeight=CAMERA_HEIGHT/6.9f;
		this.ToolbarY=CAMERA_HEIGHT/24;
		this.ToolbarX=(CAMERA_WIDTH-this.ButtonWidth*6)/2;
		this.PianoX=0;
		this.MiniPianoY=CAMERA_HEIGHT/4.4f;
		this.StepWidth=CAMERA_WIDTH/50;
		this.StepHeight=CAMERA_HEIGHT/15;
	}
	
	private void loadGUITextures()
	{
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/Backgrounds/");
		this.mTexture = new BitmapTextureAtlas(2048, 1024, TextureOptions.BILINEAR);
		
		this.mTransparent = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture, MainActivity.getInstance().getApplicationContext(), "2048x1024.png", 0, 0);
		this.mBackground = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture, MainActivity.getInstance().getApplicationContext(), "freeplay_bg.png", 0, 0);
		
		this.mButton_N=new TextureRegion[6];
		this.mButton_P=new TextureRegion[6];
		
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/Buttons/");
		
		for(byte i=0;i<6;i++)
		{
			this.mButton_N[i] = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture, MainActivity.getInstance().getApplicationContext(), i+"_released.png", 1600, i*139);
			this.mButton_P[i] = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture, MainActivity.getInstance().getApplicationContext(), +i+"_pressed.png", 1811, i*139);
		}
		
		this.mSingleStep = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture, MainActivity.getInstance().getApplicationContext(), "single_step.png", 1600, 835);
		this.mWholeStep = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture, MainActivity.getInstance().getApplicationContext(), "whole_step.png",1632, 835);
		
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
	        sp_released.setWidth(this.ButtonWidth);
	        sp_released.setHeight(this.ButtonHeight);
			
			this.mToolBar.attachChild(sp_released);
			
			this.registerTouchArea(sp_released);
			this.setTouchAreaBindingEnabled(true);
		}
		
		this.mToolBar.setPosition(this.ToolbarX,this.ToolbarY);
		
		this.attachChild(this.mToolBar);
	}
	
	private void drawSteps()
	{
		float rotationX;
		float rotationY;
		
		this.WholeStepFwX=this.mMiniPiano.getKeyboardWidth()+this.mMiniPiano.getX()+(CAMERA_WIDTH/20);
		this.WholeStepFwY=this.mMiniPiano.getY()+(this.mMiniPiano.getKeyboardHeight()-this.StepHeight)/2.0f;
		this.WholeStepBwX=this.mMiniPiano.getX()-(CAMERA_WIDTH/45)-(CAMERA_WIDTH/20);
		this.WholeStepBwY=this.WholeStepFwY;
		this.StepFwY=this.WholeStepFwY;
		this.StepFwX=this.mMiniPiano.getKeyboardWidth()+this.mMiniPiano.getX()+(CAMERA_WIDTH/55);
		this.StepBwY=this.WholeStepFwY;
		this.StepBwX=this.mMiniPiano.getX()-(CAMERA_WIDTH/48)-(CAMERA_WIDTH/55);
		
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
		stepFW_sprite[0].setWidth(this.StepWidth);
		stepFW_sprite[0].setHeight(this.StepHeight);
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
		stepBW_sprite[0].setWidth(this.StepWidth);
		stepBW_sprite[0].setHeight(this.StepHeight);
		stepBW_sprite[0].setAlpha(0.5f);
		stepBW_sprite[0].setRotationCenter(rotationX,rotationY);
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
		stepFW_sprite[1].setWidth(this.StepWidth*2);
		stepFW_sprite[1].setHeight(this.StepHeight);
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
		stepBW_sprite[1].setWidth(this.StepWidth*2);
		stepBW_sprite[1].setHeight(this.StepHeight);
		stepBW_sprite[1].setAlpha(0.5f);
		stepBW_sprite[1].setRotationCenter(rotationX,rotationY);
		stepBW_sprite[1].setRotation(180);

		this.mStepFW.attachChild(stepFW_sprite[0]);
		this.mWholeStepFW.attachChild(stepFW_sprite[1]);	
		this.mStepBW.attachChild(stepBW_sprite[0]);
		this.mWholeStepBW.attachChild(stepBW_sprite[1]);
		
		this.mWholeStepFW.setPosition(this.WholeStepFwX,this.WholeStepFwY);
		this.mWholeStepBW.setPosition(this.WholeStepBwX,this.WholeStepBwY);
		this.mStepFW.setPosition(this.StepFwX,this.StepFwY);
		this.mStepBW.setPosition(this.StepBwX,this.StepBwY);
		
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
		this.MiniPianoX=(CAMERA_WIDTH-this.mMiniPiano.getKeyboardWidth())/2;
		this.mMiniPiano.setPosition(this.MiniPianoX, this.MiniPianoY);
		this.mMiniPiano.moveViewer(this.mActiveTrack.getFirstTone());
		this.attachChild(this.mMiniPiano);	
		this.mTouchPiano.setMiniPiano(this.mMiniPiano);
	}
	
	private void drawPiano()
	{
		this.mTouchPiano = new Piano(this, CAMERA_WIDTH, CAMERA_HEIGHT, this.mActiveTrack);
		this.PianoY=CAMERA_HEIGHT-this.mTouchPiano.getKeyboardHeight();
		this.mTouchPiano.setPosition(this.PianoX, this.PianoY);
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
						Player.PlayProject(this.mProject, this.mTouchPiano);
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
}
