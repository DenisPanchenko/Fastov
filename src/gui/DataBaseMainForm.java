package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.JTree;
import javax.swing.JTable;
import javax.swing.JButton;

import core.DBManager;
import core.DataBase;

import java.awt.GridLayout;
import java.util.List;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;

public class DataBaseMainForm extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JPanel panel;
	private JButton btnNewButton;
	private JButton btnNewButton_1;
	private JButton btnNewButton_2;
	private JButton btnNewButton_3;
	private static DBManager dbManager;
	private JPanel panel_1;
	private JPanel panel_2;

	/**
	 * Create the frame.
	 */
	public DataBaseMainForm() {
		dbManager = new DBManager();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(50, 50, 950, 700);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JTree tree = new JTree(createTreeNode());
		contentPane.add(tree, BorderLayout.WEST);
		
		panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.NORTH);
		
		table = new JTable();
		panel_1.add(table);
		
		panel = new JPanel();
		contentPane.add(panel, BorderLayout.EAST);
		panel.setLayout(new GridLayout(3, 1, 0, 0));
		
		panel_2 = new JPanel();
		panel.add(panel_2);
		panel_2.setLayout(new GridLayout(0, 1, 10, 10));
		
		btnNewButton = new JButton("Save");
		panel_2.add(btnNewButton);
		
		btnNewButton_1 = new JButton("create DB");
		panel_2.add(btnNewButton_1);
		
		btnNewButton_2 = new JButton("create new table");
		panel_2.add(btnNewButton_2);
		
		btnNewButton_3 = new JButton("remove table");
		panel_2.add(btnNewButton_3);
		
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
		
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Available data bases");
		List<DataBase> dataBases = dbManager.getDataBaseList();
		DataBase curDB;
		DefaultMutableTreeNode dataBaseNode;
		DefaultMutableTreeNode tableNode;
		
		if(dataBases != null) {
			for(int i = 0; i < dataBases.size(); i++) {
				curDB = dataBases.get(i);
				dataBaseNode = new DefaultMutableTreeNode(curDB.getName());
				for(int j = 0; j < curDB.getTableList().size(); j++) {
					tableNode = new DefaultMutableTreeNode(curDB.getTableList().get(j).getTableName());
					dataBaseNode.add(tableNode);
				}
				root.add(dataBaseNode);
			}
		}
		return root;
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
