package myapp;

import java.io.*;
import java.net.*;

public class Client {
	// Initializing Socket and Input & Output Streams.
	private Socket socket = null;
	private DataInputStream input = null;
	private DataOutputStream output = null;
	
	public Client(String address, int port) {
		try {
			socket = new Socket(address, port);
			System.out.println("Connected");
			
			
		} catch (UnknownHostException u) {
			return;
		} catch (IOException i) {
			return;
		}
	}
	
	public static void main(String[] args) {
		Client client = new Client("127.0.0.1", 5000);
	}
}
