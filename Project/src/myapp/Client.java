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
					
			String username = consoleInput.readLine();
			String password = consoleInput.readLine();
			
			out.println(username);
			out.println(password);
			
			String response = in.readLine();
			System.out.println(response);
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
