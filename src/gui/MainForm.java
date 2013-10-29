package gui;

import java.awt.BorderLayout;
import java.awt.Dialog.ModalityType;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.TextArea;
import java.awt.TextField;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TreeModelListener;
import javax.swing.table.TableColumn;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.JTable;
import javax.swing.JButton;

import core.DBManager;
import core.DataBase;
import core.DataType;
import core.DataType.TYPE;
import core.Table;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainForm extends JFrame {

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
	public MainForm() {
		dbManager = new DBManager();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(50, 50, 950, 700);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		tree = new JTree(MainFormMng.createTreeNodes(dbManager));
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
				dbManager.save();
				DefaultTreeModel model = (DefaultTreeModel)tree.getModel();
				model.setRoot(MainFormMng.createTreeNodes(dbManager));
				}
		});
		
		createDBBtn = new JButton("create DB");
		panel_2.add(createDBBtn);
		createDBBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				tree = MainFormMng.createDB(dbManager, tree);
				tree.repaint();
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
				tree = MainFormMng.removeTable(tree, dbManager);
				tree.repaint();
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
		final JDialog createTableDialog = new JDialog();
		final JTextField tName = new JTextField();
		final JTextField cName = new JTextField();
		final List<String> columnsNames = new ArrayList<String>();
		final List<DataType> columnTypes = new ArrayList<DataType>();
		final JComboBox types = new JComboBox(DataType.TYPE.values());
		final JTable newTable = new JTable();
		JLabel tableNameL = new JLabel("Table Name:    ");
		JLabel columnNameL = new JLabel("Column Name: ");
		JLabel lblNewLabel = new JLabel("Choose type:   ");
		JPanel tablePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
		JPanel columnPanel = new JPanel();
		JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 50, 50));
		JPanel okPanel = new JPanel();
		JPanel cancelPanel = new JPanel();
		JPanel typePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
		JButton addBtn = new JButton("Add");
		JButton okBtn = new JButton("   Ok   ");
		JButton cancelBtn = new JButton("Cancel");
		
		cName.setColumns(19);
		tName.setColumns(25);
		tablePanel.add(tableNameL);
		tablePanel.add(tName);
		columnPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		columnPanel.add(columnNameL);
		columnPanel.add(cName);
		columnPanel.add(addBtn);
		
		createTableDialog.setSize(400, 200);
		createTableDialog.getContentPane().setLayout(new GridLayout(4, 1, 10, 10));
		createTableDialog.getContentPane().add(tablePanel);
		createTableDialog.getContentPane().add(columnPanel);
		createTableDialog.getContentPane().add(typePanel);
		createTableDialog.getContentPane().add(buttonPanel);
	
		typePanel.add(lblNewLabel);
		typePanel.add(types);
		
		buttonPanel.add(okPanel);
		okBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(tName.getText() != null) {
					newTable.setName(tName.getText());
				}
				tree = MainFormMng.createTable(dbManager, tree, newTable.getName(), columnsNames, columnTypes);
				tree.repaint();
				createTableDialog.setVisible(false);
			}
		});		
		
		cancelBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				createTableDialog.setVisible(false);
			}
		});
		
		addBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String columnName = cName.getText(); 
				if(columnName != null) {
					TableColumn column = new TableColumn();
					column.setHeaderValue(columnName);
					newTable.addColumn(column);
					columnsNames.add(columnName);
					columnTypes.add(new DataType((TYPE)types.getSelectedItem()));
					cName.setText("");
				}
			}
		});
		
		okPanel.add(okBtn);
		buttonPanel.add(cancelPanel);
		cancelPanel.add(cancelBtn);
		
		createTableDialog.setVisible(true);
		createTableDialog.setModalityType(ModalityType.APPLICATION_MODAL);
		
		return createTableDialog;
	}
	
	private JTable createTable() {
		return new JTable();
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainForm dbBaseMainForm = new MainForm();
					dbBaseMainForm.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
