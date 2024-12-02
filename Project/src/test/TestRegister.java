package test;

import org.junit.jupiter.api.Test;
import myapp.UserAuthentication;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test class for testing the user registration functionality of the UserAuthentication class.
 * 
 * This class contains test cases to validate the behavior of the user registration method in scenarios such as 
 * attempting to register a duplicate username/email and registering a new user.
 */
class TestRegister {

    /**
     * Test case to verify that a user cannot be registered with a duplicate username or email.
     * 
     * This test attempts to register a user with a username/email that already exists in the system, and
     * asserts that the response indicates a duplication error.
     * 
     * @throws Exception if an error occurs during the test execution.
     */
    @Test
    public void registerUserDupe() {
        String firstname = "Jane";
        String lastname = "Doe";
        String email = "jane_doe@gmail.com";
        String password = "jane_pass";
        
        String response = UserAuthentication.registerUser(firstname, lastname, email, password);
        
        assertEquals(response, "Username/Email Already Exists", "Duplication Usernames");
    }

    /**
     * Test case to verify successful registration of a new user.
     * 
     * This test registers a new user with a unique email and asserts that the response indicates successful
     * registration. After the test, the newly registered user is deleted to maintain a clean state.
     * 
     * @throws Exception if an error occurs during the test execution.
     */
    @Test
    public void newUser() {
        String firstname = "Cloud";
        String lastname = "Crew";
        String email = "cloud_crew@gmail.com";
        String password = "cloudcrew123";
        
        String response = UserAuthentication.registerUser(firstname, lastname, email, password);
        
        assertEquals(response, "User Registered", "New User");
        
        // Clean up by deleting the newly registered user
        UserAuthentication.deleteUserByUsername(email);
    }
}