package com.ustudio.piano;

public class Key {
	private boolean pressed;
	private boolean istone;
	private int spriteindex;
	
	public Key()
	{
		this.pressed = false;
		this.istone = false;
		this.spriteindex = 0;
	}
	
	public Key(boolean p,boolean t)
	{
		this.pressed = p;
		this.istone = t;
		this.spriteindex = 0;
	}
	
	public Key(boolean p,boolean t,int s)
	{
		this.pressed = p;
		this.istone = t;
		this.spriteindex = s;
	}
	
	public void setPressed(boolean p){
		this.pressed = p;
		
	}
	
	public void setIsTone(boolean t){
		this.istone = t;
		
	}
	
	public void setSpriteIndex(int s){
		this.spriteindex = s;
		
	}
	
	public boolean getPressed(){
		return this.pressed;
	}
	
	public boolean getIsTone(){
		return this.istone;
	}
	
	public int getSpriteIndex(){
		return this.spriteindex;
	}
}
