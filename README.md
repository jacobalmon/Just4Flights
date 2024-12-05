# Just4Flights Application

## Introduction
Just4Flights is a Java-based flight booking application that allows users to search for flights, book tickets, and manage user profiles. The application leverages a client-server architecture, integrates with an external API to fetch flight details, and uses a MySQL database for user authentication and booking management.

## Table of Contents
- [Features](#features)
- [Installation](#installation)
- [Usage](#usage)
- [Dependencies](#dependencies)
- [Configuration](#configuration)
- [Contributing](#contributing)

## Features
- **User Authentication**: Register and log in securely.
- **Flight Search**: Search for flights using a third-party API.
- **Booking Management**: Book flights and manage bookings.
- **User Profile**: View and update personal information and booking history.
- **Client-Server Communication**: Uses socket programming for communication between the client and server.

## Installation
1. Clone the repository:
    ```bash
    https://github.com/jacobalmon/SE-Project.git
    ```
2. Set up the database:
    - Create a MySQL database named `just4flights`.
    - Update the `Server.java` file with your database credentials.
3. Install JavaFX (required for the GUI).
4. Run the server:
    ```bash
    javac Server.java
    java myapp.Server
    ```
5. Run the client:
    ```bash
    javac Client.java
    java myapp.Client
    ```

## Usage
- **Login**: Start the application and log in with your credentials.
- **Register**: Create a new account if you are a new user.
- **Search Flights**: Enter the origin, destination, date, and other details to search for available flights.
- **Book Flights**: Select a flight and proceed to payment.
- **View Profile**: Access your booking history and update personal details.

## Dependencies
- **JavaFX**: For building the GUI.
- **Unirest**: For making API calls to the flight search API.
- **MySQL Connector**: For database connectivity.
- **Gson**: For JSON processing.
  
  Add dependencies manually or using Maven:

    ```xml
    <dependency>
        <groupId>com.mashape.unirest</groupId>
        <artifactId>unirest-java</artifactId>
        <version>1.4.9</version>
    </dependency>
    ```

## Configuration
- **Database**: Update the credentials in `Server.java`.
- **API Keys**: Replace the API keys in `SearchFlights.java` with your own credentials for the flight search API.
