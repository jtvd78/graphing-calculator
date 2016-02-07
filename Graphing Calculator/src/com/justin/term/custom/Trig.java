package com.justin.term.custom;

public enum Trig {

	SIN("sin"), COS("cos"), TAN("tan");
	
	String name;
	
	private Trig(String name){
		this.name = name;
	}
	
	public String toString(){
		return name;
	}
}