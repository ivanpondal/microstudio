package com.ustudio.editor;

import org.anddev.andengine.entity.Entity;
import org.anddev.andengine.entity.IEntity;
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
				if(pTouchEvent.getAction() == MotionEvent.ACTION_DOWN){
		                mTouchX = pTouchEvent.getMotionEvent().getX();
		                mTouchY = pTouchEvent.getMotionEvent().getY();
		        }
				else if(pTouchEvent.getAction() == MotionEvent.ACTION_MOVE) {
		                float newX = pTouchEvent.getMotionEvent().getX();
		                float newY = pTouchEvent.getMotionEvent().getY();
		               
		                mTouchOffsetX = (newX - mTouchX);
		                mTouchOffsetY = (newY - mTouchY);
		               
		                float newScrollX = all.getX() + mTouchOffsetX*0.5f;
		                float newScrollY = all.getY() + mTouchOffsetY*2.0f;
		               
		                all.setPosition(newScrollX, newScrollY);
		               
		                mTouchX = newX;
		                mTouchY = newY;
		        }
				if (pTouchEvent.getAction() == MotionEvent.ACTION_UP){
					// esto era para el momentum mas tarde sera
					
				}
				return true;
			}
		};
		scroll.setWidth(1000);
		scroll.setHeight(1000);
		
		all.attachChild(scroll);
		pScene.registerTouchArea(scroll);
		
		// Esto vvv es solo para probar
		addNote(1, 2, 43, 200, 1);
		addNote(2, 4, 20, 100, 0.5f);
		addNote(3, 5, 50, 50, 0.4f);
		addNote(4, 1, 100, 300, 0.8f);
		
		setNoteLenght(3, 1000);// Cambiar la longitud de la Nota (duracion)
		
		this.attachChild(all); //No tocar.
	}
	
	public void addNote(int id, int note, float tempo, float lenght, float volume){
		Key k = new Key(id, note, tempo, lenght, volume);
		all.attachChild(k, id);
	}
	public void setNoteLenght(int i, float l){
		Key k = (Key) all.getChild(i);
		k.setLenght(l);
	}
	
}
