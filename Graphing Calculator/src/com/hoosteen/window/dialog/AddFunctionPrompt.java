package com.hoosteen.window.dialog;

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
import javax.swing.SwingConstants;

import com.hoosteen.function.Function;
import com.hoosteen.window.MainWindow;

public class AddFunctionPrompt extends JDialog{
	
	JButton submitButton;
	JButton colorButton;
	JButton cancel;
	JLabel redText;
	JTextField textField;
	MainWindow mw;
	
	Color funcColor = Color.red;

	public AddFunctionPrompt(MainWindow mw){
		super(mw,true);
		this.mw = mw;
				
		setTitle("Add new function");
		setLayout(new FlowLayout());
		
		JPanel innerPanel = new JPanel(new GridLayout(0,1));
		
		
		textField = new JTextField();
		submitButton = new JButton("Submit");
		colorButton = new JButton("Chose Color");
		redText = new JLabel("", SwingConstants.CENTER);
		redText.setForeground(Color.RED);
	
		
		textField.addKeyListener(new EnterListener());
		
		ButtonListener bl = new ButtonListener();
		
		submitButton.addActionListener(bl);
		colorButton.addActionListener(bl);
		textField.setPreferredSize(new Dimension(300,25));
		
		innerPanel.add(redText);
		innerPanel.add(textField);
		innerPanel.add(colorButton);
		innerPanel.add(submitButton);
		
		add(innerPanel);
		
		pack();
		
		//Center it
		this.setLocationRelativeTo(mw);
		
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
				submit();
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
			}else if(e.getSource() == submitButton){
				submit();
			}
		}		
	}
	
	private void submit(){
		
		try{
			mw.getFunctionController().addFunction(new Function(textField.getText(),funcColor));
		}catch(Exception e){
			redText.setText("Incorrect Function Format");
			return;
		}
		
		
		mw.repaint();
			
		closeWindow();
	}
	
}