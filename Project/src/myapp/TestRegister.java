package myapp;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

class TestRegister {
	@Test
	public void registerUserDupe() {
		String firstname = "Jane";
		String lastname = "Doe";
		String email = "jane_doe@gmail.com";
		String password = "jane_pass";
		
		String response = UserAuthentication.registerUser(firstname, lastname, email, password);
		
		assertEquals(response, "Username/Email Already Exists", "Duplication Usernames");
	}
	
	@Test
	public void newUser() {
		String firstname = "Cloud";
		String lastname = "Crew";
		String email = "cloud_crew@gmail.com";
		String password = "cloudcrew123";
		
		String response = UserAuthentication.registerUser(firstname, lastname, email, password);
		
		assertEquals(response, "User Registered", "New User");
	}
}