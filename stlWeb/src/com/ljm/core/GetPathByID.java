package com.ljm.core;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.ljm.entity.Point;
import com.ljm.getDistance.*;
import ljm.Tsp.Ant.*;
import ljm.Tsp.Greedy.TspGreedy;
public class GetPathByID extends GetPathBase{



	public  void setPoints() {
		// TODO Auto-generated method stub
		String [][] goals={{"西五学生公寓","23.147295","113.353464"},
				{"研究生院","23.144059","113.353796"},
				{"计算机学院","23.144637","113.356338"},
				{"网络中心","23.143299","113.356316"},
				{"东九学生公寓","23.14445","113.358979"},
				{"东十九学生公寓","23.143457","113.358993"},
				{"研究生公寓","23.144334","113.360017"},
				{"公体楼","23.145983","113.357385"},
				{"行政楼","23.145405","113.355885"},
				{"陶北学生公寓","23.144782","113.357663"}};

		String[][]origins={{"正门","23.14207","113.35451"},{"西门","23.145775","113.352669"}};

		
		 Random random = new Random();
		 int i=(int)(2*Math.random());//随机选取一个出发点的index
		 //根据选取的index选择点
		 points=new Point [6];
		 Point lngLat=new Point(origins[i]);
		 points[0]=lngLat;
		//随机选取6个目标点的index
		 int []a=new int[6];
		 int n=0;
		 boolean flag=true;
		 while (n<6) {
			 a[n]=-1;
			i=random.nextInt(10);
			//保证选取的index不重复
			flag=true;
			for(int j=0;j<6;j++)
			{
				if (i==a[j]) {
					flag=false;
					break;
				}
			}
			if (flag) {
				a[n++]=i;
			}
		}

		 for(int j=1;j<6;j++)
			{
			 lngLat=new Point(goals[a[j]]);
			 points[j]=lngLat;
			}
		 String [] selectedPoints=new String [6];
		 String []selectedPointsName=new String[6];
		 for(int j=0;j<6;j++)
			{
			 selectedPoints[j]=points[j].getLat()+","+points[j].getLng();//构成baidu map api的地点要求 “lat,lng”
			 selectedPointsName[j]=points[j].getLabel();
			}
		 int[][]distance=new int[6][6];
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
