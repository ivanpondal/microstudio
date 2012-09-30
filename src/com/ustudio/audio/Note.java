package com.ustudio.audio;
import java.io.IOException;

import org.anddev.andengine.audio.sound.*;

import android.util.Log;

import com.ustudio.main.MainActivity;


public class Note {
	private String str_name;
	private Sound[] dat_samples;
	private VolumeDecay decay;
	private byte val_midi;
	private byte val_velocity;
	private boolean val_enabled; 
	
	public Note(byte m, byte s, String n, String[] l)//midi note, samples, instrument
	{
		setSamples(m,s,n,l);
	}
	
	public void setName(String n)
	{
		this.str_name=n;
	}
	
	public void setSamples(byte m, byte s, String n,String[] l)
	{
		String[] files,filename;
		String str_regex;
		boolean found=false,finished=false;
		int i;
		long milis;
		
		milis=System.currentTimeMillis();
		
		SoundFactory.setAssetBasePath("sfx/"+n+"/");
		this.dat_samples=new Sound[s];
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
					str_regex=m+"_[_a-zA-Z0-9#]*.ogg"; //search for the sound that belongs to the midi value asked
					if(files[i].matches(str_regex))
					{
						found=true;
						finished=true;
					}
				}
				i++;
			}
			this.val_enabled=found;
			if(found)
			{
				for(byte samp=0;samp<s;samp++)
				{
					i=0;
					found=false;
					finished=false;
					while(!finished)
					{
						if(i==files.length)
						{
							finished=true;
						}
						else
						{
							str_regex=m+"_"+samp+"_[a-zA-Z0-9#]*.ogg"; //search for the sound's samples
							if(files[i].matches(str_regex))
							{
								found=true;
								finished=true;
								filename=files[i].split("_");
								this.str_name=filename[2].substring(0, filename[2].length()-4);
								this.dat_samples[samp] = SoundFactory.createSoundFromAsset(MainActivity.getInstance().getSoundManager(), MainActivity.getInstance().getApplicationContext(), files[i]);
							}
							i++;
						}
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.d("Piano","time in ms:"+(System.currentTimeMillis()-milis));
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
	
	public Sound[] getSamples()
	{
		return this.dat_samples;
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
		float samplerange,left,right;
		byte sample;

		samplerange=(float)Math.ceil(128/(float)dat_samples.length);
		sample=(byte)Math.ceil(v/samplerange);
		sample--;
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
		this.dat_samples[sample].setVolume(right, left);
		this.dat_samples[sample].play();
	}
	
	public void stopNote(long d)
	{
		byte sample=0;
		float samplerange;

		samplerange=(float)Math.ceil(128/(float)dat_samples.length);
		sample=(byte)Math.ceil(this.getVelocity()/samplerange);
		sample--;
		
		if(d==0)
		{
			this.dat_samples[sample].stop();
		}
		else
		{	
			this.decay=new VolumeDecay(d,this.dat_samples[sample]);
			this.decay.start();
		}
	}
	
}

