package com.justin.term;

import java.util.ArrayList;

import com.justin.function.Function;

public class FunctionTerm extends Term{
	
	Function f;
	Term mainTerm;
	
	public FunctionTerm(Function f, Term mainTerm){		
		this.f = f;
		this.mainTerm = mainTerm;
	}

	@Override
	public double getValue() {
		return f.getY(mainTerm.getValue());
	}
	
	@Override
	public ArrayList<VariableTerm> getVariables(){
		
		ArrayList<VariableTerm> out = mainTerm.getVariables();
		out.addAll(super.getVariables());
		
		return out;
	}

	@Override
	public String getDisplayName() {
		return f.toString();
	}

	@Override
	public boolean isVariable() {
		return false;
	}
}