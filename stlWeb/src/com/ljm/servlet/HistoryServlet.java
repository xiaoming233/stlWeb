package com.ljm.servlet;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

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
import com.ljm.tools.DBHelper;

/**
 * Servlet implementation class HistoryServlet
 */
@WebServlet("/HistoryServlet")
public class HistoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HistoryServlet() {
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
		doGet(request, response);
	}
	protected void processRequestGet(HttpServletRequest request, HttpServletResponse response)  
    	    throws ServletException, IOException {  
	    response.setContentType("text/html;charset=utf-8");  
        response.setHeader("Cache-Control", "no-store");  
        response.setHeader("Pragma", "no-cache");  
        response.setDateHeader("Expires", 0); 
        JSONObject jsonObject= new JSONObject();
        HttpSession session = request.getSession(false);
		if (session!=null) {
			String id=String.valueOf(session.getAttribute("id"));
			JSONArray jsonArrayLabel=new JSONArray();
			JSONObject jsonResult=null;
			JSONArray jsonArrayPath=null;
			JSONObject jsonPath=null;
			StringBuilder path=null;
			DBHelper db=new DBHelper();
	        String sql="select * from delivery_route where user_id="+id;            
	        try {	        	 
	             ResultSet resultSet=db.pst.executeQuery(sql.toString());
	             while (resultSet.next()) {
	            	 jsonResult=new JSONObject();
	            	 jsonResult.put("id", resultSet.getString("nid"));
	            	 jsonArrayPath=JSONArray.parseArray(resultSet.getString("ant_path"));
	            	 path=new StringBuilder();
	            	 for (int i = 0; i < jsonArrayPath.size(); i++) {
						jsonPath=jsonArrayPath.getJSONObject(i);
						path.append(jsonPath.getString("label")+"->");
					}
	            	 path.deleteCharAt(path.length()-1);
	            	 path.deleteCharAt(path.length()-1);
	            	 jsonResult.put("path", path.toString());
	            	 jsonArrayLabel.add(jsonResult);
				}
	             jsonObject.put("status", 1);
	             jsonObject.put("result", jsonArrayLabel);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				jsonObject.put("status", 0);
				e1.printStackTrace();
			}
	        
	        finally {
				db.close();
			}
		}
		else {
			jsonObject.put("status", 0);
		}
		try{

		    response.getWriter().write(jsonObject.toString());
		   }catch(IOException e){
		    e.printStackTrace();
		   }   	       	        
 }

}
