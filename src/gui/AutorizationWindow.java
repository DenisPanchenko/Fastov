package gui;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import java.awt.Dialog;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import core.DBManager;

import javax.swing.JPanel;


import java.awt.FlowLayout;

public class AutorizationWindow extends JDialog {

	private JFrame frame;
	private JTextField textField;
	private JPasswordField password;
	private JButton btnSignIn;
	private DBManager dbManager;
	private JPanel panel;

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
		frame = new JFrame();
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setTitle("Login");
		
		textField = new JTextField();
		textField.setColumns(10);
		
		password = new JPasswordField();
		password.setColumns(10);
		getContentPane().setLayout(new GridLayout(0, 1, 20, 0));

		JLabel lblLogin = new JLabel("Login:");
		lblLogin.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		getContentPane().add(lblLogin);
		getContentPane().add(textField);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		getContentPane().add(lblPassword);
		getContentPane().add(password);
		
		panel = new JPanel();
		getContentPane().add(panel);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 15));
		
		btnSignIn = new JButton("Login");
		panel.add(btnSignIn);
		btnSignIn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(dbManager.authenticate(textField.getText(), password.getText()).equals(DBManager.AUTH_TYPE.ADMIN)) {
					//dispose();
					setVisible(false);
				} else if(dbManager.authenticate(textField.getText(), password.getText()).equals(DBManager.AUTH_TYPE.USER)) {
					setVisible(false);
				} else {
					JOptionPane.showMessageDialog(frame, "Invalid login or password");
					textField.setText("");
					password.setText("");
				}
				
			}
		});
		setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
	}

	public JFrame getFrame() {
		return frame;
	}
}
