package myapp;

import java.io.*;
import java.net.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.json.JSONArray;

import com.google.gson.Gson;
import com.mashape.unirest.http.exceptions.UnirestException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.layout.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class Client extends Application {
	// JDBC Data Members.
	private static final String SERVER_ADDRESS = "127.0.0.1";
	private static final int SERVER_PORT = 8080;
	private Stage primary;
	private String final_username;
	private String final_firstname;
	private String final_lastname;
	
	public void start(Stage primary) {
		this.primary = primary;
		showLoginPage(); // Start with Login Page.
	}
		
	private void showLoginPage() {
		
		// Main container for the image and login form.
		HBox rootContainer = new HBox();
		rootContainer.setAlignment(Pos.CENTER); // Aligns children to the left
		rootContainer.setSpacing(100); // Spacing for image and login form
		
		Region leftSpacer = new Region();
		HBox.setHgrow(leftSpacer, Priority.NEVER); // Allow the spacer to grow
		
		ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream("/cat.jpg")));
		imageView.setFitWidth(300); // Set preferred width for the image
		imageView.setPreserveRatio(true); // Maintain aspect ratio
		
		// Card container to hold login elements with a "card" appearance
		VBox cardContainer = new VBox(10); // Spacing between elements
		cardContainer.setId("login-card-container"); // ID for styling
		cardContainer.setMaxWidth(400);
		cardContainer.setPrefWidth(400);
		cardContainer.setMaxHeight(450);
		cardContainer.setPrefHeight(450);
		
		Label welcomeLabel = new Label("Welcome to Just4Flights!");
		welcomeLabel.getStyleClass().add("welcome-label");
		
		// Text box for User name.
		TextField usernameField = new TextField();
		usernameField.setPromptText("Username");
	    usernameField.setPrefWidth(300); // Set preferred width directly in Java code
	    usernameField.setMaxWidth(300);
		usernameField.getStyleClass().add("username-field"); // Adds the style for the user name field

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
		            final_username = username;
		            showFlightBooking(); // Navigate to the home page
		        } else {
		            statusLabel.setText("Invalid Username or Password");
		        }
		    }
		});
		
		// Add all login elements to the card container
		cardContainer.getChildren().addAll(welcomeLabel, usernameField, passwordField, spacer, statusLabel, loginButton, orContainer, registerButton);
		
		// Add the imageView and login form to the root Hbox
		rootContainer.getChildren().addAll(imageView, cardContainer);
		
		// Create the scene and apply the CSS file (Change first number to 800 and mainContainer to root if I can figure out images)
		Scene loginScene = new Scene(rootContainer, 1000, 600);
		loginScene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
		
		// Show Login Page.
		primary.setScene(loginScene);
		
		// Transition to Register page
		registerButton.setOnAction(e -> {
			showRegisterPage();
		});
		
		primary.setTitle("Login Page");
		primary.show();
	}
	
	private void showRegisterPage() {
		
		// Main container for the form elements
		VBox mainContainer = new VBox();
		mainContainer.getStyleClass().add("main-container"); // Class for main container
		
		VBox cardContainer = new VBox(20);
		cardContainer.setId("register-card-container"); // Id  for card container
		cardContainer.setMaxWidth(500); // Set preferred width to 400 pixels

		
		// Title label
		Label titleLabel = new Label("Create your Just4Flights Account");
		titleLabel.setId("createAccount-title-label"); // ID for title label
		
		// Text box for First Name.
		TextField firstNameText = new TextField();
		firstNameText.setPromptText("First Name");
		firstNameText.setPrefWidth(150);
		firstNameText.getStyleClass().add("first-name-field"); // Class for text field
		
		// Text box for Last Name.
		TextField lastNameText = new TextField();
		lastNameText.setPromptText("Last Name");
		lastNameText.setPrefWidth(150);
		lastNameText.getStyleClass().add("last-name-field"); // Class for text field

		HBox nameContainer = new HBox(10, firstNameText, lastNameText);
		nameContainer.getStyleClass().add("name-container"); // Class for name container
		
		// Text box for Email.
		TextField emailText = new TextField();
		emailText.setPromptText("Username/Email");
		emailText.setPrefWidth(320);
		emailText.getStyleClass().add("email-field");
		
		// Text box for Password.
		PasswordField passwordText = new PasswordField();
		passwordText.setPromptText("Password");
		passwordText.setPrefWidth(150);
		passwordText.getStyleClass().add("new-password-field");
		
		// Text box for Confirm Password.
		PasswordField confirmPasswordText = new PasswordField();
		confirmPasswordText.setPromptText("Confirm Password");
		confirmPasswordText.setPrefWidth(150);
		confirmPasswordText.getStyleClass().add("confirm-password-field");
		
		HBox passwordContainer = new HBox(10, passwordText, confirmPasswordText);
		passwordContainer.getStyleClass().add("password-container");
		
		// Button for Create Account.
		Button createAccountButton = new Button("Create Account");
		createAccountButton.getStyleClass().add("create-account-button");
		
	    // Status label
		Label statusLabel = new Label();
		statusLabel.getStyleClass().add("create-status-label");
		
		// Back button
		Button backButton = new Button("â† Back to Login");
		backButton.getStyleClass().add("back-button"); // Unique id for styling
		backButton.setOnAction(e -> showLoginPage()); // Navigate back to the login page
		
		HBox backButtonContainer = new HBox(backButton);
		backButtonContainer.setAlignment(Pos.BASELINE_LEFT); //Align left
		VBox.setMargin(backButtonContainer, new Insets(20, 0, 0, -10));
		
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
		
		mainContainer.getChildren().add(cardContainer);
		cardContainer.getChildren().addAll(titleLabel, nameContainer, emailText, passwordContainer, createAccountButton, statusLabel, backButtonContainer);
		VBox.setMargin(backButton,  new Insets(20, 0, 0, 0)); // Add margin for spacing
		
		// Screen for Creating a new Account.
		Scene registerScene = new Scene(mainContainer, 1000, 600);
		registerScene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
		
		// Show Register Page.
		primary.setScene(registerScene);
		primary.setTitle("Create New Account");
		primary.show();
	}
	
	private HBox createHeader(String pageTitle) {
	    // Logo Button
	    Button logoButton = new Button("Just4Flights |");
	    logoButton.setId("logo-button");
	    logoButton.setOnAction(e -> showFlightBooking()); // Navigate to the home screen

	    // Page Title
	    Label titleLabel = new Label(pageTitle);
	    titleLabel.setId("page-title");
	    
	    // Group Logo and Title on the Left
	    HBox leftContainer = new HBox(10, logoButton, titleLabel);
	    leftContainer.setAlignment(Pos.CENTER_LEFT);

	    // Hamburger Menu on the Right
	    MenuButton menuButton = new MenuButton("â˜°");
	    menuButton.setId("hamburger-menu");
	    
	    MenuItem profileMenuItem = new MenuItem("User Profile");
	    profileMenuItem.setOnAction(e -> showUserProfile()); // Navigate to the user profile screen

	    // Log Out Menu Item
	    MenuItem logoutMenuItem = new MenuItem("Log Out");
	    logoutMenuItem.setOnAction(e -> showLoginPage()); // Navigate to the login screen

	    menuButton.getItems().addAll(profileMenuItem, logoutMenuItem);
	    
	    // Header Layout
	    HBox header = new HBox();
	    header.getChildren().addAll(leftContainer, menuButton);
	    header.setId("header");
	    header.setSpacing(10);
	    header.setAlignment(Pos.CENTER_LEFT);
	    
	    HBox.setHgrow(leftContainer, Priority.ALWAYS); // Allow title to center properly

	    return header;
	}
	
	private void showFlightBooking() {
	    // Main container for the page
	    VBox pageContainer = new VBox(20); // Wrap everything in a VBox
	    pageContainer.getStyleClass().add("page-container");

	    // Add header to the page
	    pageContainer.getChildren().add(createHeader("Flight Booking"));

	    // Card container for grouping all form elements
	    VBox cardContainer = new VBox(20);
	    cardContainer.setId("homepage-card-container");
	    cardContainer.setAlignment(Pos.TOP_LEFT); // Align everything to the left
	    cardContainer.setPadding(new Insets(20));

	    // "From" Label and Field
	    VBox fromGroup = new VBox(5); // Group label and field vertically
	    Label fromLabel = new Label("From");
	    fromLabel.setId("from-label");
	    TextField fromField = new TextField();
	    fromField.setPromptText("Search starting location");
	    fromField.setId("from-field");
	    fromField.setStyle("-fx-border-color: #CCCCCC; -fx-border-width: 2px; -fx-border-radius: 5px; -fx-padding: 5px;");
	    fromGroup.getChildren().addAll(fromLabel, fromField);

	    // "To" Label and Field
	    VBox toGroup = new VBox(5);
	    Label toLabel = new Label("To");
	    toLabel.setId("to-label");
	    TextField toField = new TextField();
	    toField.setPromptText("Search destination");
	    toField.setId("to-field");
	    toField.setStyle("-fx-border-color: #CCCCCC; -fx-border-width: 2px; -fx-border-radius: 5px; -fx-padding: 5px;");
	    toGroup.getChildren().addAll(toLabel, toField);

	    // "Date" Label and Field
	    VBox dateGroup = new VBox(5);
	    Label dateLabel = new Label("Date (YYYY-MM-DD)");
	    dateLabel.setId("date-label");
	    TextField dateField = new TextField();
	    dateField.setPromptText("Enter travel date");
	    dateField.setId("date-field");
	    dateField.setStyle("-fx-border-color: #CCCCCC; -fx-border-width: 2px; -fx-border-radius: 5px; -fx-padding: 5px;");
	    dateGroup.getChildren().addAll(dateLabel, dateField);

	    // Passenger Selection
	    VBox passengerGroup = new VBox(5);
	    Label passengersLabel = new Label("Passengers");
	    passengersLabel.setId("passengers-label");

	    HBox passengerSelection = new HBox(20);
	    passengerSelection.setAlignment(Pos.CENTER_LEFT);
	    Label adultsLabel = new Label("Adults");
	    Spinner<Integer> adultsSpinner = new Spinner<>(1, 10, 1);
	    adultsSpinner.setId("adults-spinner");
	    Label childrenLabel = new Label("Children");
	    Spinner<Integer> childrenSpinner = new Spinner<>(0, 10, 0);
	    childrenSpinner.setId("children-spinner");
	    passengerSelection.getChildren().addAll(adultsLabel, adultsSpinner, childrenLabel, childrenSpinner);
	    passengerGroup.getChildren().addAll(passengersLabel, passengerSelection);

	    // Flight Type Dropdown
	    VBox flightTypeGroup = new VBox(5);
	    Label flightTypeLabel = new Label("Select Flight Type");
	    flightTypeLabel.setId("flight-type-label");
	    ComboBox<String> flightTypeDropdown = new ComboBox<>();
	    flightTypeDropdown.getItems().addAll("Economy", "Premium Economy", "Business", "First Class");
	    flightTypeDropdown.setPromptText("Select flight type");
	    flightTypeDropdown.setId("flight-type-dropdown");
	    flightTypeDropdown.setStyle("-fx-border-color: #CCCCCC; -fx-border-width: 2px; -fx-border-radius: 5px; -fx-padding: 5px;");
	    flightTypeGroup.getChildren().addAll(flightTypeLabel, flightTypeDropdown);

	    // Search Button
	    Button searchButton = new Button("Search Flights");
	    searchButton.setId("search-button");
	    searchButton.setOnAction(e -> {
	        boolean isValid = true;

	        // Reset styles before validation.
	        fromField.setStyle(null);
	        toField.setStyle(null);
	        dateField.setStyle(null);
	        flightTypeDropdown.setStyle(null);

	        // Validate the "From" field.
	        if (fromField.getText().trim().isEmpty()) {
	            fromField.setStyle("-fx-border-color: red; -fx-border-width: 2px; -fx-border-radius: 5px;");
	            isValid = false;
	        }

	        // Validate the "To" field.
	        if (toField.getText().trim().isEmpty()) {
	            toField.setStyle("-fx-border-color: red; -fx-border-width: 2px; -fx-border-radius: 5px;");
	            isValid = false;
	        }

	        // Validate the "Date" field.
	        if (dateField.getText().trim().isEmpty()) {
	            dateField.setStyle("-fx-border-color: red; -fx-border-width: 2px; -fx-border-radius: 5px;");
	            isValid = false;
	        } else {
	            // Check if the entered date is in the past
	            try {
	                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	                LocalDate enteredDate = LocalDate.parse(dateField.getText(), dateFormatter);
	                LocalDate currentDate = LocalDate.now();

	                if (enteredDate.isBefore(currentDate)) {
	                    dateField.setStyle("-fx-border-color: red; -fx-border-width: 2px; -fx-border-radius: 5px;");
	                    isValid = false;
	                    Alert alert = new Alert(Alert.AlertType.ERROR, "The selected date has already passed.");
	                    alert.showAndWait();
	                }
	            } catch (DateTimeParseException ex) {
	                dateField.setStyle("-fx-border-color: red; -fx-border-width: 2px; -fx-border-radius: 5px;");
	                isValid = false;
	                Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid date format. Please use YYYY-MM-DD.");
	                alert.showAndWait();
	            }
	        }

	        // Validate the "Flight Type" dropdown.
	        if (flightTypeDropdown.getValue() == null || flightTypeDropdown.getValue().trim().isEmpty()) {
	            flightTypeDropdown.setStyle("-fx-border-color: red; -fx-border-width: 2px; -fx-border-radius: 5px;");
	            isValid = false;
	        }

	        // Proceed if all fields are valid.
	        if (isValid) {
	            String src = fromField.getText();
	            String dst = toField.getText();
	            String date = dateField.getText();
	            int adults = adultsSpinner.getValue();
	            int children = childrenSpinner.getValue();
	            String type = flightTypeDropdown.getValue();
	            type = parseFlightType(type);

	            try {
	                String[] flights = searchFlights(src, dst, date, adults, children, 0, type);
	                showFlightSearchResults(flights);
	            } catch (UnirestException e1) {
	                e1.printStackTrace();
	            }
	        } else {
	            // Show error message if validation fails.
	            Alert alert = new Alert(Alert.AlertType.ERROR, "Please fill in all required fields.");
	            alert.showAndWait();
	        }
	    });

	    // Add all groups to the card container
	    cardContainer.getChildren().addAll(
	        fromGroup,
	        toGroup,
	        dateGroup,
	        passengerGroup,
	        flightTypeGroup,
	        searchButton
	    );

	    pageContainer.getChildren().add(cardContainer);

	    // Set up the scene and apply styles
	    Scene flightBookingScene = new Scene(pageContainer, 1000, 600);
	    flightBookingScene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
	    primary.setScene(flightBookingScene);
	    primary.setTitle("Flight Booking");
	    primary.show();
	}

	private void showFlightSearchResults(String[] flights) {
		// Main container for the page
	    VBox pageContainer = new VBox(20); // Wrap everything in a VBox
	    pageContainer.getStyleClass().add("page-container");

	    // Add header to the page
	    pageContainer.getChildren().add(createHeader("Search Results"));
		
	    // Scroll Pane for Results
	    ScrollPane scrollPane = new ScrollPane();
	    scrollPane.setFitToWidth(true);
	    scrollPane.getStyleClass().add("scroll-pane");

	    // Container for Flight Results
	    VBox resultsContainer = new VBox(10);
	    resultsContainer.getStyleClass().add("results-container");

	    // Add Flight Information to the Results Container
	    for (String result : flights) {
	        VBox flightCard = new VBox(5); // Create a "card" for each flight
	        flightCard.getStyleClass().add("flight-card");

	        Label flightDetails = new Label(result);
	        flightDetails.getStyleClass().add("flight-details");

	        Button detailsButton = new Button("See Details");
	        detailsButton.getStyleClass().add("details-button");

	        detailsButton.setOnAction(e -> showFlightDetails(flights, result));
	        
	        flightCard.getChildren().addAll(flightDetails, detailsButton);
	        resultsContainer.getChildren().add(flightCard);
	    }

	    scrollPane.setContent(resultsContainer);

	    // Back Button
	    Button backButton = new Button("Back to Search");
	    backButton.getStyleClass().add("action-button");
	    backButton.setOnAction(e -> showFlightBooking());

	    // Wrap Back Button in HBox for Alignment
	    HBox backButtonContainer = new HBox(backButton);
	    backButtonContainer.setAlignment(Pos.CENTER_LEFT); // Align button to the left
	    backButtonContainer.setPadding(new Insets(10, 0, 0, 20)); // Add some spacing above the button

	    // Add components to main container
	    pageContainer.getChildren().addAll(scrollPane, backButtonContainer);

	    // Set Scene
	    Scene resultsScene = new Scene(pageContainer, 1000, 600);
	    resultsScene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
	    primary.setScene(resultsScene);
	    primary.setTitle("Search Results");
	}
	
	private void showFlightDetails(String[] flights, String flight) {
		// Main container for flight details
	    VBox pageContainer = new VBox(20); // Wrap everything in a VBox
	    pageContainer.getStyleClass().add("page-container");

	    // Add header to the page
	    pageContainer.getChildren().add(createHeader("Flight Details"));
		
		//Main container for flight details
	    HBox mainContainer = new HBox(20);
	    mainContainer.getStyleClass().add("flight-details-main-container");

	    //First box for airline/flight details
	    VBox flightDetails1 = new VBox(10);
	    flightDetails1.getStyleClass().add("flight-details-card");
	    
	    Label flightDetailsTitle1 = new Label("AIRLINE/FLIGHT DETAILS");
	    flightDetailsTitle1.getStyleClass().add("flight-details-section-title");
	    
	    Label details = new Label(flight);
	    details.getStyleClass().add("flight-details-label");

	    flightDetails1.getChildren().addAll(flightDetailsTitle1, details);

	    // Book Flight Button
	    Button bookFlightButton = new Button("BOOK FLIGHT");
	    bookFlightButton.getStyleClass().add("book-flight-action-button");
	    bookFlightButton.setOnAction(e -> showPayment(flights, flight));

	    // Back Button
	    Button backButton = new Button("Back to Search Results");
	    backButton.getStyleClass().add("navigation-button");
	    backButton.setOnAction(e -> showFlightSearchResults(flights)); // Navigate to flight search results page

	    
	    // Add components + button to main container
	    mainContainer.getChildren().addAll(flightDetails1, bookFlightButton, backButton);

	    // Add main container to page container
	    pageContainer.getChildren().add(mainContainer);
	    
	    // Create and show styles
	    Scene flightDetailsScene = new Scene(pageContainer, 1000, 600);
	    flightDetailsScene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

	    // Show window
	    primary.setScene(flightDetailsScene);
	    primary.setTitle("Flight Details");
	    primary.show();
	}
	
	private void showPayment(String[] flights, String flight) {
	    // Main container for payment and flight details
	    VBox pageContainer = new VBox(20); // Wrap everything in a VBox
	    pageContainer.getStyleClass().add("page-container");

	    // Add header to the page
	    pageContainer.getChildren().add(createHeader("Payment Page"));

	    // Main container for payment and flight details
	    HBox mainContainer = new HBox(20);
	    mainContainer.getStyleClass().add("payment-container");

	    // Payment details container
	    VBox paymentDetailsContainer = new VBox(15);
	    paymentDetailsContainer.getStyleClass().add("payment-details-card");

	    // Title for payment details
	    Label paymentDetailsTitle = new Label("PAYMENT DETAILS");
	    paymentDetailsTitle.getStyleClass().add("payment-details-title");

	    // Card number input field
	    TextField cardNumberField = new TextField();
	    cardNumberField.setPromptText("Card Number");
	    cardNumberField.getStyleClass().add("payment-input");

	    TextField expiryField = new TextField();
	    expiryField.setPromptText("MM/YY");
	    expiryField.getStyleClass().add("payment-input");

	    TextField cvcField = new TextField();
	    cvcField.setPromptText("CVC");
	    cvcField.getStyleClass().add("payment-input");

	    HBox expiryAndCvcContainer = new HBox(10, expiryField, cvcField);
	    expiryAndCvcContainer.getStyleClass().add("expiry-cvc-container");

	    // Checkout Button
	    Button checkoutButton = new Button("Checkout");
	    checkoutButton.getStyleClass().add("checkout-button");
	    checkoutButton.setOnAction(e -> {
	        String cardNum = cardNumberField.getText();
	        String exp = expiryField.getText();
	        String cvc = cvcField.getText();

	        DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/yy");
	        YearMonth yearMonth = YearMonth.parse(exp, format);
	        LocalDate expDate = yearMonth.atEndOfMonth();
	        LocalDate curr = LocalDate.now();

	        if (cardNum.length() == 16 && cvc.length() == 3 && expDate.isAfter(curr)) {
	            // Convert the flight details array to a JSON string
	            Gson gson = new Gson();
	            String flightJson = gson.toJson(flight);  // Serialize flight array to JSON

	            // Insert or update the user's flight details in the database
	            appendFlightDetailsToDatabase(flightJson);
	            // Go back to the flight booking page
	            showFlightBooking();
	        } else {
	            // Show alert if any validation fails
	            Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid card details or the expiration date has passed.");
	            alert.showAndWait();
	        }
	    });

	    // Back Button
	    Button backButton = new Button("Back to Flight Details");
	    backButton.getStyleClass().add("navigation-button");
	    backButton.setOnAction(e -> showFlightDetails(flights, flight)); // Navigate to flight details page

	    // Add payment details to container
	    paymentDetailsContainer.getChildren().addAll(paymentDetailsTitle, cardNumberField, expiryAndCvcContainer, checkoutButton, backButton);

	    // Flight review details container
	    VBox flightDetailsContainer = new VBox(15);
	    flightDetailsContainer.getStyleClass().add("flight-review-container");

	    // Title for flight review section
	    Label flightDetailsTitle = new Label("REVIEW FLIGHT DETAILS");
	    flightDetailsTitle.getStyleClass().add("payment-details-title");
	    
	    Label flightDetails = new Label(flight);
	    flightDetails.getStyleClass().add("flight-review-label");

	    // Add flight details to container
	    flightDetailsContainer.getChildren().addAll(flightDetailsTitle, flightDetails);

	    // Add components to the main container
	    mainContainer.getChildren().addAll(paymentDetailsContainer, flightDetailsContainer);

	    // Add main container to page container
	    pageContainer.getChildren().add(mainContainer);

	    // Create and show styles
	    Scene paymentScene = new Scene(pageContainer, 1000, 600);
	    paymentScene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

	    // Set and show payment page
	    primary.setScene(paymentScene);
	    primary.setTitle("Payment Page");
	    primary.show();
	}
	
	private void showUserProfile() {
	    // Get Flights for their profile
	    String[] flights = extractUserDetails(final_username);

	    // Main container for the page (using BorderPane for easy positioning)
	    BorderPane pageContainer = new BorderPane();
	    pageContainer.getStyleClass().add("page-container");

	    // Add header to the page (header can go at the top)
	    pageContainer.setTop(createHeader("User Profile"));

	    // Create the container to hold both the user info and the flights
	    HBox mainContent = new HBox(20);  // Horizontal spacing between the two sections
	    mainContent.setPadding(new Insets(20));

	    // Left side: User Information group
	    VBox userInfoGroup = new VBox(10);
	    
	    // First Name
	    HBox firstNameGroup = new HBox(10);
	    Label firstNameLabel = new Label("First Name: ");
	    firstNameLabel.setStyle("-fx-font-weight: bold;");
	    Label firstNameValue = new Label(final_firstname);
	    firstNameGroup.getChildren().addAll(firstNameLabel, firstNameValue);

	    // Last Name
	    HBox lastNameGroup = new HBox(10);
	    Label lastNameLabel = new Label("Last Name: ");
	    lastNameLabel.setStyle("-fx-font-weight: bold;");
	    Label lastNameValue = new Label(final_lastname);
	    lastNameGroup.getChildren().addAll(lastNameLabel, lastNameValue);

	    // Email
	    HBox emailGroup = new HBox(10);
	    Label emailLabel = new Label("Email: ");
	    emailLabel.setStyle("-fx-font-weight: bold;");
	    Label emailValue = new Label(final_username);
	    emailGroup.getChildren().addAll(emailLabel, emailValue);

	    // Add to userInfoGroup
	    userInfoGroup.getChildren().addAll(firstNameGroup, lastNameGroup, emailGroup);

	    // Right side: Flights Group with indent
	    VBox flightsGroup = new VBox(10);
	    Label flightsLabel = new Label("Past & Upcoming Flights:");
	    flightsLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 18px;");
	    
	    VBox flightsList = new VBox(5);
	    for (String flight : flights) {
	        // Clean up the flight string by removing quotes and escaped newlines
	        String cleanedFlight = flight.replaceAll("\"", "").replaceAll("\\\\n", "\n").trim();
	        
	        // Create a label with the cleaned string for each flight
	        Label flightLabel = new Label(cleanedFlight);
	        
	        // Add a separator after each flight label
	        flightsList.getChildren().add(flightLabel);
	        flightsList.getChildren().add(new Separator());
	    }

	    // Wrap the flightsList in a ScrollPane to make it scrollable
	    ScrollPane flightsScrollPane = new ScrollPane(flightsList);
	    flightsScrollPane.setFitToWidth(true); // Ensure the content fits to the width of the ScrollPane
	    flightsScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS); // Always show vertical scrollbar if needed
	    flightsScrollPane.setMaxHeight(500); // Limit the height of the ScrollPane (increased height for more content)
	    flightsScrollPane.setVmax(500); // Set a max height to limit the scrollable space

	    // Create an ImageView to display the cat image
	    ImageView imageView = new ImageView(new Image("cat.jpg"));  // Adjusted to the path for the cat image
	    imageView.setFitHeight(150);  // Adjust the height of the image
	    imageView.setPreserveRatio(true);  // Maintain the aspect ratio of the image

	    // Create a container for the flights and the image (HBox)
	    HBox flightsAndImageContainer = new HBox(20);
	    flightsAndImageContainer.setAlignment(Pos.CENTER_LEFT);  // Align left
	    
	    // Add a spacer to indent the image to the right
	    HBox imageSpacer = new HBox();
	    imageSpacer.setMinWidth(100);  // Adjust the width of the indent to move the image further to the right

	    // Add the flightsScrollPane and the imageView to the container
	    flightsAndImageContainer.getChildren().addAll(flightsScrollPane, imageSpacer, imageView);

	    // Add the flightsAndImageContainer to the flightsGroup
	    flightsGroup.getChildren().addAll(flightsLabel, flightsAndImageContainer);

	    // Add a spacer on the left side of the flightsGroup to push it to the right
	    HBox flightsSpacer = new HBox();
	    flightsSpacer.setMinWidth(100);  // Adjust the width of the indent

	    // Add both the user info and the indented flights group to the main content (HBox)
	    mainContent.getChildren().addAll(userInfoGroup, flightsSpacer, flightsGroup);

	    // Add the main content (user info + flights) to the center of the BorderPane
	    pageContainer.setCenter(mainContent);

	    // "Go Back" Button at the bottom left
	    Button goBackButton = new Button("Go Back to Search Flights");
	    goBackButton.setId("go-back-button");
	    goBackButton.setOnAction(e -> showFlightBooking());  // Navigate back to the booking page

	    // Set the button to the bottom left using the BorderPane's bottom region
	    BorderPane.setAlignment(goBackButton, Pos.BOTTOM_LEFT);
	    pageContainer.setBottom(goBackButton);

	    // Set up the scene and apply styles
	    Scene userProfileScene = new Scene(pageContainer, 1000, 600);
	    userProfileScene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

	    // Set Primary Scene.
	    primary.setScene(userProfileScene);
	    primary.setTitle("User Profile");
	    primary.show();
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
		// Call APIs for Airport Ids.
		String srcIds = SearchFlights.getAirport(src);
		String dstIds = SearchFlights.getAirport(dst);
		
		if (srcIds.equals("No airport found for the location.") || dstIds.equals("No airport found for the location.")) {
			String[] err = {"No airport found for the location."};
			return err;
		} else {
			// Parsing API Response to call API again.
			String[] partsSrc = srcIds.split(", ");
			String[] partsDst = dstIds.split(", ");
			String originSkyId = partsSrc[0].split(": ")[1];
			String originEntityId = partsSrc[1].split(": ")[1];
			String destinationSkyId = partsDst[0].split(": ")[1];
			String destinationEntityId = partsDst[1].split(": ")[1];
			
			// Call API to return the flights.
			String flights = SearchFlights.getFlight(originSkyId, destinationSkyId, originEntityId, destinationEntityId, date, numAdults, numChildren, numInfants, flightType);
			if (flights.equals("No flights found for the specified criteria.")) {
				String[] err = {"No flights found for the specified criteria."};
				return err;
			} else {
				String[] flightDetails = SearchFlights.parseResponse(flights);
				return flightDetails;
			}
		}
	}
	
	private String parseFlightType(String raw) {
		// Parse String for API.
		String type = "";
		if (raw.equals("Economy")) {
			type = "economy";
		} else if (raw.equals("Premium Economy")) {
			type = "premium-economy";
		} else if (raw.equals("Business")) {
			type = "business";
		} else if (raw.equals("First Class")) {
			type = "first";
		}
		
		return type;
	}
	
	private void appendFlightDetailsToDatabase(String flightJson) {
	    String url = "jdbc:mysql://localhost:3306/just4flights"; // Update with your database URL
	    String user = "root"; // Update with your DB username
	    String password = "cloudcrew123"; // Update with your DB password
	    
	    String username = final_username;

	    String querySelect = "SELECT flights FROM users WHERE username = ?";
	    String queryUpdate = "UPDATE users SET flights = ? WHERE username = ?";

	    Gson gson = new Gson();
	    String[] existingDetailsArray = new String[0];
	    String existingDetailsJson = "[]";

	    try (Connection conn = DriverManager.getConnection(url, user, password)) {
	        // Retrieve the existing flight details from the database
	        try (PreparedStatement pstmtSelect = conn.prepareStatement(querySelect)) {
	            pstmtSelect.setString(1, username);
	            ResultSet rs = pstmtSelect.executeQuery();

	            if (rs.next()) {
	                existingDetailsJson = rs.getString("flights");
	                if (existingDetailsJson == null || existingDetailsJson.isEmpty()) {
	                    existingDetailsJson = "[]"; // If no flights, default to empty JSON array
	                }
	                existingDetailsArray = gson.fromJson(existingDetailsJson, String[].class);
	            }
	        }

	        // Append the new flight to the existing details
	        String[] updatedDetailsArray = new String[existingDetailsArray.length + 1];
	        System.arraycopy(existingDetailsArray, 0, updatedDetailsArray, 0, existingDetailsArray.length);
	        updatedDetailsArray[updatedDetailsArray.length - 1] = flightJson;  // Append new flight details

	        // Convert updated details array to JSON
	        String updatedDetailsJson = gson.toJson(updatedDetailsArray);

	        // Update the database with the new flight details
	        try (PreparedStatement pstmtUpdate = conn.prepareStatement(queryUpdate)) {
	            pstmtUpdate.setString(1, updatedDetailsJson);
	            pstmtUpdate.setString(2, username);
	            pstmtUpdate.executeUpdate();
	        }

	    } catch (SQLException ex) {
	        ex.printStackTrace();
	        Alert dbAlert = new Alert(Alert.AlertType.ERROR, "Failed to update flight details.");
	        dbAlert.showAndWait();
	    }
	}
	
	private String[] extractUserDetails(String username) {
        String query = "SELECT first_name, last_name, flights FROM users WHERE username = ?";
        String[] flightsArray = null;

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1/just4flights", "root", "cloudcrew123");
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String flightsJson = rs.getString("flights");

                // Parse flights JSON to array
                JSONArray jsonArray = new JSONArray(flightsJson);
                flightsArray = new String[jsonArray.length()];
                for (int i = 0; i < jsonArray.length(); i++) {
                    flightsArray[i] = jsonArray.getString(i);
                }

                // Set first and last names
                final_firstname = firstName;
                final_lastname = lastName;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flightsArray;
    }
	
	public static void main(String[] args) {
		launch(args); // runs app.
	}
}