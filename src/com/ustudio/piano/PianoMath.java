package com.ustudio.piano;

public class PianoMath {

	public static byte SelKey2SpriteIndex(int SelKey,boolean isTone){
		byte octave;
		byte noteindex;
		byte modifier=0;
		byte SpriteIndex=0;
		if(isTone)
		{
			SpriteIndex=(byte)SelKey;
		}
		else
		{
			octave=(byte)Math.floor(SelKey/14);
			noteindex=(byte)(SelKey-14*octave);
			switch(noteindex)
			{
				case 2:
					modifier=0;
					break;
				case 4:
					modifier=1;
					break;
				case 8:
					modifier=2;
					break;
				case 10:
					modifier=3;
					break;
				case 12:
					modifier=4;
					break;
			}
			SpriteIndex=(byte)(modifier+octave*5);
		}
		return SpriteIndex;
	}
	
	public static byte SelKey2MIDI(int SelKey,boolean isTone,byte midioffset){
		byte octave;
		byte noteindex;
		byte midinote;
		byte modifier=0;
		if(isTone)
		{
			octave=(byte)Math.floor(SelKey/7);
			noteindex=(byte)(SelKey-7*octave);
			switch(noteindex)
			{
				case 0:
					modifier=0;
					break;
				case 1:
					modifier=2;
					break;
				case 2:
					modifier=4;
					break;
				case 3:
					modifier=5;
					break;
				case 4:
					modifier=7;
					break;
				case 5:
					modifier=9;
					break;	
				case 6:
					modifier=11;
					break;	
			}
			
		}
		else
		{
			octave=(byte)Math.floor(SelKey/14);
			noteindex=(byte)(SelKey-14*octave);
			switch(noteindex)
			{
				case 2:
					modifier=1;
					break;
				case 4:
					modifier=3;
					break;
				case 8:
					modifier=6;
					break;
				case 10:
					modifier=8;
					break;
				case 12:
					modifier=10;
					break;
			}
		}
		midinote=(byte)(octave*12+modifier+midioffset);
		return midinote;
	}
	
	public static byte SpriteIndex2MIDI(int SpriteIndex,boolean isTone,byte midioffset)
	{
		byte octave;
		byte noteindex;
		byte modifier=0;
		byte midi=0;
		midi+=midioffset;
		if(isTone)
		{
			octave=(byte)(SpriteIndex/7);
			noteindex=(byte)(SpriteIndex-octave*7);
			switch(noteindex)
			{
				case 0:
					modifier=0;
					break;
				case 1:
					modifier=2;
					break;
				case 2:
					modifier=4;
					break;
				case 3:
					modifier=5;
					break;
				case 4:
					modifier=7;
					break;
				case 5:
					modifier=9;
					break;
				case 6:
					modifier=11;
					break;
			}
			midi+=octave*12+modifier;
		}
		else
		{
			octave=(byte)(SpriteIndex/5);
			noteindex=(byte)(SpriteIndex-octave*5);
			switch(noteindex)
			{
				case 0:
					modifier=1;
					break;
				case 1:
					modifier=3;
					break;
				case 2:
					modifier=6;
					break;
				case 3:
					modifier=8;
					break;
				case 4:
					modifier=10;
					break;
			}
			midi+=octave*12+modifier;
		}
		return midi;
	}
	
	public static byte MIDI2SpriteIndex(byte midi,boolean isTone, byte midioffset)
	{
		byte octave;
		byte noteindex;
		byte modifier=0;
		byte spriteindex;
		midi-=midioffset;
		octave=(byte)(midi/12);
		noteindex=(byte)(midi-octave*12);
		if(isTone)
		{
			switch(noteindex)
			{
				case 0:
					modifier=0;
					break;
				case 2:
					modifier=1;
					break;
				case 4:
					modifier=2;
					break;
				case 5:
					modifier=3;
					break;
				case 7:
					modifier=4;
					break;
				case 9:
					modifier=5;
					break;
				case 11:
					modifier=6;
					break;
			}
			spriteindex=(byte)(octave*7+modifier);
		}
		else
		{
			switch(noteindex)
			{
				case 1:
					modifier=0;
					break;
				case 3:
					modifier=1;
					break;
				case 6:
					modifier=2;
					break;
				case 8:
					modifier=3;
					break;
				case 10:
					modifier=4;
					break;
			}
			spriteindex=(byte)(octave*5+modifier);
		}
		return spriteindex;
	}
	
	public static boolean isMIDITone(byte midi, byte midioffset){
		byte octave;
		byte noteindex;
		midi-=midioffset;
		octave=(byte)(midi/12);
		noteindex=(byte)(midi-octave*12);
		if((noteindex==1)||(noteindex==3)||(noteindex==6)||(noteindex==8)||(noteindex==10))
		{
			return false;
		}
		return true;
	}
	
}
