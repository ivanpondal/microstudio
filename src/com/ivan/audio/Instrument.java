package com.ivan.audio;

import android.util.Log;
import com.ivan.midi.MIDIMessage;

public class Instrument {
	private Note[] dat_notes;
	private String str_name;
	private float val_left_vol;
	private float val_right_vol;
	private byte val_samples;
	
	public Instrument(String n, byte s)
	{
		setName(n);
		setSamples(s);
		setNotes();
		setVolumeLeft(1.0f);
		setVolumeRight(1.0f);
	}
	
	public void setName(String n)
	{
		this.str_name=n;
	}
	
	public void setNotes()
	{
		this.dat_notes=new Note[128];
		for(byte i=0;i!=-128;i++)//using -129 because it overflows as 128 to -128
		{
			this.dat_notes[i]=new Note(i,this.val_samples,str_name.toLowerCase());
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
	
	public void setSamples(byte s)
	{
		this.val_samples=s;
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
	
	public void processMIDI(MIDIMessage msg)
	{
		switch(msg.getFunction())
		{
			case (byte)0x80://note off	
				stopMIDI(msg.getParam1(),msg.getParam2());		
				break;
			case (byte)0x90://note on
				playMIDI(msg.getParam1(),msg.getParam2());			
				break;
		}
	}
	
	public void playMIDI(byte note,byte velocity)
	{
		if(dat_notes[note].getEnabled())
		{
			dat_notes[note].playNote(velocity);
		}
	}
	
	public void stopMIDI(byte note,byte velocity)
	{
		if(dat_notes[note].getEnabled())
		{
			dat_notes[note].stopNote(velocity);
		}
	}
	
}
