package com.ustudio.editor;

import org.anddev.andengine.entity.Entity;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TextureRegion;

import com.ustudio.main.MainActivity;

public class Key extends Entity{
	private int id;
	private int note;
	private float tempo; //se dice tempo??? ni idea.
	private float lenght;
	private float volume;
	private Sprite key;
	
	private Texture mTexture;
	private TextureRegion keyTexture;
	
	public Key(int i, int n, float t, float l, float v){
		id = i;
		note = n;
		tempo = t;
		lenght = l;
		volume = v;
		this.mTexture = new BitmapTextureAtlas(256, 1024, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.keyTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture, MainActivity.getInstance().getApplicationContext(), "gfx/key/Unknown.jpeg", 0, 0);
		MainActivity.getInstance().getEngine().getTextureManager().loadTexture(this.mTexture);
		
		 key = new Sprite(tempo, note*100, keyTexture);
		 key.setAlpha(volume);
		 key.setWidth(lenght);
		 key.setHeight(100);
		 this.attachChild(key);
	}
	public void setLenght(float l){
		lenght = l;
		key.setWidth(lenght);
	}
}
