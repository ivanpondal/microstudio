package com.ustudio.midi;

public class MIDIMessage {
	private byte sta_function;//4 higher bits
	private byte sta_channel;//4 lower bits
	private byte dat_param1;
	private byte dat_param2;
	private long dat_time;
	
	public MIDIMessage(byte f, byte c, byte p1, byte p2, long t)
	{
		setFunction(f);
		setChannel(c);
		setParam1(p1);
		setParam2(p2);
	}
	
	public MIDIMessage(byte f, byte c, byte p1, byte p2)
	{
		setFunction(f);
		setChannel(c);
		setParam1(p1);
		setParam2(p2);
	}
	
	public MIDIMessage(byte f, byte c, byte p1)
	{
		setFunction(f);
		setChannel(c);
		setParam1(p1);
		setParam2((byte)0);
	}
	
	public MIDIMessage(byte f, byte c)
	{
		setFunction(f);
		setChannel(c);
		setParam1((byte)0);
		setParam2((byte)0);
	}
	
	public void setFunction(byte f)
	{
		sta_function=(byte)f;
	}
	
	public void setChannel(byte c)
	{
		sta_channel=c;
	}
	
	public void setParam1(byte p1)
	{
		dat_param1=p1;
	}
	
	public void setParam2(byte p2)
	{
		dat_param2=p2;
	}
	
	public void setTime(long t)
	{
		dat_time=t;
	}
	
	public byte getFunction()
	{
		return sta_function;
	}
	
	public byte getChannel()
	{
		return sta_channel;
	}
	
	public byte getParam1()
	{
		return dat_param1;
	}
	
	public byte getParam2()
	{
		return dat_param2;
	}
	
	public long getTime()
	{
		return dat_time;
	}
}
