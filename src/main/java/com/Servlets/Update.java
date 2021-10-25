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

import org.json.JSONException;
import org.json.JSONObject;

import com.data.Data;
import com.dbconnection.JDBC;
/**
 * Servlet implementation class update
 */
@SuppressWarnings("serial")
@WebServlet("/Update")
public class Update extends MainServlet {
    public Update() {
        super();
       
    }

	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, JSONException {
		String payloadRequest = Data.getDataString(request);
		
		JSONObject req=new JSONObject(payloadRequest);
		JSONObject req1=new JSONObject();
		req1.put("Request", req);
		logger.info(req1);
		JSONObject res=new JSONObject();
		res = updateEmployee(req);
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
	private JSONObject updateEmployee(JSONObject req) {
		JSONObject result = new JSONObject();
		Connection connection =null;
		PreparedStatement ps =null ;
		
		try
    	{
			 connection = JDBC.getConnection();
			 logger.info("Database Connected");
			 
			 int reqId=req.getInt("Emp_code");
			 String reqName=req.getString("Name");
			 String reqDesig=req.getString("Designation");
			 String reqQuali=req.getString("Qualification");
			 String reqStatus=req.getString("Status");
			int reqSalary=req.getInt("Salary");
			
			 
			 String SQLUPDATE = "UPDATE Employee SET Name = ?, Designation = ?, Qualification =?, Status =?, Salary=?"; 
			 SQLUPDATE += " WHERE Emp_code = ?";
		     System.out.println(SQLUPDATE);
			 ps = connection.prepareStatement(SQLUPDATE);
			 //ps.setInt(1, reqId);
			 ps.setString(1, reqName);
			 ps.setString(2, reqDesig);
			 ps.setString(3, reqQuali);
			 ps.setString(4, reqStatus);
			 ps.setInt(5, reqSalary);
			 ps.setInt(6, reqId);
			 int executeUpdate=ps.executeUpdate();
			
			 if(executeUpdate>0)
			 {
				 logger.info("Employee Details updated");
				 result.put("Message", "Employee Details updated");
					
				 }
				 else
				 {
					 logger.info("Employee cannot be updated");
					 result.put("Message", "Employee cannot be updated");
				 }
				  
		    
    	}
		catch (Exception e) {
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
