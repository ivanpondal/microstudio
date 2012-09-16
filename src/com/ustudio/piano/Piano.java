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
		this.setKeyboardWidth(CAMERA_WIDTH*7);
		this.setKeyboardY(CAMERA_HEIGHT*0.2f);
		
		this.mTexture = new BitmapTextureAtlas(256, 1024, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mFTR_TN = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture, MainActivity.getInstance().getApplicationContext(), "gfx/Teclas/Blancas/BN.png", 0, 0);
		this.mFTR_TP = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture, MainActivity.getInstance().getApplicationContext(), "gfx/Teclas/Blancas/BP.png", 0,500);
		this.mFTR_STN = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture, MainActivity.getInstance().getApplicationContext(), "gfx/Teclas/Negras/NN.png", 99, 0);
		this.mFTR_STP = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture, MainActivity.getInstance().getApplicationContext(), "gfx/Teclas/Negras/NP.png", 99,500);
		
		MainActivity.getInstance().getEngine().getTextureManager().loadTexture(this.mTexture);
		
		Sprite touchControl = new Sprite(0,this.getKeyboardY(),this.mFTR_STN){
			public boolean onAreaTouched(final TouchEvent pAreaTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				int MIDInote;
				boolean tmpIsTone;
				tmpIsTone=Piano.this.isTone(pTouchAreaLocalX, pTouchAreaLocalY);
				MIDInote=Piano.this.SelKey2MIDI(Piano.this.TouchX2SelKey(pTouchAreaLocalX,tmpIsTone), tmpIsTone);
				Log.d("Piano","MIDI: "+MIDInote);
				
				switch(pAreaTouchEvent.getAction()) {
                    case TouchEvent.ACTION_DOWN: 
                    	//keyPressed(teclaX , false);
                        //Log.d("Pressed","Tecla: " + (teclaX + 1));
                    	//teclas[teclaX].setVieja(teclaX);
                    	//teclas[posSel].setTecla(true);
                    	//keyPressed();
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
		
		drawTones();
		drawST();

		this.attachChild(this.getTonesP()); 
		this.attachChild(this.getTones());
		this.attachChild(this.getSTP());
		this.attachChild(this.getST());
		this.sortChildren();
	}
	
	// SET
	
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
	
	public void setTones(Entity t)
	{
		this.tones=t;
	}
	
	public void setST(Entity st)
	{
		this.semitones=st;
	}
	
	public void setTonesP(Entity t)
	{
		this.tonesp=t;
	}
	
	public void setSTP(Entity st)
	{
		this.semitonesp=st;
	}
	
	
	//GET
	
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
	
	public Entity getTones()
	{
		return this.tones;
	}
	
	public Entity getTonesP()
	{
		return this.tonesp;
	}
	
	public Entity getST()
	{
		return this.semitones;
	}
	
	public Entity getSTP()
	{
		return this.semitonesp;
	}
	
	
	//PRIVADAS
	
	private void drawTones()
	{
		Entity tmp_tones;
		Entity tmp_tonesp;
		tmp_tones = new Entity();
		tmp_tonesp = new Entity();
		for (int i = 0; i < 83; i++){
			Sprite tp = new Sprite(i*this.getTonesWidth(),this.getKeyboardY(),this.mFTR_TP);
			tp.setWidth(this.getTonesWidth());
			tp.setHeight(this.getTonesHeight());
			tmp_tonesp.attachChild(tp);
	
			Sprite t = new Sprite(i*this.getTonesWidth(),this.getKeyboardY(),this.mFTR_TN){

			};
			t.setChildIndex(tmp_tones, i);
			t.setUserData(i);
			t.setWidth(this.getTonesWidth());
	        t.setHeight(this.getTonesHeight());
	        tmp_tones.attachChild(t);		
		}
		
		this.setTonesP(tmp_tonesp);
		this.setTones(tmp_tones);
	}
	
	private void drawST()
	{
		Entity tmp_semitones;
		Entity tmp_semitonesp;
		tmp_semitones = new Entity();
		tmp_semitonesp = new Entity();
		for (int i = 0; i < 83; i++){
			if ((i-2)%7!=0 && (i+1)%7!=0){
				Sprite stp = new Sprite(this.getSTWidth() + this.getSpaceST() + i*this.getTonesWidth(),this.getKeyboardY(),this.mFTR_STP);
				stp.setWidth(this.getSTWidth());
		        stp.setHeight(this.getSTHeight());
		        tmp_semitonesp.attachChild(stp);
				//np.setChildIndex(negras, (int)(i));
				Sprite st = new Sprite(this.getSTWidth() + this.getSpaceST() + i*this.getTonesWidth(),this.getKeyboardY(),this.mFTR_STN);		
				st.setChildIndex(tmp_semitones, i);
				st.setUserData(i+10);
				st.setWidth(this.getSTWidth());
		        st.setHeight(this.getSTHeight());
		        tmp_semitones.attachChild(st);
			}
		}
		
		this.setST(tmp_semitones);
		this.setSTP(tmp_semitonesp);
	}
	
	//PUBLICAS
	
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
	
	public int TouchX2SelKey(float TouchX,boolean isTone)
	{
		int posBlanca = (int)(TouchX/this.widthTone);
		int posNegra = (int)((TouchX+this.widthSpaceST)/this.widthSemitone);
		
		if(!isTone)
		{
			if(posNegra!=0 && posNegra%2==0 && (posNegra+8)%14!=0 && posNegra%14!=0)
			{
				return posNegra;
			}
			else
			{
				return posBlanca;
			}
		}
		return posBlanca;
	}
	
	private int SelKey2MIDI(int SelKey,boolean isTone){
		int octave;
		int noteindex;
		int midinote;
		int modifier=0;
		if(isTone)
		{
			octave=(int)Math.floor(SelKey/7);
			noteindex=SelKey-7*octave;
			switch(noteindex)
			{
				case 0:
					modifier=0;
					break;
				case 1:
					modifier=2;
					break;
				case 2:
					modifier=4;
					break;
				case 3:
					modifier=5;
					break;
				case 4:
					modifier=7;
					break;
				case 5:
					modifier=9;
					break;	
				case 6:
					modifier=11;
					break;	
			}
			
		}
		else
		{
			octave=(int)Math.floor(SelKey/14);
			noteindex=SelKey-14*octave;
			switch(noteindex)
			{
				case 2:
					modifier=1;
					break;
				case 4:
					modifier=3;
					break;
				case 8:
					modifier=6;
					break;
				case 10:
					modifier=8;
					break;
				case 12:
					modifier=10;
					break;
			}
		}
		midinote=octave*12+modifier;
		Log.d("Piano","noteindex: "+noteindex+" octava:"+octave+" isTone:"+isTone+" selkey:"+SelKey);
		return midinote;
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
