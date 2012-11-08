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

import com.ustudio.audio.Instrument;
import com.ustudio.loading.LoadingScreen;
import com.ustudio.main.MainActivity;
import com.ustudio.managers.SamplesManager;
import com.ustudio.project.Project;
import com.ustudio.project.Track;

public class RecordScene extends Scene {

	static int CAMERA_WIDTH;
	static int CAMERA_HEIGHT;

	private Texture	mTexture;

	private LoadingScreen mLoadingScreen;
	
	private TextureRegion mBackground;
	private TextureRegion mTransparent;
	
	private TextureRegion[] mButton_N;
	private TextureRegion[] mButton_P;
	
	private Entity mToolBar;
	
	private float btnMenuButtonsHeight;
	private float btnMenuButtonsWidth;

	private float ToolbarY;
	private float ToolbarX;
	
	private boolean mLoading;

	
	public RecordScene(int w ,int h) {

		CAMERA_WIDTH = w;
		CAMERA_HEIGHT = h;

		loadSizes();
		loadGUITextures();
		drawBG();
		drawToolBar();
	}
	
	private void createProject()
	{
		SamplesManager tmpSamplesManager;
		String tmpLoadmsg="Loading sample #loaded of #total...";
		
		this.mLoading=true;
		this.mLoadingScreen=new LoadingScreen(this,CAMERA_WIDTH,CAMERA_HEIGHT);
		
		tmpSamplesManager=MainActivity.getInstance().getSamplesManager();
		tmpSamplesManager.loadSamples("piano", (byte)60,(byte)73, (byte)1, this.mLoadingScreen, tmpLoadmsg);
		
		MainActivity.getInstance().setSamplesManager(tmpSamplesManager);
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
	}	
	
	//PUBLIC
	public void ToolbarAction(byte selindex, boolean pressed)
	{
		switch(selindex)
		{
			case 0:
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



