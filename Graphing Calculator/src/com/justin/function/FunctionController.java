package com.justin.function;

import java.awt.Color;
import java.util.ArrayList;

public class FunctionController {
	
	ArrayList<Function> functionList = new ArrayList<Function>();
	
	public void addFunction(Function f ){
		functionList.add(f);
	}
	
	public double getY(double x,int funct){
		return functionList.get(funct).getY(x);
	}
	
	public Color getColor(int funct){
		return functionList.get(funct).getColor();
	}
	
	public int getFunctionCount(){
		return functionList.toArray().length;
	}

	public void setFunctionColor(int num, Color color) {
		functionList.get(num).setColor(color);
	}
	
	public String getFunctionString(int num){
		return functionList.get(num).getFunctionString();
	}

	public void removeFunction(int num) {
		functionList.remove(num);
	}
}