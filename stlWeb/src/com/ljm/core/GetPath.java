package com.ljm.core;

import com.ljm.entity.Point;
import com.ljm.getDistance.GetDistance;

import ljm.Tsp.Ant.TspAnt;
import ljm.Tsp.Greedy.TspGreedy;

public class GetPath extends GetPathBase {
	
	public GetPath(Point origin,Point[]goals) {
		// TODO Auto-generated constructor stub
		pointNum=goals.length+1;
		points=new Point [pointNum];
		points[0]=origin;
		for(int j=1;j<pointNum;j++)
		{
		    points[j]=goals[j-1];
		}
		 String [] selectedPoints=new String [pointNum];
		 String []selectedPointsName=new String[pointNum];
		 for(int j=0;j<pointNum;j++)
			{
			 selectedPoints[j]=points[j].getLat()+","+points[j].getLng();//构成baidu map api的地点要求 “lat,lng”
			 selectedPointsName[j]=points[j].getLabel();
			}
		 int[][]distance=new int[pointNum][pointNum];
		 GetDistance getDistance=new GetDistance();
		 distance=getDistance.getDistance_ThroughBaiduMapAPI(selectedPoints);
		 TspAnt tspAnt=new TspAnt(selectedPointsName, distance);
		 pathAnt=tspAnt.tspTest();
		 TspGreedy tspGreedy=new TspGreedy(selectedPointsName, distance);
		 pathGreedy=tspGreedy.tspTest();
		 distanceAnt=tspAnt.getDiasance();
		 distanceGreedy=tspGreedy.getDiasance();
	}
}
