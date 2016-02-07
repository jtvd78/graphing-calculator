package com.justin.term;

public class Number extends Term{
	
	double value;
	
	public Number(double value){
		this.value = value;
	}

	@Override
	public boolean isVariable() {
		return false;
	}

	@Override
	public String getDisplayName() {
		return value + "";
	}

	@Override
	public double getValue() {
		return value;
	}
}