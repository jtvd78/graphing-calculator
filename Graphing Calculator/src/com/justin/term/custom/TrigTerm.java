package com.justin.term.custom;

import java.util.ArrayList;

import com.justin.term.Term;
import com.justin.term.Variable;

public class TrigTerm extends Term{

	Trig trigType;
	Term term;
	
	public TrigTerm(Trig trigType, Term term) {
		super(1);
		this.term = term;
		this.trigType = trigType;
	}
	
	@Override
	public double resolve(){
		switch(trigType){
		case SIN: return Math.sin(term.resolve());
		case COS: return Math.cos(term.resolve());
		case TAN: return Math.tan(term.resolve());
		}
		return 1;
	}

	@Override
	public boolean isVariable() {
		return false;
	}

	public ArrayList<Variable> getVariables() {		
		return term.getVariables();
	}

	@Override
	public String getDisplayName() {
		return trigType + "(" + term.toString() + ")";
	}
	
	

}
