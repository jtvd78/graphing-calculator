package com.justin.function;

import java.awt.Color;

import com.justin.term.Term;
import com.justin.term.TermReader;

public class Function {
	
	Color col;
	Term mainTerm;
	
	public Function(Term t){
		col = Color.RED;
		mainTerm = t;
	}
	
	public Function(String functionString, Color c){
		col = c;
		mainTerm = TermReader.readTerm(functionString);
	}	
	
	public double getY(double x){
		mainTerm.setVariable("x", x);
		return mainTerm.resolve();
	}

	public String toString(){
		return mainTerm.toString();
	}
	
	public Color getColor(){
		return col;
	}
	
	public void setColor(Color color) {
		col = color;
	}
}