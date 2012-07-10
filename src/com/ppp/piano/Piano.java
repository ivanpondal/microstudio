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
	private Entity negras;
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
		
		
		this.mTexture = new BitmapTextureAtlas(256, 1024, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mFaceTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture, MainActivity.getInstance().getApplicationContext(), "gfx/Teclas/Blancas/NB.png", 0, 0);
		this.mFaceTextureRegion2 = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture, MainActivity.getInstance().getApplicationContext(), "gfx/Teclas/Negras/NN.png", 99, 0);
		this.mFaceTextureRegion3 = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture, MainActivity.getInstance().getApplicationContext(), "gfx/Teclas/Blancas/PB.png", 0,500);
		this.mFaceTextureRegion4 = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture, MainActivity.getInstance().getApplicationContext(), "gfx/Teclas/Negras/NP.png", 99,500);
		MainActivity.getInstance().getEngine().getTextureManager().loadTexture(this.mTexture);
		
		Sprite touchControl = new Sprite(0,CAMERA_HEIGHT*0.2f,mFaceTextureRegion4){
			public boolean onAreaTouched(final TouchEvent pAreaTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				int calcNegras = (int)(pTouchAreaLocalX + (CAMERA_WIDTH/32))/(CAMERA_WIDTH/16);
				int teclaX = ((int)pTouchAreaLocalX/(CAMERA_WIDTH/8));
				if (pTouchAreaLocalY <= CAMERA_HEIGHT*0.5f){
					if (calcNegras%2 == 0 && calcNegras != 0 && calcNegras != 6 && calcNegras != 14){
						teclaX = calcNegras/2 +10;
					}
				}
				Log.d("negras","" + teclaX);
				switch(pAreaTouchEvent.getAction()) {
                    case TouchEvent.ACTION_DOWN: 
                        keyPressed(teclaX , false);
                        //Log.d("Pressed","Tecla: " + (teclaX + 1));
                        break;
                        
                    case TouchEvent.ACTION_UP: 
                    	keyPressed(teclaX , true);
                    	//Log.d("Relesed","Tecla: " + (teclaX + 1));
                        break;
                        
                    case TouchEvent.ACTION_MOVE:
                    	keyPressed(teclaXVieja , true);
                    	keyPressed(teclaX , false);
                    	teclaXVieja = teclaX;
                		//Log.d("MOVE", "" + pTouchAreaLocalX);
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
		negras = new Entity();
	
		for (int i = 0; i < 8; i++){
			if (i != 2 && i != 6){
				Sprite np = new Sprite(CAMERA_WIDTH/16 + CAMERA_WIDTH/32 + i*(CAMERA_WIDTH/16 + CAMERA_WIDTH/16),CAMERA_HEIGHT*0.2f,this.mFaceTextureRegion4);
				np.setWidth(CAMERA_WIDTH/16);
		        np.setHeight(CAMERA_HEIGHT*0.5f);
				negras.attachChild(np);
				Sprite n = new Sprite(CAMERA_WIDTH/16 + CAMERA_WIDTH/32 + i*(CAMERA_WIDTH/16 + CAMERA_WIDTH/16),CAMERA_HEIGHT*0.2f,this.mFaceTextureRegion2){
					
				};
				
				n.setChildIndex(negras, (i + 12));
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
	public int keyCheck(){
			
		return 0;
	}
	public void keyPressed(int s, boolean mode){
		if (s >= 8){
			IEntity entity = negras.getChild(s);
	    	entity.setVisible(mode);
		}else{
			IEntity entity = blancas.getChild(s);
	    	entity.setVisible(mode);
		}
		
    
 
	}
}
