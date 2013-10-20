package gui;

import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import java.awt.Dialog;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import core.DBManager;

public class AutorizationWindow extends JDialog {

	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	private JLabel lblNewLabel_1;
	private JButton btnSignIn;
	private DBManager dbManager;

	/**
	 * Create the application.
	 */
	public AutorizationWindow(DBManager dbManager) {
		this.dbManager = dbManager;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		textField = new JTextField();
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		getContentPane().setLayout(new GridLayout(0, 1, 0, 0));
		
		lblNewLabel_1 = new JLabel("Enter login and password");
		getContentPane().add(lblNewLabel_1);
		
		JLabel lblLogin = new JLabel("Login:");
		getContentPane().add(lblLogin);
		getContentPane().add(textField);
		
		JLabel lblNewLabel = new JLabel("Password:");
		getContentPane().add(lblNewLabel);
		getContentPane().add(textField_1);
		
		btnSignIn = new JButton("Sign in");
		getContentPane().add(btnSignIn);
		btnSignIn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(dbManager.authenticate(textField.getText(), textField_1.getText()).equals(DBManager.AUTH_TYPE.ADMIN)) {
					//dispose();
					setVisible(false);
				} else if(dbManager.authenticate(textField.getText(), textField_1.getText()).equals(DBManager.AUTH_TYPE.USER)) {
					dispose();
				} else {
					JOptionPane.showMessageDialog(frame, "Invalid login or password");
					textField.setText("");
					textField_1.setText("");
				}
				
			}
		});
		setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
	}

	public JFrame getFrame() {
		return frame;
	}
}
