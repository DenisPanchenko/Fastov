package gui;

import java.awt.GridLayout;
import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.FlowLayout;

public class CreateTableDialog {
	
	public static void main(String[] args) {
		JDialog dialog = getCreationTableDialog();
		dialog.setVisible(true);
	}
	
	private static JDialog getCreationTableDialog() {
		final JDialog createTableDialog = new JDialog();
		JLabel tableNameL = new JLabel("Table Name:    ");
		JLabel columnNameL = new JLabel("Column Name: ");
		JTextField tName = new JTextField();
		tName.setColumns(25);
		JTextField cName = new JTextField();
		cName.setColumns(19);
		JButton addBtn = new JButton("Add");
		JPanel tablePanel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) tablePanel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		JPanel columnPanel = new JPanel();
		JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 50, 50));
		
		tablePanel.add(tableNameL);
		tablePanel.add(tName);
		columnPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		columnPanel.add(columnNameL);
		columnPanel.add(cName);
		columnPanel.add(addBtn);
		
		createTableDialog.setSize(400, 150);
		createTableDialog.getContentPane().setLayout(new GridLayout(3, 1, 10, 10));
		createTableDialog.getContentPane().add(tablePanel);
		createTableDialog.getContentPane().add(columnPanel);
		createTableDialog.getContentPane().add(buttonPanel);
		
		JPanel panel = new JPanel();
		buttonPanel.add(panel);
		JButton okBtn = new JButton("   Ok   ");
		okBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		panel.add(okBtn);
		
		JPanel panel_1 = new JPanel();
		buttonPanel.add(panel_1);
		JButton cancelBtn = new JButton("Cancel");
		cancelBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				createTableDialog.setVisible(false);
			}
		});
		panel_1.add(cancelBtn);
		
		createTableDialog.setVisible(true);
		createTableDialog.setModalityType(ModalityType.APPLICATION_MODAL);
		
		return createTableDialog;
	}
}
