package com.ustudio.usb;

import android.util.Log;

import com.ustudio.audio.Instrument;
import com.ustudio.main.MainActivity;
import com.ustudio.midi.MIDIMessage;
import com.ustudio.project.Project;
import com.ustudio.project.Track;
import com.ustudio.scenes.FreePlay;

public class USBAction {
	public static void processMIDI(MIDIMessage msg)
	{
		Project tmpProject=MainActivity.getInstance().getProject();
		if(tmpProject!=null)
		{
			if(tmpProject.getActiveTrack()!=null)
			{
				if(MainActivity.getInstance().getSceneManager().getCurrentScene().equals(FreePlay.getInstance()))
				{
					Track tmpTrack=tmpProject.findActiveTrack();
					Instrument tmpIns=tmpTrack.getInstr();
					Log.d("Piano", msg.getFunction()+" "+msg.getParam1()+" "+msg.getParam2());
					tmpIns.processMIDI(msg, FreePlay.getInstance().getPiano());
				}
			}
		}
	}
	
}
