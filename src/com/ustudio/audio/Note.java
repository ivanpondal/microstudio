package com.ustudio.audio;
import java.io.IOException;

import org.anddev.andengine.audio.sound.*;

import android.util.Log;

import com.ustudio.main.MainActivity;


public class Note {
	private String str_name;
	private Sound dat_sample;
	private VolumeDecay decay;
	private byte val_midi;
	private byte val_velocity;
	private boolean val_enabled; 
	
	public Note(byte m, String[] l)//midi note, samples, instrument
	{
		setSamples(m,l);
	}
	
	public void setName(String n)
	{
		this.str_name=n;
	}
	
	public void setSamples(byte m,String[] l)
	{
		String[] files,filename;
		String str_regex;
		boolean found=false,finished=false;
		int i;
		
		try {
			files=l; //load all the files in the instruments folder
			i=0;
			while(!finished)
			{
				if(i==files.length)
				{
					finished=true;
				}
				else
				{
					str_regex=m+"_[a-zA-Z0-9#]*.ogg"; //search for the sound that belongs to the midi value asked
					if(files[i].matches(str_regex))
					{
						filename=files[i].split("_");
						this.str_name=filename[1].substring(0, filename[1].length()-4);
						this.dat_sample = SoundFactory.createSoundFromAsset(MainActivity.getInstance().getSoundManager(), MainActivity.getInstance().getApplicationContext(), files[i]);					
						found=true;
						finished=true;
					}
				}
				i++;
			}
			this.val_enabled=found;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setMidi(byte m)
	{
		this.val_midi=m;
	}
	
	public void setEnabled(boolean e)
	{
		this.val_enabled=e;
	}
	
	public void setVelocity(byte v)
	{
		this.val_velocity=v;
	}
	
	public String getName()
	{
		return this.str_name;
	}
	
	public Sound getSample()
	{
		return this.dat_sample;
	}
	
	public byte getMidi()
	{
		return this.val_midi;
	}
	
	public boolean getEnabled()
	{
		return this.val_enabled;
	}
	
	public byte getVelocity()
	{
		return this.val_velocity;
	}
	
	public void playNote(byte v, float l, float r)
	{
		float left,right;

		left=1.0f*l;
		right=1.0f*r;

		if(this.decay!=null)
		{
			
			if(this.decay.isAlive())
			{
				this.decay.stopthread();
				while(this.decay.isAlive())
				{
					//espero;
				}
			}
		}
		this.setVelocity(v);
		this.dat_sample.setVolume(right, left);
		this.dat_sample.play();
	}
	
	public void stopNote(long d)
	{
		if(d==0)
		{
			this.dat_sample.stop();
		}
		else
		{	
			this.decay=new VolumeDecay(d,this.dat_sample);
			this.decay.start();
		}
	}
	
}

