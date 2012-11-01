package com.ustudio.scenes;

import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TextureRegion;

import com.ustudio.loading.LoadingScreen;
import com.ustudio.main.MainActivity;

public class RecordScene extends Scene {

	static int CAMERA_WIDTH;
	static int CAMERA_HEIGHT;

	private Texture	mTexture;

	private LoadingScreen mLoadingScreen;
	
	private TextureRegion mBackground;
	private TextureRegion mTransparent;
	private TextureRegion mButtonMenuR;
	private TextureRegion mButtonMenuP;
	private TextureRegion mButtonPlayR;
	private TextureRegion mButtonPlayP;
	private TextureRegion mButtonPauseR;
	private TextureRegion mButtonPauseP;
	private TextureRegion mButtonStopR;
	private TextureRegion mButtonStopP;
	
	private float btnMenuButtonsHeight;
	private float btnMenuButtonsWidth;
	private float btnMenuButtonsY;
	private float btnMenuX;
	private float btnPlayX;
	private float btnPauseX;
	private float btnStopX;

	private boolean mLoading;

	
	public RecordScene(int w ,int h) {

		CAMERA_WIDTH = w;
		CAMERA_HEIGHT = h;

		loadSizes();
		loadGUITextures();
		drawBG();
		drawMenuButtons();
		tmpLoader();
	}
	
	private void tmpLoader()
	{
		this.mLoading=true;
		this.mLoadingScreen=new LoadingScreen(this,CAMERA_WIDTH,CAMERA_HEIGHT,"Loading sample 1 of 100...",10);
	}
	
	private void loadGUITextures()
	{
		this.mTexture = new BitmapTextureAtlas(2048, 2048, TextureOptions.BILINEAR);
		
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/Backgrounds/");
		
		this.mTransparent = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture, MainActivity.getInstance().getApplicationContext(), "1024x2048.png", 0, 0);
		this.mBackground = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture, MainActivity.getInstance().getApplicationContext(), "tracks_bg.png", 0, 0);
				
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/Buttons/");

		this.mButtonMenuR = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture, MainActivity.getInstance().getApplicationContext(), "menu_relesed.png", 960, 0);
		this.mButtonMenuP = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture, MainActivity.getInstance().getApplicationContext(), "menu_pressed.png", 960, 145);
		this.mButtonPlayR = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture, MainActivity.getInstance().getApplicationContext(), "play_relesed.png", 960, 290);
		this.mButtonPlayP = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture, MainActivity.getInstance().getApplicationContext(), "play_pressed.png", 960, 435);
		this.mButtonPauseR = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture, MainActivity.getInstance().getApplicationContext(), "pause_relesed.png", 960, 580);
		this.mButtonPauseP = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture, MainActivity.getInstance().getApplicationContext(), "pause_pressed.png", 960, 725);
		this.mButtonStopR = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture, MainActivity.getInstance().getApplicationContext(), "stop_relesed.png", 960, 870);
		this.mButtonStopP = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture, MainActivity.getInstance().getApplicationContext(), "stop_pressed.png", 960, 1015);
		
		MainActivity.getInstance().getEngine().getTextureManager().loadTexture(this.mTexture);
	}
	
	private void drawBG()
	{
		Sprite bg_sprite = new Sprite(0,0,this.mBackground);
		bg_sprite.setWidth(CAMERA_WIDTH);
		bg_sprite.setHeight(CAMERA_HEIGHT);
		this.attachChild(bg_sprite);
	}

	private void drawMenuButtons()
	{
		Sprite menup_sprite = new Sprite(0,0,this.mButtonMenuP);
		menup_sprite.setWidth(this.btnMenuButtonsWidth);
		menup_sprite.setHeight(this.btnMenuButtonsHeight);
		
		menup_sprite.setPosition(this.btnMenuX,this.btnMenuButtonsY);
		
		this.attachChild(menup_sprite);
		
		Sprite menur_sprite = new Sprite(0,0,this.mButtonMenuR)
		{
            public boolean onAreaTouched(final TouchEvent pAreaTouchEvent, final float pX, final float pY) {
            	if(!RecordScene.this.getLoading())
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
        
        menur_sprite.setWidth(this.btnMenuButtonsWidth);
        menur_sprite.setHeight(this.btnMenuButtonsHeight);
		
        menur_sprite.setPosition(this.btnMenuX,this.btnMenuButtonsY);
		
		this.attachChild(menur_sprite);
		
		this.registerTouchArea(menur_sprite);
		
		//-----------------------------------------------------------------------
		
		Sprite playp_sprite = new Sprite(0,0,this.mButtonPlayP);
		playp_sprite.setWidth(this.btnMenuButtonsWidth);
		playp_sprite.setHeight(this.btnMenuButtonsHeight);
		
		playp_sprite.setPosition(this.btnPlayX,this.btnMenuButtonsY);
		
		this.attachChild(playp_sprite);
		
		Sprite playr_sprite = new Sprite(0,0,this.mButtonPlayR)
		{
            public boolean onAreaTouched(final TouchEvent pAreaTouchEvent, final float pX, final float pY) {
            	if(!RecordScene.this.getLoading())
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
        
        playr_sprite.setWidth(this.btnMenuButtonsWidth);
        playr_sprite.setHeight(this.btnMenuButtonsHeight);
		
        playr_sprite.setPosition(this.btnPlayX,this.btnMenuButtonsY);
		
		this.attachChild(playr_sprite);
		
		this.registerTouchArea(playr_sprite);
		
		//-----------------------------------------------------------------------
		
		Sprite pausep_sprite = new Sprite(0,0,this.mButtonPauseP);
		pausep_sprite.setWidth(this.btnMenuButtonsWidth);
		pausep_sprite.setHeight(this.btnMenuButtonsHeight);
		
		pausep_sprite.setPosition(this.btnPauseX,this.btnMenuButtonsY);
		
		this.attachChild(pausep_sprite);
		
		Sprite pauser_sprite = new Sprite(0,0,this.mButtonPauseR)
		{
            public boolean onAreaTouched(final TouchEvent pAreaTouchEvent, final float pX, final float pY) {
            	if(!RecordScene.this.getLoading())
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
        
        pauser_sprite.setWidth(this.btnMenuButtonsWidth);
        pauser_sprite.setHeight(this.btnMenuButtonsHeight);
		
        pauser_sprite.setPosition(this.btnPauseX,this.btnMenuButtonsY);
		
		this.attachChild(pauser_sprite);
		
		this.registerTouchArea(pauser_sprite);
		
		//-----------------------------------------------------------------------
		
		Sprite stopp_sprite = new Sprite(0,0,this.mButtonStopP);
		stopp_sprite.setWidth(this.btnMenuButtonsWidth);
		stopp_sprite.setHeight(this.btnMenuButtonsHeight);
		
		stopp_sprite.setPosition(this.btnStopX,this.btnMenuButtonsY);
		
		this.attachChild(stopp_sprite);
		
		Sprite stopr_sprite = new Sprite(0,0,this.mButtonStopR)
		{
            public boolean onAreaTouched(final TouchEvent pAreaTouchEvent, final float pX, final float pY) {
            	if(!RecordScene.this.getLoading())
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
        
        stopr_sprite.setWidth(this.btnMenuButtonsWidth);
        stopr_sprite.setHeight(this.btnMenuButtonsHeight);
		
        stopr_sprite.setPosition(this.btnStopX,this.btnMenuButtonsY);
		
		this.attachChild(stopr_sprite);
		
		this.registerTouchArea(stopr_sprite);
		
		this.setTouchAreaBindingEnabled(true);
		
		
	}
	
	private void loadSizes()
	{
		this.btnMenuButtonsHeight=CAMERA_HEIGHT/11.03f;
		this.btnMenuButtonsWidth=CAMERA_WIDTH/4.7f;
		this.btnMenuButtonsY=CAMERA_HEIGHT-(this.btnMenuButtonsHeight+(CAMERA_HEIGHT/64.0f));
		
		this.btnMenuX=CAMERA_WIDTH-(this.btnMenuButtonsWidth+(CAMERA_WIDTH/1.39f));
		this.btnPlayX=CAMERA_WIDTH-(this.btnMenuButtonsWidth+(CAMERA_WIDTH/2.00f));
		this.btnPauseX=CAMERA_WIDTH-(this.btnMenuButtonsWidth+(CAMERA_WIDTH/3.55f));
		this.btnStopX=CAMERA_WIDTH-(this.btnMenuButtonsWidth+(CAMERA_WIDTH/16.55f));
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



