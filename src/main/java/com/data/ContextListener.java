package com.data;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.dbconnection.JDBC;

public class ContextListener implements ServletContextListener {

	private static Logger log = Logger.getLogger(ContextListener.class);

	@SuppressWarnings("static-access")
	public void contextInitialized(ServletContextEvent servletContextEvent) {

//		ServletContext context = servletContextEvent.getServletContext();
//build and deply now
		
//		String log4jConfigFile = context.getInitParameter("log4j-config-location");
//		String fullPath = context.getRealPath("") + File.separator + log4jConfigFile;
		String fullPath = "D:\\Log4j\\log4j.properties";
		Properties logProperties = new Properties();

		try {

			logProperties.load(new FileInputStream(fullPath));
			PropertyConfigurator.configure(logProperties);

			log = Logger.getLogger(ContextListener.class);

		} catch (Exception e) {
			System.out.println("Cannot load log4j.properties");
			System.out.println(e.getMessage());
		}

		@SuppressWarnings("unused")
		Connection con = null;

		try {
			con = new JDBC().getConnection();
			System.out.println("Connection from Application context Listener");
		} catch (SQLException e) {
			System.out.println("Sorry, something went wrong" + e);
			e.printStackTrace();
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		ServletContext ctx = sce.getServletContext();
		@SuppressWarnings("unused")
		JDBC dbManager = (JDBC) ctx.getAttribute("DBManager");
		System.out.println("Database connection is closed");

	}

}
