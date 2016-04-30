package com.hoosteen.window;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class HelpWindow extends JDialog{
	public HelpWindow(MainWindow mw){
		super((MainWindow) mw,true);
		
		setTitle("About");
		setLayout(new GridLayout(0,1));
		setResizable(false);
		
		JLabel j = new JLabel("Created by Justin Van Dort", SwingConstants.CENTER);
		JLabel j2 = new JLabel("Justinvandort@gmail.com", SwingConstants.CENTER);
		add(j);
		add(j2);
		
		pack();
		setSize(getWidth()+30, getHeight()+30);
		j.setPreferredSize(new Dimension(j.getWidth(),j.getHeight()+20));
		setLocation(mw.getX()+mw.getWidth()/2-getWidth()/2,mw.getY()+mw.getHeight()/2-getHeight()/2);
		
		setVisible(true);
	}
	
	public static void show(MainWindow w){
		new HelpWindow(w);
	}
}