package com.hoosteen.term;

public class ConstantTerm extends NumberTerm{

	String name;
	
	public final static int e = 0;
	public final static int pi = 1;
	
	private ConstantTerm(double value, String name) {
		super(value);
		this.name = name;
	}
	
	@Override
	public String getDisplayName(){
		return name;
	}
	
	public static ConstantTerm getConstant(int constant){
		switch(constant){
		case e: return new ConstantTerm(Math.E, "e");
		case pi:return new ConstantTerm(Math.PI, "π");
		}
		return null;
	} 
}