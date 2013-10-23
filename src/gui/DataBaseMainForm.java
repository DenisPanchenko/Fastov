package gui;

import java.awt.BorderLayout;
import java.awt.Dialog.ModalityType;
import java.awt.EventQueue;
import java.awt.TextArea;
import java.awt.TextField;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.JTable;
import javax.swing.JButton;

import core.DBManager;
import core.DataBase;
import core.Table;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class DataBaseMainForm extends JFrame implements ActionListener{

	private JPanel contentPane;
	private JTable table;
	private JPanel panel;
	private static JButton saveBtn;
	private static JButton createDBBtn;
	private static JButton createTableBtn;
	private static JButton removeTableBtn;
	private static JButton projectionBtn;
	private static JButton unitTableBtn;
	private static DBManager dbManager;
	private JPanel panel_1;
	private JPanel panel_2;
	private JTree tree;

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
		
		tree = new JTree(createTreeNodes());
		contentPane.add(tree, BorderLayout.WEST);
		
		panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.NORTH);
		
		table = createTable();
		panel_1.add(table);
		
		panel = new JPanel();
		contentPane.add(panel, BorderLayout.EAST);
		panel.setLayout(new GridLayout(2, 1, 0, 0));
		
		panel_2 = new JPanel();
		panel.add(panel_2);
		panel_2.setLayout(new GridLayout(0, 1, 10, 10));
		
		saveBtn = new JButton("Save");
		panel_2.add(saveBtn);
		saveBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//dbManager.save();
				
			}
		});
		
		createDBBtn = new JButton("create DB");
		panel_2.add(createDBBtn);
		createDBBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				List<String> dbNames = new ArrayList<String>();
				List<DataBase> dataBases = new ArrayList<DataBase>()/* dbManager.getDataBaseList()*/;
				
				//assert.notNull(dataBases)
				for(DataBase db: dataBases) {
					dbNames.add(db.getName());
				}
				String dbName = JOptionPane.showInputDialog("Enter data base name");
				
				while(dbNames.contains(dbName) && dbName != null){
					dbName = JOptionPane.showInputDialog("DataBase is already exists. Enter another name");
				}
				if(dbName != null) {
					DataBase db = dbManager.createNewDB(dbName);
					addNewNodeToTree(db);
				}
			}
		});
		
		createTableBtn = new JButton("create new table");
		panel_2.add(createTableBtn);
		createTableBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				JTable table = new JTable();
				JDialog dialog = getCreationTableDialog();
				
				//Table newTable = new Table(filePath);
				
			}
		});
		
		removeTableBtn = new JButton("remove table");
		panel_2.add(removeTableBtn);
		removeTableBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				DefaultMutableTreeNode node = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
				Object userObject = node.getUserObject();
				if(userObject instanceof Table) {
					Table table = (Table)userObject;
					DataBase dataBase = (DataBase)(node.getParent());
					dataBase.deleteTable(dataBase.getTableList().indexOf(table));
				}
			}
		});
		
		projectionBtn = new JButton("tables projection");
		panel_2.add(projectionBtn);
		
		unitTableBtn = new JButton("unit tables");
		panel_2.add(unitTableBtn);
		
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
	
	public static void disableButtons() {
		createDBBtn.setEnabled(false);
		saveBtn.setEnabled(false);
		removeTableBtn.setEnabled(false);
		createTableBtn.setEnabled(false);
	}
	
	private JDialog getCreationTableDialog() {
		JDialog createTableDialog = new JDialog();
		JLabel tableNameL = new JLabel("Table Name: ");
		JLabel columnNameL = new JLabel("Column Name: ");
		JTextField tName = new JTextField();
		JTextField cName = new JTextField();
		JButton addBtn = new JButton("Add");
		JButton okBtn = new JButton("Ok");
		JButton cancelBtn = new JButton("Cancel");
		JPanel tablePanel = new JPanel(new GridLayout(1, 2, 10, 10));
		JPanel columnPanel = new JPanel(new GridLayout(1, 3, 10, 10));
		JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 10));
		
		tablePanel.add(tableNameL);
		tablePanel.add(tName);
		columnPanel.add(columnNameL);
		columnNameL.add(cName);
		columnPanel.add(addBtn);
		buttonPanel.add(okBtn);
		buttonPanel.add(cancelBtn);
		
		createTableDialog.setSize(300, 200);
		createTableDialog.setLayout(new GridLayout(3, 1, 10, 10));
		createTableDialog.add(tablePanel);
		createTableDialog.add(columnPanel);
		createTableDialog.add(buttonPanel);
		
		createTableDialog.setVisible(true);
		createTableDialog.setModalityType(ModalityType.APPLICATION_MODAL);
		
		return createTableDialog;
	}
	
	private void addNewNodeToTree(Object o) {
		DefaultTreeModel model = (DefaultTreeModel)tree.getModel();
		DefaultMutableTreeNode root = (DefaultMutableTreeNode)model.getRoot();
		root.add(new DefaultMutableTreeNode(o));
	}
	
	private DefaultMutableTreeNode createTreeNodes() {
		
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Available data bases");
		List<DataBase> dataBases = dbManager.getDataBaseList();
		System.out.println(dataBases);
		DataBase curDB;
		DefaultMutableTreeNode dataBaseNode;
		DefaultMutableTreeNode tableNode;
		
		if(dataBases != null) {
			for(int i = 0; i < dataBases.size(); i++) {
				curDB = dataBases.get(i);
				dataBaseNode = new DefaultMutableTreeNode(curDB);
				if(curDB.getTableList() != null) {
					for(int j = 0; j < curDB.getTableList().size(); j++) {
						tableNode = new DefaultMutableTreeNode(curDB.getTableList().get(j));
						dataBaseNode.add(tableNode);
					}
				}
				root.add(dataBaseNode);
			}
		}
		return root;
	}
	
	private JTable createTable() {
		return new JTable();
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

	@Override
	public void actionPerformed(ActionEvent event) {
		// TODO Auto-generated method stub
		if(event.getActionCommand().equals("create db"))
		{
			String dbName = null;
			// TODO
			/*
			 * Create input dialog as separate class and show it here to 
			 * get name of database. Then pass this name as a string to
			 * DBManager.createNewDB(name);
			 * 
			 * 
			 * !!! Do not forget to check whether name is already exists !!!
			 * 
			 *  import javax.swing.*;
import java.awt.event.*;

public class ShowInputDialog{
  public static void main(String[] args){
  JFrame frame = new JFrame("Input Dialog Box Frame");
  JButton button = new JButton("Show Input Dialog Box");
  button.addActionListener(new ActionListener(){
  public void actionPerformed(ActionEvent ae){
  String str = JOptionPane.showInputDialog(null, "Enter some text : ", 
"Roseindia.net", 1);
  if(str != null)
  JOptionPane.showMessageDialog(null, "You entered the text : " + str, 
"Roseindia.net", 1);
  else
  JOptionPane.showMessageDialog(null, "You pressed cancel button.", 
"Roseindia.net", 1);
  }
  });
  JPanel panel = new JPanel();
  panel.add(button);
  frame.add(panel);
  frame.setSize(400, 400);
  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  frame.setVisible(true);
  }
}
			 */
			dbManager.createNewDB(dbName);
		}
		
	}
}
