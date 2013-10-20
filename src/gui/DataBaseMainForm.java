package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.JTree;
import javax.swing.JTable;
import javax.swing.JButton;

import core.DBManager;

import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class DataBaseMainForm extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JPanel panel;
	private JButton btnNewButton;
	private JButton btnNewButton_1;
	private JButton btnNewButton_2;
	private JButton btnNewButton_3;
	private static DBManager dbManager;

	/**
	 * Create the frame.
	 */
	public DataBaseMainForm() {
		dbManager = new DBManager();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JTree tree = new JTree();
		contentPane.add(tree, BorderLayout.WEST);
		
		table = new JTable();
		contentPane.add(table, BorderLayout.CENTER);
		
		panel = new JPanel();
		contentPane.add(panel, BorderLayout.EAST);
		panel.setLayout(new GridLayout(4, 1, 0, 0));
		
		btnNewButton = new JButton("Save");
		panel.add(btnNewButton);
		
		btnNewButton_1 = new JButton("create DB");
		panel.add(btnNewButton_1);
		
		btnNewButton_2 = new JButton("create new table");
		panel.add(btnNewButton_2);
		
		btnNewButton_3 = new JButton("remove table");
		panel.add(btnNewButton_3);
		
		AutorizationWindow dialog = new AutorizationWindow(dbManager);
		
		dialog.setVisible(true);
		dialog.addWindowListener(new WindowAdapter(){
			@Override
			public void windowClosed(WindowEvent e){
				System.exit(NORMAL);
			}
		}
		);
		//dialog.setModal(true);
	}
	
	private DefaultMutableTreeNode createTreeNode() {
		
		
		return null;
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DataBaseMainForm dbBaseMainForm = new DataBaseMainForm();
					dbBaseMainForm.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
