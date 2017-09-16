package com.ljm.tools;

import java.sql.Connection;  
import java.sql.DriverManager;  
import java.sql.PreparedStatement;  
import java.sql.SQLException;  

  
public class DBHelper {  
    public static String url = null;  
    public static String classname = null;  
    public static String user = null;  
    public static String password = null;  
  
    public Connection conn = null;  
    public PreparedStatement pst = null;  
    static {  
    	PropertiesUtils.loadFile("db.properties");  
    	url = PropertiesUtils.getPropertyValue("url"); 
    	classname  = PropertiesUtils.getPropertyValue("classname");
    	user = PropertiesUtils.getPropertyValue("user");  
    	password = PropertiesUtils.getPropertyValue("password");  
    }
    public DBHelper() {  
        try {  
            Class.forName(classname);//ָ����������  
            conn = DriverManager.getConnection(url, user, password);//��ȡ����  
            pst = conn.prepareStatement("");//׼��ִ�����  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
  
    public void close() {  
        try {  
            this.conn.close();  
            this.pst.close();  
        } catch (SQLException e) {  
            e.printStackTrace();  
        }  
    }  
}  