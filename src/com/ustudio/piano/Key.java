package com.ustudio.piano;

public class Key {
	private boolean pressed;
	private boolean moving;

	public Key()
	{
		this.pressed = false;
		this.moving = false;
	}
	
	public Key(boolean p,boolean m)
	{
		this.pressed=p;
		this.moving=m;
	}
	
	public void setPressed(boolean p){
		this.pressed = p;
		
	}

	public void setMoving(boolean m){
		this.moving = m;
		
	}
	
	public boolean getPressed(){
		return this.pressed;
	}
	
	public boolean getMoving(){
		return this.moving;
	}
}
