package com.hoosteen.window;

import java.util.ArrayList;

import com.hoosteen.function.Function;

public class Polyline {

	int[] xPoints;
	int[] yPoints;
	int nPoints;
	
	Function f;
	
	
	ArrayList<Line> lineList = new ArrayList<Line>();	
	
	public Polyline(int[] xPoints, int[] yPoints, int nPoints, Function f){
		this.xPoints = xPoints;
		this.yPoints = yPoints;
		this.nPoints = nPoints;
		
		this.f = f;
		
		for(int i =  0; i < nPoints - 1; i++){
			lineList.add(new Line(xPoints[i], yPoints[i], xPoints[i+1], yPoints[i+1]));
		}	
		
	}
	
	public int getLowestDistance(int x, int y){
		
		int lowest = Integer.MAX_VALUE;
		
		for(Line l : lineList){
			int lowestReturn = l.getLowestDistance(x, y);
			
			if(lowestReturn < lowest){
				lowest = lowestReturn;
			}
			
		}		
		
		return lowest;
		
	}	
	
	public Function getFunction(){
		return f;
	}
	
}
