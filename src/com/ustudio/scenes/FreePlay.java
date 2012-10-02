package com.ustudio.scenes;


import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TextureRegion;

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
	
	public FreePlay(int w, int h) {
		CAMERA_WIDTH = w;
		CAMERA_HEIGHT = h;
		DrawGUI();
		DrawPiano();
	}
	
	private void DrawGUI()
	{
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/Piano/");
		this.mTexture = new BitmapTextureAtlas(2048, 512, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mBackground = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture, MainActivity.getInstance().getApplicationContext(), "Background/bg.png", 0, 0);
		MainActivity.getInstance().getEngine().getTextureManager().loadTexture(this.mTexture);
		Sprite bg_sprite = new Sprite(0,0,this.mBackground);
		bg_sprite.setWidth(CAMERA_WIDTH);
		bg_sprite.setHeight(CAMERA_HEIGHT/2.5f);
		this.attachChild(bg_sprite);
	}
	
	private void DrawPiano()
	{
		this.mInsPiano=new Instrument("Piano",(byte)1,400,(byte)60,(byte)73);
		this.mTouchPiano = new Piano(this, CAMERA_WIDTH, CAMERA_HEIGHT, this.mInsPiano);
		this.attachChild(this.mTouchPiano);
		this.mTouchPiano.setPosition((CAMERA_WIDTH-CAMERA_WIDTH/8)*-3, 0.0f);//hago que empieze desde el middle C
	}
}
