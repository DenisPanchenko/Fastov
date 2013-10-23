/**
 * Table class.
 * Self-dependent storage of data in in a table structure.
 * Functionality:
 * 1) store data in a table structure
 * 2) provide elementary data manipulations: add, delete, modify, select
 * 
 * @Author:			Denis Panchenko, Tanya Pushchalo
 * Written:			20/10/2013
 * Last Updated:	20/10/2013
 */

package core;

import java.text.SimpleDateFormat;
import java.util.*;

public class Table {
	private Integer _WIDTH; // width of table
	private Integer _HEIGHT; // height of table
	private String _name; // name of table
	private ArrayList<ArrayList<DataType> > _content; // actually content of table
	
	private String generateDefaultName()
	{
		StringBuilder defaultName = new StringBuilder();
		defaultName.append("New Table (");
		defaultName.append(new SimpleDateFormat("ddMMyy_HHmmss")
		.format(Calendar.getInstance().getTime()));
		defaultName.append(")");
		return defaultName.toString();
	}
	
	/**
	 * Return a cell by two indices
	 * If pair of indices does not match real cell
	 * in the table throws an wrong argument exception
	 * @param i - row number
	 * @param j - column number
	 * @return DataType of specified cell
	 * @throws WrongArgumentException
	 */
	public DataType getCell(Integer i, Integer j) {
		return _content.get(i).get(j);
	}	
	
	/**
	 * 
	 * @param tableName - String name of the table
	 */
	public Table(String tableName){
		if(tableName == null)
			_name = generateDefaultName();
		else
		{
			_name = tableName;
		}
	}
	
	/**
	 * Returns the name of the table
	 * @return String - name of the table
	 */
	public String getTableName() {
		return _name;
	}

	@Override
	public String toString() {
		return _name;
	}
	
	
}
