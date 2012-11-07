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
	
	public void loadSamples(String str_name, byte first_midi, byte last_midi,byte samples, LoadingScreen loadscreen,String loadtext)
	{
		Note[] tmp_notes;
		loadscreen.setText(loadtext);
		loadscreen.setTotal((last_midi+1)-first_midi);
		loadscreen.setLoaded(0);
		loadscreen.refreshText();
		AssetManager SamplesDir = MainActivity.getInstance().getAssets();
		String[] list=null;
		String name=str_name.toLowerCase();
        LoadSamplesTask loadTask;
		try {
			list=SamplesDir.list("sfx/"+name);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		tmp_notes=new Note[128];
		
	    SoundFactory.setAssetBasePath("sfx/"+name+"/");
	    
	    loadTask= new LoadSamplesTask().execute(void, url2, url3);
	        

		
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
