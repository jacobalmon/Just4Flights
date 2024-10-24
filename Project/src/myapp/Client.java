package myapp;

import java.io.*;
import java.net.*;

public class Client {
	private static final String SERVER_ADDRESS = "127.0.0.1";
	private static final int SERVER_PORT = 8080;
	
	public static void main(String[] args) {
		try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
			 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			 PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			 BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in))) {
			
			while (true) {
				// Read the Type of Request.
				String request = consoleInput.readLine();
				
				// Checking if it's a Register Request.
				if ("register".equals(request)) {
					out.println("REGISTER");
					
					// Reading Username & Sending to Server.
					String username = consoleInput.readLine();
					out.println(username);
					
					// Reading Password & Sending to Server.
					String password = consoleInput.readLine();
					out.println(password);
					
					// Receiving Server's Response.
					String response = in.readLine();
					System.out.println(response);
				// Checking if it's a Authenticate Request.
				} else if("authenticate".equals(request)) {
					out.println("AUTHENTICATE");
					
					String username = consoleInput.readLine();
					out.println(username);
					
					String password = consoleInput.readLine();
					out.println(password);
					
					String response = in.readLine();
					System.out.println(response);
				// Throw an Error for now.
				} else {
					System.out.println("ERROR");
				}
			}	
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
