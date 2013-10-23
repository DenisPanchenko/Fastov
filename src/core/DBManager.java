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


public class DBManager {
	/**
	 * Class create DataBase object based on
	 * directory hierarchy
	 * @author denis
	 */
	class DBFactory{
		public DataBase getDataBase(String filePath){
			DataBase result = new DataBase();
			
			return result;
		}
	}
	
	static class ConfigManager // TODO translate class into STATE object
	{
		
		// TODO implement action queue
		
		private static File _file;
		private static String _configFilePath;
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
			}
		}
		/**
		 * Add new database specified by name
		 * to config file
		 * @param name - String name of new database
		 */
		public static void addNewDB(String name)
		{
			try
			{
				FileWriter fw = new FileWriter(_file, true);
				BufferedWriter bw = new BufferedWriter(fw);
				bw.append(name + "\n");
				bw.close();
			} catch (Exception e) {
				System.out.println(e.getStackTrace().toString());
			}
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
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(_dbConfigPath));
			Integer num = Integer.parseInt(br.readLine());
			DBFactory factory = new DBFactory();
			for(int i = 0; i < num; i++)
			{
				String dbPath = br.readLine();
				DataBase db = factory.getDataBase(dbPath);
				_dataBases.add(db);
			}
			
		} catch(Exception e) {
			System.out.println(e.toString());
		}
	}
	
	/**
	 * Returns the list of available databases
	 * @return ArrayList<DataBase> - list of databases
	 */
	public ArrayList<DataBase> getDataBaseList(){
		return _dataBases;
	}
	
	/**
	 * 
	 */
	public DataBase createNewDB(String dbName){
		DataBase db = new DataBase(dbName);
		new File(_dbPath + dbName).mkdir();
		_configManager.addNewDB(db.getName());
		_dataBases.add(db);
		
		return db;
	}
	/**
	 * Deletes database from list
	 * @param dbName - String name of the DB
	 */
	public void deleteDB(String dbName)
	{
		Integer index = new Integer(-1);
		if(dbName != null)
		{
			for(DataBase db : _dataBases)
				if(db.getName() == dbName)
					index = _dataBases.indexOf(db);
		}
		if(index > -1)
		{
			_dataBases.remove(index);
			// TODO delete from file system and from config file!!!
		}
	}
}
