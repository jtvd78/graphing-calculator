package com.justin.term;

import java.util.ArrayList;

public class TrigTerm extends Term{
	
	public final static int SIN = 0;
	public final static int COS = 1;
	public final static int TAN = 2;

	int trigType;
	Term term;
	
	public TrigTerm(int trigType, Term term) {
		this.term = term;
		this.trigType = trigType;
	}

	@Override
	public boolean isVariable() {
		return false;
	}

	public ArrayList<VariableTerm> getVariables() {		
		
		ArrayList<VariableTerm> outList = term.getVariables();
		outList.addAll(super.getVariables());		
		
		return outList;
	}

	@Override
	public String getDisplayName() {
		return trigType + "(" + term.toString() + ")";
	}

	@Override
	public double getValue() {
		switch(trigType){
		case SIN: return Math.sin(term.resolve());
		case COS: return  Math.cos(term.resolve());
		case TAN: return  Math.tan(term.resolve());
		}
		return 1;
	}
}