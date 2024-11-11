package myapp;

import java.io.*;
import java.net.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.layout.*;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class Client extends Application {
	private static final String SERVER_ADDRESS = "127.0.0.1";
	private static final int SERVER_PORT = 8080;
	private Stage primary;
	
	public void start(Stage primary) {
		this.primary = primary;
		showLoginPage(); // Start with Login Page.
	}
	
	private void showLoginPage() {
		
		// Main container for the image and login form
//		HBox rootContainer = new HBox();
//		rootContainer.setAlignment(Pos.CENTER);
//		rootContainer.setSpacing(50); // Spacing for image and login form
//		
//		ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream("/images/company-logo.png")));
//		imageView.setFitWidth(300); // Set preferred width for the image
//		imageView.setPreserveRatio(true); // Maintain aspect ratio
		
		// Main container for all the elements
		VBox mainContainer = new VBox();
		mainContainer.setStyle("-fx-alignment: center; -fx-padding: 50;");
		
		// Card container to hold login elements with a "card" appearance
		VBox cardContainer = new VBox(10); // Spacing between elements
		cardContainer.setId("cardContainer"); // ID for styling
		cardContainer.setMaxWidth(400);
		
		Label welcomeLabel = new Label("Welcome to Just4Flights");
		welcomeLabel.getStyleClass().add("welcome-label");
		
		// Text box for User name.
		TextField usernameField = new TextField();
		usernameField.setPromptText("Username");
	    usernameField.setPrefWidth(300); // Set preferred width directly in Java code
	    usernameField.setMaxWidth(300);
		usernameField.getStyleClass().add("text-field"); // Adds the style for the user name field

		// Text box for Password.
		PasswordField passwordField = new PasswordField();
		passwordField.setPromptText("Password");
	    passwordField.setPrefWidth(300); // Set preferred width directly in Java code
		passwordField.setMaxWidth(300);
	    passwordField.getStyleClass().add("password-field"); // Adds style for the password field
		
	    // Make space between text fields and buttons
	    Region spacer = new Region();
	    spacer.setPrefHeight(30);
	    
	    Label statusLabel = new Label();
		statusLabel.setId("statusLabel");; // Optional styling for status messages
	    
		// Login Button.
		Button loginButton = new Button("Login");
		loginButton.getStyleClass().add("button"); // Adds style for the login button
		loginButton.setMaxWidth(250);
		
		HBox orContainer = new HBox();
	    orContainer.setAlignment(Pos.CENTER);
	    orContainer.setSpacing(10); // Spacing between separators and label

	    Separator leftSeparator = new Separator();
	    leftSeparator.setPrefWidth(90); // This width will dynamically adjust with HBox
	    leftSeparator.getStyleClass().add("separator");
	    
	    // "or" label
	    Label orLabel = new Label("or");
	    orLabel.getStyleClass().add("or-label");
		
	    Separator rightSeparator = new Separator();
	    rightSeparator.setPrefWidth(90);
	    rightSeparator.getStyleClass().add("separator");

	    
	    orContainer.getChildren().addAll(leftSeparator, orLabel, rightSeparator);
		
		// Register Button.'
		Button registerButton = new Button("Create New Account");
		registerButton.getStyleClass().add("button"); // Adds style for register button
		registerButton.setMaxWidth(250);
		
		// Login Button Event Listener.
		loginButton.setOnAction(e -> {
			statusLabel.setText("");
			
			String username = usernameField.getText();
			String password = passwordField.getText();
			
			 if (username.isEmpty() || password.isEmpty()) {
				 statusLabel.setText("Username and Password cannot be empty.");
			 } else {
			     boolean isAuthenticated = sendLoginRequest(username, password);
			        
			     if (isAuthenticated) {
			    	 statusLabel.setText("Login Successful.");
			         showHomePage();
			     } else {
			         statusLabel.setText("Invalid Username or Password");
			     }
			 }
		});
		
		// Register Button Event Listener.
		registerButton.setOnAction(e -> {
			showRegisterPage();
		});
		
		// Add all login elements to the card container
		cardContainer.getChildren().addAll(welcomeLabel, usernameField, passwordField, spacer, statusLabel, loginButton, orContainer, registerButton);
		
		// Add the imageView and login form to the root Hbox
		// rootContainer.getChildren().addAll(imageView, cardContainer);
		
		 // Add all card container to the main container
		 mainContainer.getChildren().add(cardContainer);
		
		// Create the scene and apply the CSS file (Change first number to 800 and mainContainer to root if I can figure out images)
		Scene loginScene = new Scene(mainContainer, 400, 400);
		loginScene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
		
		// Show Login Page.
		primary.setScene(loginScene);
		primary.setTitle("Login Page");
		primary.show();
	}
	
	private void showRegisterPage() {
		
		// Main container for the form elements
		VBox mainContainer = new VBox();
		mainContainer.getStyleClass().add("main-container"); // Class for main container
		
		VBox cardContainer = new VBox(20);
		cardContainer.getStyleClass().add("card-container"); // Class for card container
		cardContainer.setMaxWidth(500); // Set preferred width to 400 pixels

		
		// Title label
		Label titleLabel = new Label("Create your Just4Flights Account");
		titleLabel.setId("title-label"); // ID for title label
		
		// Text box for First Name.
		TextField firstNameText = new TextField();
		firstNameText.setPromptText("First Name");
		firstNameText.setPrefWidth(150);
		firstNameText.getStyleClass().add("text-field"); // Class for text field
		
		// Text box for Last Name.
		TextField lastNameText = new TextField();
		lastNameText.setPromptText("Last Name");
		lastNameText.setPrefWidth(150);
		lastNameText.getStyleClass().add("text-field"); // Class for text field

		HBox nameContainer = new HBox(10, firstNameText, lastNameText);
		nameContainer.getStyleClass().add("name-container"); // Class for name container
		
		// Text box for Email.
		TextField emailText = new TextField();
		emailText.setPromptText("Username/Email");
		emailText.setPrefWidth(320);
		emailText.getStyleClass().add("text-field");
		
		// Text box for Password.
		PasswordField passwordText = new PasswordField();
		passwordText.setPromptText("Password");
		passwordText.setPrefWidth(150);
		passwordText.getStyleClass().add("text-field");
		
		// Text box for Confirm Password.
		PasswordField confirmPasswordText = new PasswordField();
		confirmPasswordText.setPromptText("Confirm Password");
		confirmPasswordText.setPrefWidth(150);
		confirmPasswordText.getStyleClass().add("text-field");
		
		HBox passwordContainer = new HBox(10, passwordText, confirmPasswordText);
		passwordContainer.getStyleClass().add("password-container");
		
		// Button for Create Account.
		Button createAccountButton = new Button("Create Account");
		createAccountButton.setId("create-account-button");
		
	    // Status label
		Label statusLabel = new Label();
		statusLabel.setId("status-label");
		
		// Create Account Event Listener.
		createAccountButton.setOnAction(e -> {
			String firstname = firstNameText.getText();
			String lastname = lastNameText.getText();
			String email = emailText.getText();
			String password = passwordText.getText();
			String confirmPassword = confirmPasswordText.getText();
			
			if (!password.equals(confirmPassword)) {
				statusLabel.setText("Passwords Don't Match");
			} else if (firstname.isEmpty() || lastname.isEmpty() || email.isEmpty() || password.isEmpty()) {
				statusLabel.setText("Fill all Fields");
			} else {
				String response = sendRegisterRequest(firstname, lastname, email, password);
				if (response.equals("User Registered")) {
					showLoginPage();
				} else {
					statusLabel.setText(response);
				}
			}
		});
		
		cardContainer.getChildren().addAll(titleLabel, nameContainer, emailText, passwordContainer, createAccountButton, statusLabel);

		mainContainer.getChildren().add(cardContainer);
		
		// Screen for Creating a new Account.
		Scene registerScene = new Scene(mainContainer, 500, 200);
		registerScene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
		
		// Show Register Page.
		primary.setScene(registerScene);
		primary.setTitle("Create New Account");
		primary.show();
	}
	
	private void showHomePage() {
		
	}
	
	public boolean sendLoginRequest(String username, String password) {
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
	
	public String sendRegisterRequest(String firstname, String lastname, String email, String password) {
		try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
				 PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
				 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
			
			// Send Registration Request to the Server.
			out.println("REGISTER " + firstname + " " + lastname + " " + email + " " + password);
			// Receive Response from the Client.s
			String response = in.readLine();
			return response;
		} catch (IOException e) {
			e.printStackTrace();
			return "Invalid Credentials";
		}
	}
	
	public static void main(String[] args) {
		launch(args); // runs app.
	}
}