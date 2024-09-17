package newloginpage;

import java.awt.event.*;
import javax.swing.*;

public class NewLoginPage {
	public static void main(String[] args) {
        JFrame f = new JFrame("Login Page"); //create JFrame
        
        JButton b = new JButton("Login"); //create JButton
        b.setBounds(130,250,90,30);
        
        JLabel user = new JLabel("Username:"); //create Username JLabel
        user.setBounds(50,150,80,30);
        
        JLabel password = new JLabel("Password:"); //create Password JLabel
        password.setBounds(50,200,80,30);
        
        JTextField t1 = new JTextField(20); //create JTextField for t1
        t1.setBounds(150,150,150,30);
        
        JTextField t2 = new JPasswordField(20); //create JTextfield for t2
        t2.setBounds(150,200,150,30);
        
        b.addActionListener(new ActionListener() { 
        	public void actionPerformed(ActionEvent e) {
        		//get text from textboxes
        		String username = t1.getText();  
            	String password = t2.getText(); 
            	
            	//checks if user is authenticated
            	if(username.equals("software") && password.equals("engineering")) { 
            		System.out.println("success");
            	} else {
            		System.out.println("unsucessful"); 
            	}
        	} 
        });
       
        //adding widgets to JFrame
        f.add(user);
        f.add(password);
        f.add(b);
        f.add(t1);
        f.add(t2);
        
        //setting size of the JFrame and make it visible
        f.setSize(400,400);
        f.setLayout(null);
        f.setVisible(true);
    }
}
