package com.hoosteen.function;

import java.awt.Frame;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.hoosteen.graphics.DoublePoint;
import com.hoosteen.math.term.FunctionTerm;
import com.hoosteen.math.term.NumberTerm;
import com.hoosteen.math.term.Term;
import com.hoosteen.math.term.VariableTerm;

public class Integral {
	
	private static final int INTEGRAL_PRECISION = 1000;
	
	Function f;
	double startX;
	double endX;
	
	double area;
	boolean areaFound = false;
	

	
	
	public Integral(Function f, double startX, double endX){
		this.f = f;
		this.startX = startX;
		this.endX = endX;		
	}

	public Function getFunction() {
		return f;
	}

	public double getStartX() {
		return startX;
	}

	public double getEndX() {
		return endX;
	}
	
	public double getArea(){
		
		if(!areaFound){
			areaFound = true;
			return (area = integrate(f, startX, endX, (int) Math.pow(INTEGRAL_PRECISION, 2)));			
		}		
		
		return area;
	}
	
	public double getMomentY(){
		Term t = new FunctionTerm(f, new VariableTerm("x"));
		return integrate(new Function(new VariableTerm("x").times(t)),startX, endX, INTEGRAL_PRECISION);
	}
	
	public double getMomentX(){
		Term t = new FunctionTerm(f, new VariableTerm("x"));
		return integrate(new Function(new NumberTerm(0.5).times(t.toThe(2))),startX, endX, INTEGRAL_PRECISION);
	}
	
	public DoublePoint getCenterOfGravity(){
		double area = getArea();
		return new DoublePoint(getMomentY()/area, getMomentX()/area);
	}

	public static Integral showNewIntegralPopupDialog(Frame frame, Function f) {
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0,2));
		
		panel.add(new JLabel("Left Bound"));
		panel.add(new JLabel("Right Bound"));
		
		JTextField leftBound = new JTextField();
		JTextField rightBound = new JTextField();
		
		panel.add(leftBound);
		panel.add(rightBound);
		
		
		int result = JOptionPane.showConfirmDialog(frame, panel, "Enter Bounds", JOptionPane.OK_CANCEL_OPTION);
		
		if(result == 0){
			return new Integral(f,Double.parseDouble(leftBound.getText()), Double.parseDouble(rightBound.getText()));
		}
		
		return null;
	}
	
	public static double integrate(Function f, double start, double end, int precision){
		double out = 0 ;
		
		double range = end - start;
		double changeX = range/precision;
		
		//x = x-point in pixels
		for(double x = start; x < end; x+=changeX){
			double yPointGraph =  f.getY(x);
			out += yPointGraph*changeX;
		}
		
		return out;
	}
}