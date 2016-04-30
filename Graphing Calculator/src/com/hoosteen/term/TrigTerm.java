package com.hoosteen.term;

import java.util.ArrayList;

public class TrigTerm extends Term{
	
	public enum Type{
		SIN("sin"), COS("cos"), TAN("tan");
		
		private String name;
		private Type(String name){
			this.name = name;
		}
		
		public String toString(){
			return name;
		}
	}

	Type trigType;
	Term term;
	
	public TrigTerm(Type trigType, Term term) {
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