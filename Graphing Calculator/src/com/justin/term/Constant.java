package com.justin.term;

public class Constant extends Number{

	String name;
	
	public final static int e = 0;
	public final static int pi = 1;
	
	private Constant(double value, String name) {
		super(value);
		this.name = name;
	}
	
	@Override
	public String getDisplayName(){
		return name;
	}
	
	public static Constant getConstant(int constant){
		switch(constant){
		case e: return new Constant(Math.E, "e");
		case pi:return new Constant(Math.PI, "π");
		}
		return null;
	}
}