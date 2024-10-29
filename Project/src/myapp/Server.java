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
	    try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
	         PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {
	         
	    	// Receive the Request Type from the Client.
	        String requestType = in.readLine();
	        
	        // Receiving Information from the Server.
	        if (requestType.startsWith("REGISTER")) {
	        	String[] creds = requestType.split(" ");
	            String firstName = creds[1];
	            String lastName = creds[2];
	            String email = creds[3];
	            String password = creds[4];
	            
	            // Register User.
	            String response = UserAuthentication.registerUser(firstName, lastName, email, password);
	            out.println(response);
	            
	        // Receiving Information from the Server.
	        } else if (requestType.startsWith("AUTHENTICATE")) {
	        	String[] creds = requestType.split(" ");
	        	String username = creds[1];
	        	String password = creds[2];
	            
	        	// Authenticate User.
	            boolean isAuthenticated = UserAuthentication.authenticateUser(username, password);
	            out.println(isAuthenticated ? "Authentication Successful" : "Authentication Failed");
	        } 

	    } catch (SocketTimeoutException e) {
	        System.err.println("Client request timed out: " + e.getMessage());
	    } catch (IOException e) {
	        System.err.println("Client communication error: " + e.getMessage());
	    } finally {
	        try {
	            clientSocket.close();
	        } catch (IOException e) {
	            System.err.println("Error closing client socket: " + e.getMessage());
	        }
	    }
	}

		
	private static String executeQuery(String query) {
		// Stores Result of the Query.
		StringBuilder result = new StringBuilder();
			
		try (Connection connection = DriverManager.getConnection(CONNECTION, USER, PASSWORD);
			 Statement statement = connection.createStatement();
			 ResultSet resultSet = statement.executeQuery(query)) {
				
			// Get Meta Data from Result Set for Columns.
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