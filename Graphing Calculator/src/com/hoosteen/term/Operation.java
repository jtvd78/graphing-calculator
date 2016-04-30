package com.hoosteen.term;

public enum Operation {
	
	MULTIPLY("*"), SUBTRACTION("-"), ADDITION("+"), DIVISION("/"), EXPONENT("^");
	
	String symbol;
	
	private Operation(String symbol){
		this.symbol = symbol;
	}
	
	public String toString(){
		return symbol;
	}	
}