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
import java.text.SimpleDateFormat;
import java.util.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

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
		defaultName.append("NewDatabase(");
		defaultName.append(new SimpleDateFormat("ddMMyy:HHmmss")
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
			addAction(createTable);
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
	public boolean removeTable(Integer id) 
	{
		boolean result = false;
		
		return result;
	}
	
	public void removeTable(String tableName)
	{
		if(tableName == null)
			return;
		for(int i = 0; i < _tableList.size(); i++)
			if(_tableList.get(i).getTableName().equals(tableName))
			{
				Action deleteTable = new Action(Action.ACTION_TYPE.DELETE);
				deleteTable.setData("name", tableName);
				addAction(deleteTable);
			}
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
		super();
		_dbName = generateDefaultName();
		
	}
	
	public DataBase(String name){
		super();
		if(name == null)
			name = generateDefaultName();
		_dbName = name;
		_tableList = new ArrayList<Table>();
	}
	
	public String getName(){
		return _dbName;
	}

	public void save()
	{
		performAll();
	}
	
	@Override
	public String toString() 
	{		
		return getName();
	}

	@Override
	protected void performAll() {
		while(!_actionPool.isEmpty())
		{
			Action action = _actionPool.get(0);
			if(action.getAction().equals(Action.ACTION_TYPE.CREATE))
			{
				// TODO check this function
				try
				{
					String tableName = action.getValue();
					File newTable = new File(getPath() + tableName);
					newTable.createNewFile();
					Table table = new Table(newTable);
					_tableList.add(table);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			else if(action.getAction().equals(Action.ACTION_TYPE.DELETE))
			{
				// TODO check this code block!!!
				String tableName = action.getValue();
				System.out.println(tableName);
				for(int i = 0; i < _tableList.size(); i++)
					if(_tableList.get(i).getTableName().equals(tableName))
					{
						System.out.println("REMOVED");
						_tableList.remove(i);
						break;
					}
				File table = new File(getPath() + tableName);
				table.delete();
			}
			removeAction();
		}
		for(Table table : _tableList)
			table.save();
	}

	private String getPath()
	{
		return "DB" + File.separator + getName() + File.separator;
	}
	
	public void addExistingTableFromFile(File t) 
	{
		try 
		{
			_tableList.add(Table.fromFile(t));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
