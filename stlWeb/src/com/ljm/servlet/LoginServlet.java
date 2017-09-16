package com.ljm.servlet;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ljm.tools.DBHelper;
import com.ljm.tools.Encoder;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub	
		processLogin(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		processLogin(request, response);
	}
	private void processLogin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException,IOException
    {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        JSONObject loginResult=new JSONObject();
        DBHelper db=new DBHelper();
        String sql="";
        if (checkEmaile(username)) {
			sql="select * from user_info where email='"+username+"'";
		} else {
			sql="select * from user_info where username='"+username+"'";
		}
        try {
			ResultSet resultSet=db.pst.executeQuery(sql);
			if (resultSet.next()) {
				password=Encoder.EncoderByMd5(password);
				if (resultSet.getString("password").equals(password)) {
					loginResult.put("result", 2);
					loginResult.put("username", resultSet.getString("username"));
					HttpSession session = request.getSession();
					session.setAttribute("id", resultSet.getInt("nid"));
				} else {
					loginResult.put("result", 1);
				}
			} else {
				loginResult.put("result", 0);//用户不存在
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        catch (NoSuchAlgorithmException e) {
			// TODO: handle exception
        	e.printStackTrace();
		}
        finally {
			db.close();
		}
        try{

		    response.getWriter().write(loginResult.toString());
		   }catch(IOException e){
		    e.printStackTrace();
		   }
    }
	private static boolean checkEmaile(String emaile){
        String RULE_EMAIL = "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$";
        //正则表达式的模式
        Pattern p = Pattern.compile(RULE_EMAIL);
        //正则表达式的匹配器
        Matcher m = p.matcher(emaile);
        //进行正则匹配
        return m.matches();
    }
}
