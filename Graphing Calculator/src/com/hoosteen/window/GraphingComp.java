package com.hoosteen.window;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;

import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import com.hoosteen.function.Function;
import com.hoosteen.function.FunctionController;
import com.hoosteen.function.Integral;
import com.hoosteen.graphics.DoublePoint;
import com.hoosteen.graphics.GraphicsWrapper;
import com.hoosteen.graphics.Rect;

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
			int fontSize = 20;
			
		//Whether to draw the background grid or not
			boolean drawGrid = true;
			
		//Preferred Scale
			int prefScale = (int) (fontSize*2.5);
			
		//Padding on each side of the function box
			int functionBoxWidthPadding = 8;
	
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
		
	//Stores the last known values for the width and height of each function in the function box
		int functionBoxWidth;
		int functionBoxHeight;
		
	//ArrayList of integrals to be drawn
		ArrayList<Integral> integralList = new ArrayList<Integral>();
		
	//The JPopupMenu which appears when a user right clicks on a function within the function box
		FunctionPopup functionPopup;
	
	//Parent frame
		JFrame frame;
		
	//Color of Axis and Number Labels
		Color axisColor = Color.WHITE;
		
	//Color of Background grid
		Color gridColor = new Color(40,40,40);
		
		
	//Constructor
	public GraphingComp(JFrame frame, FunctionController c){
		
		this.frame = frame;
		
		//Random functions added for testing
		this.functionController = c;
		
		//Listener is the subclass that implements all of the input interfaces
		Listener l = new Listener();
		
		addMouseMotionListener(l);
		addMouseListener(l);
		addMouseWheelListener(l);
		addComponentListener(l);
	}

	//This method is called whenever the program needs to paint the window
	public void paintComponent(Graphics gOld){
		updateVariables();
		
		//Stops the drawing if the window hasn't been properly initialized yet.
		if(w == -1 || h == -1) return;
		
		//Called only when the program has been first started.
		if(firstTime){
			initFirstTime();
			firstTime = false;
		}
		
		gOld.setFont(new Font(null,Font.PLAIN,fontSize)); //Sets the font to the font size in the settings		
		
		GraphicsWrapper g = new GraphicsWrapper(gOld);
		
		//----START GRAPH DRAWING----//
		drawBackground(g);
		
		drawAxis(g);
		
		for(Integral i : integralList){
			drawIntegral(i, g);
		}	
		
		//Draw currentX line
		g.setColor(Color.white);
		g.drawLine((int)(currentX*xScl)+(int)(originX),0,(int)(currentX*xScl)+(int)(originX),h);
		
		drawFunctions(g);
		drawXOval(g);
		
		drawFunctionBox(g);
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
		
		xAmt = (int)((double)prefScale/xScl);
		yAmt = (int)((double)prefScale/yScl);
		
		if(xAmt == 0){
			xAmt = 1;
		}
		if(yAmt == 0){
			yAmt = 1;
		}
	}
	
	//Draws the background
	public void drawBackground(GraphicsWrapper g){
		g.setColor(Color.black);
		g.fillRect(0, 0, w,h);
	}
	
	//Draws the axis
	public void drawAxis(GraphicsWrapper g){
		
		g.setColor(gridColor); 
		
		//Edits where the loops should start at in order to have the tick marks in the right spot
		int xStart = (int)(originX/xScl) % xAmt - xAmt;
		int yStart = (int)(originY/yScl) % yAmt - yAmt;
		
		//String to display next to each tick
		String tickString;
		
		//Size of half a tick mark
		int halfTick = tickMarkSize/2;	

		//Draws background grid
		if(drawGrid){
			for(int xCtr = xStart; xCtr < Math.ceil(w/xScl)+1; xCtr+=xAmt){
				
				int x = (int)(xScl*xCtr + originX % xScl);
				
				//Draw grid
				g.setColor(gridColor);
				g.drawLine(x, 0, x, h);
				
				//Draw tick
				g.setColor(axisColor);		
				g.drawLine(x, (int)(originY)+halfTick, x, (int)(originY)-halfTick);
				
				//Draw text
				tickString = xCtr-(int)(originX/xScl) + "";			
				g.drawString(tickString, x, (int) originY + halfTick, GraphicsWrapper.Position.BOTTOM);
			}
			
			for(int yCtr = yStart; yCtr < Math.ceil(h/yScl)+1; yCtr+=yAmt){
				
				int y = (int)(yScl*yCtr + originY % yScl);
				
				//Draw grid
				g.setColor(gridColor);
				g.drawLine(0,y, w , y);				
				
				//Draw tick
				g.setColor(axisColor);	
				g.drawLine((int)(originX)+halfTick, y, (int)(originX)-halfTick, y);	
				
				//Draw text
				tickString = (int)(originY/yScl)-yCtr + "";			
				g.drawString(tickString, (int)(originX)+halfTick + 5, y, GraphicsWrapper.Position.RIGHT);
			}
		}		
		
		//3 Point (thickness) line
		Graphics2D g2 = (Graphics2D)g.getGraphics();
		g2.setStroke(new BasicStroke(3));
		
		//Draws X and Y axis lines
		g.drawLine((int)(originX), 0, (int)(originX), h);		
		g.drawLine(0, (int)(originY), w, (int)(originY)); 
		
		//1 Point line
		g2.setStroke(new BasicStroke(1));
	}
	
	private int xPointToPx(double xPoint){
		return (int)(xPoint*xScl + originX);
	}
	
	private int yPointToPx(double yPoint){
		return (int)(-1*yPoint*yScl + originY);
	}
	
	/**
	 * Draws an integral onto the graphics object
	 * @param f - Function to integrate
	 * @param g - Graphics object to draw on to
	 * @param startX - Starting x of the integral
	 * @param endX - Ending x of the integral
	 * @return the value of the integral
	 */
	public void drawIntegral(Integral integral, GraphicsWrapper g){	
		
		Function function = integral.getF();
		
		g.setColor(function.getColor()); //Sets color to the graph's corresponding color
		
		ArrayList<Integer> xPolyPoints = new ArrayList<Integer>();
		ArrayList<Integer> yPolyPoints = new ArrayList<Integer>();
		
		//Add initial point
		xPolyPoints.add(xPointToPx(integral.getStartX()));
		yPolyPoints.add((int)originY);	
		
		//X positions (px) that the graph should start and stop (edge of the screen)
		int begin = -(int)(originX);
		int end = w + begin;
		
		//Add middle points
		//x = x-point in pixels
		for(int x = begin; x < end; x++){
			
			double xOnGraph = x/xScl;
			
			if(xOnGraph > integral.getStartX() && xOnGraph < integral.getEndX()){
				
				double yPointGraph =  function.getY(xOnGraph);
				
				xPolyPoints.add(x + (int) originX);
				yPolyPoints.add(yPointToPx(yPointGraph));
			}
		}
		
		//Add ending point
		xPolyPoints.add(xPointToPx(integral.getEndX()));
		yPolyPoints.add((int)originY);
		
		//Convert ArrayList of Integers to array of ints
		int[] xPolyPointsArr = new int[xPolyPoints.size()];
		int[] yPolyPointsArr = new int[yPolyPoints.size()];
		
		for(int i = 0; i < xPolyPoints.size(); i++){
			xPolyPointsArr[i] = xPolyPoints.get(i);
			yPolyPointsArr[i] = yPolyPoints.get(i);
		}		
		
		//Fill the shape
		g.fillPolygon(xPolyPointsArr, yPolyPointsArr, xPolyPoints.size());		
		
		//Find the center and draw area
		g.setColor(Color.BLACK);
		DoublePoint center = integral.getCenterOfGravity();
		g.drawCenteredString(round(integral.getArea(), 3), xPointToPx(center.getX()), yPointToPx(center.getY()));
		
	}
	
	/**
	 * Rounds a double value to a number of characters, and returns the value as a string
	 * @param value Value to round
	 * @param decimals Number of characters
	 * @return a String representation of the rounded decimal
	 */
	public String round(double value, int decimals){
		int round = (int) Math.pow(10, decimals);
		
		return (double)(Math.round(value*round))/round + "";
	}
	
	//Draws the functions
	public void drawFunctions(GraphicsWrapper g){
		
		int halfCircle = circleSize/2;		
		int xLocation = xPointToPx(currentX);		
		
		//X positions (px) that the graph should start and stop (edge of the screen)
		int begin = -(int)(originX);
		int end = w+begin;
		
		Color functionColor;
		
		//Loops through every function in function controller
		for(int functionCtr = 0; functionCtr < functionController.size(); functionCtr++){
			
			functionColor = functionController.getColor(functionCtr);
			
			g.setColor(functionColor); //Sets color to the graph's corresponding color
			
			int[] xPoints = new int[end-begin];
			int[] yPoints = new int[end-begin];
			
			//x = x-point in pixels
			for(int x = begin; x < end; x++){		
				
				xPoints[x - begin] = x + (int) originX;				
				yPoints[x - begin] = yPointToPx(functionController.getY(x/xScl,functionCtr));
			}
			
			g.drawPolyline(xPoints, yPoints, xPoints.length);
			
			//Value of Y where the current X marker is.
			double yy = functionController.getY(currentX,functionCtr);
			int circlePxLocationY = yPointToPx(yy);
			
			//Draws a circle on the function where the current X is. 
			g.fillOval(xLocation - halfCircle, circlePxLocationY - halfCircle, circleSize, circleSize);			
			 
			//Displays the Function Value on screen
			String displayValue = round(yy,3);			
			Rect r = g.getStringRect(displayValue, xLocation + fontSize, circlePxLocationY, GraphicsWrapper.Position.RIGHT);
			
			g.setColor(new Color(0,0,0,255/2));
			g.fillRoundRect(r,15);
			
			g.setColor(functionColor); //Sets color to the graph's corresponding color
			
			g.drawCenteredString(displayValue, r,GraphicsWrapper.HorizAlign.CENTER, GraphicsWrapper.VertAlign.MIDDLE);	
			
			
			//Draws the outline
			g.setColor(Color.black);
			g.drawOval(xLocation - halfCircle, circlePxLocationY - halfCircle, circleSize, circleSize);
		}
	}
	
	//Draws Oval for the current X marker
	public void drawXOval(GraphicsWrapper g){		
		
		int halfCircle = circleSize/2;
		int xLocation = xPointToPx(currentX);		
		
		//oval
		g.setColor(Color.white);
		g.fillOval(xLocation-halfCircle,(int)(originY)-halfCircle,(circleSize),(circleSize));
		
		//oval border
		g.setColor(Color.black);
		g.drawOval(xLocation-halfCircle,(int)(originY)-halfCircle,(circleSize),(circleSize));
		g.drawLine(xLocation,(int)(originY)-halfCircle,xLocation,(int)(originY)+halfCircle);
		g.drawLine(xLocation-halfCircle,(int)(originY),xLocation+halfCircle,(int)(originY));
	}
	
	//Draws functions in the corner
	public void drawFunctionBox(GraphicsWrapper g){
		
		if(functionController.size() == 0){
			return;
		}
		
		//Each box has the same height. Get the height of the first one
		int boxHeight = g.getStringRect(functionController.getFunction(0).toString()).getHeight();
		
		//Find longest string		
		int maxBoxWidth = 0;
		for(Function f : functionController){
			int stringWidth = g.getStringRect(f.toString()).getWidth();
			
			if(stringWidth > maxBoxWidth){
				maxBoxWidth = stringWidth;
			}
		}
		
		//Set global function box dimensions
		functionBoxWidth = maxBoxWidth;
		functionBoxHeight = boxHeight;
		
		//Draw the box
		g.setColor(new Color(0,0,0,255/2));
		g.fillRoundRect(0, 0, maxBoxWidth + functionBoxWidthPadding*2, boxHeight*functionController.size(), 15,15);
		

		//Draw Function Strings
		for(int functionCtr = 0; functionCtr < functionController.size(); functionCtr++){
			Function f = functionController.getFunction(functionCtr);
			
			//Draw the function's string within the box
			g.setColor(f.getColor());			
			g.drawString(f.toString(),functionBoxWidthPadding, functionCtr*boxHeight,GraphicsWrapper.Position.BOTTOM_RIGHT);
			
		}
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
				Rectangle r = new Rectangle(xPointToPx(currentX)-circleSize/2,(int)originY-circleSize/2,circleSize,circleSize);
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
				if(xScl <= 1 || Double.isNaN(xScl)|| Double.isInfinite(xScl)){
					xScl = 1;
				}
				if(yScl <= 1 || Double.isNaN(yScl) || Double.isInfinite(yScl)){
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
		
		@Override
		public void mouseClicked(MouseEvent e) {
			
			//True when user right clicks on function box
			if(e.getButton() == 3 && functionController.size() != 0){
				
				int functionLevel = mouseY/functionBoxHeight;
				if(mouseX < functionBoxWidth + functionBoxWidthPadding*2 && functionLevel < functionController.size()){
					Function f = functionController.getFunction(functionLevel);
					showFunctionPopup(f, mouseX, mouseY);
				}				
			}
		}

		//-----START UNUSED METHODS----//
		@Override
		public void componentMoved(ComponentEvent e) {}

		@Override
		public void componentShown(ComponentEvent e) {}

		@Override
		public void componentHidden(ComponentEvent e) {}

		@Override
		public void mouseEntered(MouseEvent e) {}

		@Override
		public void mouseExited(MouseEvent e) {}
		//------END UNUSED METHODS----//
		
	}
	
	/*
	 * The JPopupMenu which should appear when a user right clicks on a function within the function window
	 */
	class FunctionPopup extends JPopupMenu{
		Function f;
		
		JMenuItem color;
		JMenuItem remove;
		JMenuItem integrate;		
		
		public FunctionPopup(){			
			PressListener pl = new PressListener();
			
			color = new JMenuItem("Change color");
			remove = new JMenuItem("Remove function");
			integrate = new JMenuItem("Integrate");
			
			color.addActionListener(pl);
			remove.addActionListener(pl);			
			integrate.addActionListener(pl);
			
			add(color);
			add(remove);
			add(integrate);
		}
		
		public void setFunction(Function f){
			this.f = f;
		}
		
		class PressListener implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == color){
					
					Color newCol =JColorChooser.showDialog(GraphingComp.this,"Chose Function Color",f.getColor());
					if(newCol != null){
						f.setColor(newCol);
						GraphingComp.this.repaint();
					}					
					
				}else if(e.getSource() == remove){
					
					functionController.removeFunction(f);
					
					ArrayList<Integral> removeList = new ArrayList<Integral>();
					for(Integral i : integralList){
						if(i.getF().equals(f)){
							removeList.add(i);
						}
					}
					
					for(Integral i : removeList){
						integralList.remove(i);
					}				
					
					GraphingComp.this.repaint();				
					
				}else if(e.getSource() == integrate){
					
					integralList.add(Integral.showNewIntegralPopupDialog(frame,f));
					GraphingComp.this.repaint();
					
				}
			}
		}
	}	
	
	
	
	private void showFunctionPopup(Function f, int x, int y){
		
		if(functionPopup == null){
			functionPopup = new FunctionPopup();
		}
		functionPopup.setFunction(f);
		functionPopup.show(this, x, y);
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