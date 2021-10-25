package com.Servlets;



import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.data.Data;
import com.dbconnection.JDBC;


/**
 * Servlet implementation class create
 */
@SuppressWarnings("serial")
@WebServlet("/Create")
public class Create extends MainServlet {
	
    public Create() {
    	 super();
        
    }
   
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String payloadRequest = Data.getDataString(request);
		logger.info(payloadRequest);
		
	 JSONObject req = new JSONObject(payloadRequest);
	 JSONObject req1=new JSONObject();
	 req1.put("Request", req);
		logger.info(req1);
		
		
		JSONObject res=new JSONObject();
		
		res = createEmployee(req);
		 JSONObject req2=new JSONObject();
		 req2.put("Response", res);
	System.out.println(res);
	logger.info(req2);
	
		
		request.setCharacterEncoding("utf8");
        response.setContentType("application/json");
		
		 PrintWriter out = response.getWriter();
	        try {
	            out.println(res);
	        } finally {
	            out.close();
	        }
	}

	private JSONObject createEmployee(JSONObject req) {
		JSONObject result = new JSONObject();
		Connection connection =null;
		PreparedStatement ps =null ;
		
		try
    	{
			 connection = JDBC.getConnection();
			 logger.info("Database Connected");
			 
//			 String reqName=req.getString("Name");
//			 String reqDesig=req.getString("Designation");
//			 String reqQuali=req.getString("Qualification");
//			 String reqStatus=req.getString("Status");
//			 int reqSalary=req.getInt("Salary");
			 
			 
			 String SQLINSERT = "insert into Employee (Name,Designation,Qualification,Status,Salary) values(?,?,?,?,?)";
			 ps = connection.prepareStatement(SQLINSERT);
			 ps.setString(1, req.getString("Name"));
			 ps.setString(2, req.getString("Designation"));
			 ps.setString(3,req.getString("Qualification"));
			 ps.setString(4, req.getString("Status"));
			 ps.setInt(5, req.getInt("Salary"));
			 int executeUpdate=ps.executeUpdate();
			 if(executeUpdate>0)
			 {
				 
						 logger.info("Employee Details created");
						 result.put("Message", "Employee Details Created");
					 
					
					 }
				 
				 else
				 {
					 logger.info("Invalid Employee Type");
					 result.put("Message", "Invalid Employee Type");
				 
			 }
			 
			 
			 }catch (Exception e) {
					e.printStackTrace();
					logger.error("Sorry, something wrong!", e);
					result.put("Message", "Error!");
    	}
		JDBC.closeStatement(ps);
 	    JDBC.closeConnection(connection);
 	    logger.info("Connection closed");
 	    
 	   return result ;
	}

}
