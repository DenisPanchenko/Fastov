/**
 * Manger of databases.
 * Functionality:
 * 1) contains database list
 * 2) provide elementary manipulations: add, delete, edit, view
 * 
 * @author:			Denis Panchenko, Tanya Pushchalo
 * Wriiten:			20/10/2013
 * Last Updated:	20/10/2013	
 */
package core;

import java.text.SimpleDateFormat;
import java.util.*;
import java.io.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;

public class DBManager extends ActionPool{
	
	/**
	 * Class create DataBase object based on
	 * directory hierarchy
	 * @author denis
	 */
	class DBFactory{
		public DataBase getDataBase(String filePath) throws FileNotFoundException{
			DataBase result = null;
			if(filePath == null)
				return result;
			File file = new File(filePath);
			if(file.exists() && file.isDirectory())
			{
				result = new DataBase(file.getName());
				System.out.println(file.getName());
				File[] tables = file.listFiles();
				for(File t : tables)
					result.addExistingTableFromFile(t);
			} else throw new FileNotFoundException();
			return result;
		}
	}
	
	static class ConfigManager // TODO translate class into STATE object
	{
		
		// TODO implement action queue
		private static File _file;
		private static String _configFilePath;
		private static Document _configDoc;
		/**
		 * Open specified file
		 * @param configFilePath - String
		 */
		public ConfigManager(String configFilePath)
		{
			if(configFilePath != null)
			{
				_configFilePath = configFilePath;
				_file = new File(_configFilePath);
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				try 
				{
					DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
					_configDoc = dBuilder.parse(_file);
					_configDoc.normalize();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		/**
		 * Add new database specified by name
		 * to configuration file.
		 * @param name - String name of new database
		 */
		public static void addNewDB(String name)
		{
			try
			{
				Node root = _configDoc.getElementsByTagName("system").item(0);
				Element db = _configDoc.createElement("database");
				db.appendChild(_configDoc.createTextNode(_dbPath + name));
			    root.appendChild(db);
			    TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				DOMSource source = new DOMSource(_configDoc);
				StreamResult result = new StreamResult(_file);
		 		transformer.transform(source, result);		
			} catch (Exception e) {
				System.out.println(e.getStackTrace().toString());
			}
		}
		
		public static void removeDB(String name)
		{
			try
			{
				NodeList dbs = _configDoc.getElementsByTagName("database");
				for(int i = 0; i < dbs.getLength(); i++)
					if(dbs.item(i).getNodeName().equals(name))
						_configDoc.removeChild(dbs.item(i));
				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				DOMSource source = new DOMSource(_configDoc);
				StreamResult result = new StreamResult(_file);
		 		transformer.transform(source, result);
			}catch (Exception e){
				
			}
		}
		
		/**
		 * Returns the list of databases
		 * registered in configuration file.
		 * Returns empty ArrayList if configuration file is empty.
		 * @return ArrayList<String>
		 */
		public ArrayList<String> getDBList()
		{
			ArrayList<String> result = new ArrayList<String>();
			NodeList nodes = _configDoc.getElementsByTagName("database");
			for(int i = 0; i < nodes.getLength(); i++)
				result.add(nodes.item(i).getChildNodes().item(0).getNodeValue());
			return result;
		}
	}
	
	public enum AUTH_TYPE {ADMIN, USER, FAIL};
	
	private static final String _ADMIN_LOGIN = "admin"; // login for admin
	private static final String _ADMIN_PASS = "admin"; // password for admin
	private static final String _USER_LOGIN = "user"; // login for user
	private static final String _USER_PASS = ""; // password for user
	
	private static AUTH_TYPE _CURRENT_USER_TYPE; // type of current user
	
	/**
	 * Validate login and password of user in order
	 * to yield appropriate rights
	 * @param login - user login
	 * @param password - user password
	 * @return AUTH_TYPE
	 */
	public AUTH_TYPE authenticate(String login, String password){
		AUTH_TYPE result = AUTH_TYPE.FAIL;
		if(login.equals(_ADMIN_LOGIN) && password.equals(_ADMIN_PASS))
		{
			result = AUTH_TYPE.ADMIN;
			_CURRENT_USER_TYPE = AUTH_TYPE.ADMIN;
		}
		else if(login.equals(_USER_LOGIN) && password.equals(_USER_PASS))
		{
			result = AUTH_TYPE.USER;
			_CURRENT_USER_TYPE = AUTH_TYPE.USER;
		}
		return result;
	}
	
	private static final String _dbConfigPath = "config"; 
	private static final String _dbPath = "DB/";
	private static ConfigManager _configManager;
	private ArrayList<DataBase> _dataBases;
	
	/**
	 *	Create a database manager 
	 */
	public DBManager() {
		_configManager = new ConfigManager(_dbConfigPath);
		_dataBases = new ArrayList<DataBase>();
		_actionStack = new Stack<Action>();
		try
		{
			ArrayList<String> dbList = _configManager.getDBList();
			DBFactory factory = new DBFactory();
			for(String dbPath : dbList)
			{
				DataBase db = factory.getDataBase(dbPath);
				_dataBases.add(db);
			}
			
		} catch(Exception e) {
			System.out.println(e.toString());
		}
	}
	
	// TODO
	public void removeTable(String dbName, String tableName)
	{
		
	}
	
	/**
	 * Returns the list of available databases
	 * @return ArrayList<DataBase> - list of databases
	 */
	public ArrayList<DataBase> getDataBaseList(){
		return _dataBases;
	}
	
	/**
	 * Create database by specified name
	 * @param dbName - String
	 */
	public void createDB(String dbName){
		Action createDB = new Action(Action.ACTION_TYPE.CREATE);
		createDB.setData("name", dbName);
		pushAction(createDB);
	}
	
	/**
	 * Deletes database from list
	 * @param dbName - String name of the DB
	 */
	public void deleteDB(String dbName)
	{
		if(dbName == null)
			return;
		Action deleteDB = new Action(Action.ACTION_TYPE.DELETE);
		deleteDB.setData(dbName, null);
		pushAction(deleteDB);
	}
	
	public void undoAction()
	{
		popAction();
	}

	public void save()
	{
		performAll();
	}

	public void deleteTable(String dbName, String tableName) 
	{
		//TODO
		for(int i = 0; i < _dataBases.size(); i++)
		{
			if(_dataBases.get(i).getName().equals(dbName))
				_dataBases.get(i).removeTable(tableName);
		}
	}
	
	public Table createTable(DataBase dataBase, List<String> columnsNames, List<DataType> columnTypes) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	protected void performAll() {
		
		for(int i = 0; i < _actionStack.size(); i++)
		{
			Action action = _actionStack.get(i);
			if(action.getAction() == Action.ACTION_TYPE.CREATE)
			{
				String dbName = action.getValue();
				DataBase db = new DataBase(dbName);
				new File("DB/" + db.getName()).mkdir();
				_configManager.addNewDB(db.getName());
				_dataBases.add(db);
				popAction();
			} else if(action.getAction() == Action.ACTION_TYPE.DELETE) {
				String dbName = action.getValue();
				File f = new File(_dbPath + dbName);
				if(f.isDirectory())
					for(File c : f.listFiles())
						c.delete();
				for(int j = 0; j < _dataBases.size(); j++)
					if(_dataBases.get(j).getName().equals(dbName))
						_dataBases.remove(j);
				_configManager.removeDB(dbName);
				popAction();
			}
		}
	}
}
