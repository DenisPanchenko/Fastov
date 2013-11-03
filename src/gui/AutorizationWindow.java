package gui;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import core.DBManager;

public class AutorizationWindow extends InputDialog implements ActionListener {

	private JFrame frame;
	private JTextField textField;
	private JPasswordField password;
	private DBManager dbManager;

	/**
	 * Create the application.
	 */
	public AutorizationWindow(DBManager dbManager) {
		super();
		this.dbManager = dbManager;
		initComponents();
		super.initialize(textField, password, "Login", "Login:", "Password:", "Login");
		getAcceptButton().addActionListener(this);
		getAcceptButton().setActionCommand("LOGIN");
		getBtnCancel().addActionListener(this);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initComponents() {
		
		textField = new JTextField();
		textField.setColumns(10);
		
		password = new JPasswordField();
		password.setColumns(10);
	}

	public JFrame getFrame() {
		return frame;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().endsWith("LOGIN"))
		{
			if(dbManager.authenticate(textField.getText(), password.getText()).equals(DBManager.AUTH_TYPE.ADMIN)) {
				setVisible(false);
			} else if(dbManager.authenticate(textField.getText(), password.getText()).equals(DBManager.AUTH_TYPE.USER)) {
				setVisible(false);
				MainForm.disableButtons();
			} else {
				JOptionPane.showMessageDialog(this, "Invalid login or password");
				textField.setText("");
				password.setText("");
			}
		} else if(e.getActionCommand().equals("EXIT")) {
			dispose();
		}
	}
}
