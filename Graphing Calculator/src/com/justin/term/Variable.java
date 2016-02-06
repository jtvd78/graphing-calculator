package com.justin.term;
public class Variable extends Term{

	String name;
	
	public Variable(String name) {
		super(1);
		this.name = name;
	}
	
	public void setValue(double value){
		this.value = value;
	}

	@Override
	public boolean isVariable() {
		return true;
	}
	
	public String getName(){
		return name;
	}

	@Override
	public String getDisplayName() {
		return name;
	}
}