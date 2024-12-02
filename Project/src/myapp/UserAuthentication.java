package myapp;

import java.sql.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UserAuthentication {

    // Database Credentials.
    private static final String CONNECTION = "jdbc:mysql://127.0.0.1/just4flights";
    private static final String USER = "root";
    private static final String PASSWORD = "cloudcrew123";
    
    /**
     * Authenticates the user by checking the provided username and password against the stored credentials.
     *
     * @param username The username of the user attempting to log in.
     * @param password The password provided by the user.
     * @return True if the user is authenticated, false otherwise.
     */
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
    
    /**
     * Hashes the password using the SHA-256 hashing algorithm.
     *
     * @param password The plain-text password to be hashed.
     * @return The hashed password in hexadecimal format.
     */
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
    
    /**
     * Registers a new user with the provided details.
     * It first checks if the username already exists before proceeding with registration.
     *
     * @param firstname The first name of the user.
     * @param lastname The last name of the user.
     * @param username The username of the user.
     * @param password The password of the user.
     * @return A message indicating the success or failure of the registration.
     */
    public static String registerUser(String firstname, String lastname, String username, String password) {
        
        if (userExists(username)) {
            return "Username/Email Already Exists";
        }
        // Hash the Input Password.
        String hashedPassword = hashPassword(password);
        // Query for Registering a User.
        String query = "INSERT INTO users (first_name, last_name, username, password) VALUES (?,?,?,?)";
        
        try (Connection connection = DriverManager.getConnection(CONNECTION, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {
            
                // Set First Name, Last Name, Username & Password in Statement.
                statement.setString(1, firstname);
                statement.setString(2, lastname);
                statement.setString(3, username);
                statement.setString(4, hashedPassword);
                // Execute the Query.
                statement.executeUpdate(); 
                return "User Registered";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Registration Failed";
        } 
    }
        
    /**
     * Checks if a user with the given username already exists in the database.
     *
     * @param username The username to check for existence.
     * @return True if the username exists, false otherwise.
     */
    private static boolean userExists(String username) {
        // Query to Check if Username Exists in Database.
        String query = "SELECT COUNT(*) FROM users WHERE username = ?";
        
        try (Connection connection = DriverManager.getConnection(CONNECTION, USER, PASSWORD);
                 PreparedStatement statement = connection.prepareStatement(query)) {
            
            // Execute Query.
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            
            // Return if it Exists or Not.
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    /**
     * Deletes a user from the database using their username.
     *
     * @param username The username of the user to be deleted.
     * @return A message indicating the success or failure of the deletion.
     */
    public static String deleteUserByUsername(String username) {
        try (Connection conn = DriverManager.getConnection(CONNECTION, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement("DELETE FROM users WHERE username = ?")) {
            
            pstmt.setString(1, username);
            int rowsAffected = pstmt.executeUpdate();
            return (rowsAffected > 0) ? "User Deleted" : "User Not Found";
            
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error Deleting User";
        }
    }
}