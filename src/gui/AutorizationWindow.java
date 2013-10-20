package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import core.DBManager;

public class AutorizationWindow {

	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	private JLabel lblNewLabel_1;
	private JButton btnSignIn;
	private DBManager dbManager;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AutorizationWindow window = new AutorizationWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public AutorizationWindow() {
		dbManager = new DBManager();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		textField = new JTextField();
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		frame.getContentPane().setLayout(new GridLayout(0, 1, 0, 0));
		
		lblNewLabel_1 = new JLabel("Enter login and password");
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblLogin = new JLabel("Login:");
		frame.getContentPane().add(lblLogin);
		frame.getContentPane().add(textField);
		
		JLabel lblNewLabel = new JLabel("Password:");
		frame.getContentPane().add(lblNewLabel);
		frame.getContentPane().add(textField_1);
		
		btnSignIn = new JButton("Sign in");
		frame.getContentPane().add(btnSignIn);
		btnSignIn.setMargin(new Insets(10, 10, 10, 10));
		btnSignIn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(dbManager.authenticate(textField.getText(), textField_1.getText()) 
						== DBManager.AUTH_TYPE.ADMIN) {
				} else if(dbManager.authenticate(textField.getText(), textField_1.getText()) 
						== DBManager.AUTH_TYPE.USER) {
					
				} else {
					JOptionPane.showMessageDialog(frame, "Invalid login or password");
					textField.setText("");
					textField_1.setText("");
				}
				
			}
		});
	}
}
