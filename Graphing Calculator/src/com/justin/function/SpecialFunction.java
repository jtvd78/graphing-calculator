package com.justin.function;

import java.awt.Color;

import com.justin.term.Term;
import com.justin.term.TermReader;

public class SpecialFunction extends Function{
	
	Term mainTerm;

	public SpecialFunction(String functionString, Color c) {
		
		super(c);
		mainTerm = TermReader.readTerm(functionString);
	}

	@Override
	public double getY(double x) {
		
//		System.out.println("Before");
		mainTerm.setVariable("x", x);
		return mainTerm.resolve();
		
	}

	@Override
	public String getFunctionString() {
		return mainTerm.toString();
	}
}