package com.hoosteen.math;

import com.hoosteen.math.term.Term;

public class Step {
	
	Operation o;
	Term t;

	public Step(Term t, Operation o){
		this.t = t;
		this.o = o;
	}
	
	public Operation getOperation(){
		return o;
	}
	
	public Term getTerm(){
		return t;
	}
	
	public String toString(){
		return o.toString();
	}
}