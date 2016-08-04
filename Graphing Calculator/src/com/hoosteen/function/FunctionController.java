package com.hoosteen.function;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;

public class FunctionController implements Iterable<Function>{
	
	ArrayList<Function> functionList = new ArrayList<Function>();
	
	public void addFunction(Function f ){
		functionList.add(f);
	}
	
	public Function getFunction(int index){
		return functionList.get(index);
	}
	
	public double getY(double x,int funct){
		return functionList.get(funct).getY(x);
	}
	
	public Color getColor(int funct){
		return functionList.get(funct).getColor();
	}
	
	public int size(){ 
		return functionList.toArray().length;
	}

	public void setFunctionColor(int num, Color color) {
		functionList.get(num).setColor(color);
	}
	
	public String getFunctionString(int num){
		return functionList.get(num).toString();
	}

	public void removeFunction(int num) {
		functionList.remove(num);
	}

	@Override
	public Iterator<Function> iterator() {
		return functionList.iterator();
	}

	public void removeFunction(Function f) {
		functionList.remove(f);
	}
}