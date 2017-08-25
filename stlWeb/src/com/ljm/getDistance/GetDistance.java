package com.ljm.getDistance;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;


 import com.alibaba.fastjson.JSONObject;
 import com.alibaba.fastjson.JSONArray;
public class GetDistance {

	public int[][]  getDistance_ThroughBaiduMapAPI (String []points) {
    StringBuilder url=new StringBuilder("http://api.map.baidu.com/routematrix/v2/walking?output=json&origins=");
    StringBuilder origin=new StringBuilder();
//		String url="http://api.map.baidu.com/direction/v1?mode=riding&origin="
//	+origin+"&destination="+destination+"&region="+region
//	+"&output=json&ak=gA7XfBujUTeN0Qnk3tr7jqpwngjcw1or";
    for(String s1:points)
    {
    	origin.append(s1+"|");
    }
    origin.deleteCharAt(origin.length()-1);
    url.append(origin);
    url.append("&destinations=");
    url.append(origin);
    url.append("&ak=gA7XfBujUTeN0Qnk3tr7jqpwngjcw1or");
    String json = loadJSON(url.toString());
    JSONObject obj = JSONObject.parseObject(json);
    int l=points.length;
    int [][]distance=new int[l][l];
    int n=0;
    for(int i=0;i<l;i++)
	{
		for(int j=0;j<l;j++)
		{
			distance[i][j]=n++;
		}
	}
    if(obj.get("status").toString().equals("0")){
    	JSONArray jsonArray=obj.getJSONArray("result");;
    	for(int i=0;i<l;i++)
    	{
    		distance[i][i]=0;
    		for(int j=i+1;j<l;j++)
    		{
    			distance[i][j]=distance[j][i]=jsonArray.getJSONObject(distance[i][j]).getJSONObject("distance").getIntValue("value");
    		}
    	}
    }
       	return distance;
      

	}
	public static String loadJSON (String url) {
	       StringBuilder json = new StringBuilder();
	       try {
	           URL url2 = new URL(url);
	           URLConnection urlConnection = url2.openConnection();
	           BufferedReader in = new BufferedReader(new InputStreamReader(
	        		   urlConnection .getInputStream(),"UTF-8"));
	           String inputLine = null;
	           while ( (inputLine = in.readLine()) != null) {
	               json.append(inputLine);
	           }
	           in.close();
	       } catch (MalformedURLException e) {
	       } catch (IOException e) {
	       }
	       return json.toString();

	   }
}
