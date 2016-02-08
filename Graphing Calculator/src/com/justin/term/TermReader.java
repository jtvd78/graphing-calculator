package com.justin.term;

// log(1/x,x) doesnt work
// log(sin(x)^2,2) doesnt work

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
				
				int closingParen = getClosingParenIndex(i, s);
				out = operate(out, readTerm(s.substring(i + 1)), nextOperation);
				
				//Move outer loop to the closing paren.
				i = closingParen;
				
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
					
					String letters = getFirstAlphabeticString(i,s);
					i += letters.length() - 1;
					
					if(isFunctionString(letters)){
						int closingParen = getClosingParenIndex(i,s);						
						String functionArgumentsString = s.substring(i + 2, closingParen + 1);
						
						//Move outer loop to the closing paren.
						i = closingParen;
						
						out = operate(out, getFunctionTerm(letters, readFunctionArguments(functionArgumentsString)), nextOperation);;
					}else{
						
						//Constants
						
						Term constant;
						if((constant = getConstant(letters)) != null){
							out = operate(out, constant,nextOperation);
						}else{
							out = operate(out, new VariableTerm(letters), nextOperation);
						}			
					}				
					
				}else{
					//Its a number
					
					String numbers = getFirstNumericString(i,s);
					i += numbers.length() - 1;					
					
					double d = Double.parseDouble(numbers);
					out = operate(out, new NumberTerm(d), nextOperation);
				}				
				
				nextOperation = null;
			}
		}
		
		return out;
	}
	
	private static Term[] readFunctionArguments(String s){
		
		String[] arguments = s.split(",");
		Term[] out = new Term[arguments.length];
		
		int ctr = 0;
		for(String arg : arguments){
			out[ctr] = readTerm(arg);
			ctr++;
		}
		return out;
	}
	
	private static int getClosingParenIndex(int start, String s){
		//If open paren found, find corresponding closed.
		int openCount = 0;
		int closedCount = 0;
		boolean openFound = false;
		
		for(int ctr = start; ctr < s.length(); ctr++){
			char scanChar = s.charAt(ctr);
			
			if(scanChar == '('){
				openFound = true;
				openCount++;
			}else if(scanChar == ')'){
				closedCount++;
			}
			
			if(openFound && openCount == closedCount){
				return ctr;
			}
		}
		
		return s.length() - 1;
	}
	
	static String[] functionStringArr = {
			"sin", "cos", "tan", "log"
	};
	
	private static boolean isFunctionString(String s){
		for(String compare : functionStringArr){
			if(s.equals(compare)){
				return true;
			}
		}
		return false;
	}
	
	private static Term getConstant(String s){
		
		switch(s){
		case "e": return ConstantTerm.getConstant(ConstantTerm.e);
		case "pi": return ConstantTerm.getConstant(ConstantTerm.pi);
		}
		return null;
	}
	
	private static Term getFunctionTerm(String function, Term[] argumentList){	
		
		switch(function){
		case "sin": return new TrigTerm(TrigTerm.SIN, argumentList[0]);
		case "cos": return new TrigTerm(TrigTerm.COS, argumentList[0]);
		case "tan": return new TrigTerm(TrigTerm.TAN, argumentList[0]);
		case "log": return new LogTerm(argumentList[0], argumentList[1]);
		}
		return null;
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
	
	private static String getFirstAlphabeticString(int start, String s){		
		
		int endIndex = start;
		for(int i = start; i < s.length(); i++){
			
			if(Character.isAlphabetic(s.charAt(i))){
				endIndex = i;
			}else{
				break;
			}
		}
		
		return s.substring(start, endIndex + 1 );
	}
	
	private static String getFirstNumericString(int start, String s){
		int endIndex = start;
		for(int i = start; i < s.length(); i++){
			
			if(isNumeric(s.charAt(i))){
				endIndex = i;
			}else{
				break;
			}
		}
		
		return s.substring(start, endIndex + 1 );
	}
	
	private static boolean isNumeric(char c){
		switch(c){
		case '1':
		case '2':
		case '3':
		case '4':
		case '5':
		case '6':
		case '7':
		case '8':
		case '9':
		case '0':
		case '.': return true;
		}
		return false;
	}
}