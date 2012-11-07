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
	
	public Instrument(String n, byte s, long d, Note[] nt)
	{
		setName(n);
		setSamples(s);
		setDecay(d);
		setNotes(nt);
		setVolumeLeft(1.0f);
		setVolumeRight(1.0f);
	}
	
	public void setName(String n)
	{
		this.str_name=n;
	}
	
	public void setNotes(Note[] n)
	{
		this.dat_notes=n;
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
