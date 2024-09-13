package swingcalculator;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class SwingCalculator {
	public static int calc(int num1, int num2, char operator) {
		int result = 0;
		switch(operator)
		{
		case '+':
			result = num1 + num2;
		break;
		case '-':
			result = num1 - num2;
		break;
		case '*':
			result = num1 * num2;
		break;
		case '/':
			result = num1 / num2;
		}
		return result;
	}
	
	public static void main(String[] args) {	
		// JFrame.
		JFrame f = new JFrame("Swing Calculator");
		
		// First Integer Text Box.
		JTextField t1 = new JTextField(20);
		t1.setBounds(120,50,150,30);

		// Second Integer Text Box.
		JTextField t2 = new JTextField(20);
		t2.setBounds(120,100,150,30);
		
		// Addition Button.
		JButton addB = new JButton("+");
		addB.setBounds(70, 150, 50, 50);
		addB.setFont(new Font("Arial",Font.PLAIN, 25));
	
		// Subtraction Button.
		JButton subB = new JButton("-");
		subB.setBounds(130, 150, 50, 50);
		subB.setFont(new Font("Arial",Font.PLAIN, 25));
		
		// Multiplication Button.
		JButton multB = new JButton("*");
		multB.setBounds(190, 150, 50, 50);
		multB.setFont(new Font("Arial",Font.PLAIN, 25));

		// Division Button.
		JButton divB = new JButton("/");
		divB.setBounds(250, 150, 50, 50);
		divB.setFont(new Font("Arial",Font.PLAIN, 25));
		
		// Output Text Box.
		JTextField t3 = new JTextField(20);
		t3.setEditable(false); // Note that we can make the box not editable.
		t3.setBounds(120, 230, 150, 30);
		
		// Event Listener for Addition.
		addB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int num1 = Integer.parseInt(t1.getText());
					int num2 = Integer.parseInt(t2.getText());
					int result = calc(num1, num2, '+');
					t3.setText(Integer.toString(result));
				// Exception is thrown for missing textbox inputs.
				} catch(NumberFormatException ex) {
					t3.setText("Invalid Response");
				}
			}
		});
		
		// Event Listener for Subtraction.
		subB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int num1 = Integer.parseInt(t1.getText());
					int num2 = Integer.parseInt(t2.getText());
					int result = calc(num1, num2, '-');
					t3.setText(Integer.toString(result));
				// Exception is thrown for missing textbox inputs.
				} catch(NumberFormatException ex) {
					t3.setText("Invalid Response");
				}
			}
		});
		
		// Event Listener for Multiplication.
		multB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int num1 = Integer.parseInt(t1.getText());
					int num2 = Integer.parseInt(t2.getText());
					int result = calc(num1, num2, '*');
					t3.setText(Integer.toString(result));
				// Exception is thrown for missing textbox inputs.
				} catch(NumberFormatException ex) {
					t3.setText("Invalid Response");
				}
			}
		});
		
		// Event Listener for Division.
		divB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int num1 = Integer.parseInt(t1.getText());
					int num2 = Integer.parseInt(t2.getText());
					if (num2 == 0) { // Stop's Dividing by 0 Error.
						t3.setText("Math Error"); 
					} else {
						int result = calc(num1, num2, '/');
						t3.setText(Integer.toString(result));
					}
				// Exception is thrown for missing textbox inputs.
				} catch(NumberFormatException ex) {
					t3.setText("Invalid Response");
				}
			}
		});
		
		// Adding Widgets to JFrame.
		f.add(t1);
		f.add(t2);
		f.add(addB);
		f.add(subB);
		f.add(multB);
		f.add(divB);
		f.add(t3);
		
		// Sizing & Making JFrame Visible.
		f.setSize(400, 400);
		f.setLayout(null);
		f.setVisible(true);
	}
	
}
