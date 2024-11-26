package myapp;

import java.io.*;
import java.net.*;

import com.mashape.unirest.http.exceptions.UnirestException;

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
	// JDBC Data Members.
	private static final String SERVER_ADDRESS = "127.0.0.1";
	private static final int SERVER_PORT = 8080;
	private Stage primary;
	
	// API Data Members.
	private static final String host = "https://rapidapi.com/apiheya/api/sky-scrapper";
	private static final String charSet = "UTF-8";
	private static final String x_rapidapi_host = "sky-scrapper.p.rapidapi.com";
	private static final String x_rapidapi_key = "1d7688e53emshc14635c6a9a41b4p1f00bbjsnd3275bd81949";
	
	public void start(Stage primary) {
		this.primary = primary;
		showLoginPage(); // Start with Login Page.
	}
	
	private void showLoginPage() {
		
		// Main container for the image and login form.
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
			         //showFlightBooking();
			    	 showFlightDetails();
			    	 //showPayment();
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
	
	private void showFlightBooking() {
		// Melissa Does this...
	}
	
	private void showFlightSearchResults() {
		// Kyle is here
	}
	
	private void showFlightDetails() {
		//Main container for flight details
	    HBox mainContainer = new HBox(20);
	    mainContainer.getStyleClass().add("main-container");

	    //First box for airline/flight details
	    VBox flightDetails1 = new VBox(10);
	    flightDetails1.getStyleClass().add("card-container");
	    
	    Label flightDetailsTitle1 = new Label("AIRLINE/FLIGHT DETAILS");
	    flightDetailsTitle1.getStyleClass().add("section-title");
	    
	    Label detail1_1 = new Label("Flight: Just4");
	    Label detail1_2 = new Label("Departure: New York");
	    Label detail1_3 = new Label("Arrival: Los Angeles");
	    Label detail1_4 = new Label("Date: Dec 25, 2024");
	    Label detail1_5 = new Label("Duration: 6h");
	    Label detail1_6 = new Label("Airline: Just4");

	    flightDetails1.getChildren().addAll(flightDetailsTitle1, detail1_1, detail1_2, detail1_3, detail1_4, detail1_5, detail1_6);
	    
	    //Second box for airling.flight for additional details
	    VBox flightDetails2 = new VBox(10);
	    flightDetails2.getStyleClass().add("card-container");

	    Label flightDetailsTitle2 = new Label("ADDITIONAL DETAILS");
	    flightDetailsTitle2.getStyleClass().add("section-title");
	    
	    Label detail2_1 = new Label("Price: $350");
	    Label detail2_2 = new Label("Baggage: 2 bags");
	    Label detail2_3 = new Label("Seat: Economy");

	    flightDetails2.getChildren().addAll(flightDetailsTitle2, detail2_1, detail2_2, detail2_3);

	    //Book Flight Button
	    Button bookFlightButton = new Button("BOOK FLIGHT");
	    bookFlightButton.getStyleClass().add("action-button");
	    bookFlightButton.setOnAction(e -> showPayment());

	    //Add components + button to main container
	    mainContainer.getChildren().addAll(flightDetails1, flightDetails2, bookFlightButton);

	    //Create and show styles
	    Scene flightDetailsScene = new Scene(mainContainer, 800, 400);
	    flightDetailsScene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

	    //show window
	    primary.setScene(flightDetailsScene);
	    primary.setTitle("Flight Details");
	    primary.show();
	}
	
	private void showPayment() {
		 //Main container for payment and flight details
	    HBox mainContainer = new HBox(20);
	    mainContainer.getStyleClass().add("main-container");

	    //Payment details container
	    VBox paymentDetailsContainer = new VBox(15);
	    paymentDetailsContainer.getStyleClass().add("card-container");

	    //Title for payment details
	    Label paymentDetailsTitle = new Label("PAYMENT DETAILS");
	    paymentDetailsTitle.getStyleClass().add("section-title");

	    //Card number input field
	    TextField cardNumberField = new TextField();
	    cardNumberField.setPromptText("Card Number");
	    cardNumberField.getStyleClass().add("text-field");

	    TextField expiryField = new TextField();
	    expiryField.setPromptText("MM/YY");
	    expiryField.getStyleClass().add("text-field");

	    TextField cvcField = new TextField();
	    cvcField.setPromptText("CVC");
	    cvcField.getStyleClass().add("text-field");

	    HBox expiryAndCvcContainer = new HBox(10, expiryField, cvcField);
	    expiryAndCvcContainer.getStyleClass().add("name-container");

	    //Checkout Button
	    Button checkoutButton = new Button("Checkout");
	    checkoutButton.getStyleClass().add("action-button");
	    checkoutButton.setOnAction(e -> {
	        //Add checkout logic here. PLACEHOLDERRRR
	    });

	    //Add payment details to container
	    paymentDetailsContainer.getChildren().addAll(paymentDetailsTitle, cardNumberField, expiryAndCvcContainer, checkoutButton);

	    //Flight review details container
	    VBox flightDetailsContainer = new VBox(15);
	    flightDetailsContainer.getStyleClass().add("card-container");

	    //Title for flight review section
	    Label flightDetailsTitle = new Label("REVIEW FLIGHT DETAILS");
	    flightDetailsTitle.getStyleClass().add("section-title");

	    //Flight detail labels
	    Label flightDetail1 = new Label("Flight: Just4");
	    Label flightDetail2 = new Label("Departure: New York");
	    Label flightDetail3 = new Label("Arrival: Los Angeles");
	    Label flightDetail4 = new Label("Price: $350");

	    //Add flight details to container
	    flightDetailsContainer.getChildren().addAll(flightDetailsTitle, flightDetail1, flightDetail2, flightDetail3, flightDetail4);

	    //Add components to the main container
	    mainContainer.getChildren().addAll(paymentDetailsContainer, flightDetailsContainer);

	    // Create and show styles
	    Scene paymentScene = new Scene(mainContainer, 800, 400);
	    paymentScene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

	    //Set and show payment page
	    primary.setScene(paymentScene);
	    primary.setTitle("Payment Page");
	    primary.show();
	}
	
	private void showUserProfile() {
		
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
	
	private String[] searchFlights(String src, String dst, String date, int numAdults, int numChildren, int numInfants, String flightType) throws UnirestException {
		String srcIds = SearchFlights.getAirport(src);
		String dstIds = SearchFlights.getAirport(dst);
		String flights = SearchFlights.getFlight(srcIds, dst, dstIds, dst, date, numAdults, numChildren, numInfants, flightType);
		String[] flightDetails = SearchFlights.parseResponse(flights);
		return flightDetails;
	}
	
	public static void main(String[] args) {
		launch(args); // runs app.
	}
}