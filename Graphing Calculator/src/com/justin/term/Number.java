package com.justin.term;

public class Number extends Term{
	
	public Number(double value){
		super(value);
	}

	@Override
	public boolean isVariable() {
		return false;
	}

	@Override
	public String getDisplayName() {
		return value + "";
	}
}