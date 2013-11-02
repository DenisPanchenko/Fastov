package gui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import core.DBManager;
import core.DataBase;
import core.DataType;
import core.Table;

public class MainFormMng {
	
	public static JTree createDB(DBManager dbManager, JTree tree) {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
		if(node.isRoot()) {
			List<String> dbNames = new ArrayList<String>();
			List<DataBase> dataBases = dbManager.getDataBaseList();
			
			String dbName = JOptionPane.showInputDialog("Enter data base name");
			
			if(dataBases != null) {
				for(DataBase db: dataBases) {
					dbNames.add(db.getName());
				}
			}
			while(dbNames.contains(dbName) && dbName != null){
				dbName = JOptionPane.showInputDialog("DataBase is already exists. Enter another name");
			}
			if(dbName != null) {
				dbManager.createDB(dbName);
				return tree;
			} else {
				return tree;
			}
		} else {
			return tree;
		}
	}
	
	private static JTree addNewNodeToTree(Object o, JTree tree) {
		DefaultMutableTreeNode root = getRoot(tree);
		if(o instanceof DataBase) {
			root.add(new DefaultMutableTreeNode(o));
		} else {
			DefaultMutableTreeNode node = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
			node.add(new DefaultMutableTreeNode(o));
		}
		
		tree.setModel(new DefaultTreeModel(root));
		return tree;
	}
	
	private static DefaultMutableTreeNode getRoot(JTree tree) {
		DefaultTreeModel model = (DefaultTreeModel)tree.getModel();
		return (DefaultMutableTreeNode)model.getRoot();
	}
	
	public static JTree removeTable(JTree tree, DBManager dbManager) {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
		Object userObject = node.getUserObject();
		if(userObject instanceof Table) {
			Table table = (Table)userObject;
			DefaultMutableTreeNode parent = (DefaultMutableTreeNode)node.getParent();
			DataBase dataBase = (DataBase)(parent.getUserObject());
			dbManager.deleteTable(dataBase.getName(), table.getTableName());
		}
		removeNodeFromTree(node);
		return tree;
	}
	
	public static void removeNodeFromTree(Object o) {
		DefaultMutableTreeNode node = new DefaultMutableTreeNode(o);
		node.removeFromParent();
	}
	
	public static DefaultMutableTreeNode createTreeNodes(DBManager dbManager) {
		
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Available data bases");
		List<DataBase> dataBases = dbManager.getDataBaseList();
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

	public static JTree createTable(DBManager dbManager, JTree tree) {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
		Object userObject = node.getUserObject();
		if(userObject instanceof DataBase) {
			DataBase dataBase = (DataBase) userObject;
			List<Table> tables = dataBase.getTableList();
			List<String> tablesNames = new ArrayList<String>();
			String tableName = JOptionPane.showInputDialog("Enter table name");
			
			if(tables != null) {
				for(Table table: tables) {
					tablesNames.add(table.getTableName());
				}
			}
			while(tablesNames.contains(tableName) && tableName != null){
				tableName = JOptionPane.showInputDialog("Table is already exists. Enter another name");
			}
			if(tableName != null) {
				dbManager.createTable(dataBase.getName(), tableName);
				return tree;
			} else {
				return tree;
			}
			//Table table = dbManager.createTable(dataBase, columnsNames, columnTypes);
			//dbManager.createTable(((DataBase) userObject).getName(), columnsNames, columnTypes);
			//return addNewNodeToTree(table, tree);
		}
		return tree;
	}

	public static JTree removeDB(JTree tree, DBManager dbManager) {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
		Object o = node.getUserObject();
		if(o instanceof DataBase) {
			DataBase dataBase = (DataBase)o;
			dbManager.deleteDB(dataBase.getName());
			removeNodeFromTree(dataBase);
		}
		return tree;
	}
}
