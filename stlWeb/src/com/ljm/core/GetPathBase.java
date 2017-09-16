package com.ljm.core;

import java.util.ArrayList;
import java.util.List;

import com.ljm.entity.Point;

public class GetPathBase {

	protected int[] pathAnt;
	protected int[] pathGreedy;
	protected Point []points;
	protected int distanceAnt;
	protected int distanceGreedy;
	protected int pointNum;
	
	public int[] getPathAnt() {
		return pathAnt;
	}
	public int[] getPathGreedy() {
		return pathGreedy;
	}
	public Point[]getPoints()
	{
		return points;
	}
	public List<Point> getPathAntInfo() {
		List<Point> pathInfo=new ArrayList<Point>();
		for (int i = 0; i < pathAnt.length; i++) {
			pathInfo.add(points[pathAnt[i]]);
		}
		return pathInfo;
		
	}
	public List<Point> getPathGreedyInfo() {
		List<Point> pathInfo=new ArrayList<Point>();
		for (int i = 0; i < pathGreedy.length; i++) {
			pathInfo.add(points[pathGreedy[i]]);
		}
		return pathInfo;
		
	}
	public int getDistanceAnt() {
		return distanceAnt;
	}
	public int getDistanceGreedy() {
		return distanceGreedy;
	}
}
