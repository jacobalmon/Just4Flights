package test;

import org.junit.jupiter.api.Test;
import myapp.UserAuthentication;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test class for testing the login functionality of the UserAuthentication class.
 * 
 * This class contains test cases to validate the behavior of the authentication method for various scenarios,
 * including valid and invalid credentials, empty username, empty password, and both empty username and password.
 */
public class TestLogin {

    /**
     * Test case to verify successful login with valid credentials.
     * 
     * This test authenticates a user with a valid username and password and asserts that the authentication
     * succeeds, returning true.
     * 
     * @throws Exception if an error occurs during the test execution.
     */
    @Test
    public void loginUserValid() {
        String username = "jane_doe@gmail.com";
        String password = "jane_pass";

        boolean result = UserAuthentication.authenticateUser(username, password);

        assertTrue(result, "Authentication should succeed for valid credentials");
    }

    /**
     * Test case to verify failed login with an invalid password.
     * 
     * This test authenticates a user with a valid username but an incorrect password, expecting the authentication
     * to fail, returning false.
     * 
     * @throws Exception if an error occurs during the test execution.
     */
    @Test
    public void loginUserInvalid() {
        String username = "jane_doe@gmail.com";
        String password = "not_jane";
        
        boolean result = UserAuthentication.authenticateUser(username, password);

        assertFalse(result, "Authentication should fail for invalid credentials");
    }
    
    /**
     * Test case to verify failed login with an empty username.
     * 
     * This test attempts to authenticate a user with an empty username and a valid password, expecting the 
     * authentication to fail, returning false.
     * 
     * @throws Exception if an error occurs during the test execution.
     */
    @Test
    public void emptyUsername() {
    	String username = "";
    	String password = "jane_pass";
    	
    	boolean result = UserAuthentication.authenticateUser(username, password);

        assertFalse(result, "Authentication should fail for empty username");
    }
    
    /**
     * Test case to verify failed login with an empty password.
     * 
     * This test attempts to authenticate a user with a valid username but an empty password, expecting the 
     * authentication to fail, returning false.
     * 
     * @throws Exception if an error occurs during the test execution.
     */
    @Test
    public void emptyPassword() {
    	String username = "jane_doe@gmail.com";
    	String password = "";
    	
    	boolean result = UserAuthentication.authenticateUser(username, password);

        assertFalse(result, "Authentication should fail for empty password");
    }
    
    /**
     * Test case to verify failed login with both empty username and password.
     * 
     * This test attempts to authenticate a user with both an empty username and password, expecting the 
     * authentication to fail, returning false.
     * 
     * @throws Exception if an error occurs during the test execution.
     */
    @Test
    public void emptyUserPass() {
    	String username = "";
    	String password = "";
    	
    	boolean result = UserAuthentication.authenticateUser(username, password);
    	
    	assertFalse(result, "Authentication should fail for empty username and password");
    }
}