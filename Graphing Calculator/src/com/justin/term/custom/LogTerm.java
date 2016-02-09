package com.justin.term.custom;

import java.util.ArrayList;

import com.justin.term.Term;
import com.justin.term.Variable;

public class LogTerm extends Term{

	Term main;
	Term base;
	
	public LogTerm(Term main, Term base) {
		
		System.out.println(main);
		System.out.println(base);
		
		this.main = main;
		this.base = base;
	}

	@Override
	public boolean isVariable() {
		return false;
	}
	
	public ArrayList<Variable> getVariables() {
		
		ArrayList<Variable> out = main.getVariables();
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