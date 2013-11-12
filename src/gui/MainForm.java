package gui;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
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

public class MainForm extends JFrame implements ActionListener, MouseListener, TableModelListener {

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
	
	private String selectedTable;
	private String selectedDB;

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
		table.setMinimumSize(new Dimension(700, height));
		table.setFillsViewportHeight(true);
		table.getModel().addTableModelListener(this);
		panel_1.add(new JScrollPane(table));
		panel_1.setLayout(new GridLayout(1, 1, 0, 0));
		
		//contentPane.add(panel, BorderLayout.EAST);
		
		panel_2 = new JPanel();
		//panel.add(panel_2);
		panel_2.setLayout(new GridLayout(0, 1, 10, 10));
		
		saveBtn = new JButton("Save");
		panel_2.add(saveBtn);
		saveBtn.setActionCommand("SAVE");
		saveBtn.addActionListener(this);
		
		cancelBtn = new JButton("Cancel");
		panel_2.add(cancelBtn);
		cancelBtn.setActionCommand("CANCEL");
		cancelBtn.addActionListener(this);
				
		
		createDBBtn = new JButton("Create DB");
		panel_2.add(createDBBtn);
		createDBBtn.setActionCommand("CREATE_DB");
		createDBBtn.addActionListener(this);
				
		
		removeDB = new JButton("Remove DB");
		panel_2.add(removeDB);
		removeDB.setActionCommand("DELETE_DB");
		removeDB.addActionListener(this);
				
		
		createTableBtn = new JButton("Create Table");
		panel_2.add(createTableBtn);
		createTableBtn.setActionCommand("CREATE_TABLE");
		createTableBtn.addActionListener(this);
				
		
		removeTableBtn = new JButton("Remove Table");
		panel_2.add(removeTableBtn);
		removeTableBtn.setActionCommand("DELETE_TABLE");
		removeTableBtn.addActionListener(this);
				
		
		addColumnBtn = new JButton("Add column");
		panel_2.add(addColumnBtn);
		addColumnBtn.setActionCommand("CREATE_COLUMN");
		addColumnBtn.addActionListener(this);
				
		
		deleteColumnBtn = new JButton("Remove column");
		panel_2.add(deleteColumnBtn);
		deleteColumnBtn.setActionCommand("DELETE_COLUMN");
		deleteColumnBtn.addActionListener(this);
		
		addRowBtn = new JButton("Add row");
		panel_2.add(addRowBtn);
		addRowBtn.setActionCommand("ADD_ROW");
		addRowBtn.addActionListener(this);
				
		
		deleteRowBtn = new JButton("Remove row");
		panel_2.add(deleteRowBtn);
		deleteRowBtn.setActionCommand("DELETE_ROW");
		deleteRowBtn.addActionListener(this); 
				
			
		projectionBtn = new JButton("Tables Projection");
		panel_2.add(projectionBtn);
		projectionBtn.setActionCommand("PROJECTION");
		projectionBtn.addActionListener(this);
		
		unitTableBtn = new JButton("Tables Join");
		panel_2.add(unitTableBtn);
		unitTableBtn.setActionCommand("JOIN");
		unitTableBtn.addActionListener(this);
				
		
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
		TreePath path = tree.getPathForLocation(event.getX(), event.getY());
		if(path != null)
		{
			if(path.getPathCount() == 3) // level of table in a tree
			{
				selectedTable = path.getLastPathComponent().toString();
				selectedDB = path.getPathComponent(1).toString();
				
				setButtonsSetLevel(BUTTON_SET_LEVEL.TABLE_LEVEL);
				
				DataBase db = null;
				for(int i = 0; i < dbManager.getDataBaseList().size(); i++)
					if(dbManager.getDataBaseList().get(i).getName().equals(selectedDB))
					{
						db = dbManager.getDataBaseList().get(i);
						break;
					}
				if(db == null)
					return;
				Table t = null;
				for(int j = 0; j < db.getTableList().size(); j++)
					if(db.getTableList().get(j).getTableName().equals(selectedTable))
					{
						t = db.getTableList().get(j);
						break;
					}
				if(t == null)
					return;
				table.setModel(TableConverter.convertTableToJTable(t).getModel());
				table.getModel().addTableModelListener(this);
				table.repaint();
			} else if(path.getPathCount() == 2) { // level of database in a tree
				selectedTable = null;
				selectedDB = path.getLastPathComponent().toString();
				setButtonsSetLevel(BUTTON_SET_LEVEL.DATABASE_LEVEL);
			} else { // level of manager in a tree
				selectedTable = null;
				selectedDB = null;
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
	public void mouseEntered(MouseEvent arg0) 
	{
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) 
	{
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) 
	{
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) 
	{
		
	}

	@Override
	public void tableChanged(TableModelEvent e) {
		
		//	TODO Something wrong here, during table modifications magic things happen!
		
		// 	TODO Add input validation here!
		//	enum must have next format: <token1>,<token2>, ..., <token>
		
		int row = e.getFirstRow();
        int col = e.getColumn();
        String columnName = table.getModel().getColumnName(col);
        String data = (String)table.getModel().getValueAt(row, col);
        
        MainFormMng.setNewCellToTable(tree, table, row, col, data);
        
        //	Seems to work fine without next lines
        
        /*
        table.setModel(MainFormMng.setNewCellToTable(tree, table, row, col, data).getModel());
		((AbstractTableModel)table.getModel()).fireTableDataChanged();
		*/
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if(event.getActionCommand().equals("SAVE"))
		{
			dbManager.save();
			DefaultTreeModel model = (DefaultTreeModel)tree.getModel();
			model.setRoot(MainFormMng.createTreeNodes(dbManager));
		} else if(event.getActionCommand().equals("CANCEL")) {
			dbManager.cancelAllActions();
		} else if(event.getActionCommand().equals("CREATE_DB")) {
			tree = MainFormMng.createDB(dbManager, tree);
		} else if(event.getActionCommand().equals("DELETE_DB")) {
			tree = MainFormMng.removeDB(tree, dbManager);
		} else if(event.getActionCommand().equals("CREATE_TABLE")) {
			tree = MainFormMng.createTable(dbManager, tree);
		} else if(event.getActionCommand().equals("DELETE_TABLE")) {
			tree = MainFormMng.removeTable(tree, dbManager);
		} else if(event.getActionCommand().equals("CREATE_COLUMN")) {
			//
			table.setModel(MainFormMng.addColumnToTable(dbManager, tree).getModel());
			((AbstractTableModel)table.getModel()).fireTableDataChanged();
		} else if(event.getActionCommand().equals("DELETE_COLUMN")) {
			
			//	TODO Bind this method to 
			//	DBManager.deleteColumn(String database, String table, String column);
			
		} else if(event.getActionCommand().equals("ADD_ROW")) {
			table.setModel(MainFormMng.addRowToTable(dbManager, table, tree).getModel());
			((AbstractTableModel)table.getModel()).fireTableDataChanged();
		} else if(event.getActionCommand().equals("DELETE_ROW")) {
			table.setModel(MainFormMng.deleteRowFromTable(dbManager, table, tree).getModel());
			((AbstractTableModel)table.getModel()).fireTableDataChanged();
		} else if(event.getActionCommand().equals("JOIN")) {
			
			//  TODO Bind this method to
			//	DBManager.tableJoin()
			String dbName = selectedDB;
			String targetTableName = selectedTable;
			
			MainFormMng.createJoinDialog(dbManager, dbName, targetTableName);
//			dbManager.tableJoin(null, targetTableName, destTableName, colName);
			
			//table.setModel(MainFormMng.unitTable(dbManager, tree).getModel());
			//((AbstractTableModel)table.getModel()).fireTableDataChanged();
			
		} else if(event.getActionCommand().equals("PROJECTION")) {
			
		}
	} 
}
