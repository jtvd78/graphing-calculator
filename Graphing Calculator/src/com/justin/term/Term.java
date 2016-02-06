package com.justin.term;
import java.util.ArrayList;

public abstract class Term {
	
	protected double value;
	ArrayList<Step> stepList = new ArrayList<Step>();
	
	public Term(double value){
		this.value = value;
	}
	
	public Term plus(double next){
		return plus(new Number(next));
	}
	
	public Term plus(Term next){
		addStep(next, Operation.ADDITION);
		return this;
	}
	
	public Term dividedBy(double next){
		return dividedBy(new Number(next));
	}
	
	public Term dividedBy(Term next){
		addStep(next, Operation.DIVISION);
		return this;
	}
	
	public Term times(double next){
		return times(new Number(next));
	}
	
	public Term times(Term next){
		addStep(next, Operation.MULTIPLY);
		return this;
	}
	
	public Term minus(double next){
		return minus(new Number(next));
	}
	
	public Term minus(Term next) {
		addStep(next, Operation.SUBTRACTION);
		return this;
	}
	
	public Term toThe(double next){
		return toThe(new Number(next));
	}
	
	public Term toThe(Term next) {
		addStep(next, Operation.EXPONENT);
		return this;
	}
	
	public double resolve(){
		
		double out = value;
		
		for(Step s : stepList){
			switch(s.getOperation()){
			case MULTIPLY: out *= s.getTerm().resolve(); break;
			case SUBTRACTION: out -= s.getTerm().resolve(); break;
			case ADDITION: out += s.getTerm().resolve(); break;
			case DIVISION: out /= s.getTerm().resolve(); break;
			case EXPONENT: out = Math.pow(out, s.getTerm().resolve()); break;
			}
		}
		
		return out;
	}
	
	public void addStep(Term t, Operation o){		
		stepList.add(new Step(t,o));
	}

	public ArrayList<Variable> getVariables() {
		
		
		//If this is the last step in a sequence....
		if(stepList.isEmpty()){
			
			if(isVariable()){
				ArrayList<Variable> out = new ArrayList<Variable>();
				out.add((Variable)this);
				return out;
			}else{
				return new ArrayList<Variable>();
			}
		}	
		
		//If this is not the last step...
		
		ArrayList<Variable> outList = stepList.get(0).getTerm().getVariables();
		
		for(int i = 1; i < stepList.size(); i++){
			outList.addAll(stepList.get(i).getTerm().getVariables());
		}
		
		if(isVariable()){
			outList.add((Variable)this);
		}
		
		return outList;
	}
	
	public abstract boolean isVariable();

	public void setVariable(String name, double x) {
		ArrayList<Variable> varList = getVariables();
		
		for(Variable v : varList){
			if(v.getName().equals(name)){
				v.setValue(x);
			}
		}
	}
	
	public abstract String getDisplayName();
	
	
	public String toString(){	
		
		String out = "";
		
		if(!stepList.isEmpty()){
			out += "(";
		}
		
		out += getDisplayName();
		
		for(Step s : stepList){
			out += s.getOperation().toString() +  s.getTerm().toString();
				
		}
		
		if(!stepList.isEmpty()){
			out+= ")";
		}	
		
		return out;
	}


}