package com.hoosteen.math.term;

public class NumberTerm extends Term{
	
	double value;
	
	public NumberTerm(double value){
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