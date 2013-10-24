package gui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import core.DBManager;
import core.DataBase;

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
			DataBase db = dbManager.createNewDB(dbName);
			return addNewNodeToTree(db, tree);
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
}
