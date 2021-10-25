package com.dbconnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class JDBC {
	private static DataSource ds = null;
	private static Context ctx = null;
	public static Connection con = null;
	public static PreparedStatement stmt = null;

	public JDBC() {

		try {
			ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/Servletdb");
//			 con = ds.getConnection();
//
//			System.out.println("connected");


		} catch (NamingException e) {
			e.printStackTrace();
		} 
	}

	
	public static Connection getConnection() throws SQLException {
//		Connection c=ds.getConnection();
//		System.out.println("Opening datasource connection : " + System.identityHashCode(c));
//		return c;
		return ds.getConnection();
	}
	public static void closeConnection(Connection con) {

		if (con != null) {
			try {
				con.close();
				System.out.println("connection closed");
			} catch (SQLException e) {
				e.printStackTrace();

			}
		}
	}

	public static void closeStatement(Statement stmt) {
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
