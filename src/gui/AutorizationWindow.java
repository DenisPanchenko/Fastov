package gui;

import javax.swing.GroupLayout;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import core.DBManager;

import javax.swing.JPanel;

import java.awt.FlowLayout;

public class AutorizationWindow extends JDialog implements ActionListener {

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
		//frame = new JFrame();
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		//setBounds(100, 100, 450, 300);
		setTitle("Login");
		
		textField = new JTextField();
		textField.setColumns(10);
		
		password = new JPasswordField();
		password.setColumns(10);
		//getContentPane().setLayout(new GridLayout(0, 1, 20, 0));

		JLabel lblLogin = new JLabel("Login:");
		//lblLogin.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		//getContentPane().add(lblLogin);
		//getContentPane().add(textField);
		
		JLabel lblPassword = new JLabel("Password:");
		//lblPassword.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		//getContentPane().add(lblPassword);
		//getContentPane().add(password);
		
		panel = new JPanel();
		//getContentPane().add(panel);
		//panel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 15));
		
		btnSignIn = new JButton("Login");
		btnSignIn.setActionCommand("LOGIN");
		//panel.add(btnSignIn);
		btnSignIn.addActionListener(this);
		getRootPane().setDefaultButton(btnSignIn);
		btnSignIn.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
			.put(KeyStroke.getKeyStroke("ENTER"), "pressed");
		btnSignIn.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
		.put(KeyStroke.getKeyStroke("released ENTER"), "released");
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setActionCommand("EXIT");
		btnCancel.addActionListener(this);
		
		GroupLayout mainLayout = new GroupLayout(getContentPane());
		getContentPane().setLayout(mainLayout);
		mainLayout.setAutoCreateGaps(true);
		mainLayout.setAutoCreateContainerGaps(true);
		
		mainLayout.setHorizontalGroup(
				mainLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
					.addGroup(
							mainLayout.createSequentialGroup()
								.addGroup(mainLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(lblLogin)
										.addComponent(lblPassword)
								)
								.addGroup(mainLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(textField)
										.addComponent(password)
								)
					)
					.addGroup(mainLayout.createSequentialGroup()
							.addComponent(btnSignIn)
							.addComponent(btnCancel)
					)
				);
		
		mainLayout.setVerticalGroup(
				mainLayout.createSequentialGroup()
					.addGroup(mainLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
							.addComponent(lblLogin)
							.addComponent(textField)
					)
					.addGroup(mainLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
							.addComponent(lblPassword)
							.addComponent(password)
					)
					.addGroup(mainLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
							.addComponent(btnSignIn)
							.addComponent(btnCancel)
					)
				);
		
		setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		setMinimumSize(new Dimension(getPreferredSize().width + 125, getPreferredSize().height));
		pack();
		setResizable(false);
		setLocationRelativeTo(null);
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
