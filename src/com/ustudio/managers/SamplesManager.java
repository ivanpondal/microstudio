package com.ustudio.managers;

import java.io.IOException;
import java.util.Hashtable;

import org.anddev.andengine.audio.sound.SoundFactory;

import android.content.res.AssetManager;
import android.os.AsyncTask;

import com.ustudio.audio.Note;
import com.ustudio.loading.LoadingScreen;
import com.ustudio.main.MainActivity;

public class SamplesManager {
	private Hashtable<String,Note[]> mSamples;
	
	public SamplesManager()
	{
		this.mSamples=new Hashtable<String,Note[]>();
	}
	
	public boolean isLoaded(String str_name)
	{
		String name=str_name.toLowerCase();
		
		if(this.mSamples.containsKey(name))
		{
			return true;
		}
		return false;
	}
	
	public void removeSamples(String str_name)
	{
		String name=str_name.toLowerCase();
		
		this.mSamples.remove(name);
	}
	
	public void loadSamples(String str_name, byte first_midi, byte last_midi,byte samples, LoadingScreen loadscreen)
	{
		Note[] tmp_notes;
		
		AssetManager SamplesDir = MainActivity.getInstance().getAssets();
		String[] list=null;
		String name=str_name.toLowerCase();

		try {
			list=SamplesDir.list("sfx/"+name);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	    SoundFactory.setAssetBasePath("sfx/"+name+"/");
	    
		tmp_notes=new Note[128];
		
    	for(byte i=first_midi;i<(last_midi+1);i++)//using -129 because it overflows as 128 to -128
		{
			tmp_notes[i]=new Note(i,samples,list);
			loadscreen.setLoaded((byte)(loadscreen.getLoaded()+1));
			loadscreen.updateProgress();
		}   	
    	
    	this.mSamples.put(name, tmp_notes);
	 }
	
	public void setSamples(Hashtable<String,Note[]> s)
	{
		this.mSamples=s;
	}
	
	public Hashtable<String,Note[]> getSamples()
	{
		return this.mSamples;
	}
}
