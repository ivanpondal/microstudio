package com.ustudio.piano;

public class Tecla {
	private boolean presionada;
	
	public Tecla(int i){
		presionada = false;
	}
	public void setTecla(boolean p){
		presionada = p;
		
	}

	public boolean getTecla(){
		return presionada;
	}
}
