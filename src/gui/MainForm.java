package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.ScrollPane;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.GroupLayout;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.JTable;
import javax.swing.JButton;

import core.DBManager;
import core.DataBase;
import core.Table;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainForm extends JFrame implements MouseListener{

	private JPanel contentPane;
	private JTable table;
	private JPanel panel;
	private static JButton saveBtn;
	private static JButton cancelBtn;
	private static JButton createDBBtn;
	private static JButton createTableBtn;
	private static JButton removeTableBtn;
	private static JButton projectionBtn;
	private static JButton unitTableBtn;
	private static JButton removeDB;
	private static JButton addColumnBtn;
	private static JButton deleteColumnBtn;
	private static JButton addRowBtn;
	private static JButton deleteRowBtn;
	private static DBManager dbManager;
	private JPanel panel_1;
	private JPanel panel_2;
	private JTree tree;

	private enum BUTTON_SET_LEVEL {MANAGER_LEVEL, DATABASE_LEVEL, TABLE_LEVEL};
	
	/**
	 * Create the frame.
	 */
	public MainForm() {
		dbManager = new DBManager();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//setBounds(50, 50, 950, 700);
		//contentPane = new JPanel();
		//contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		//contentPane.setLayout(new BorderLayout(0, 0));
		//setContentPane(contentPane);
		
		Integer height = new Integer(600);
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1, 1, 0, 0));
		tree = new JTree(MainFormMng.createTreeNodes(dbManager));
		tree.setMinimumSize(new Dimension(75,height));
		panel.add(tree);
		tree.addMouseListener(this);
		
		panel_1 = new JPanel();
		//contentPane.add(panel_1, BorderLayout.CENTER);
		
		table = new JTable();
		table.setMinimumSize(new Dimension(600, height));
		table.setFillsViewportHeight(true);
		panel_1.add(new JScrollPane(table));
		panel_1.setLayout(new GridLayout(1, 1, 0, 0));
		
		//contentPane.add(panel, BorderLayout.EAST);
		
		
		panel_2 = new JPanel();
		//panel.add(panel_2);
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
		
		cancelBtn = new JButton("Cancel");
		panel_2.add(cancelBtn);
		cancelBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dbManager.cancelAllActions();
			}
		});
		
		createDBBtn = new JButton("Create DB");
		panel_2.add(createDBBtn);
		createDBBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				tree = MainFormMng.createDB(dbManager, tree);
			}
		});
		
		removeDB = new JButton("Remove DB");
		panel_2.add(removeDB);
		removeDB.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				tree = MainFormMng.removeDB(tree, dbManager);
			}
		});
		
		createTableBtn = new JButton("Create Table");
		panel_2.add(createTableBtn);
		createTableBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				tree = MainFormMng.createTable(dbManager, tree);
			}
		});
		
		removeTableBtn = new JButton("Remove Table");
		panel_2.add(removeTableBtn);
		removeTableBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				tree = MainFormMng.removeTable(tree, dbManager);
			}
		});
		
		addColumnBtn = new JButton("Add column");
		panel_2.add(addColumnBtn);
		addColumnBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				table.setModel(MainFormMng.addColumnToTable(dbManager, tree).getModel());
				((AbstractTableModel)table.getModel()).fireTableDataChanged();
			}
		});
		
		deleteColumnBtn = new JButton("Remove column");
		panel_2.add(deleteColumnBtn);
		deleteColumnBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				 
				
			}
		});
		
		addRowBtn = new JButton("Add row");
		panel_2.add(addRowBtn);
		addRowBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				table.setModel(MainFormMng.addRowToTable(dbManager, table, tree).getModel());
				((AbstractTableModel)table.getModel()).fireTableDataChanged();
			}
		});
		
		deleteRowBtn = new JButton("Remove row");
		panel_2.add(deleteRowBtn);
		deleteRowBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				table.setModel(MainFormMng.deleteRowFromTable(dbManager, table, tree).getModel());
				((AbstractTableModel)table.getModel()).fireTableDataChanged();
			}
		});
		
		projectionBtn = new JButton("Tables Projection");
		panel_2.add(projectionBtn);
		
		unitTableBtn = new JButton("Tables Join");
		panel_2.add(unitTableBtn);
		unitTableBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				table.setModel(MainFormMng.unitTable(dbManager, tree).getModel());
				((AbstractTableModel)table.getModel()).fireTableDataChanged();
			}
		});
		
		AutorizationWindow dialog = new AutorizationWindow(dbManager);
		
		dialog.setVisible(true);
		dialog.addWindowListener(new WindowAdapter(){
			@Override
			public void windowClosed(WindowEvent e){
				System.exit(NORMAL);
			}
		}
		);

		GroupLayout mainLayout = new GroupLayout(getContentPane());
		getContentPane().setLayout(mainLayout);
		mainLayout.setAutoCreateGaps(true);
		mainLayout.setAutoCreateContainerGaps(true);
		
		mainLayout.setHorizontalGroup(
				mainLayout.createSequentialGroup()
					.addComponent(panel)
					.addComponent(panel_1)
					.addComponent(panel_2)
				);
		
		mainLayout.setVerticalGroup(
				mainLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addComponent(panel)
					.addComponent(panel_1)
					.addComponent(panel_2)
				);
		
		setButtonsSetLevel(BUTTON_SET_LEVEL.MANAGER_LEVEL);
		pack();
		setLocationRelativeTo(null);
	}
	
	public static void disableButtons() {
		createDBBtn.setEnabled(false);
		saveBtn.setEnabled(false);
		removeTableBtn.setEnabled(false);
		createTableBtn.setEnabled(false);
		removeDB.setEnabled(false);
		cancelBtn.setEnabled(false);
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

	@Override
	public void mouseClicked(MouseEvent event) {
		// TODO Auto-generated method stub
		TreePath path = tree.getPathForLocation(event.getX(), event.getY());
		if(path != null)
		{
			if(path.getPathCount() == 3)
			{
				setButtonsSetLevel(BUTTON_SET_LEVEL.TABLE_LEVEL);
				String tableName = path.getLastPathComponent().toString();
				String dbName = path.getPathComponent(1).toString();
				DataBase db = null;
				for(int i = 0; i < dbManager.getDataBaseList().size(); i++)
					if(dbManager.getDataBaseList().get(i).getName().equals(dbName))
					{
						db = dbManager.getDataBaseList().get(i);
						break;
					}
				if(db == null)
					return;
				Table t = null;
				for(int j = 0; j < db.getTableList().size(); j++)
					if(db.getTableList().get(j).getTableName().equals(tableName))
					{
						t = db.getTableList().get(j);
						break;
					}
				if(t == null)
					return;
				table.setModel(TableConverter.convertTableToJTable(t).getModel());
				table.repaint();
			} else if(path.getPathCount() == 2) {
				setButtonsSetLevel(BUTTON_SET_LEVEL.DATABASE_LEVEL);
			} else {
				setButtonsSetLevel(BUTTON_SET_LEVEL.MANAGER_LEVEL);
			}
		}
	}

	public void setButtonsSetLevel(BUTTON_SET_LEVEL setLevel)
	{
		if(saveBtn.isEnabled())
			if(setLevel == BUTTON_SET_LEVEL.MANAGER_LEVEL)
			{
				createDBBtn.setEnabled(true);
				removeDB.setEnabled(false);
				createTableBtn.setEnabled(false);
				removeTableBtn.setEnabled(false);
				addColumnBtn.setEnabled(false);
				deleteColumnBtn.setEnabled(false);
				addRowBtn.setEnabled(false);
				deleteRowBtn.setEnabled(false);
				unitTableBtn.setEnabled(false);
				projectionBtn.setEnabled(false);
			} else if(setLevel == BUTTON_SET_LEVEL.DATABASE_LEVEL) {
				createDBBtn.setEnabled(true);
				removeDB.setEnabled(true);
				createTableBtn.setEnabled(true);
				removeTableBtn.setEnabled(false);
				addColumnBtn.setEnabled(false);
				deleteColumnBtn.setEnabled(false);
				addRowBtn.setEnabled(false);
				deleteRowBtn.setEnabled(false);
				unitTableBtn.setEnabled(false);
				projectionBtn.setEnabled(false);
			} else if(setLevel == BUTTON_SET_LEVEL.TABLE_LEVEL) {
				createDBBtn.setEnabled(true);
				removeDB.setEnabled(false);
				createTableBtn.setEnabled(true);
				removeTableBtn.setEnabled(true);
				addColumnBtn.setEnabled(true);
				deleteColumnBtn.setEnabled(true);
				addRowBtn.setEnabled(true);
				deleteRowBtn.setEnabled(true);
				unitTableBtn.setEnabled(true);
				projectionBtn.setEnabled(true);
			}
		getContentPane().repaint();
	}
	
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
