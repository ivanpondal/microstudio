package com.ustudio.editor;

import org.anddev.andengine.entity.Entity;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TextureRegion;

import android.view.MotionEvent;

import com.ustudio.main.MainActivity;

public class Editor extends Entity{
	
	private float entityWidth;
	private float entityHight;
	
	private Texture mTexture;
	private TextureRegion backTexture;
	private TextureRegion keyTexture;
	private Entity all;
	
	private float mTouchX = 0, mTouchY = 0, mTouchOffsetX = 0, mTouchOffsetY = 0;
	private Sprite scroll;
	
	
	public Editor(float w, float h, Scene pScene){
		entityHight = h;
		entityWidth = w;
		
		this.mTexture = new BitmapTextureAtlas(256, 1024, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.backTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture, MainActivity.getInstance().getApplicationContext(), "gfx/Teclas/Blancas/BN.png", 0, 0);
		this.keyTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture, MainActivity.getInstance().getApplicationContext(), "gfx/Teclas/Negras/NN.png", 99, 0);
		MainActivity.getInstance().getEngine().getTextureManager().loadTexture(this.mTexture);
		
		all = new Entity();
		
		scroll = new Sprite(0 ,0 ,this.backTexture){
			public boolean onAreaTouched(final TouchEvent pTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				if(pTouchEvent.getAction() == MotionEvent.ACTION_DOWN)
		        {
		                mTouchX = pTouchEvent.getMotionEvent().getX();
		                mTouchY = pTouchEvent.getMotionEvent().getY();
		        }
		        else if(pTouchEvent.getAction() == MotionEvent.ACTION_MOVE)
		        {
		                float newX = pTouchEvent.getMotionEvent().getX();
		                float newY = pTouchEvent.getMotionEvent().getY();
		               
		                mTouchOffsetX = (newX - mTouchX);
		                mTouchOffsetY = (newY - mTouchY);
		               
		                float newScrollX = (float) (all.getX() + mTouchOffsetX*0.5);
		                float newScrollY = all.getY() + mTouchOffsetY*2;
		               
		                all.setPosition(newScrollX, newScrollY);
		               
		                mTouchX = newX;
		                mTouchY = newY;
		        }
			
				return true;
			}
		};
		scroll.setWidth(1000);
		scroll.setHeight(1000);
		
		all.attachChild(scroll);
		pScene.registerTouchArea(scroll);
		
		addNote(1,2,43,200,1);
		
		this.attachChild(all); //No tocar.
	}
	
	public void addNote(int id, int note, float tempo, float lenght, float volume){
		Key k = new Key(id, note, tempo, lenght, volume);
		all.attachChild(k);
		
	}
	
}
