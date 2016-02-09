package com.justin.window.dialog;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.justin.function.Function;
import com.justin.window.MainWindow;

public class AddFunctionPrompt extends JDialog{
	
	JButton b;
	JButton colorButton;
	JButton cancel;
	JLabel d;
	JTextField t;
	MainWindow mw;
	
	Color funcColor = Color.red;

	public AddFunctionPrompt(MainWindow mw){
		super(mw,true);
		this.mw = mw;
		
		setLocation(mw.getX()+mw.getWidth()/2-400/2,mw.getY()+mw.getHeight()/2-300/2);
		setTitle("Add new function");
		setSize(400,300);
		setLayout(new FlowLayout());
		
		JPanel p = new JPanel(new GridLayout(0,1));
		add(p);
	
		b = new JButton("Submit");
		colorButton = new JButton("Chose Color");
		d = new JLabel("");
		t = new JTextField();
		
		t.addKeyListener(new EnterListener());
		
		ButtonListener bl = new ButtonListener();
		
		b.addActionListener(bl);
		colorButton.addActionListener(bl);
		t.setPreferredSize(new Dimension(getWidth()-20,25));
		
		p.add(d);
		p.add(t);
		p.add(colorButton);
		p.add(b);
		
		setVisible(true);
	}
	
	private void closeWindow(){
		dispose();
	}
	
	private AddFunctionPrompt getFrame(){
		return this;
	}
	
	private class EnterListener implements KeyListener{

		@Override
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_ENTER){
				mw.getFunctionController().addFunction(new Function(t.getText(),funcColor));
				mw.repaint();
					
				closeWindow();
			}
		}

		@Override
		public void keyReleased(KeyEvent arg0) {}

		@Override
		public void keyTyped(KeyEvent arg0) {}
		
	}
	
	private class ButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == colorButton){
				funcColor = JColorChooser.showDialog(getFrame(),"Chose Function Color",Color.red);
			}else if(e.getSource() == b){

				mw.getFunctionController().addFunction(new Function(t.getText(),funcColor));
				mw.repaint();
					
				closeWindow();
			}
		}		
	}
}