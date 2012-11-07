package com.ustudio.loading;

import java.util.Arrays;

import android.os.AsyncTask;

import com.ustudio.audio.Note;
import com.ustudio.main.MainActivity;

public class LoadSamplesTask extends AsyncTask<Object, LoadingScreen, Object[]> {
    @Override
    protected Object[] doInBackground(Object...params) {
    	Note[] tmp_notes;
		byte first_midi=Byte.parseByte(String.valueOf(params[0]));
		byte last_midi=Byte.parseByte(String.valueOf(params[1]));
		byte samples=Byte.parseByte(String.valueOf(params[2]));
		String[] list=(String[]) params[3];
		LoadingScreen loadscreen=(LoadingScreen)params[4];
		String name=String.valueOf(params[5]);
		tmp_notes=new Note[128];
    	for(byte i=first_midi;i<(last_midi+1);i++)//using -129 because it overflows as 128 to -128
		{
			tmp_notes[i]=new Note(i,samples,list);
			publishProgress(loadscreen);
		}
    	Object[] resultado={name,tmp_notes};
    	return resultado;
    }
    
    @Override
    protected void onProgressUpdate(LoadingScreen... loadscreen) {
		loadscreen[0].setLoaded(loadscreen[0].getLoaded()+1);
		loadscreen[0].updateProgress();
    }

    @Override
    protected void onPostExecute(Object[] result) {
    	String name=String.valueOf(result[0]);
    	Note[] notes=(Note[]) result[1];
    	MainActivity.getInstance().getSamplesManager().getSamples().put(name, notes);
    }
}