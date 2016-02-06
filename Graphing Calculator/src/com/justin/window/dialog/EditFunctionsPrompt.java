package com.justin.window.dialog;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;

import com.justin.function.FunctionController;
import com.justin.window.MainWindow;

public class EditFunctionsPrompt extends JDialog{
	
	int WIDTH = 400;
	int HEIGHT = 300;
	
	JDialog frame;
	MainWindow mw;
	
	public EditFunctionsPrompt(MainWindow mw){
		super(mw,false);
		frame = this;
		this.mw = mw;
		
		setLocation(mw.getX()+mw.getWidth()/2-WIDTH/2,mw.getY()+mw.getHeight()/2-HEIGHT/2);
		setTitle("Edit functions");
		setSize(WIDTH,HEIGHT);
		setLayout(new FlowLayout());
		
		
		JScrollPane sp = new JScrollPane();
		sp.setPreferredSize(new Dimension(getWidth(),getHeight()));
		sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		sp.setBackground(Color.green);
		JPanel functionPanel = new JPanel();
		functionPanel.setLayout(new GridLayout(0,2));
		functionPanel.setBackground(Color.YELLOW);
		
		sp.getViewport().add(functionPanel);
		
		
		for(int c = 0; c < mw.getFunctionController().getFunctionCount(); c++){
			ColorPanel cp = new ColorPanel(mw.getFunctionController().getColor(c),c);
			cp.setBorder(new LineBorder(Color.black, 2));
			JLabel l = new JLabel();
			
			l.setText(mw.getFunctionController().getFunctionString(c));
			l.setBorder(new LineBorder(Color.black, 2));
			functionPanel.add(cp);
			functionPanel.add(l);
			
		}
		
		getContentPane().add(sp);
		pack();
		functionPanel.setPreferredSize(new Dimension(50+400,mw.getFunctionController().getFunctionCount()*50));
		System.out.println(mw.getFunctionController().getFunctionCount()*50);
		setVisible(true);
	}
	
	class ColorPanel extends JComponent{
		
		public Color color;
		public int num;
		public ColorPanel(Color c,int num){
			color = c;
			this.num = num;
			addMouseListener(new Listener());
			setPreferredSize(new Dimension(50,50));
		}
		
		public void paintComponent(Graphics g){
			g.setColor(color);
			g.fillRect(0, 0, getWidth(),getHeight());
		}
		
		private class Listener implements MouseListener{
			
			public void mousePressed(MouseEvent e) {
				if(e.getButton() == 1){
					Color newCol;
					if((newCol = JColorChooser.showDialog(frame,"Chose Function Color",color)) != null){
						color = newCol;
					}
					
					mw.getFunctionController().setFunctionColor(num, color);
					mw.repaint();
					repaint();
				}else if(e.getButton() == 3){
					mw.getFunctionController().removeFunction(num);
					mw.repaint();
					repaint();
				}				
			}

			public void mouseReleased(MouseEvent arg0) {}
			public void mouseClicked(MouseEvent arg0) {}
			public void mouseEntered(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {}
			
		}
	}
}