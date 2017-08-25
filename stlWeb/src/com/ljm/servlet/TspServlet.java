package com.ljm.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.ljm.entity.LngLat;

import ljm.Tsp.Ant.Ant;

/**
 * Servlet implementation class TspServlet
 */
@WebServlet("/TspServlet")
public class TspServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TspServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		processRequest(request, response);
	}

	 protected void processRequest(HttpServletRequest request, HttpServletResponse response)  
	    	    throws ServletException, IOException {  
		    response.setContentType("text/html;charset=utf-8");  
	        response.setHeader("Cache-Control", "no-store");  
	        response.setHeader("Pragma", "no-cache");  
	        response.setDateHeader("Expires", 0); 
//	        int [] pathAnt;
//	        int [] pathGreedy;
	        test1 test=new test1();
	        test.setPoints();
	        List<LngLat> pathAntInfo=new ArrayList<LngLat>();	        
	        List<LngLat> pathGreedyInfo=new ArrayList<LngLat>();
	        pathAntInfo=test.getPathAntInfo();
	        pathGreedyInfo=test.getPathGreedyInfo();
	        JSONObject jsonObject= new JSONObject();
	       // JSONObject jsonObjectGreedy= new JSONObject();
	        String strAnt=JSON.toJSONString(pathAntInfo, SerializerFeature.DisableCircularReferenceDetect);
	        String strGreedy=JSON.toJSONString(pathGreedyInfo, SerializerFeature.DisableCircularReferenceDetect);
			jsonObject.put("Ant", strAnt);
			jsonObject.put("Greedy", strGreedy);
			jsonObject.put("dstAnt", test.getDistanceAnt());
			jsonObject.put("dstGreedy", test.getDistanceGreedy());
//			JSONArray jsonArray=new JSONArray();
//			jsonArray.add(jsonObjectAnt);
//			jsonArray.add(jsonObjectGreedy);
			try{

			    response.getWriter().write(jsonObject.toString());
			   }catch(IOException e){
			    e.printStackTrace();
			   }
    	        
	        
	 }
}
