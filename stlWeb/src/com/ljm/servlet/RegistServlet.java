package com.ljm.servlet;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.ljm.tools.DBHelper;
import com.ljm.tools.Encoder;

/**
 * Servlet implementation class RegistServlet
 */
@WebServlet("/RegistServlet")
public class RegistServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegistServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		processRegist(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		processRegist(request, response);
	}
	private void processRegist(HttpServletRequest request, HttpServletResponse response)
            throws ServletException,IOException
    {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
       
        JSONObject registResult=new JSONObject();
        DBHelper db=new DBHelper();
        StringBuilder sql=new StringBuilder("insert into user_info(username,email,password) values('");            
        try {
        	 password=Encoder.EncoderByMd5(password);
        	 sql.append(username+"','");
             sql.append(email+"','");
             sql.append(password+"')");
             db.pst.execute(sql.toString());		
			registResult.put("result", 1);							
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			registResult.put("result", 0);
		}
        catch (NoSuchAlgorithmException e) {
			// TODO: handle exception
        	e.printStackTrace();
        	registResult.put("result", 0);
		}
        finally {
			db.close();
		}
        try{

		    response.getWriter().write(registResult.toString());
		   }catch(IOException e){
		    e.printStackTrace();
		   }
    }
}
