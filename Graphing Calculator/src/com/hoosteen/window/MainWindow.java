package com.hoosteen.window;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.UIManager;

import com.hoosteen.function.FunctionController;
import com.hoosteen.window.dialog.AddFunctionPrompt;

public class MainWindow extends JFrame{
	
	public static void main(String[] args){
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		new MainWindow().run();
	}
	
	FunctionController fc;
	GraphingComp gc;
	
	public MainWindow(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800,600);
	}
	
	public void run(){		
		fc = new FunctionController();
		gc = new GraphingComp(this, fc);
		add(gc);
		
		setJMenuBar(new Bar());
		setVisible(true);
	}
	
	private void addNewFunction(){new AddFunctionPrompt(this);}
	
	public FunctionController getFunctionController(){
		return fc;
	}
	
	private void showAbout() {
		HelpWindow.show(this);
	}
	
	private class Bar extends JMenuBar{
		public Bar(){
			
			JMenu file = new JMenu("File");
			JMenu functions = new JMenu("Functions");
			JMenu options = new JMenu("Options");
			JMenu help = new JMenu("Help");
			
			file.add(new JMenuItem("Exit"));
			
			functions.add(new JMenuItem("Add New Function"));
			
			options.add(new JMenuItem("Toggle X-Lock"));
			
			help.add(new JMenuItem("About"));
			
			
			add(file);
			add(functions);
			add(options);
			add(help);		
			
			
			MenuListener l = new MenuListener();
			
			//adds Listener to each JMenuItem
			for(Component j : getComponents()){
				if(j instanceof JMenu){
					for(Component jj : ((JMenu) j).getMenuComponents()){
						if(jj instanceof JMenuItem){
							((JMenuItem) jj).addActionListener(l);
						}
					}
				}
			}
		}
		
		private class MenuListener implements ActionListener{
			public MenuListener(){
				
			}

			@Override
			public void actionPerformed(ActionEvent e) {
				String title = ((JMenuItem)e.getSource()).getText();
				
				switch(title){
				case "Exit" : System.exit(0); break;
				case "Add New Function" : addNewFunction(); break;
				case "About" : showAbout(); break;
				case "Toggle X-Lock": gc.toggleXLock(); break;
				}
			}
		}
	}	
}