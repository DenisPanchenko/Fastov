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
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DataBase extends ActionPool implements Serializable{
	
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
	/**
	 * Removes table from database.
	 * If name is null do nothing, otherwise push the
	 * delete action to the pool.
	 * @param tableName - String name of the table
	 */
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
	
	/**
	 * Construct a new database with default name
	 */
	public DataBase(){
		super();
		_dbName = generateDefaultName();
		
	}
	/**
	 * Construct a new database by the given name
	 * @param name - String the name of the database
	 */
	public DataBase(String name){
		super();
		if(name == null)
			name = generateDefaultName();
		_dbName = name;
		_tableList = new ArrayList<Table>();
	}
	
	/**
	 * Returns the name of the database
	 * @return String - name of the database
	 */
	public String getName(){
		return _dbName;
	}

	/**
	 * External interface for perform all function.
	 */
	public void save()
	{
		performAll();
	}
	
	/**
	 * Returns the name of the database.
	 * @return String - actually the name of the database 
	 */
	@Override
	public String toString() 
	{		
		return getName();
	}

	/**
	 * Perform all actions from the action pool
	 */
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
	
	public void tableJoin(String tTableName, String dTableName, String colName)
	{
		try
		{
			//	create result table in a database
			String resultName = "Join:" + tTableName + ":" + dTableName + ":" + colName;
			for(Table t : _tableList)
				if(t.getTableName().equals(resultName))
					return;
			File f = new File(getPath() + resultName);
			f.createNewFile();
			
			//	create an object result table
			Table result = new Table(f);
			Table tTable = getTableByName(tTableName);
			Table dTable = getTableByName(dTableName);
			Integer tIndex = tTable.getColumnNames().indexOf(colName);
			Integer dIndex = dTable.getColumnNames().indexOf(colName);
			
			if(tIndex == -1) //	if the destination table does not have the joining field
			{
				//result.copyContent(tTable);
				_tableList.add(result);
				System.out.println("Target table: " + tTable.getTableName()
						+ " has no join column: " + dTable.getColumnNames().get(dIndex));
				return;
			}
			
			//	create columns from target table
			for(int i = 0; i < tTable.getWidth(); i++)
				result.createColumn(tTable.getColumnNames().get(i),
						tTable.getColumnTypes().get(i));
			
			//	append columns from destination table 
			for(int i = 0; i < dTable.getWidth(); i++)
				if(i != dIndex)
					result.createColumn(dTable.getColumnNames().get(i),
							dTable.getColumnTypes().get(i));
			
			result.performAll(); //	force to create columns
			
			System.out.println(result.getColumnNames().toString());
			System.out.println(result.getColumnTypes().toString());
			
			for(int i = 0; i < tTable.getHeight(); i++)
			{
				for(int j = 0; j < dTable.getHeight(); j++)
				{
					DataType tCell = tTable.getCell(i, tIndex); //	value from target cell
					DataType dCell = dTable.getCell(j, dIndex); //	value from destination cell
					if(tCell.equals(dCell))
					{
						// Create new row and concat values here
						result.createRow();
						System.out.println("HEIGHT IS: " + result.getHeight());
						for(int k = 0; k < tTable.getWidth(); k++)
							result.setCellValue(result.getHeight() - 1, k, tTable.getCell(i, k).toString());
						
						for(int k = 0; k < dTable.getWidth(); k++)
							if(k != dIndex)
								result.setCellValue(result.getHeight() - 1, k + tTable.getWidth() - 1, dTable.getCell(j, k).toString());
					}
				}
			}
			result.performAll();
			performAll();
			_tableList.add(result);
		} catch(Exception e) {
			e.printStackTrace();
		}
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
	
	protected Table getTableByName(String name)
	{
		Table result = null;
		for(int i = 0; i < _tableList.size(); i++)
			if(_tableList.get(i).getTableName().equals(name))
				result = _tableList.get(i);
		return result;
	}

	public void projectTable(List<Integer> numbersOfcolumns, String tableName) {
		try
		{
			StringBuilder code = new StringBuilder();
			for(int index : numbersOfcolumns)
				code.append(index);
			String resultName = "Projection:" + tableName + code.toString();
			if(!_tableList.contains(resultName))
			{
				File f = new File(getPath() + resultName);
				f.createNewFile();
				Table result = new Table(f);
				Table sourceTable = getTableByName(tableName);

				result.copyContent(sourceTable);

				for(int i = 0; i < result.getWidth(); i++)
					if(!numbersOfcolumns.contains(i))
						result.deleteColumn(sourceTable.getColumnNames().get(i));
				result.performAll();

				_tableList.add(result);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
