package gui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import core.DBManager;
import core.DataBase;
import core.Table;

public class MainFormMng {
	
	public static JTree createDB(DBManager dbManager, JTree tree) {
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
			// TODO 
			/*
			DataBase db = dbManager.createNewDB(dbName);
			return addNewNodeToTree(db, tree);
			*/
			return null;
		} else {
			return tree;
		}
	}
	
	private static JTree addNewNodeToTree(Object o, JTree tree) {
		DefaultMutableTreeNode root = getRoot(tree);
		root.add(new DefaultMutableTreeNode(o));
		
		tree.setModel(new DefaultTreeModel(root));
		return tree;
	}
	
	private static DefaultMutableTreeNode getRoot(JTree tree) {
		DefaultTreeModel model = (DefaultTreeModel)tree.getModel();
		return (DefaultMutableTreeNode)model.getRoot();
	}
	
	public static JTree removeTable(JTree tree) {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
		Object userObject = node.getUserObject();
		if(userObject instanceof Table) {
			Table table = (Table)userObject;
			DataBase dataBase = (DataBase)(node.getParent());
			dataBase.deleteTable(dataBase.getTableList().indexOf(table));
		}
		return tree;
	}
	
	public static void removeNodeFromTree(Object o, JTree tree) {
		DefaultMutableTreeNode root = getRoot(tree);
		
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
}
