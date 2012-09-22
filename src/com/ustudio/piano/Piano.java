package com.ustudio.piano;


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

import java.util.Hashtable;

import com.ustudio.main.MainActivity;
import com.ustudio.audio.Instrument;
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
	private float KeyboardY;
	private float widthKeyboard;
	private float heightKeyboard;
	
	private int midioffset;
	private Hashtable<Integer,Key> TouchIDs;
	
	private Instrument mInstrument;
	
	public Piano(Scene pScene, int w, int h, Instrument Instr) {
		Key tmptonekeys[];
		Key tmpsemitonekeys[];
		Hashtable<Integer,Key> tmpTouchIDs;
		
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
		this.setMIDIOffset(24);
		this.setInstrument(Instr);
		
		tmptonekeys=new Key[49];
		tmpsemitonekeys=new Key[35];
		for (int i=0;i<49;i++)
		{
			tmptonekeys[i]=new Key(false,true,i);
		}
		
		for (int i=0;i<35;i++)
		{
			tmpsemitonekeys[i]=new Key(false,false,i);
		}
		
		this.setToneKeys(tmptonekeys);
		this.setSemitoneKeys(tmpsemitonekeys);
		
		tmpTouchIDs=new Hashtable<Integer,Key>();
		this.setTouchIDs(tmpTouchIDs);
		
		this.mTexture = new BitmapTextureAtlas(256, 1024, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mFTR_TN = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture, MainActivity.getInstance().getApplicationContext(), "gfx/Teclas/Blancas/BN.png", 0, 0);
		this.mFTR_TP = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture, MainActivity.getInstance().getApplicationContext(), "gfx/Teclas/Blancas/BP.png", 0,500);
		this.mFTR_STN = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture, MainActivity.getInstance().getApplicationContext(), "gfx/Teclas/Negras/NN.png", 99, 0);
		this.mFTR_STP = BitmapTextureAtlasTextureRegionFactory.createFromAsset((BitmapTextureAtlas) this.mTexture, MainActivity.getInstance().getApplicationContext(), "gfx/Teclas/Negras/NP.png", 99,500);
		
		MainActivity.getInstance().getEngine().getTextureManager().loadTexture(this.mTexture);
		
		Sprite touchControl = new Sprite(0,this.getKeyboardY(),this.mFTR_STN){
			public boolean onAreaTouched(final TouchEvent pAreaTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				int MIDInote,SpriteIndex,PointerID;
				boolean tmpIsTone;
				tmpIsTone=Piano.this.isTone(pTouchAreaLocalX, pTouchAreaLocalY);
				MIDInote=Piano.this.SelKey2MIDI(Piano.this.TouchX2SelKey(pTouchAreaLocalX,tmpIsTone), tmpIsTone);
				SpriteIndex=Piano.this.MIDI2SpriteIndex(MIDInote, tmpIsTone);
    			PointerID=pAreaTouchEvent.getPointerID();
    			
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
				
				Piano.this.processKeys(MIDInote);
				
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

				Sprite st = new Sprite(this.getSTWidth() + this.getSpaceST() + i*this.getTonesWidth(),this.getKeyboardY(),this.mFTR_STN);		
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
	
	public int SelKey2MIDI(int SelKey,boolean isTone){
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
		midinote=octave*12+modifier+this.getMIDIOffset();
		return midinote;
	}
	
	public boolean isMIDITone(int midi){
		int octave;
		int noteindex;
		midi-=this.getMIDIOffset();
		octave=midi/12;
		noteindex=midi-octave*12;
		if((noteindex==1)||(noteindex==3)||(noteindex==6)||(noteindex==8)||(noteindex==10))
		{
			return false;
		}
		return true;
	}
	
	public int MIDI2SpriteIndex(int midi,boolean isTone)
	{
		int octave;
		int noteindex;
		int modifier=0;
		int spriteindex;
		midi-=this.getMIDIOffset();
		octave=midi/12;
		noteindex=midi-octave*12;
		if(isTone)
		{
			switch(noteindex)
			{
				case 0:
					modifier=0;
					break;
				case 2:
					modifier=1;
					break;
				case 4:
					modifier=2;
					break;
				case 5:
					modifier=3;
					break;
				case 7:
					modifier=4;
					break;
				case 9:
					modifier=5;
					break;
				case 11:
					modifier=6;
					break;
			}
			spriteindex=octave*7+modifier;
		}
		else
		{
			switch(noteindex)
			{
				case 1:
					modifier=0;
					break;
				case 3:
					modifier=1;
					break;
				case 6:
					modifier=2;
					break;
				case 8:
					modifier=3;
					break;
				case 10:
					modifier=4;
					break;
			}
			spriteindex=octave*5+modifier;
		}
		return spriteindex;
	}
	
	public void doUpDownAction(boolean tmpIsTone,boolean isDown, int PointerID, int SpriteIndex)
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
	
	public void doMoveAction(boolean tmpIsTone, int PointerID, int SpriteIndex)
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
	
	public void processKeys(int MIDInote)
	{
		MIDIMessage tmpMIDI;
		for (int i=0;i<49;i++)
		{
			if(this.getToneKeys()[i].getPressed())
			{
				if(this.getTones().getChild(i).isVisible())
				{
					this.getTones().getChild(i).setVisible(false);
					tmpMIDI=new MIDIMessage((byte)0x90,(byte)0x00,(byte)MIDInote,(byte)0x7F);
					this.getInstrument().processMIDI(tmpMIDI);
				}
			}
			else
			{
				if(!this.getTones().getChild(i).isVisible())
				{
					this.getTones().getChild(i).setVisible(true);
					tmpMIDI=new MIDIMessage((byte)0x80,(byte)0x00,(byte)MIDInote,(byte)0x7F);
					this.getInstrument().processMIDI(tmpMIDI);
				}
			}
		}
		
		for (int i=0;i<35;i++)
		{
			if(this.getSemitoneKeys()[i].getPressed())
			{
				if(this.getST().getChild(i).isVisible())
				{
					this.getST().getChild(i).setVisible(false);
					tmpMIDI=new MIDIMessage((byte)0x90,(byte)0x00,(byte)MIDInote,(byte)0x7F);
					this.getInstrument().processMIDI(tmpMIDI);
				}
			}
			else
			{
				if(!this.getST().getChild(i).isVisible())
				{
					this.getST().getChild(i).setVisible(true);
					tmpMIDI=new MIDIMessage((byte)0x80,(byte)0x00,(byte)MIDInote,(byte)0x7F);
					this.getInstrument().processMIDI(tmpMIDI);
				}
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
	
	public void setMIDIOffset(int s)
	{
		this.midioffset=s;
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
	
	public int getMIDIOffset()
	{
		return this.midioffset;
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
}
