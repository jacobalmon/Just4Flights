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
		// Textbox for Username.
		TextField usernameField = new TextField();
		usernameField.setPromptText("Username");
		
		// Textbox for Password.
		PasswordField passwordField = new PasswordField();
		passwordField.setPromptText("Password");
		
		// Login Button.
		Button loginButton = new Button("Login");
		Label statusLabel = new Label();
		
		// Login Button Event Listener.
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
		
		// Screen for Login Page.
		VBox layout = new VBox(10, usernameField, passwordField, loginButton, statusLabel);
		Scene loginScene = new Scene(layout, 300, 200);
		
		// Show Login Page.
		primary.setScene(loginScene);
		primary.setTitle("Login Page");
		primary.show();
	}
	
	private void showRegisterPage() {
		// TBA.
	}
	
	private boolean sendLoginRequest(String username, String password) {
		try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
			 PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
			
			// Send Authentication Request to the Server.
			out.println("AUTHENTICATE " + username + " " + password);
			// Receive Response from the Client.
			String response = in.readLine();
			return "Authentication Successful".equals(response);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static void main(String[] args) {
		launch(args); // runs app.
	}
}