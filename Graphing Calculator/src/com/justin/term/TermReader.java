package com.justin.term;

public class TermReader {
	
	//PEMDAS
	//Parenthesis
	//Exponents
	//Multiplication / Division
	//Addition / Subtraction

	public static Term readTerm(String s){
		
		
		
		Term out = null;
		Operation nextOperation = null;		
		s = s.replace(" ", "").toLowerCase();
		
		//Main loop
		for(int i = 0; i < s.length(); i++){
			char c = s.charAt(i);
			
			if(c == '('){
				
				//If open paren found, find corresponding closed.
				int openCount = 0;
				int closedCount = 0;
				boolean openFound = false;
				
				for(int ctr = i; ctr < s.length(); ctr++){
					char scanChar = s.charAt(ctr);
					
					if(scanChar == '('){
						openFound = true;
						openCount++;
					}else if(scanChar == ')'){
						closedCount++;
					}
					
					if(openFound && openCount == closedCount){
						out = operate(out, readTerm(s.substring(i + 1)), nextOperation);
						i = ctr;
						break;
					}
				}
				
				//No end parenthesis found. Break to end reading
				if(closedCount == 0){
					out = operate(out, readTerm(s.substring(i + 1)), nextOperation);
					break;
				}
			}else if(c == ')'){
				break;	
			}else if(c == '*'){
				nextOperation = Operation.MULTIPLY;
			}else if(c == '/'){
				nextOperation = Operation.DIVISION;
			}else if(c == '+'){
				nextOperation = Operation.ADDITION;
			}else if(c == '-'){
				nextOperation = Operation.SUBTRACTION;
			}else if(c == '^'){
				nextOperation = Operation.EXPONENT;
			}else{
				
				if(Character.isAlphabetic(c)){
					
					//TODO make a string that records any string of alphabetical characters
					out = operate(out, new Variable(c + ""), nextOperation);					
					nextOperation = null;
					
					//Its a variable
				}else{
					
					int n = Integer.parseInt(c + "");					
					out = operate(out, new Number(n), nextOperation);				
					nextOperation = null;
				}
			}
		}
		
		return out;
	}
	
	private static Term operate(Term main, Term add, Operation nextOperation){
		
		if(main == null){
			return add;
		}
		
		if(nextOperation == null){
			main.times(add);
		}else{
			switch(nextOperation){
			case MULTIPLY: main.times(add); break;
			case DIVISION: main.dividedBy(add); break;
			case ADDITION: main.plus(add); break;
			case SUBTRACTION: main.minus(add); break;
			case EXPONENT: main.toThe(add); break;
			}
		}
		
		return main;
	}
}