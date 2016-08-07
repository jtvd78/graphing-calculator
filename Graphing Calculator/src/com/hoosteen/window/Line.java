package com.hoosteen.window;

public class Line {
	
	int x1, x2, y1, y2;
	
	public Line(int x1, int y1, int x2, int y2){
		this.x1 = x1;
		this.x2 = x2;
		this.y1 = y1;
		this.y2 = y2;
	}
	
	public int getLowestDistance(int x, int y){
		
		//If the line is flat, just get the distance from the endpoints,
		//since the following code does not work for a flat line
		if(y1 == y2){
			if(x >= x2){
				return distance(x,y,x2,y2);
			}else if(x <= x1){
				return distance(x,y,x1,y1);
			}
		}
		
		//Get the slope of the line
		double m = ((double)y2 - (double)y1)/((double)x2-(double)x1);
		
		//X position of the intersecting perpendicular line
		//This position can be to the right or left of the actual line, or on the line
		double xOut = ( y - y1 + m*((double)x1) + ((double)x)/m ) / (1.0/m + m);
		
		//Adjust the xOut to be on the original line
		if(xOut > x2){
			xOut = x2;
		}
		
		if(xOut < x1){
			xOut = x1;
		}		
		
		//Find the corresponding yPoint
		int yOut = (int) (m * (xOut - x1) + y1);
		
		//Return the distance
		return distance(x, y, xOut, yOut);	
		
	}
	
	public int distance(double x1, double y1, double x2, double y2){
		return (int) Math.sqrt(Math.pow(x2-x1, 2) + Math.pow(y2-y1, 2));
	}

	public String toString(){
		return x1 + " : " + y1 + " : " + x2 + " : " + y2;
	}
	
	
}
