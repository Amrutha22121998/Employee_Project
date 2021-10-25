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
 * Servlet implementation class create
 */
@SuppressWarnings("serial")
@WebServlet("/Delete")
public class Delete extends MainServlet {

	public Delete() {
		super();

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	// use proper naming convention for classes
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, JSONException {
		String payloadRequest = Data.getDataString(request);
		JSONObject req = new JSONObject(payloadRequest);
		JSONObject req1=new JSONObject();
		req1.put("Request", req);
		logger.info(req1);
		JSONObject res = new JSONObject();
		res = deleteEmployee(req);
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

	private JSONObject deleteEmployee(JSONObject req) {
		JSONObject result = new JSONObject();
		Connection connection = null;
		PreparedStatement ps = null;

		try {
			connection = JDBC.getConnection();
			logger.info("Database Connected");

			int reqId = req.getInt("Emp_code");
			String SQLINSERT = "delete from  Employee where Emp_code = ? ";
			System.out.println(SQLINSERT);
			ps = connection.prepareStatement(SQLINSERT);
			ps.setInt(1, reqId);
			System.out.println(SQLINSERT);
			int executeUpdate = ps.executeUpdate();
			if (executeUpdate > 0) {
					logger.info("Employee Details Deleted");
					result.put("Message", "Deleted Succesfully");
				}
					 else {
				logger.info("Invalid id");
				result.put("Message", "Invalid id");
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Sorry, something wrong!", e);
			result.put("Message", "Error!");
		}
		JDBC.closeStatement(ps);
		JDBC.closeConnection(connection);
		logger.info("Connection closed");

		return result;
	}
}

