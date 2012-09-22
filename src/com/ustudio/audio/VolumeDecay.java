package com.ustudio.audio;
import org.anddev.andengine.audio.music.*;

import android.util.Log;

class VolumeDecay extends Thread {
	private long val_decay_ms;
	private Music dat_sample;
	private boolean stop_thread;
	private long debug_mil;
	public VolumeDecay(long d, Music s)
	{
		this.val_decay_ms=d;
		this.dat_sample=s;
		this.stop_thread=false;
	}
	
	public void stopthread()
	{
		this.stop_thread=true;
		this.debug_mil=System.currentTimeMillis();
	}
	
	public void run() {
		float lapse;
		float vol_l;
		float vol_r;
		float dec;
		long curr_mil;
		this.debug_mil=System.currentTimeMillis();
		lapse=val_decay_ms/10;
		vol_l=this.dat_sample.getLeftVolume();
		vol_r=this.dat_sample.getRightVolume();
		if(vol_l>vol_r)
		{
			dec=vol_l/lapse;
		}
		else
		{
			dec=vol_r/lapse;
		}
		curr_mil=System.currentTimeMillis();
		while((vol_l>0 || vol_r>0) && this.stop_thread==false)
		{	
			if(System.currentTimeMillis()-curr_mil>10)
			{
				vol_l-=dec;
				vol_r-=dec;
				if(vol_l<0)
				{
					vol_l=0;
				}
				if(vol_r<0)
				{
					vol_r=0;
				}
				this.dat_sample.setVolume(vol_l, vol_r);
				curr_mil=System.currentTimeMillis();
			}
		}
		this.dat_sample.pause();
		this.dat_sample.seekTo(0);
		Log.d("Piano","Tiempo en ms:"+(System.currentTimeMillis()-this.debug_mil));
	}
}

