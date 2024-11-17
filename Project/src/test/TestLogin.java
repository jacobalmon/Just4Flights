package test;

import org.junit.jupiter.api.Test;
import myapp.UserAuthentication;
import static org.junit.jupiter.api.Assertions.*;

public class TestLogin {

    @Test
    public void loginUserValid() {
        String username = "jane_doe@gmail.com";
        String password = "jane_pass";

        boolean result = UserAuthentication.authenticateUser(username, password);

        assertTrue(result, "Authentication should succeed for valid credentials");
    }

    @Test
    public void loginUserInvalid() {
        String username = "jane_doe@gmail.com";
        String password = "not_jane";
        
        boolean result = UserAuthentication.authenticateUser(username, password);

        assertFalse(result, "Authentication should fail for invalid credentials");
    }
    
    @Test
    public void emptyUsername() {
    	String username = "";
    	String password = "jane_pass";
    	
    	boolean result = UserAuthentication.authenticateUser(username, password);

        assertFalse(result, "Authentication should fail for empty username");
    }
    
    @Test
    public void emptyPassword() {
    	String username = "jane_doe@gmail.com";
    	String password = "";
    	
    	boolean result = UserAuthentication.authenticateUser(username, password);

        assertFalse(result, "Authentication should fail for empty password");
    }
    
    @Test
    public void emptyUserPass() {
    	String username = "";
    	String password = "";
    	
    	boolean result = UserAuthentication.authenticateUser(username, password);
    	
    	assertFalse(result, "Authentication should fail for empty username and password");
    }
}
