package com.ustudio.piano;


import org.anddev.andengine.entity.Entity;
import org.anddev.andengine.entity.primitive.Rectangle;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TextureRegion;

import android.util.Log;

import java.util.Hashtable;

import com.ustudio.main.MainActivity;
import com.ustudio.audio.Instrument;
import com.ustudio.midi.MIDIMessage;
import com.ustudio.project.IniConstants;
import com.ustudio.project.Track;

public class MiniPiano extends Entity {
	
	static int CAMERA_WIDTH;
	static int CAMERA_HEIGHT;
	static Scene scene;
	
	private Key tonekeys[];
	private Key semitonekeys[];
	
	private Entity tones;
	private Entity tonesp;
	private Entity semitones;
	private Entity semitonesp;
	private Entity viewer;
	
	private Texture mTexture;
	private Texture mBGTexture;
	private TextureRegion mBG;
	private TextureRegion mFTR_TN;
	private TextureRegion mFTR_TP;
	private TextureRegion mFTR_STN;
	private TextureRegion mFTR_STP;
	
	private float widthTone;
	private float heightTone;
	private float widthSemitone;
	private float heightSemitone;
	private float widthSpaceST;
	private float TorST;
	private float widthKeyboard;
	private float heightKeyboard;
	private float widthBG;
	private float heightBG;
	
	private Track mActiveTrack;
	private Piano mPiano;
	
	private byte tonesVisible;
	
	public MiniPiano(Scene pScene, int w, int h, Track t, Piano p) {
		Key tmptonekeys[];
		Key tmpsemitonekeys[];
		
		CAMERA_WIDTH = w;
		CAMERA_HEIGHT = h;
		
		scene = pScene;
		loadSizes();
		loadGUITextures();
		tmptonekeys=new Key[49];
		tmpsemitonekeys=new Key[35];
		for (byte i=0;i<49;i++)
		{
			tmptonekeys[i]=new Key(false,true,i,PianoMath.SpriteIndex2MIDI(i, true));
		}
		
		for (byte i=0;i<35;i++)
		{
			tmpsemitonekeys[i]=new Key(false,false,i,PianoMath.SpriteIndex2MIDI(i, false));
		}
		
		this.setToneKeys(tmptonekeys);
		this.setSemitoneKeys(tmpsemitonekeys);
		this.setTonesVisible(t.getTonesVisible());
		this.setActiveTrack(t);
		this.setPiano(p);
	
		Rectangle touchControl = new Rectangle(0,0,this.getKeyboardWidth(), this.getKeyboardHeight()){
			public boolean onAreaTouched(final TouchEvent pAreaTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				switch(pAreaTouchEvent.getAction()) 
				{
                    case TouchEvent.ACTION_DOWN:
                    	MiniPiano.this.moveViewer(MiniPiano.this.getSelTone(pTouchAreaLocalX));
                    	break;
                    case TouchEvent.ACTION_MOVE:  
                    	MiniPiano.this.moveViewer(MiniPiano.this.getSelTone(pTouchAreaLocalX));
                    	break;	
				}
                return true;
            }
		};
		
		this.attachChild(touchControl);
		pScene.registerTouchArea(touchControl);
		
		loadGUITextures();
		
		drawBG();
		drawTones();
		drawST();
		drawViewer();

		this.attachChild(this.getTonesP()); 
		this.attachChild(this.getTones());
		this.attachChild(this.getSTP());
		this.attachChild(this.getST());
		this.attachChild(this.getViewer());
		this.sortChildren();
	}

	
	//PRIVADAS
	
	private void loadSizes()
	{
		this.setKeyboardHeight(CAMERA_HEIGHT/9.5f);
		this.setKeyboardWidth(CAMERA_WIDTH/1.6f);
		this.setTonesWidth(this.getKeyboardWidth()/49);
		this.setTonesHeight(this.getKeyboardHeight());
		this.setSTWidth(this.getTonesWidth()/2);
		this.setSTHeight(this.getTonesHeight()/1.75f);
		this.setSpaceST(this.getSTWidth()/2);
		this.setTorST(this.getSTHeight());
		this.setBGWidth(this.getKeyboardWidth()*1.02f);
		this.setBGHeight(this.getKeyboardHeight()*1.2f);
	}
	
	private void loadGUITextures()
	{
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/Backgrounds/");
		this.mBGTexture = new BitmapTextureAtlas(1024, 128, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mBG = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mBGTexture, MainActivity.getInstance().getApplicationContext(), "minipiano_bg.png", 0, 0);
		MainActivity.getInstance().getEngine().getTextureManager().loadTexture(this.mBGTexture);
		
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/Piano/SmallKeys/");
		this.mTexture = new BitmapTextureAtlas(64, 128, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mFTR_TN = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture, MainActivity.getInstance().getApplicationContext(), "Tones/to_released.png", 0, 0);
		this.mFTR_TP = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture, MainActivity.getInstance().getApplicationContext(), "Tones/to_pressed.png", 20,0);
		this.mFTR_STN = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture, MainActivity.getInstance().getApplicationContext(), "Semitones/st_released.png", 40, 0);
		this.mFTR_STP = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture, MainActivity.getInstance().getApplicationContext(), "Semitones/st_pressed.png", 52,0);
		MainActivity.getInstance().getEngine().getTextureManager().loadTexture(this.mTexture);
	}
	
	private void drawBG()
	{
		Sprite bg_sprite = new Sprite((this.widthKeyboard-this.widthBG)/2,(this.heightKeyboard-this.heightBG)/2,this.mBG);
		bg_sprite.setWidth(this.getBGWidth());
		bg_sprite.setHeight(this.getBGHeight());
		bg_sprite.setAlpha(0.4f);
		this.attachChild(bg_sprite); 
	}
	
	private void drawTones()
	{
		Entity tmp_tones;
		Entity tmp_tonesp;
		tmp_tones = new Entity();
		tmp_tonesp = new Entity();
		for (int i = 0; i < 49; i++){
			Sprite tp = new Sprite(i*this.getTonesWidth(),0,this.mFTR_TP);
			tp.setWidth(this.getTonesWidth());
			tp.setHeight(this.getTonesHeight());
			tmp_tonesp.attachChild(tp);
			
			Sprite t = new Sprite(i*this.getTonesWidth(),0,this.mFTR_TN){

			};
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
		for (int i = 0; i < 49; i++){
			if ((i-2)%7!=0 && (i+1)%7!=0){
				Sprite stp = new Sprite(this.getSTWidth() + this.getSpaceST() + i*this.getTonesWidth(),0,this.mFTR_STP);
				stp.setWidth(this.getSTWidth());
		        stp.setHeight(this.getSTHeight());
		        tmp_semitonesp.attachChild(stp);

				Sprite st = new Sprite(this.getSTWidth() + this.getSpaceST() + i*this.getTonesWidth(),0,this.mFTR_STN);		
				st.setWidth(this.getSTWidth());
		        st.setHeight(this.getSTHeight());
		        tmp_semitones.attachChild(st);
			}
		}
		
		this.setST(tmp_semitones);
		this.setSTP(tmp_semitonesp);
	}
	
	private void drawViewer()
	{
		Rectangle recviewer;
		
		recviewer=new Rectangle(0,0,this.getTonesWidth()*this.getTonesVisible(),this.getKeyboardHeight());
		recviewer.setColor(0, 0, 0);
		recviewer.setAlpha(0.5f);
		
		this.setViewer(recviewer);
	}
	
	//PUBLICAS
	
	public byte getSelTone(float TouchX)
	{
		byte selTone;
		selTone=(byte)((TouchX/this.getTonesWidth())-(this.getTonesVisible()/2));
		return selTone;
	}
	
	
	public void moveViewer(byte selTone)
	{
		float posX;
		if((49-selTone)<this.tonesVisible)
		{
			selTone=(byte)(49-this.tonesVisible);
		}
		else if(selTone<0)
		{
			selTone=0;
		} 
		posX=this.getTonesWidth()*selTone;
		this.getActiveTrack().setFirstTone(selTone);
		this.getPiano().moveViewer(selTone);
		this.getViewer().setPosition(posX,this.getViewer().getY());
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
	
	public void setBGWidth(float w)
	{
		this.widthBG=w;
	}
	
	public void setBGHeight(float h)
	{
		this.heightBG=h;
	}
	
	public void setToneKeys(Key t[])
	{
		this.tonekeys=t;
	}
	
	public void setSemitoneKeys(Key st[])
	{
		this.semitonekeys=st;
	}
	
	public void setViewer(Entity v)
	{
		this.viewer=v;
	}
	
	public void setTonesVisible(byte t)
	{
		this.tonesVisible=t;
	}
	
	public void setActiveTrack(Track t)
	{
		this.mActiveTrack=t;
	}
	
	public void setPiano(Piano p)
	{
		this.mPiano=p;
	}
	
	//GET}
	
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
	
	public float getBGWidth()
	{
		return this.widthBG;
	}
	
	public float getBGHeight()
	{
		return this.heightBG;
	}
	
	public Key[] getToneKeys()
	{
		return this.tonekeys;
	}
	
	public Key[] getSemitoneKeys()
	{
		return this.semitonekeys;
	}
	
	public Entity getViewer()
	{
		return this.viewer;
	}	
	
	public byte getTonesVisible()
	{
		return this.tonesVisible;
	}
	
	public Track getActiveTrack()
	{
		return this.mActiveTrack;
	}
	
	public Piano getPiano()
	{
		return this.mPiano;
	}
}
