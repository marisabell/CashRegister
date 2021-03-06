package ua.kapitonenko.app.fixtures;

import ua.kapitonenko.app.exceptions.DAOException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestConnection {
	private static final String USERNAME = "root";
	private static final String PASSWORD = "root";
	private static final String URL = "jdbc:mysql://localhost:3366/cashregister_test?autoReconnect=true&useSSL=false";
	
	private static TestConnection instance = new TestConnection();
	
	private TestConnection() {
	}
	
	public static TestConnection getInstance() {
		return instance;
	}
	
	public Connection getConnection() {
		try {
			return DriverManager.getConnection(URL, USERNAME, PASSWORD);
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}
	
	public void close(Connection connection) {
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}
	
}
