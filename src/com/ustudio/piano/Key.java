package com.ustudio.piano;

public class Key {
	private boolean pressed;

	public Key()
	{
		this.pressed = false;
	}
	
	public Key(boolean p)
	{
		this.pressed=p;
	}
	
	public void setPressed(boolean p){
		this.pressed = p;
		
	}

	public boolean getPressed(){
		return this.pressed;
	}
}
