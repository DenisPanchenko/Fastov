package gui;

import java.awt.Dimension;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.KeyStroke;

public class InputDialog extends JDialog{

	private JButton button;
	private JButton btnCancel;
	
	protected void initialize(JComponent firstInput, JComponent secondInput, String title, String firstInputLbl, 
			String secondInputLbl, String buttonName) {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle(title);
		JLabel lblLogin = new JLabel(firstInputLbl);		
		JLabel lblPassword = new JLabel(secondInputLbl);
		
		button = new JButton(buttonName);
		getRootPane().setDefaultButton(button);
		button.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
			.put(KeyStroke.getKeyStroke("ENTER"), "pressed");
		button.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
		.put(KeyStroke.getKeyStroke("released ENTER"), "released");
		
	    btnCancel = new JButton("Cancel");
		btnCancel.setActionCommand("EXIT");
		
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
										.addComponent(firstInput)
										.addComponent(secondInput)
								)
					)
					.addGroup(mainLayout.createSequentialGroup()
							.addComponent(button)
							.addComponent(btnCancel)
					)
				);
		
		mainLayout.setVerticalGroup(
				mainLayout.createSequentialGroup()
					.addGroup(mainLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
							.addComponent(lblLogin)
							.addComponent(firstInput)
					)
					.addGroup(mainLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
							.addComponent(lblPassword)
							.addComponent(secondInput)
					)
					.addGroup(mainLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
							.addComponent(button)
							.addComponent(btnCancel)
					)
				);
		
		setModalityType(ModalityType.APPLICATION_MODAL);
		setMinimumSize(new Dimension(getPreferredSize().width + 125, getPreferredSize().height));
		pack();
		setResizable(false);
		setLocationRelativeTo(null);
	}

	public JButton getAcceptButton() {
		return button;
	}

	public JButton getBtnCancel() {
		return btnCancel;
	}	
}
