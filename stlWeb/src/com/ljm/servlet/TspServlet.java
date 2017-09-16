package com.ljm.servlet;

import java.io.IOException;
import java.net.URLDecoder;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.ljm.core.GetPath;
import com.ljm.core.GetPathByID;
import com.ljm.entity.Point;
import com.ljm.tools.DBHelper;
import com.ljm.tools.Encoder;

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
		processRequestGet(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		processRequestPost(request, response);
	}

	 protected void processRequestGet(HttpServletRequest request, HttpServletResponse response)  
	    	    throws ServletException, IOException {  
		    response.setContentType("text/html;charset=utf-8");  
	        response.setHeader("Cache-Control", "no-store");  
	        response.setHeader("Pragma", "no-cache");  
	        response.setDateHeader("Expires", 0); 
	        String id=request.getParameter("id");
	        DBHelper db=new DBHelper();
	        String sql="select * from delivery_route where nid="+id; 
	        JSONObject jsonObject= new JSONObject();
	        try {	        	 
	             ResultSet resultSet=db.pst.executeQuery(sql.toString());
	             if (resultSet.next()) {
	     			jsonObject.put("Ant", resultSet.getString("ant_path"));
	    			jsonObject.put("Greedy",  resultSet.getString("greedy_path"));
	    			jsonObject.put("dstAnt",  resultSet.getString("ant_length"));
	    			jsonObject.put("dstGreedy", resultSet.getString("greedy_length"));
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				jsonObject.put("status", 0);
				e1.printStackTrace();
			}
	        
	        finally {
				db.close();
			}
	        
//	       // JSONObject jsonObjectGreedy= new JSONObject();
//	        String strAnt=JSON.toJSONString(pathAntInfo, SerializerFeature.DisableCircularReferenceDetect);
//	        String strGreedy=JSON.toJSONString(pathGreedyInfo, SerializerFeature.DisableCircularReferenceDetect);

			try{

			    response.getWriter().write(jsonObject.toString());
			   }catch(IOException e){
			    e.printStackTrace();
			   }   	       	        
	 }
	 protected void processRequestPost(HttpServletRequest request, HttpServletResponse response)  
	    	    throws ServletException, IOException {  
		    response.setContentType("text/html;charset=utf-8");  
	        response.setHeader("Cache-Control", "no-store");  
	        response.setHeader("Pragma", "no-cache");  
	        response.setDateHeader("Expires", 0); 
	        String strStartPoint=request.getParameter("startPoint");
	        String strDesdestinationPoint=request.getParameter("destinationPoints");
	        strStartPoint=new String(strStartPoint.getBytes("iso-8859-1"),"utf-8");
	        strDesdestinationPoint=new String(strDesdestinationPoint.getBytes("iso-8859-1"),"utf-8");
	        JSONObject jsonStartPoint=JSONObject.parseObject(URLDecoder.decode(strStartPoint,"utf-8"));
	        JSONArray  jsonDesdestinationPoints=JSONArray.parseArray(URLDecoder.decode(strDesdestinationPoint,"utf-8"));
	        //String[] origin={jsonStartPoint.getString("label"),jsonStartPoint.getString("lat"),jsonStartPoint.getString("lng")};
	        Point origin=JSONObject.toJavaObject(jsonStartPoint, Point.class);
	        Point[]goals =JSONArray.toJavaObject(jsonDesdestinationPoints,  Point[].class);

	        GetPath getPath=new  GetPath(origin,goals);
	        List<Point> pathAntInfo=new ArrayList<Point>();	        
	        List<Point> pathGreedyInfo=new ArrayList<Point>();
	        pathAntInfo=getPath.getPathAntInfo();
	        pathGreedyInfo=getPath.getPathGreedyInfo();
	        JSONObject jsonObject= new JSONObject();
	       // JSONObject jsonObjectGreedy= new JSONObject();
	        String strAnt=JSON.toJSONString(pathAntInfo, SerializerFeature.DisableCircularReferenceDetect);
	        String strGreedy=JSON.toJSONString(pathGreedyInfo, SerializerFeature.DisableCircularReferenceDetect);
			jsonObject.put("Ant", strAnt);
			jsonObject.put("Greedy", strGreedy);
			int dstAnt=getPath.getDistanceAnt();
			int dstGreedy=getPath.getDistanceGreedy();
			jsonObject.put("dstAnt", dstAnt);
			jsonObject.put("dstGreedy", dstGreedy);
			HttpSession session = request.getSession(false);
			if (session!=null) {
				String id=String.valueOf(session.getAttribute("id"));
				DBHelper db=new DBHelper();
		        StringBuilder sql=new StringBuilder("insert into delivery_route (user_id,ant_path,greedy_path,ant_length,greedy_length) values(");            
		        try {
		        	 sql.append(id+",'");
		             sql.append(strAnt+"','");
		             sql.append(strGreedy+"',");
		             sql.append(String.valueOf(dstAnt)+",");
		             sql.append(String.valueOf(dstGreedy)+")");
		             db.pst.execute(sql.toString());									
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		        
		        finally {
					db.close();
				}
			}
			try{

			    response.getWriter().write(jsonObject.toString());
			   }catch(IOException e){
			    e.printStackTrace();
			   }
 	        
	        
	 }
}
