package com.Servlets;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import com.data.Data;
import com.dbconnection.JDBC;
/**
 * Servlet implementation class Display
 */
@WebServlet("/Display")
public class Display extends MainServlet {
	private static final long serialVersionUID = 1L;
	
	
    public Display() {
    	super();
       
    }
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String payloadRequest = Data.getDataString(request);
		 
		JSONObject req=new JSONObject(payloadRequest);
		JSONObject req1=new JSONObject();
		req1.put("Request", req);
		logger.info(req1);
		
		JSONObject res=new JSONObject();
		res = displayEmployee(req);
		logger.info(res);
	
		
		request.setCharacterEncoding("utf8");
        response.setContentType("application/json");
		
		 PrintWriter out = response.getWriter();
	        try {
	            out.println(res);
	        } finally {
	            out.close();
	        }
	}
	private JSONObject displayEmployee(JSONObject req) {
		JSONObject response = new JSONObject();
		Connection connection =null;
		PreparedStatement ps =null ;
		
		try
    	{
			 connection = JDBC.getConnection();
			 
			 
			 String SQLINSERT = "SELECT * FROM Employee";
			 
			 ps = connection.prepareStatement(SQLINSERT);
				
				ResultSet rs = ps.executeQuery();
				
				JSONArray array=new JSONArray();
				
				while(rs.next())
                {
					
					JSONObject result = new JSONObject();
					
					
					result.put("Id", rs.getString("Emp_code"));
					result.put("Name", rs.getString("Name"));
					result.put("Designation", rs.getString("Designation"));
					result.put("Qualification", rs.getString("Qualification"));
					result.put("Status", rs.getString("Status"));
					result.put("Salary", rs.getInt("Salary"));
					
					

					array.put(result);
				}
				response.put("response", array);	
    	} catch (Exception e) {
    		e.printStackTrace();
    		logger.error("Sorry, something wrong!", e);
    		

    		response.put("Message", "Something went wrong!");

    }
    		JDBC.closeStatement(ps);
     	    JDBC.closeConnection(connection);
     	   logger.info("Connection closed");

     	    
	 		return response ;

}
}

