package com.justin.term.custom;

import java.util.ArrayList;

import com.justin.term.Term;
import com.justin.term.Variable;

public class LogTerm extends Term{

	Term main;
	Term base;
	
	public LogTerm(Term main, Term base) {
		super(1);
		this.main = main;
		this.base = base;
	}
	
	@Override
	public double resolve(){
		return Math.log(main.resolve())/Math.log(base.resolve());
	}

	@Override
	public boolean isVariable() {
		return false;
	}
	
	public ArrayList<Variable> getVariables() {
		
		ArrayList<Variable> out = main.getVariables();
		out.addAll(base.getVariables());
		
		return out;
	}

	@Override
	public String getDisplayName() {
		return "log(" + main.getDisplayName() + ", " + base.getDisplayName() + ")";
	}
}