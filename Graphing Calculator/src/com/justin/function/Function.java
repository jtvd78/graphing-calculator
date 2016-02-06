package com.justin.function;

import java.awt.Color;

public abstract class Function {
	
	Color col;
	
	public abstract double getY(double x);
	public abstract String getFunctionString();
	
	public Function(Color c){
		col = c;
	}
	
	public Color getColor(){
		return col;
	}
	
	public void setColor(Color color) {
		col = color;
	}
}