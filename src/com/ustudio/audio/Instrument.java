package com.ustudio.audio;

import java.io.IOException;

import org.anddev.andengine.audio.sound.SoundFactory;

import android.content.res.AssetManager;
import android.os.Debug;
import android.util.Log;

import com.ustudio.loading.LoadingScreen;
import com.ustudio.main.MainActivity;
import com.ustudio.midi.MIDIMessage;
import com.ustudio.piano.Piano;

public class Instrument {
	private Note[] dat_notes;
	private String str_name;
	private float val_left_vol;
	private float val_right_vol;
	private byte val_samples;
	private long val_decay;
	private byte val_first_midi;
	private byte val_last_midi;
	
	public Instrument(String n, byte s, long d, byte f, byte l)
	{
		setName(n);
		setSamples(s);
		setFirstMIDI(f);
		setLastMIDI(l);
		setDecay(d);
		setVolumeLeft(1.0f);
		setVolumeRight(1.0f);
	}
	
	public void setName(String n)
	{
		this.str_name=n;
	}
	
	public void setNotes(LoadingScreen loadscreen,String loadtext)
	{
		loadscreen.setText(loadtext);
		loadscreen.setTotal((this.val_last_midi+1)-this.val_first_midi);
		loadscreen.setLoaded(0);
		AssetManager SamplesDir = MainActivity.getInstance().getAssets();
		String[] list=null;
		String name=this.str_name.toLowerCase();
        
		try {
			list=SamplesDir.list("sfx/"+name);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.dat_notes=new Note[128];
		
	    SoundFactory.setAssetBasePath("sfx/"+name+"/");
		for(byte i=this.val_first_midi;i<(this.val_last_midi+1);i++)//using -129 because it overflows as 128 to -128
		{
			this.dat_notes[i]=new Note(i,this.val_samples,list);
			loadscreen.setLoaded(loadscreen.getLoaded()+1);
		}

	 }
	
	public void setVolumeLeft(float l)
	{
		this.val_left_vol=l;
	}
	
	public void setVolumeRight(float r)
	{
		this.val_right_vol=r;
	}
	
	public void setVolume(float l, float r)
	{
		this.val_left_vol=l;
		this.val_right_vol=r;
	}
	
	public void setSamples(byte s)
	{
		this.val_samples=s;
	}
	
	public void setDecay(long d)
	{
		this.val_decay=d;
	}
	
	public void setFirstMIDI(byte f)
	{
		this.val_first_midi=f;
	}
	
	public void setLastMIDI(byte l)
	{
		this.val_last_midi=l;
	}
	
	public String getName()
	{
		return this.str_name;
	}
	
	public Note[] getNotes()
	{
		return this.dat_notes;
	}
	
	public float getVolumeLeft()
	{
		return this.val_left_vol;
	}
	
	public float getVolumeRight()
	{
		return this.val_right_vol;
	}
	
	public byte getSamples()
	{
		return this.val_samples;
	}
	
	public long getDecay()
	{
		return this.val_decay;
	}
	
	public byte getFirstMIDI()
	{
		return this.val_first_midi;
	}
	
	public byte getLastMIDI()
	{
		return this.val_last_midi;
	}
	
	public void processMIDI(MIDIMessage msg)
	{
		switch(msg.getFunction())
		{
			case (byte)0x80://note off	
				stopMIDI(msg.getParam1());	
				break;
			case (byte)0x90://note on
				playMIDI(msg.getParam1(),msg.getParam2());	
				break;
		}
	}
	
	public void processMIDI(MIDIMessage msg, Piano p)
	{
		switch(msg.getFunction())
		{
			case (byte)0x80://note off	
				stopMIDI(msg.getParam1());	
				p.showAction(msg.getParam1(), false);
				break;
			case (byte)0x90://note on
				playMIDI(msg.getParam1(),msg.getParam2());	
				p.showAction(msg.getParam1(), true);
				break;
		}
	}
	
	public void playMIDI(byte note,byte velocity)
	{
		if(dat_notes[note]!=null)
		{
			if(dat_notes[note].getEnabled())
			{
				dat_notes[note].playNote(velocity, val_left_vol, val_right_vol);
			}
		}
	}
	
	public void stopMIDI(byte note)
	{
		if(dat_notes[note]!=null)
		{
			if(dat_notes[note].getEnabled())
			{
				dat_notes[note].stopNote(val_decay);
			}
		}
	}
	
}
