package myapp;

import java.sql.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UserAuthenication {
	private static final String CONNECTION = "jdbc:mysql://127.0.0.1/just4flights";
	private static final String USER = "root";
	private static final String PASSWORD = "cloudcrew123";
	
	public static boolean authenicateUser(String username, String password) {
		boolean isAuthenicated = false;
		
		String query = "SELECT password FROM users WHERE username = ?";
		try (Connection connection = DriverManager.getConnection(CONNECTION, USER, PASSWORD);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			
			statement.setString(1, username);
			ResultSet resultSet = statement.executeQuery();
			
			if (resultSet.next()) {
				String storedPassword = resultSet.getString("password");
				String hashedPassword = hashPassword(password);
				isAuthenicated = storedPassword.equals(hashedPassword);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return isAuthenicated;
	}
	
	private static String hashPassword(String password) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] hash = md.digest(password.getBytes());
			StringBuilder hexString = new StringBuilder();
			
			for (byte b : hash) {
				String hex = Integer.toHexString(0xff & b);
				if (hex.length() == 1) {
					hexString.append('0');
				}
				hexString.append(hex);
			}
			
			return hexString.toString();
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}
}
