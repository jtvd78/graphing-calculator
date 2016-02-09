package com.justin.graphics;

import java.awt.Frame;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.justin.function.Function;

public class Integral {
	
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
}