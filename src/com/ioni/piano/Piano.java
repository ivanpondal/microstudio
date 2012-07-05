package com.ioni.piano;


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
import android.view.MotionEvent;

import com.main.MainActivity;

public class Piano extends Entity {
	
	
	static int CAMERA_WIDTH;
	static int CAMERA_HEIGHT;
	
	private Texture mTexture;
	private TextureRegion mFaceTextureRegion;
	private TextureRegion mFaceTextureRegion2;
	private TextureRegion mFaceTextureRegion3;
	private TextureRegion mFaceTextureRegion4;
	
	
	public Piano(Scene pScine, int w, int h) {
		
		CAMERA_WIDTH = w;
		CAMERA_HEIGHT = h;
		
		//this.mMainActivity = pMainActivity;
		
		
		this.mTexture = new BitmapTextureAtlas(256, 1024, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mFaceTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture, MainActivity.getInstance().getApplicationContext(), "gfx/Teclas/Blancas/NB.png", 0, 0);
		this.mFaceTextureRegion2 = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture, MainActivity.getInstance().getApplicationContext(), "gfx/Teclas/Negras/NN.png", 99, 0);
		this.mFaceTextureRegion3 = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture, MainActivity.getInstance().getApplicationContext(), "gfx/Teclas/Blancas/PB.png", 0,500);
		this.mFaceTextureRegion4 = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture, MainActivity.getInstance().getApplicationContext(), "gfx/Teclas/Negras/NP.png", 99,500);
		MainActivity.getInstance().getEngine().getTextureManager().loadTexture(this.mTexture);
		
		
		Entity negras = new Entity();
	
		for (int i = 0; i < 8; i++){
			if (i != 2 && i != 6){
				Sprite np = new Sprite(CAMERA_WIDTH/16 + CAMERA_WIDTH/32 + i*(CAMERA_WIDTH/16 + CAMERA_WIDTH/16),CAMERA_HEIGHT*0.2f,this.mFaceTextureRegion4);
				np.setWidth(CAMERA_WIDTH/16);
		        np.setHeight(CAMERA_HEIGHT*0.5f);
				negras.attachChild(np);
				Sprite n = new Sprite(CAMERA_WIDTH/16 + CAMERA_WIDTH/32 + i*(CAMERA_WIDTH/16 + CAMERA_WIDTH/16),CAMERA_HEIGHT*0.2f,this.mFaceTextureRegion2){
					@Override
					public boolean onAreaTouched(final TouchEvent pAreaTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
		                switch(pAreaTouchEvent.getAction()) {
	                    	
		                    case TouchEvent.ACTION_DOWN:                    
		                        this.setAlpha(0.0f);
		                        
		                        keyPressed(this);
		                        break;
		                        
		                    case TouchEvent.ACTION_UP:  
		                        this.setAlpha(1.0f);
		                        break;
		                        
		                    case TouchEvent.ACTION_MOVE:
	                    		if (pTouchAreaLocalX>(CAMERA_WIDTH/16)-(CAMERA_WIDTH/32) || pTouchAreaLocalX<(CAMERA_WIDTH/32)){
	                    			this.setAlpha(1.0f);
	                    			Log.d("MOVE", "" + pTouchAreaLocalY);
	                    		}else{
	                    			this.setAlpha(0.0f);
	                    		}
		                    	break;
		                    	
		                    case TouchEvent.ACTION_OUTSIDE:
		                    	Log.d("IONI","funciopno?");
		                    	break;
		                    	
		                   	}
		                
		                return true;
		            }
				};
				
				n.setUserData(i+10);
				n.setWidth(CAMERA_WIDTH/16);
		        n.setHeight(CAMERA_HEIGHT*0.5f);
				negras.attachChild(n);
				pScine.registerTouchArea(n);
			}
		}
		for (int i = 0; i < 8; i++){
			
			Sprite bp = new Sprite(i*(CAMERA_WIDTH/8),CAMERA_HEIGHT*0.2f,this.mFaceTextureRegion3);
			bp.setWidth(CAMERA_WIDTH/8);
	        bp.setHeight(CAMERA_HEIGHT*0.8f);
			this.attachChild(bp);
			
			Sprite b = new Sprite(i*(CAMERA_WIDTH/8),CAMERA_HEIGHT*0.2f,this.mFaceTextureRegion){
				@Override
				public boolean onAreaTouched(final TouchEvent pAreaTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
	                switch(pAreaTouchEvent.getAction()) {
                    	
	                    case TouchEvent.ACTION_DOWN:                    
	                        this.setVisible(false);
	                        
	                        keyPressed(this);
	                        break;
	                        
	                    case TouchEvent.ACTION_UP:  
	                    	this.setVisible(true);
	                        break;
	                        
	                    case TouchEvent.ACTION_MOVE:
                    		if (pTouchAreaLocalX>(CAMERA_WIDTH/8)-(CAMERA_WIDTH/32) || pTouchAreaLocalX<(CAMERA_WIDTH/32)){
                    			this.setVisible(true);
                    			Log.d("MOVE", "" + pTouchAreaLocalX);
                    		}else{
                    			this.setVisible(false);
                    			Log.d("MOVE", "" + pTouchAreaLocalX);
                    		}
	                    	break;
	                    	
	                   	}
	                
	                //Log.d("Nothing", "YEY");
	                return true;
	               
	            }
			};
			b.setUserData(i);
			b.setWidth(CAMERA_WIDTH/8);
	        b.setHeight(CAMERA_HEIGHT*0.8f);
			this.attachChild(b);
			pScine.registerTouchArea(b);
			
			
		}
		this.attachChild(negras);
		this.sortChildren();
	}
	public void checkPressed(){
		
	}
	public boolean onTouchEvent(MotionEvent event) {
		float x = event.getX();
		float y = event.getY();
		Log.d("onTouch","X=" + x + " Y=" + y);
		return true;
	}
	public void keyPressed(Sprite s){
		
		Log.d("Piano: ","tecla: " + s.getUserData());  
	}
}
