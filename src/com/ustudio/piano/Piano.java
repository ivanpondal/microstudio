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

import com.ustudio.project.IniConstants;
import com.ustudio.project.Track;
import com.ustudio.main.MainActivity;
import com.ustudio.audio.Instrument;
import com.ustudio.audio.Record;
import com.ustudio.midi.MIDIMessage;

public class Piano extends Entity {
	
	static int CAMERA_WIDTH;
	static int CAMERA_HEIGHT;
	static Scene scene;
	
	private Key tonekeys[];
	private Key semitonekeys[];
	
	private Entity tones;
	private Entity tonesp;
	private Entity semitones;
	private Entity semitonesp;
	
	private Texture mTexture;
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
	
	private Hashtable<Integer,Key> TouchIDs;
	
	private Instrument mInstrument;
	
	private Track mActiveTrack;
	
	private MiniPiano mMiniPiano;
	
	public Piano(Scene pScene, int w, int h, Track t) {
		Key tmptonekeys[];
		Key tmpsemitonekeys[];
		Hashtable<Integer,Key> tmpTouchIDs;
		
		CAMERA_WIDTH = w;
		CAMERA_HEIGHT = h;
		
		scene = pScene;
		this.setTonesWidth(CAMERA_HEIGHT/(float)t.getTonesVisible());
		
		this.setKeyboardHeight(CAMERA_WIDTH/1.6f);
		this.setKeyboardWidth(this.getTonesWidth()*42);

		this.setTonesHeight(this.getKeyboardHeight());
		this.setSTWidth(this.getTonesWidth()/2);
		this.setSTHeight(this.getTonesHeight()/1.75f);
		this.setSpaceST(this.getSTWidth()/2);
		this.setTorST(this.getKeyboardHeight()-this.getSTHeight());
		this.setInstrument(t.getInstr());
		this.setActiveTrack(t);

		tmptonekeys=new Key[42];
		tmpsemitonekeys=new Key[30];
		for (byte i=0;i<42;i++)
		{
			tmptonekeys[i]=new Key(false,true,i,PianoMath.SpriteIndex2MIDI(i, true));
		}
		
		for (byte i=0;i<30;i++)
		{
			tmpsemitonekeys[i]=new Key(false,false,i,PianoMath.SpriteIndex2MIDI(i, false));
		}
		
		this.setToneKeys(tmptonekeys);
		this.setSemitoneKeys(tmpsemitonekeys);
		
		tmpTouchIDs=new Hashtable<Integer,Key>();
		this.setTouchIDs(tmpTouchIDs);
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/Piano/Keys/");
		this.mTexture = new BitmapTextureAtlas(1024, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mFTR_TN = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture, MainActivity.getInstance().getApplicationContext(), "Tones/to_released.png", 0, 0);
		this.mFTR_TP = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture, MainActivity.getInstance().getApplicationContext(), "Tones/to_pressed.png", 500,0);
		this.mFTR_STN = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture, MainActivity.getInstance().getApplicationContext(), "Semitones/st_released.png", 0, 99);
		this.mFTR_STP = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture, MainActivity.getInstance().getApplicationContext(), "Semitones/st_pressed.png", 500, 99);
		
		MainActivity.getInstance().getEngine().getTextureManager().loadTexture(this.mTexture);
		
		Rectangle touchControl = new Rectangle(0,0,this.getKeyboardHeight(), this.getKeyboardWidth()){
			public boolean onAreaTouched(final TouchEvent pAreaTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				int PointerID,SelKey;
				byte SpriteIndex;
				boolean tmpIsTone;
				
				tmpIsTone=Piano.this.isTone(pTouchAreaLocalY, pTouchAreaLocalX);
				SelKey=Piano.this.TouchX2SelKey(pTouchAreaLocalY,tmpIsTone);
				SpriteIndex=PianoMath.SelKey2SpriteIndex(SelKey, tmpIsTone);	
    			PointerID=pAreaTouchEvent.getPointerID();
    			if(SpriteIndex<42)
    			{
					switch(pAreaTouchEvent.getAction()) 
					{
	                    case TouchEvent.ACTION_DOWN:
	                    	Piano.this.doUpDownAction(tmpIsTone, true, PointerID, SpriteIndex);
	                    	break;
	                    case TouchEvent.ACTION_UP: 
	                    	Piano.this.doUpDownAction(tmpIsTone, false, PointerID, SpriteIndex);
	                        break;
	                    case TouchEvent.ACTION_MOVE:    
	                    	Piano.this.doMoveAction(tmpIsTone, PointerID, SpriteIndex);
	                    	break;	
					}
	
					Piano.this.processKeys();
    			}
                return true;
            }
		};

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

	
	//PRIVADAS
	
	private void drawTones()
	{
		Entity tmp_tones;
		Entity tmp_tonesp;
		tmp_tones = new Entity();
		tmp_tonesp = new Entity();
		for (int i = 0; i < 42; i++){
			Sprite tp = new Sprite(0,i*this.getTonesWidth(),this.mFTR_TP);
			tp.setWidth(this.getTonesHeight());
			tp.setHeight(this.getTonesWidth());
			tmp_tonesp.attachChild(tp);
			
			Sprite t = new Sprite(0,i*this.getTonesWidth(),this.mFTR_TN){

			};
			t.setWidth(this.getTonesHeight());
	        t.setHeight(this.getTonesWidth());
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
		for (int i = 0; i < 42; i++){
			if ((i-2)%7!=0 && (i+1)%7!=0){
				Sprite stp = new Sprite(this.getTonesHeight()-this.getSTHeight(),this.getSTWidth() + this.getSpaceST() + i*this.getTonesWidth(),this.mFTR_STP);
				stp.setWidth(this.getSTHeight());
		        stp.setHeight(this.getSTWidth());
		        tmp_semitonesp.attachChild(stp);

				Sprite st = new Sprite(this.getTonesHeight()-this.getSTHeight(),this.getSTWidth() + this.getSpaceST() + i*this.getTonesWidth(),this.mFTR_STN);		
				st.setWidth(this.getSTHeight());
		        st.setHeight(this.getSTWidth());
		        tmp_semitones.attachChild(st);
			}
		}
		
		this.setST(tmp_semitones);
		this.setSTP(tmp_semitonesp);
	}
	
	//PUBLICAS
	public void moveViewer(byte selTone)
	{
		float posX;
		if((42-selTone)<this.mActiveTrack.getTonesVisible())
		{
			selTone=(byte)(42-this.mActiveTrack.getTonesVisible());
		}
		posX=this.widthTone*-selTone;
		this.setPosition(0,posX);
	}
	
	public boolean isTone(float TouchX,float TouchY)
	{
		int posNegra = (int)((TouchX+this.widthSpaceST)/this.widthSemitone);
		if (TouchY >= this.TorST){ 
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
	
	public void doUpDownAction(boolean tmpIsTone,boolean isDown, int PointerID, byte SpriteIndex)
	{
		if(tmpIsTone)
    	{
    		this.getToneKeys()[SpriteIndex].setPressed(isDown);
    		if(!this.getTouchIDs().containsKey(PointerID))
    		{
    			this.getTouchIDs().put(PointerID,this.getToneKeys()[SpriteIndex]);
    		}
    		else
    		{
    			this.getTouchIDs().remove(PointerID);
    			this.getTouchIDs().put(PointerID,this.getToneKeys()[SpriteIndex]);
    		}
    	}
    	else
    	{
    		this.getSemitoneKeys()[SpriteIndex].setPressed(isDown);
    		if(!this.getTouchIDs().containsKey(PointerID))
    		{
    			this.getTouchIDs().put(PointerID,this.getSemitoneKeys()[SpriteIndex]);
    		}
    		else
    		{
    			this.getTouchIDs().remove(PointerID);
    			this.getTouchIDs().put(PointerID,this.getSemitoneKeys()[SpriteIndex]);
    		}
    	}
	}
	
	public void doMoveAction(boolean tmpIsTone, int PointerID, byte SpriteIndex)
	{
     	if(tmpIsTone)
    	{
    		this.getToneKeys()[SpriteIndex].setPressed(true);
    		if(!this.getTouchIDs().containsKey(PointerID))
    		{
    			this.getTouchIDs().put(PointerID,this.getToneKeys()[SpriteIndex]);
    		}
    		else
    		{
    			if(!this.getTouchIDs().get(PointerID).equals(this.getToneKeys()[SpriteIndex]))
    			{
    				if(this.getTouchIDs().get(PointerID).getIsTone())
    				{
    					this.getToneKeys()[this.getTouchIDs().get(PointerID).getSpriteIndex()].setPressed(false);
    				}
    				else
    				{
        				this.getSemitoneKeys()[this.getTouchIDs().get(PointerID).getSpriteIndex()].setPressed(false);
    				}
    			}
    			this.getTouchIDs().remove(PointerID);
    			this.getTouchIDs().put(PointerID,this.getToneKeys()[SpriteIndex]);
    		}
    	}
    	else
    	{
    		this.getSemitoneKeys()[SpriteIndex].setPressed(true);
    		if(!this.getTouchIDs().containsKey(PointerID))
    		{
    			this.getTouchIDs().put(PointerID,this.getSemitoneKeys()[SpriteIndex]);
    		}
    		else
    		{
    			if(!this.getTouchIDs().get(PointerID).equals(Piano.this.getSemitoneKeys()[SpriteIndex]))
    			{
    				if(this.getTouchIDs().get(PointerID).getIsTone())
    				{
        				this.getToneKeys()[this.getTouchIDs().get(PointerID).getSpriteIndex()].setPressed(false);
    				}
    				else
    				{
        				this.getSemitoneKeys()[this.getTouchIDs().get(PointerID).getSpriteIndex()].setPressed(false);
    				}
    			}
    			this.getTouchIDs().remove(PointerID);
    			this.getTouchIDs().put(PointerID,this.getSemitoneKeys()[SpriteIndex]);
    		}
    	}
	}
	
	public void processKeys()
	{
		MIDIMessage tmpMIDI;

		for (int i=0;i<42;i++)
		{
			if(this.getToneKeys()[i].getPressed())
			{
				if(this.getTones().getChild(i).isVisible())
				{
					this.getTones().getChild(i).setVisible(false);
					this.getMiniPiano().getTones().getChild(i).setVisible(false);
					tmpMIDI=new MIDIMessage((byte)0x90,(byte)0x00,this.getToneKeys()[i].getMIDI(),(byte)0x7F);
					if(Record.getIsRecording())
					{
						Record.saveMIDI(this.mActiveTrack, tmpMIDI);
					}
					this.getInstrument().processMIDI(tmpMIDI);
				}
			}
			else
			{
				if(!this.getTones().getChild(i).isVisible())
				{
					this.getTones().getChild(i).setVisible(true);
					this.getMiniPiano().getTones().getChild(i).setVisible(true);
					tmpMIDI=new MIDIMessage((byte)0x80,(byte)0x00,this.getToneKeys()[i].getMIDI(),(byte)0x7F);
					if(Record.getIsRecording())
					{
						Record.saveMIDI(this.mActiveTrack, tmpMIDI);
					}
					this.getInstrument().processMIDI(tmpMIDI);
				}
			}
		}
		
		for (int i=0;i<30;i++)
		{
			if(this.getSemitoneKeys()[i].getPressed())
			{
				if(this.getST().getChild(i).isVisible())
				{
					this.getST().getChild(i).setVisible(false);
					this.getMiniPiano().getST().getChild(i).setVisible(false);
					tmpMIDI=new MIDIMessage((byte)0x90,(byte)0x00,this.getSemitoneKeys()[i].getMIDI(),(byte)0x7F);
					if(Record.getIsRecording())
					{
						Record.saveMIDI(this.mActiveTrack, tmpMIDI);
					}
					this.getInstrument().processMIDI(tmpMIDI);
				}
			}
			else
			{
				if(!this.getST().getChild(i).isVisible())
				{
					this.getST().getChild(i).setVisible(true);
					this.getMiniPiano().getST().getChild(i).setVisible(true);
					tmpMIDI=new MIDIMessage((byte)0x80,(byte)0x00,this.getSemitoneKeys()[i].getMIDI(),(byte)0x7F);
					if(Record.getIsRecording())
					{
						Record.saveMIDI(this.mActiveTrack, tmpMIDI);
					}
					this.getInstrument().processMIDI(tmpMIDI);
				}
			}
		}
	}
	
	public void showAction(byte midi,boolean press)
	{
		boolean isTone;
		byte SpriteIndex;
		
		isTone=PianoMath.isMIDITone(midi);
		SpriteIndex=PianoMath.MIDI2SpriteIndex(midi, isTone);
		
		if(press)
		{
			if(isTone)
			{
				this.getTones().getChild(SpriteIndex).setVisible(false);
				this.getMiniPiano().getTones().getChild(SpriteIndex).setVisible(false);
			}
			else
			{
				this.getST().getChild(SpriteIndex).setVisible(false);
				this.getMiniPiano().getST().getChild(SpriteIndex).setVisible(false);
			}
		}
		else
		{
			if(isTone)
			{
				this.getTones().getChild(SpriteIndex).setVisible(true);
				this.getMiniPiano().getTones().getChild(SpriteIndex).setVisible(true);
			}
			else
			{
				this.getST().getChild(SpriteIndex).setVisible(true);
				this.getMiniPiano().getST().getChild(SpriteIndex).setVisible(true);
			}
		}
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
	
	public void setToneKeys(Key t[])
	{
		this.tonekeys=t;
	}
	
	public void setSemitoneKeys(Key st[])
	{
		this.semitonekeys=st;
	}
	
	public void setTouchIDs(Hashtable<Integer,Key> t)
	{
		this.TouchIDs=t;
	}

	public void setInstrument(Instrument i)
	{
		this.mInstrument=i;
	}
	
	public void setActiveTrack(Track t)
	{
		this.mActiveTrack=t;
	}
	
	public void setMiniPiano(MiniPiano p)
	{
		this.mMiniPiano=p;
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
	
	public Key[] getToneKeys()
	{
		return this.tonekeys;
	}
	
	public Key[] getSemitoneKeys()
	{
		return this.semitonekeys;
	}
	
	public Hashtable<Integer,Key> getTouchIDs()
	{
		return this.TouchIDs;
	}
	
	public Instrument getInstrument()
	{
		return this.mInstrument;
	}
	
	public Track getActiveTrack()
	{
		return this.mActiveTrack;
	}
	
	public MiniPiano getMiniPiano()
	{
		return this.mMiniPiano;
	}
}

