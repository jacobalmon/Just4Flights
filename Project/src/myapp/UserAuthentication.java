package myapp;

import java.sql.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UserAuthentication {
	private static final String CONNECTION = "jdbc:mysql://127.0.0.1/just4flights";
	private static final String USER = "root";
	private static final String PASSWORD = "cloudcrew123";
	
	public static boolean authenticateUser(String username, String password) {
		boolean isauthenticated = false;
		
		// Query to Authenticate or Unauthenticate a user.
		String query = "SELECT password FROM users WHERE username = ?";
		try (Connection connection = DriverManager.getConnection(CONNECTION, USER, PASSWORD);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			
			// Setting Username Parameter.
			statement.setString(1, username);
			ResultSet resultSet = statement.executeQuery();
			
			if (resultSet.next()) {
				// Retrieve Stored Password.
				String storedPassword = resultSet.getString("password");
				// Hash the Password.
				String hashedPassword = hashPassword(password);
				// Check if Authenticated.
				isauthenticated = storedPassword.equals(hashedPassword);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return isauthenticated;
	}
	
	private static String hashPassword(String password) {
		try {
			// Create SHA-256 Message.
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			// Hash the Input Password.
			byte[] hash = md.digest(password.getBytes());
			// Convert Byte Array to Hexadecimal String.
			StringBuilder hexString = new StringBuilder();
			
			for (byte b : hash) {
				String hex = Integer.toHexString(0xff & b);
				if (hex.length() == 1) {
					// Ensure Each Byte is represented by 2 hex digits.
					hexString.append('0');
				}
				hexString.append(hex);
			}
			
			return hexString.toString();
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void registerUser(String username, String password) {
		// Hash the Input Password.
		String hashedPassword = hashPassword(password);
		// Query for Registering a User.
		String query = "INSERT INTO users (username, password) VALUES (?,?)";
		
		try (Connection connection = DriverManager.getConnection(CONNECTION, USER, PASSWORD);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			
				 // Set Username & Password in Statement.
				 statement.setString(1, username);
				 statement.setString(2, hashedPassword);
				 // Execute the Query.
				 statement.executeUpdate(); 
				 
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
		
	
}
