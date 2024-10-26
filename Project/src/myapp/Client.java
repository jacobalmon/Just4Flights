package myapp;

import java.io.*;
import java.net.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Client extends Application {
	private static final String SERVER_ADDRESS = "127.0.0.1";
	private static final int SERVER_PORT = 8080;
	private Stage primary;
	
	public void start(Stage primary) {
		this.primary = primary;
		showLoginPage(); // Start with Login Page for now.
	}
	
	private void showLoginPage() {
		// Add UI Code Here...
		
		TextField usernameField = new TextField();
		usernameField.setPromptText("Username");
		
		PasswordField passwordField = new PasswordField();
		passwordField.setPromptText("Password");
		
		Button loginButton = new Button("Login");
		Label statusLabel = new Label();
		
		loginButton.setOnAction(e -> {
			String username = usernameField.getText();
			String password = passwordField.getText();
			
			boolean isAuthenticated = sendLoginRequest(username, password);
			
			if (isAuthenticated) {
				statusLabel.setText("Login Successful.");
				// Go to Home Page...
			} else {
				statusLabel.setText("Invalid Username or Password");
			}
		});
		
		VBox layout = new VBox(10, usernameField, passwordField, loginButton, statusLabel);
		Scene loginScene = new Scene(layout, 300, 200);
		
		primary.setScene(loginScene);
		primary.setTitle("Login Page");
		primary.show();
	}
	
	private boolean sendLoginRequest(String username, String password) {
		try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
			 PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
			
			out.println("AUTHENTICATE " + username + " " + password);
			String response = in.readLine();
			return "Authentication Successful".equals(response);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
//	public static void main(String[] args) {
//		try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
//			 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//			 PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
//			 BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in))) {
//			
//			while (true) {
//				// Read the Type of Request.
//				String request = consoleInput.readLine();
//				
//				// Checking if it's a Register Request.
//				if ("register".equals(request)) {
//					out.println("REGISTER");
//					
//					// Reading Username & Sending to Server.
//					String username = consoleInput.readLine();
//					out.println(username);
//					
//					// Reading Password & Sending to Server.
//					String password = consoleInput.readLine();
//					out.println(password);
//					
//					// Receiving Server's Response.
//					String response = in.readLine();
//					System.out.println(response);
//				// Checking if it's a Authenticate Request.
//				} else if("authenticate".equals(request)) {
//					out.println("AUTHENTICATE");
//					
//					String username = consoleInput.readLine();
//					out.println(username);
//					
//					String password = consoleInput.readLine();
//					out.println(password);
//					
//					String response = in.readLine();
//					System.out.println(response);
//				// Throw an Error for now.
//				} else {
//					System.out.println("ERROR");
//				}
//			}	
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
}