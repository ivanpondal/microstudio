package com.ppp.piano;

public class Teclas {
	
	private int teclasXvieja;
	private boolean presionada;
	
	public Teclas(){
		presionada = false;
	}
	public void setTecla(boolean p){
		presionada = p;
	}
	public void setVieja(int t){
		teclasXvieja = t;
	}
	public int getVieja(){
		return teclasXvieja;
	}
	public boolean getTecla(){
		return presionada;
	}
}
