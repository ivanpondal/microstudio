package com.ustudio.scenes;

import java.util.Hashtable;

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
import com.ustudio.piano.Key;
import com.ustudio.track.TrackGUI;

public class MenuScene extends Scene {
	
	static int CAMERA_WIDTH;
	static int CAMERA_HEIGHT;
	
	private Texture	mTexture;
	
	private TextureRegion mTransparent;
	private TextureRegion mBackground;
	private TextureRegion mButtonNewN;
	private TextureRegion mButtonNewP;
	
	private float btnNewHeight;
	private float btnNewWidth;
	private float btnNewX;
	private float btnNewY;
	
	public MenuScene(int w, int h) {
		CAMERA_WIDTH = w;
		CAMERA_HEIGHT = h;
		
		loadSizes();
		loadGUITextures();
		drawBG();
		drawNewBtn();
	}
	
	private void loadGUITextures()
	{
		this.mTexture = new BitmapTextureAtlas(1024, 2048, TextureOptions.BILINEAR);
		
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/Backgrounds/");
		this.mTransparent = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture, MainActivity.getInstance().getApplicationContext(), "1024x2048.png", 0, 0);
		this.mBackground = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture, MainActivity.getInstance().getApplicationContext(), "menu_bg.png", 0, 0);
		
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/Buttons/");

		this.mButtonNewN = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture, MainActivity.getInstance().getApplicationContext(), "new_released.png", 0, 1600);
		this.mButtonNewP = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture, MainActivity.getInstance().getApplicationContext(), "new_pressed.png", 0, 1746);
		
		MainActivity.getInstance().getEngine().getTextureManager().loadTexture(this.mTexture);
	}
	
	private void drawBG()
	{
		Sprite bg_sprite = new Sprite(0,0,this.mBackground);
		bg_sprite.setWidth(CAMERA_WIDTH);
		bg_sprite.setHeight(CAMERA_HEIGHT);
		this.attachChild(bg_sprite);
	}
	
	private void drawNewBtn()
	{
		Sprite newp_sprite = new Sprite(0,0,this.mButtonNewP);
		newp_sprite.setWidth(this.btnNewWidth);
		newp_sprite.setHeight(this.btnNewHeight);
		
		newp_sprite.setPosition(this.btnNewX,this.btnNewY);
		
		this.attachChild(newp_sprite);
		
		Sprite newn_sprite = new Sprite(0,0,this.mButtonNewN)
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
        
		newn_sprite.setWidth(this.btnNewWidth);
		newn_sprite.setHeight(this.btnNewHeight);
		
		newn_sprite.setPosition(this.btnNewX,this.btnNewY);
		
		this.attachChild(newn_sprite);
		
		this.registerTouchArea(newn_sprite);
	}
	
	private void loadSizes()
	{
		this.btnNewHeight=CAMERA_HEIGHT/10.56f;
		this.btnNewWidth=CAMERA_WIDTH/2.22f;
		this.btnNewX=CAMERA_WIDTH-(this.btnNewWidth+(CAMERA_WIDTH/45.71f));
		this.btnNewY=CAMERA_HEIGHT-(this.btnNewHeight+(CAMERA_HEIGHT/55.5f));
	}	
}
