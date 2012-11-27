package com.ustudio.scenes;

import java.util.Hashtable;

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

import com.ustudio.audio.Instrument;
import com.ustudio.loading.LoadingScreen;
import com.ustudio.main.MainActivity;
import com.ustudio.managers.SamplesManager;
import com.ustudio.managers.SceneManager;
import com.ustudio.project.IniConstants;
import com.ustudio.project.Project;
import com.ustudio.project.Track;
import com.ustudio.track.TrackGUI;

public class RecordScene extends Scene {

	static int CAMERA_WIDTH;
	static int CAMERA_HEIGHT;

	private Texture	mTexture;

	private LoadingScreen mLoadingScreen;
	
	private TextureRegion mBackground;
	private TextureRegion mTransparent;
	
	private Hashtable<String,TrackGUI> mTrackGUIs;
	
	private TextureRegion[] mButton_N;
	private TextureRegion[] mButton_P;
	
	private Entity mToolBar;
	
	private float btnMenuButtonsHeight;
	private float btnMenuButtonsWidth;

	private float ToolbarY;
	private float ToolbarX;
	
	private float TrackOffsetY;
	private float TrackOffsetX;
	
	private boolean mLoading;

	
	public RecordScene(int w ,int h) {

		CAMERA_WIDTH = w;
		CAMERA_HEIGHT = h;

		createProject();
		loadSizes();
		loadGUITextures();
		drawBG();
		drawToolBar();
		drawTrack();
		loadWindow();
	}

	private void drawTrack()
	{
		mTrackGUIs=new Hashtable<String,TrackGUI>();
		TrackGUI tmpTrack;
		tmpTrack=new TrackGUI(CAMERA_WIDTH, CAMERA_HEIGHT, this);
		tmpTrack.setPosition(this.TrackOffsetX,this.TrackOffsetY);
		mTrackGUIs.put("Piano 1", tmpTrack);
		this.attachChild(mTrackGUIs.get("Piano 1"));
	}
	
	private void createProject()
	{
		Project tmpProject;
		Track tmpTrack;
		Instrument tmpPiano;
		
		tmpPiano=new Instrument("Piano",750);
		tmpTrack=new Track("Piano 1",tmpPiano);
		tmpProject=new Project("Test",tmpTrack);
		tmpProject.setActiveTrack("Piano 1");
		MainActivity.getInstance().setProject(tmpProject);
	}
	
	private void loadWindow()
	{		
		this.mLoadingScreen=new LoadingScreen(this,CAMERA_WIDTH,CAMERA_HEIGHT);
	}
	
	
	private void loadGUITextures()
	{
		this.mTexture = new BitmapTextureAtlas(2048, 2048, TextureOptions.BILINEAR);
		
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/Backgrounds/");
		
		this.mTransparent = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture, MainActivity.getInstance().getApplicationContext(), "1024x2048.png", 0, 0);
		this.mBackground = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture, MainActivity.getInstance().getApplicationContext(), "tracks_bg.png", 0, 0);
				
		this.mButton_N=new TextureRegion[6];
		this.mButton_P=new TextureRegion[6];
		
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/Buttons/");
		
		for(byte i=0;i<4;i++)
		{
			this.mButton_N[i] = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture, MainActivity.getInstance().getApplicationContext(), i+"_m_released.png", 1024, i*147);
			this.mButton_P[i] = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture, MainActivity.getInstance().getApplicationContext(), i+"_m_pressed.png", 1235, i*147);
		}
		
		MainActivity.getInstance().getEngine().getTextureManager().loadTexture(this.mTexture);
	}
	
	private void drawBG()
	{
		Sprite bg_sprite = new Sprite(0,0,this.mBackground);
		bg_sprite.setWidth(CAMERA_WIDTH);
		bg_sprite.setHeight(CAMERA_HEIGHT);
		this.attachChild(bg_sprite);
	}

	private void drawToolBar()
	{

		this.mToolBar=new Entity();
		for(byte i=0;i<4;i++)
		{
			Sprite sp_pressed = new Sprite(i*this.btnMenuButtonsWidth,0,this.mButton_P[i]);
			sp_pressed.setWidth(this.btnMenuButtonsWidth);
			sp_pressed.setHeight(this.btnMenuButtonsHeight);
			
			this.mToolBar.attachChild(sp_pressed);
			
			Sprite sp_released = new Sprite(i*this.btnMenuButtonsWidth,0,this.mButton_N[i])
			{
	            public boolean onAreaTouched(final TouchEvent pAreaTouchEvent, final float pX, final float pY) {
	            	switch(pAreaTouchEvent.getAction()) {
					    case TouchEvent.ACTION_DOWN:                    
					        RecordScene.this.ToolbarAction(Byte.parseByte(this.getUserData().toString()), true);
					        break;
					    case TouchEvent.ACTION_UP:  
					    	RecordScene.this.ToolbarAction(Byte.parseByte(this.getUserData().toString()), false);
					        break;
					}
					return true;
	            }
	        };
	        sp_released.setUserData((i*2)+1);
	        sp_released.setWidth(this.btnMenuButtonsWidth);
	        sp_released.setHeight(this.btnMenuButtonsHeight);
			
			this.mToolBar.attachChild(sp_released);
			
			this.registerTouchArea(sp_released);
			this.setTouchAreaBindingEnabled(true);
		}
		
		this.mToolBar.setPosition(this.ToolbarX,this.ToolbarY);
		
		this.attachChild(this.mToolBar);
	}
	
	private void loadSizes()
	{
		this.btnMenuButtonsHeight=CAMERA_HEIGHT/10.88f;
		this.btnMenuButtonsWidth=CAMERA_WIDTH/4.55f;
		this.ToolbarY=CAMERA_HEIGHT-(CAMERA_HEIGHT/9.35f);
		this.ToolbarX=CAMERA_WIDTH/2-((this.btnMenuButtonsWidth*4)/2);
		this.TrackOffsetY=CAMERA_HEIGHT/(400/13.0f);
		this.TrackOffsetX=CAMERA_WIDTH/(480/29.0f);
	}	
	
	//PUBLIC
	public void loadPiano()
	{
		
		this.mLoading=true;	
		SamplesManager tmpSamplesManager;
		MainActivity tmpMainActivity;
		tmpMainActivity=MainActivity.getInstance();
		String tmpLoadMsg="Loading sample #loaded of #total...";
		String tmpFinishMsg="Finished loading samples!";
		
		this.mLoadingScreen.setValues(tmpLoadMsg, tmpFinishMsg,(byte)0,(byte)((IniConstants.PianoLastMIDI-IniConstants.PianoFirstMIDI)+1));
		this.mLoadingScreen.setLoaderVisible(true);
		
		tmpSamplesManager=MainActivity.getInstance().getSamplesManager();
		tmpSamplesManager.loadSamples("piano", IniConstants.PianoFirstMIDI,IniConstants.PianoLastMIDI, this.mLoadingScreen);
		tmpMainActivity.getProject().getTracks().get("Piano 1").getInstr().setNotes(tmpSamplesManager.getSamples().get("piano"));
		tmpMainActivity.setSamplesManager(tmpSamplesManager);
		tmpMainActivity.getProject().setActiveTrack("Piano 1");
		
		MainActivity.getInstance().getSceneManager().setFreePlay(CAMERA_WIDTH, CAMERA_HEIGHT);
		
		this.mLoading=false;	
		this.mLoadingScreen.setLoaderVisible(false);
	}
	
	public void ToolbarAction(byte selindex, boolean pressed)
	{
		switch(selindex)
		{
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
			case 3:
				if(pressed)
				{
					this.mToolBar.getChild(selindex).setVisible(false);
				}
				else
				{
					this.mToolBar.getChild(selindex).setVisible(true);
				}
				break;
			case 5:
				if(pressed)
				{
					this.mToolBar.getChild(selindex).setVisible(false);
				}
				else
				{
					this.mToolBar.getChild(selindex).setVisible(true);
				}
				break;
			case 7:
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
	public void setLoading(boolean l)
	{
		this.mLoading=l;
	}
	
	//GET
	public boolean getLoading()
	{
		return this.mLoading;
	}
	
}



