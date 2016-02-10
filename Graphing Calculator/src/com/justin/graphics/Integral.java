package com.justin.graphics;

import java.awt.Frame;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.justin.function.Function;
import com.justin.term.FunctionTerm;
import com.justin.term.NumberTerm;
import com.justin.term.Term;
import com.justin.term.VariableTerm;

public class Integral {
	
	private static final int INTEGRAL_PRECISION = 1000;
	
	Function f;
	double startX;
	double endX;
	
	public Integral(Function f, double startX, double endX){
		this.f = f;
		this.startX = startX;
		this.endX = endX;		
	}

	public Function getF() {
		return f;
	}

	public double getStartX() {
		return startX;
	}

	public double getEndX() {
		return endX;
	}
	
	public double getArea(){
		return integrate(f, startX, endX);
	}
	
	public double getMomentY(){
		Term t = new FunctionTerm(f, new VariableTerm("x"));
		return integrate(new Function(new VariableTerm("x").times(t)),startX, endX);
	}
	
	public double getMomentX(){
		Term t = new FunctionTerm(f, new VariableTerm("x"));
		return integrate(new Function(new NumberTerm(0.5).times(t.toThe(2))),startX, endX);
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
	
	public static double integrate(Function f, double start, double end){
		double out = 0;
		
		double range = end - start;
		double changeX = range/INTEGRAL_PRECISION;
		
		//x = x-point in pixels
		for(double x = start; x < end; x+=changeX){
			double yPointGraph =  f.getY(x);
			out += yPointGraph*changeX;
		}
		
		return out;
	}
}