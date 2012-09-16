package com.ustudio.piano;


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

import com.ustudio.main.MainActivity;

public class Piano extends Entity {
	
	
	static int CAMERA_WIDTH;
	static int CAMERA_HEIGHT;
	static Scene scene;
	
	private Entity tones;
	private Entity tonesp;
	private Entity semitones;
	private Entity semitonesp;
	private Texture mTexture;
	private TextureRegion mFTR_TN;
	private TextureRegion mFTR_TP;
	private TextureRegion mFTR_STN;
	private TextureRegion mFTR_STP;
	private boolean teclasA[];
	private Tecla teclas[];
	
	private float widthTone;
	private float heightTone;
	private float widthSemitone;
	private float heightSemitone;
	private float widthSpaceST;
	private float TorST;
	private float KeyboardY;
	private float widthKeyboard;
	private float heightKeyboard;

	
	public Piano(Scene pScene, int w, int h) {
		
		CAMERA_WIDTH = w;
		CAMERA_HEIGHT = h;
		
		
		scene = pScene;
		
		this.setTonesWidth(CAMERA_WIDTH/8);
		this.setTonesHeight(CAMERA_HEIGHT*0.8f);
		this.setSTWidth(CAMERA_WIDTH/16);
		this.setSTHeight(CAMERA_HEIGHT*0.5f);
		this.setSpaceST(CAMERA_WIDTH/32);
		this.setTorST(CAMERA_HEIGHT*0.5f);
		this.setKeyboardHeight(CAMERA_HEIGHT*0.8f);
		this.setKeyboardWidth(CAMERA_WIDTH);
		
		this.mTexture = new BitmapTextureAtlas(256, 1024, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mFTR_TN = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture, MainActivity.getInstance().getApplicationContext(), "gfx/Teclas/Blancas/BN.png", 0, 0);
		this.mFTR_TP = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture, MainActivity.getInstance().getApplicationContext(), "gfx/Teclas/Blancas/BP.png", 0,500);
		this.mFTR_STN = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture, MainActivity.getInstance().getApplicationContext(), "gfx/Teclas/Negras/NN.png", 99, 0);
		this.mFTR_STP = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture, MainActivity.getInstance().getApplicationContext(), "gfx/Teclas/Negras/NP.png", 99,500);

		MainActivity.getInstance().getEngine().getTextureManager().loadTexture(this.mTexture);
		
		Sprite touchControl = new Sprite(0,this.getKeyboardY(),mFTR_STP){
			public boolean onAreaTouched(final TouchEvent pAreaTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				int MIDInote;
				
				MIDInote=Piano.this.SelKey2MIDI(Piano.this.TouchX2SelKey(pTouchAreaLocalX), Piano.this.isTone(pTouchAreaLocalX, pTouchAreaLocalY));

				
				switch(pAreaTouchEvent.getAction()) {
                    case TouchEvent.ACTION_DOWN: 
                    	//keyPressed(teclaX , false);
                        //Log.d("Pressed","Tecla: " + (teclaX + 1));
                    	//teclas[teclaX].setVieja(teclaX);
                    	//teclas[posSel].setTecla(true);
                    	//keyPressed();
                        break;
                        
                    case TouchEvent.ACTION_UP: 
                    	//teclas[posSel].setTecla(false);
                    	//keyPressed(teclaX , true);
                    	//Log.d("Relesed","Tecla: " + (teclaX + 1));
                    	//keyPressed();
                        break;
                        
                    case TouchEvent.ACTION_MOVE:                    	
                    	//teclas[teclas[teclaX].getVieja()].setTecla(false);
                    	//keyPressed();
                    	
                    	//teclas[posSel].setTecla(true);
                    	//keyPressed();
                    	//for (int i = 0; i < 8; i++){
                    		
                    	//}
                    	
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
		touchControl.setHeight(this.getKeyboardHeight());
		touchControl.setWidth(this.getKeyboardWidth());
		this.attachChild(touchControl);
		pScene.registerTouchArea(touchControl);
		
		
		//Negras------------------------------------
		semitones = new Entity();
		semitonesp = new Entity();
		for (int i = 0; i < 83; i++){
			if ((i-2)%7!=0 && (i+1)%7!=0){
				Sprite np = new Sprite(this.getSTWidth() + this.getSpaceST() + i*this.getTonesWidth(),this.getKeyboardY(),this.mFTR_STP);
				np.setWidth(this.getSTWidth());
		        np.setHeight(this.getSTHeight());
		        semitonesp.attachChild(np);
				//np.setChildIndex(negras, (int)(i));
				Sprite n = new Sprite(this.getSTWidth() + this.getSpaceST() + i*this.getTonesWidth(),this.getKeyboardY(),this.mFTR_STN);		
				n.setChildIndex(semitones, (int)(i));
				n.setUserData(i+10);
				n.setWidth(this.getSTWidth());
		        n.setHeight(this.getSTHeight());
		        semitones.attachChild(n);
			}
		}
		
		//Blancas-------------------------------------------
		tones = new Entity();
		tonesp = new Entity();
		for (int i = 0; i < 83; i++){
			Sprite bp = new Sprite(i*this.getTonesWidth(),this.getKeyboardY(),this.mFTR_TP);
			bp.setWidth(this.getTonesWidth());
			bp.setHeight(this.getTonesHeight());
			tonesp.attachChild(bp);
	
			Sprite b = new Sprite(i*this.getTonesWidth(),this.getKeyboardY(),this.mFTR_TN){

			};
			b.setChildIndex(tones, i);
			b.setUserData(i);
			b.setWidth(this.getTonesWidth());
	        b.setHeight(this.getTonesHeight());
	        tones.attachChild(b);		
		}
		this.attachChild(tonesp);
		this.attachChild(tones);
		this.attachChild(semitonesp);
		this.attachChild(semitones);
		this.sortChildren();
	}
	
	public void setTonesWidth(float w)
	{
		this.widthTone=w;
	}
	
	public void setTonesHeight(float h)
	{
		this.heightTone=h;
	}
	
	public void setSTWidth(float w)
	{
		this.widthSemitone=w;
	}
	
	public void setSTHeight(float h)
	{
		this.heightSemitone=h;
	}
	
	public void setSpaceST(float w)
	{
		this.widthSpaceST=w;
	}
	
	public void setTorST(float h)
	{
		this.TorST=h;
	}
	
	public void setKeyboardY(float y)
	{
		this.KeyboardY=y;
	}
	
	public void setKeyboardWidth(float w)
	{
		this.widthKeyboard=w;
	}
	
	public void setKeyboardHeight(float h)
	{
		this.heightKeyboard=h;
	}
	
	public float getTonesWidth()
	{
		return this.widthTone;
	}
	
	public float getTonesHeight()
	{
		return this.heightTone;
	}
	
	public float getSTWidth()
	{
		return this.widthSemitone;
	}
	
	public float getSTHeight()
	{
		return this.heightSemitone;
	}
	
	public float getSpaceST()
	{
		return this.widthSpaceST;
	}
	
	public float getTorST()
	{
		return this.TorST;
	}
	
	public float getKeyboardY()
	{
		return this.KeyboardY;
	}
	
	public float getKeyboardWidth()
	{
		return this.widthKeyboard;
	}
	
	public float getKeyboardHeight()
	{
		return this.heightKeyboard;
	}
	
	public boolean isTone(float TouchX,float TouchY)
	{
		int posNegra = (int)((TouchX+this.widthSpaceST)/this.widthSemitone);
		if (TouchY <= this.TorST){ 
				if(posNegra!=0 && posNegra%2==0 && (posNegra+8)%14!=0 && posNegra%14!=0)
				{
					return false;
				}
				else
				{
					return true;
				}
		}
		return true;
	}
	
	public int TouchX2SelKey(float TouchX)
	{
		int posBlanca = (int)(TouchX/this.widthTone);
		int posNegra = (int)((TouchX+this.widthSpaceST)/this.widthSemitone);
		
		if(posNegra!=0 && posNegra%2==0 && (posNegra+8)%14!=0 && posNegra%14!=0)
		{
			return posNegra;
		}
		else
		{
			return posBlanca;
		}
	}
	
	private int SelKey2MIDI(int SelKey,boolean isTone){
		
		return 0;
	}
	
	
	public int keyCheck(){
			
		return 0;
	}
	
	public void keyPressed(){//int s, boolean mode){
	
		
		for (int t = 0; t<8; t++){
			if (teclas[t].getTecla() == true){
				IEntity entity = tones.getChild(t);
		    	entity.setVisible(false);
			}else{
				IEntity entity = tones.getChild(t);
		    	entity.setVisible(true);
			}
			
		}
		
		for (int t = 1; t<8; t++){
			//int vieja = teclas[t].getVieja();
			//boolean viejaStatus = teclas[t].getTecla();
			//if (vieja == t){
				//teclas[vieja].setTecla(false);
			//}
			
			
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
