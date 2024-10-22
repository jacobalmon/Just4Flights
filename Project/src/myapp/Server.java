package myapp;

import java.io.*;
import java.net.*;
import java.sql.*;

public class Server {
	private static final String DB_CLASSNAME = "com.mysql.cj.jdbc.Driver";
	private static final String CONNECTION = "jdbc:mysql://127.0.0.1/just4flights";
	private static final String USER = "root";
	private static final String PASSWORD = "cloudcrew123";
	
	public static void main(String[] args) {
		try {
			// Loading JDBC Driver.
			Class.forName(DB_CLASSNAME);
			
			// Create Server Socket on Port 8080.
			try (ServerSocket serverSocket = new ServerSocket(8080)) {
				// Continuously Accept Client Connections.
				while (true) {
					Socket clientSocket = serverSocket.accept();
					// Handles Client Communication.
					handleClient(clientSocket);
				}
				
			} catch (IOException e) {
				e.getMessage();
				e.printStackTrace();
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private static void handleClient(Socket clientSocket) {
		try (BufferedReader in = new BufferedReader (new InputStreamReader(clientSocket.getInputStream()));
			 PrintWriter out = new PrintWriter (clientSocket.getOutputStream(), true)) {
				
			// Read SQL Query from Client.
			String username = in.readLine();
		    String password = in.readLine();
		    
		    boolean isAuthenticated = UserAuthenication.authenicateUser(username, password);
		    
		    if (isAuthenticated) {
	            out.println("Authentication Successful");
	        } else {
	            out.println("Authentication Failed");
	        }
		    
		} catch (IOException e) {
			e.getMessage();
			e.printStackTrace();
		} finally {
			try {
				clientSocket.close();
			} catch (IOException e) {
				e.getMessage();
			}
		}
	}
		
	private static String executeQuery(String query) {
		StringBuilder result = new StringBuilder();
			
		try (Connection connection = DriverManager.getConnection(CONNECTION, USER, PASSWORD);
			 Statement statement = connection.createStatement();
			 ResultSet resultSet = statement.executeQuery(query)) {
				
			ResultSetMetaData metaData = resultSet.getMetaData();
			int columns = metaData.getColumnCount();
			
			// Formatting Result Set Data.
			while (resultSet.next()) {
				for (int i = 1; i <= columns; ++i) {
					result.append(resultSet.getString(i)).append("\t");
				}
				result.append("\n");
			}

		} catch (SQLException e) {
			e.printStackTrace();
			e.getMessage();
		}
			
		return result.toString();
	}
}