package com.justin.window;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JDialog;
import javax.swing.JLabel;

public class HelpWindow extends JDialog{
	public HelpWindow(MainWindow mw){
		super((MainWindow) mw,true);
		
		setTitle("About");
		setLayout(new FlowLayout());
		setResizable(false);
		
		JLabel j = new JLabel("Made By Justin Van Dort");
		add(j);
		
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