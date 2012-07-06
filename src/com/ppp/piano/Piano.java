package com.ppp.piano;


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

import android.util.Log;

import com.ppp.main.MainActivity;

public class Piano extends Entity {
	
	
	static int CAMERA_WIDTH;
	static int CAMERA_HEIGHT;
	static Scene scene;
	
	private Entity blancas;
	private Texture mTexture;
	private TextureRegion mFaceTextureRegion;
	private TextureRegion mFaceTextureRegion2;
	private TextureRegion mFaceTextureRegion3;
	private TextureRegion mFaceTextureRegion4;
	private int teclaXVieja;
	
	public Piano(Scene pScine, int w, int h) {
		
		CAMERA_WIDTH = w;
		CAMERA_HEIGHT = h;
		scene = pScine;
		//this.mMainActivity = pMainActivity;
		
		
		this.mTexture = new BitmapTextureAtlas(256, 1024, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mFaceTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture, MainActivity.getInstance().getApplicationContext(), "gfx/Teclas/Blancas/NB.png", 0, 0);
		this.mFaceTextureRegion2 = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture, MainActivity.getInstance().getApplicationContext(), "gfx/Teclas/Negras/NN.png", 99, 0);
		this.mFaceTextureRegion3 = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture, MainActivity.getInstance().getApplicationContext(), "gfx/Teclas/Blancas/PB.png", 0,500);
		this.mFaceTextureRegion4 = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture, MainActivity.getInstance().getApplicationContext(), "gfx/Teclas/Negras/NP.png", 99,500);
		//this.mFaceTextureRegion5 = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture, MainActivity.getInstance().getApplicationContext(), "gfx/Teclas/Negras/NP.png", 1000,1000);
		MainActivity.getInstance().getEngine().getTextureManager().loadTexture(this.mTexture);
		
		Sprite touchControl = new Sprite(0,CAMERA_HEIGHT*0.2f,mFaceTextureRegion4){
			public boolean onAreaTouched(final TouchEvent pAreaTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				int teclaX = ((int)pTouchAreaLocalX/(CAMERA_WIDTH/8));
				switch(pAreaTouchEvent.getAction()) {
                    case TouchEvent.ACTION_DOWN: 
                        keyPressed(teclaX , false);
                        Log.d("Pressed","Tecla: " + (teclaX + 1));
                        break;
                        
                    case TouchEvent.ACTION_UP: 
                    	keyPressed(teclaX , true);
                    	Log.d("Relesed","Tecla: " + (teclaX + 1));
                        break;
                        
                    case TouchEvent.ACTION_MOVE:
                    	keyPressed(teclaXVieja , true);
                    	keyPressed(teclaX , false);
                    	teclaXVieja = teclaX;
                		Log.d("MOVE", "" + pTouchAreaLocalX);
                    	break;
                    	
                   	}
                
                return true;
            }
		};
		touchControl.setHeight(CAMERA_HEIGHT*0.8f);
		touchControl.setWidth(CAMERA_WIDTH);
		this.attachChild(touchControl);
		pScine.registerTouchArea(touchControl);
		
		
		//Negras------------------------------------
		Entity negras = new Entity();
	
		for (int i = 0; i < 8; i++){
			if (i != 2 && i != 6){
				Sprite np = new Sprite(CAMERA_WIDTH/16 + CAMERA_WIDTH/32 + i*(CAMERA_WIDTH/16 + CAMERA_WIDTH/16),CAMERA_HEIGHT*0.2f,this.mFaceTextureRegion4);
				np.setWidth(CAMERA_WIDTH/16);
		        np.setHeight(CAMERA_HEIGHT*0.5f);
				negras.attachChild(np);
				Sprite n = new Sprite(CAMERA_WIDTH/16 + CAMERA_WIDTH/32 + i*(CAMERA_WIDTH/16 + CAMERA_WIDTH/16),CAMERA_HEIGHT*0.2f,this.mFaceTextureRegion2){
					/*@Override
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
		            }*/
				};
				
				n.setUserData(i+10);
				n.setWidth(CAMERA_WIDTH/16);
		        n.setHeight(CAMERA_HEIGHT*0.5f);
				negras.attachChild(n);
				pScine.registerTouchArea(n);
			}
		}
		
		
		//Blancas-------------------------------------------
		
		blancas = new Entity();
		for (int i = 0; i < 8; i++){
			
			Sprite bp = new Sprite(i*(CAMERA_WIDTH/8),CAMERA_HEIGHT*0.2f,this.mFaceTextureRegion3);
			bp.setWidth(CAMERA_WIDTH/8);
	        bp.setHeight(CAMERA_HEIGHT*0.8f);
			this.attachChild(bp);
			
			
			
			Sprite b = new Sprite(i*(CAMERA_WIDTH/8),CAMERA_HEIGHT*0.2f,this.mFaceTextureRegion){
				/*@Override
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
	               
	            }*/
			};
			b.setChildIndex(blancas, i);
			b.setUserData(i);
			b.setWidth(CAMERA_WIDTH/8);
	        b.setHeight(CAMERA_HEIGHT*0.8f);
			blancas.attachChild(b);
			pScine.registerTouchArea(b);
			
			
		}
		this.attachChild(blancas);
		this.attachChild(negras);
		this.sortChildren();
	}
	public void checkPressed(){
		
	}
	public void keyPressed(int s, boolean mode){
		/*int count = blancas.getChildCount();
		for(int i = 0; i < count; i++) {
			IEntity entity = blancas.getChild(i);
			if(entity instanceof Sprite) {
		        if(entity.getUserData().equals(s)){
		        	if (mode == 0){
		        		entity.setVisible(false);
		        	}else{
		        		entity.setVisible(true);
		        	}
		            
		            Log.d("Found","Tecla: " + s);
		        }
			}
		}*/
		IEntity entity = blancas.getChild(s);
    	entity.setVisible(mode);
    
        
		//Log.d("Piano: ","tecla: " + s);  
	}
}
