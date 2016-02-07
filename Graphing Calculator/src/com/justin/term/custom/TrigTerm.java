package com.justin.term.custom;

import java.util.ArrayList;

import com.justin.term.Term;
import com.justin.term.Variable;

public class TrigTerm extends Term{

	Trig trigType;
	Term term;
	
	public TrigTerm(Trig trigType, Term term) {
		this.term = term;
		this.trigType = trigType;
	}

	@Override
	public boolean isVariable() {
		return false;
	}

	public ArrayList<Variable> getVariables() {		
		
		ArrayList<Variable> outList = term.getVariables();
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