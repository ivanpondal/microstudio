package com.ppp.piano;

public class Teclas {
	
	private int teclasXvieja;
	private boolean presionada;
	
	public Teclas(int i){
		presionada = false;
		teclasXvieja = i;
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
