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
	private Entity negrasp;
	private Texture mTexture;
	private TextureRegion mFaceTextureRegion;
	private TextureRegion mFaceTextureRegion2;
	private TextureRegion mFaceTextureRegion3;
	private TextureRegion mFaceTextureRegion4;
	private int teclaXVieja;
	private boolean teclasA[];
	private Teclas teclas[];
	

	
	public Piano(Scene pScine, int w, int h) {
		
		CAMERA_WIDTH = w;
		CAMERA_HEIGHT = h;
		scene = pScine;
		
		teclasA = new boolean[16];
		for (int i = 0; i<16; i++){
			teclasA[i] = false;
		}
		
		teclas = new Teclas[16];
		for (int i = 0; i<16; i++){
			teclas[i] = new Teclas(i);
		}
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
						teclaX = 2*(calcNegras/2);
						Log.d("LULULULULLU","" + teclaX);
						teclas[teclaX].setTecla(true);
						
					}
				}else{
					teclas[teclaX].setTecla(true);
				}
				
				
				switch(pAreaTouchEvent.getAction()) {
                    case TouchEvent.ACTION_DOWN: 
                        //keyPressed(teclaX , false);
                        //Log.d("Pressed","Tecla: " + (teclaX + 1));
                    	//teclas[teclaX].setVieja(teclaX);
                    	keyPressed();
                        break;
                        
                    case TouchEvent.ACTION_UP: 
                    	teclas[teclaX].setTecla(false);
                    	//keyPressed(teclaX , true);
                    	//Log.d("Relesed","Tecla: " + (teclaX + 1));
                    	keyPressed();
                        break;
                        
                    case TouchEvent.ACTION_MOVE:
                    	
                    	
                    	//teclas[teclas[teclaX].getVieja()].setTecla(false);
                    	//keyPressed();
                    	
                    	teclas[teclaX].setTecla(true);
                    	keyPressed();
                    	
                    	//if (teclaX != teclas[teclaX].getVieja()){
                    	//	teclas[teclaX].setVieja(teclaX);
                    	//}
                    	//keyPressed();
                    	
                    	//keyPressed(teclaXVieja , true);
                    	//keyPressed(teclas[teclaX].getVieja() , true);
                    	//keyPressed(teclaX , false);
                    	//teclaXVieja = teclaX;
                    	//teclas[teclaX].setVieja(teclaX);
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
		negrasp = new Entity();
		for (int i = 0; i < 8; i++){
			if (i != 2 && i != 6){
				Sprite np = new Sprite(CAMERA_WIDTH/16 + CAMERA_WIDTH/32 + i*(CAMERA_WIDTH/16 + CAMERA_WIDTH/16),CAMERA_HEIGHT*0.2f,this.mFaceTextureRegion4);
				np.setWidth(CAMERA_WIDTH/16);
		        np.setHeight(CAMERA_HEIGHT*0.5f);
				negrasp.attachChild(np);
				//np.setChildIndex(negras, (int)(i));
				Sprite n = new Sprite(CAMERA_WIDTH/16 + CAMERA_WIDTH/32 + i*(CAMERA_WIDTH/16 + CAMERA_WIDTH/16),CAMERA_HEIGHT*0.2f,this.mFaceTextureRegion2){
					
				};
				
				n.setChildIndex(negras, (int)(i));
				Log.d("LALALALALALA","" + (i+7));
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
		this.attachChild(negrasp);
		this.attachChild(negras);
		this.sortChildren();
		
	}
	public int keyCheck(){
			
		return 0;
	}
	public void keyPressed(){//int s, boolean mode){
	
		
		for (int t = 0; t<8; t++){
			if (teclas[t].getTecla() == true){
				IEntity entity = blancas.getChild(t);
		    	entity.setVisible(false);
			}else{
				IEntity entity = blancas.getChild(t);
		    	entity.setVisible(true);
			}
			
		}
		
		for (int t = 0; t<8; t++){
			int vieja = teclas[t].getVieja();
			teclas[vieja].setTecla(false);
			
		}
		/*if (s<0){
			if (s*(-1)>3){
				if (s*(-1)>6){
					IEntity entity = negras.getChild((-1)*s -3);
			    	entity.setVisible(mode);
				}else{
					IEntity entity = negras.getChild((-1)*s -2);
			    	entity.setVisible(mode);
				}
			}else{
				IEntity entity = negras.getChild((-1)*s -1);
		    	entity.setVisible(mode);
			}
			

		}else{
			IEntity entity = blancas.getChild(s);
	    	entity.setVisible(mode);
		}*/
		
    
 
	}
}
