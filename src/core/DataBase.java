/**
 * Data base class.
 * Functionality:
 * 1) contains list of tables
 * 2) provide elementary manipulations: add, delete, get.
 * 3) provide operations: union, intersection
 * 
 * @author:			Denis Panchenko, Tanya Pushchalo
 * Written:			20/10/2013
 * Last Updated: 	20/10/2013
 */

package core;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class DataBase extends ActionPool{
	private String _dbName;
	
	private ArrayList<Table> _tableList; // list of tables	
	
	/**
	 * Generates name for database.
	 * Name is based upon current date and time in order
	 * to avoid collisions.
	 * @return String
	 */
	private String generateDefaultName()
	{
		StringBuilder defaultName = new StringBuilder();
		defaultName.append("New Database (");
		defaultName.append(new SimpleDateFormat("ddMMyy_HHmmss")
		.format(Calendar.getInstance().getTime()));
		defaultName.append(")");
		return defaultName.toString();
	}
	
	/**
	 * Add a new table to the database
	 * @return void
	 */
	public void createTable(Table table) 
	{
		if(table != null)
			_tableList.add(table);
	}
	
	/**
	 * Create new table with defined name
	 * Returns false if operation was successfully completed
	 * otherwise returns false.
	 * @param tableName - String name of new table
	 * @return boolean - success flag
	 */
	public boolean createTable(String tableName)
	{
		boolean result = false;
		for(Table table : _tableList)
			if(table.getTableName().equals(tableName))
			{
				result = true;
				break;
			}
		if(result == false)
		{
			Action createTable = new Action(Action.ACTION_TYPE.CREATE);
			createTable.setData(tableName, null);
			pushAction(createTable);
		}
		return result;
	}
	
	/**
	 * Delete table from database.
	 * Table is specified by id in table list. 
	 * Throws wrong argument exception if gets an id
	 * of non-existing table.
	 * Returns false in case of successfully completed
	 * @throws WrongArgumentException if id is not exists
	 * @return boolean
	 */
	public boolean deleteTable(Integer id) {
		boolean result = false;
		
		return result;
	}
	
	/**
	 * Returns number of tables in database
	 * @return Integer - quantity of tables
	 */
	public Integer getNumOfTables() {
		return _tableList.size();
	};
	
	/**
	 * Returns the list of tables 
	 * @return ArrayList<Table> - list of tables in database
	 */
	public ArrayList<Table> getTableList() {
		return _tableList;
	};
	
	public DataBase(){
		_dbName = generateDefaultName();
	}
	
	public DataBase(String name){
		if(name == null)
			name = generateDefaultName();
		_dbName = name;
	}
	
	public String getName(){
		return _dbName;
	}

	@Override
	public String toString() {
		
		return getName();
	}

	@Override
	public void performAll() {
		// TODO Auto-generated method stub
		
	}

	public void addExistingTableFromFile(File t) 
	{
		try 
		{
			_tableList.add(new Table(t));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
