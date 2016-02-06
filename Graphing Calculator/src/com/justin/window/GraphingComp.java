package com.justin.window;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JComponent;

import com.justin.function.FunctionController;
import com.justin.function.SpecialFunction;

public class GraphingComp extends JComponent{
	
	//--------SETTINGS--------//
	
		//False: No restriction on Current X
		//True: The current X Slider will be restricted to integers
			boolean lockCurrentX = false;
		
		//Size of circles (px) at current X location
			int circleSize = 10;
			
		//Size (px) that the tick marks will be
			int tickMarkSize = 20;
		
		//Font size
			int fontSize = 12;
			
		//Whether to draw the background grid or not
			boolean drawGrid = true;
			
		//Preferred Scale
			int prefScale = fontSize*3;
	
	//---------END SETTINGS----------//
		
	//Amount in pixels that each unit on the graph is.
	//This is used a lot in the program.
	//These variables are essential to mouse input and drawing.
	//Defaults to 'prefScale'
		double xScl = prefScale;
		double yScl = prefScale;
		
	//The amount that a single tick mark is.
	//This variable is changed in the 'updateVariables()' method...
	//so the graph does not become too cluttered
		int xAmt = 1;
		int yAmt = 1;
	
	//This object holds all of the functions that are drawn on the graph
		FunctionController functionController;
	
	//Width and Height of the window. They are updated when the program repaints; 'UpdateVariables()'
	//It is easier to use 'w' instead of 'getWidth()'
		int w = -1;
		int h = -1;
	
	//Position of origin (px) on screen.
	//They are moved to the center of the screen when the window initializes
		double originX = -1;
		double originY = -1;
		
	//Holds the location of the current X
		double currentX = 0;
	
	//If the mouse is clicked on the currentX circle, This will be true
	//This tells the program to move the current X if it being clicked on.
		boolean allowCurrentXMovement = false;
	
	//Indicates whether the screen is being drawn for the first time.
	//It is made false after the program is initialized
		boolean firstTime = true;
	
	//true if mouse is pressed
	//includes all buttons
		boolean pressed[] = new boolean[4];
	
	//position of mouse on window
		int mouseX = -1;
		int mouseY = -1;
		
	//Variables used for resizing the screen.
	//They make sure that the origin is in the same relative...
	//Spot when the screen is resized
		int oldWidth = -1;
		int oldHeight = -1;	
		
	//Constructor
	public GraphingComp(FunctionController c){
		
		//Random functions added for testing
		c.addFunction(new SpecialFunction("(x^2 + (1(x+1)))/x",Color.blue));
		c.addFunction(new SpecialFunction("x^2", Color.RED));
		this.functionController = c;
		
		//Listener is the subclass that implements all of the input interfaces
		Listener l = new Listener();
		
		addMouseMotionListener(l);
		addMouseListener(l);
		addMouseWheelListener(l);
		addComponentListener(l);
	}

	//This method is called whenever the program needs to paint the window
	public void paintComponent(Graphics g){
		updateVariables();
		
		//Stops the drawing if the window hasn't been properly initialized yet.
		if(w == -1 || h == -1) return;
		
		//Called only when the program has been first started.
		if(firstTime){
			initFirstTime();
			firstTime = false;
		}
		
		g.setFont(new Font(null,Font.PLAIN,fontSize)); //Sets the font to the font size in the settings
		
		//----START GRAPH DRAWING----//
		drawBackground(g);
		drawAxis(g);
		
		//Draw currentX line
		g.setColor(Color.white);
		g.drawLine((int)(currentX*xScl)+(int)(originX),0,(int)(currentX*xScl)+(int)(originX),h);
		
		drawFunctions(g);
		drawXOval(g);
		//-----END GRAPH DRAWING--//
	}
	
	//Does first-time initializing
	public void initFirstTime(){
		oldWidth = w;
		oldHeight = h;
		
		originX = w/2;
		originY = h/2;
	}
	
	public void updateVariables(){
		w = getWidth();
		h = getHeight();
		
		xAmt = (int)((double)(prefScale)/xScl);
		yAmt = (int)((double)(prefScale)/yScl);
		
		if(xAmt == 0){
			xAmt = 1;
		}
		if(yAmt == 0){
			yAmt = 1;
		}
	}
	
	//Draws the background
	public void drawBackground(Graphics g){
		g.setColor(Color.black);
		g.fillRect(0, 0, w,h);
	}
	
	//Draws the axis
	public void drawAxis(Graphics g){
		
		g.setColor(new Color(40,40,40)); //Gray
		
		//Edits where the loops should start at in order to have the tick marks in the right spot
		int xc = (int)(originX/xScl) %xAmt - xAmt;
		int yc = (int)(originY/yScl) %yAmt - yAmt;

		//Draws background grid
		if(drawGrid){
			for(int c = xc; c < Math.ceil(w/xScl)+1; c+=xAmt){
				g.drawLine((int)(xScl*c + originX % xScl), 0, (int)(xScl*c  + originX % xScl), h); //x
			}
			
			for(int c = yc; c < Math.ceil(h/yScl)+1; c+=yAmt){
				g.drawLine(0,(int)(yScl*c + originY % yScl), w , (int)(yScl*c  + originY % yScl)); //y
			}
		}
			
		g.setColor(Color.white);
		
		//Draws tick marks and numbers for X axis
		for(int c = xc; c < Math.ceil(w/xScl)+1; c+=xAmt){
			g.drawLine((int)(xScl*c + (int)(originX) % xScl), (int)(originY)+(tickMarkSize/2), (int)(xScl*c + (int)(originX) % xScl), (int)(originY)-(tickMarkSize/2));
			g.drawString(c-((int)((int)(originX)/xScl)) + "", (int)(xScl*c + (int)(originX) % xScl - fontSize/4), (int)(originY)+(tickMarkSize/2)+fontSize);
		}
			
		//Draws tick marks and numbers for Y axis
		for(int c = yc; c < Math.ceil(h/yScl)+1; c+=yAmt){
			g.drawLine((int)(originX)+(tickMarkSize/2),(int)(yScl*c + originY % yScl), (int)(originX)-(tickMarkSize/2), (int)(yScl*c + originY % yScl));
			g.drawString(-c+((int)(originY/yScl)) + "",(int)(originX)+(tickMarkSize/2)+fontSize/4,(int)(yScl*c + originY % yScl)+fontSize/2);
		}
		
		//Draws X and Y axis lines
		g.drawLine((int)(originX),0,(int)(originX),h); g.drawLine((int)(originX)-1,0,(int)(originX)-1,h); g.drawLine((int)(originX)+1,0,(int)(originX)+1,h);
		g.drawLine(0, (int)(originY), w, (int)(originY)); g.drawLine(0, (int)(originY)-1, w, (int)(originY)-1); g.drawLine(0, (int)(originY)+1, w, (int)(originY)+1);
	}
	
	//Draws the functions
	public void drawFunctions(Graphics g){	
		
		//X positions (px) that the graph should start and stop (edge of the screen)
		int begin = -(int)(originX);
		int end = w+begin;
		
		//Loops through every function in function controller
		for(int c = 0; c < functionController.getFunctionCount(); c++){
			g.setColor(functionController.getColor(c)); //Sets color to the graph's corresponding color
			
			//Previous x and y points.
			//The functions are graphed when by finding a point, and then drawing a line...
			//from that point to the previous point. So it is necessary to save the previous point. 
			//I defaulted them to -1000, so they would be off the screen
			int px = -1000;
			int py = -1000;
			
			//Loops from begin to end, and draws the corresponding points.  
			for(int x = begin; x < end; x++){
				int y = (int) ((functionController.getY(x/xScl,c))*yScl);	
				
				g.drawLine(px, py, x+(int)(originX),-y+(int)(originY));
				
				px = x+(int)(originX);
				py =  -y+(int)(originY);
			}
			
			//Value of Y where the current X marker is.
			double yy = functionController.getY((currentX),c); //sets yy.
			
			//Draws a circle on the function where the current X is. 
			g.fillOval((int)(currentX*xScl)+(int)(originX)-(circleSize/2),((int)((-yy*yScl))+(int)(originY))-(circleSize/2),(circleSize),(circleSize));
			g.drawString((double)(Math.round(yy*100))/100 + "",(int)(currentX*xScl)+(int)(originX) +20,((int)((-yy*yScl))+(int)(originY)));
			
			g.setColor(Color.black);
			g.drawOval((int)(currentX*xScl)+(int)(originX)-(circleSize/2),((int)((-yy*yScl))+(int)(originY))-(circleSize/2),(circleSize),(circleSize));
		}
	}
	
	//Draws Oval for the current X marker
	public void drawXOval(Graphics g){		
		//oval
		g.setColor(Color.white);
		g.fillOval((int)(currentX*xScl)+(int)(originX)-(circleSize/2),(int)(originY)-(circleSize/2),(circleSize),(circleSize));
		
		//oval border
		g.setColor(Color.black);
		g.drawOval((int)(currentX*xScl)+(int)(originX)-(circleSize/2),(int)(originY)-(circleSize/2),(circleSize),(circleSize));
		g.drawLine((int)(currentX*xScl)+(int)(originX),(int)(originY)-(circleSize/2),(int)(currentX*xScl)+(int)(originX),(int)(originY)+(circleSize/2));
		g.drawLine((int)(currentX*xScl)+(int)(originX)-(circleSize/2),(int)(originY),(int)(currentX*xScl)+(int)(originX)+(circleSize/2),(int)(originY));
	}
	
	//Gets the quadrant of a point on the screen
	public int getQuadrant(int x, int y){
		if(x > (int)(originX)){
			if(y < originY){
				return 1;
			}else if(y > originY){
				return 4;
			}else{
				System.out.println("y"); //if mouse is on y axis
			}
		}else if(x < (int)(originX)){
			if(y < originY){
				return 2;
			}else if(y > originY){
				return 3;
			}else{
				System.out.println("y2");
			}
		}else{
			System.out.println("x"); //if mouse is on x axis
		}
		return -1;
	}
	
	public void toggleXLock(){
		lockCurrentX = !lockCurrentX;
	}
	
	//This class implements every interface used for input.
	//It only contains inherited methods
	class Listener implements MouseListener, MouseWheelListener,MouseMotionListener, ComponentListener{

		//Zooms in and out when the mouse wheel is moved
		@Override
		public void mouseWheelMoved(MouseWheelEvent e) {
			if(e.getWheelRotation() > 0){
				zoomOut();
			}else{
				zoomIn();
			}
		}

		//Called when mouse button is pressed down. 
		@Override
		public void mousePressed(MouseEvent e) {
			pressed[e.getButton()] = true;
			
			//If the left mouse button is pressed, it checks to see if the mouse is in the current X circle.
			//If it is, 'allowCurrentXMovement' is made true. Actual movement is dealt with in 'mouseMoved(MouseEvent e)'
			if(pressed[1]){
				Rectangle r = new Rectangle((int)(currentX*xScl)+(int)(originX)-(circleSize/2),(int)(originY)-(circleSize/2),(circleSize),(circleSize));
				if(r.contains(new Point(mouseX,mouseY))){
					allowCurrentXMovement = true;
				}
			}
		}

		//called when mouse button is released
		@Override
		public void mouseReleased(MouseEvent e) {
			allowCurrentXMovement = false;
			pressed[e.getButton()] = false;
		}

		//Called when mouse is pressed while it is being moved
		//I just called MouseMoved(e), because I handle all of the mouse movement in that method.
		@Override
		public void mouseDragged(MouseEvent e) {
			mouseMoved(e);
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			if(pressed[1]){
				//If mouse is on current X circle, move the current X location
				if(allowCurrentXMovement){
					 currentX = (e.getX()-originX)/xScl;
					
					if(lockCurrentX){
						currentX = Math.round(currentX);
					}
				}else{
					//Allows the graph to be moved around when the Mouse Button 1 is being held down.
					originX -= mouseX-e.getX();
					originY -= mouseY-e.getY();
				}
				repaint();
			}else if(pressed[2]){
				//Middle Mouse Button				
			
			}else if(pressed[3]){
				
				//When the right mouse button is being held down, the user can adjust the x and y scale.\
				if(getQuadrant(mouseX, mouseY) != -1){
					if(!((e.getX()-originX) == 0 || (e.getY()-originY) == 0)){
						xScl -= (mouseX-e.getX())*(xScl/(e.getX()-originX));
						yScl -= (mouseY-e.getY())*(yScl/(e.getY()-originY));
					}
				}				
				
				//makes sure that the screen is not resized to be too zoomed in.
				if(yScl > h/2){
					yScl = h/2;
				}
				if(xScl > w/2){
					xScl = w/2;
				}

				//sets x and y scale to 1 if they have been set to lower than 1.
				if(xScl <= 1 || xScl == Double.NaN || xScl == Double.POSITIVE_INFINITY){
					xScl = 1;
				}
				if(yScl <= 1 || yScl == Double.NaN || yScl == Double.POSITIVE_INFINITY){
					yScl = 1;
				}
				repaint();
			}
			
			//sets mouse coordinates
			mouseX = e.getX();
			mouseY = e.getY();
		}
		
		//Called when window is resized
		@Override
		public void componentResized(ComponentEvent e) {
			
			//Keeps the origin the same relative distance from the edge of the screen.			
			originX *= ((double)(getWidth())/(double)(oldWidth));
			originY *= ((double)(getHeight())/(double)(oldHeight));
			
			oldWidth = getWidth();
			oldHeight = getHeight();
			
			repaint();
		}

		//-----START UNUSED METHODS----//
		@Override
		public void componentMoved(ComponentEvent e) {}

		@Override
		public void componentShown(ComponentEvent e) {}

		@Override
		public void componentHidden(ComponentEvent e) {}

		@Override
		public void mouseClicked(MouseEvent e) {}

		@Override
		public void mouseEntered(MouseEvent e) {}

		@Override
		public void mouseExited(MouseEvent e) {}
		//------END UNUSED METHODS----//
		
	}
	
	//Zooms In
	public void zoomIn(){
		
		double rat = (double)(xScl)/(double)(yScl);
		
		xScl*=1.25;
		yScl*=1.25;
		
		if(xScl > w/2){
			xScl = w/2;
			yScl = xScl/rat;
		}else if(yScl > h/2){
			yScl = h/2;
			xScl = rat*yScl;
		}
		repaint();
	}
	
	//Zooms out
	public void zoomOut(){
		
		double rat = (double)(xScl)/(double)(yScl);
		
		xScl*=0.8;
		yScl*=0.8;
		
		if(xScl < 1){
			xScl = 1;
			yScl = xScl/rat;
		}else if(yScl < 1){
			yScl = 1;
			xScl = rat*yScl;
		}
		repaint();
	}
}