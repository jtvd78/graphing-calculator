package com.hoosteen.function;

import java.awt.Color;

import com.hoosteen.term.Term;
import com.hoosteen.term.TermReader;

public class Function {
	
	Color col;

	Term mainTerm;
	
	public Function(String functionString, Color c){
		col = c;
		mainTerm = TermReader.readTerm(functionString);
	}
	
	public Function(Term t){
		col = Color.RED;
		mainTerm = t;
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