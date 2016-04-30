package com.hoosteen.term;
public class VariableTerm extends Term{

	String name;
	double value;
	
	public VariableTerm(String name) {
		this.name = name;
		
		//Default
		value = 1;
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

	@Override
	public double getValue() {
		return value;
	}
}