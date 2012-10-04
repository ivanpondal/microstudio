package com.ustudio.scenes;

import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TextureRegion;

import com.ustudio.main.MainActivity;

public class MenuScene extends Scene {
	
	static int CAMERA_WIDTH;
	static int CAMERA_HEIGHT;
	
	private Texture	mTexture;
	private TextureRegion mBackground;
	
	public MenuScene(int w, int h) {
		CAMERA_WIDTH = w;
		CAMERA_HEIGHT = h;
		
		loadSizes();
		loadGUITextures();
		drawBG();
	}
	
	private void loadGUITextures()
	{
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/Backgrounds/");
		this.mTexture = new BitmapTextureAtlas(2048, 1024, TextureOptions.BILINEAR);
		
		this.mBackground = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture, MainActivity.getInstance().getApplicationContext(), "menu_bg.png", 0, 0);
		
		MainActivity.getInstance().getEngine().getTextureManager().loadTexture(this.mTexture);
	}
	
	private void drawBG()
	{
		Sprite bg_sprite = new Sprite(0,0,this.mBackground);
		bg_sprite.setWidth(CAMERA_WIDTH);
		bg_sprite.setHeight(CAMERA_HEIGHT);
		this.attachChild(bg_sprite);
	}
	
	private void loadSizes()
	{
		
	}	
}
