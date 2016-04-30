package com.hoosteen.term;

import java.util.ArrayList;

public class LogTerm extends Term{

	Term main;
	Term base;
	
	public LogTerm(Term main, Term base) {
		this.main = main;
		this.base = base;
	}

	@Override
	public boolean isVariable() {
		return false;
	}
	
	public ArrayList<VariableTerm> getVariables() {
		
		ArrayList<VariableTerm> out = main.getVariables();
		out.addAll(base.getVariables());
		out.addAll(super.getVariables());
		
		return out;
	}

	@Override
	public String getDisplayName() {
		return "log(" + main + ", " + base + ")";
	}

	@Override
	public double getValue() {
		return Math.log(main.resolve())/Math.log(base.resolve());
	}
}