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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class Table extends ActionPool{
	private Integer _WIDTH; // width of table
	private Integer _HEIGHT; // height of table
	private String _name; // name of table
	private ArrayList<DataType.TYPE> _columnPattern; // types sequence for column
	private ArrayList<String> _columnNames; // name sequence for column
	private ArrayList<ArrayList<DataType> > _content; // actually content of table
	
	/**
	 * Returns default unique name for table.
	 * Based on the current date and time.
	 * @return String - generated name
	 */
	public static String generateDefaultName()
	{
		StringBuilder defaultName = new StringBuilder();
		defaultName.append("NewTable(");
		defaultName.append(new SimpleDateFormat("ddMMyy:HHmmss")
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
	 * Constructor with obligatory name parameter
	 * @param tableName - String name of the table
	 */
	public Table(String tableName){
		if(tableName == null)
			_name = generateDefaultName();
		else
			_name = tableName;
		_WIDTH = new Integer(0);
		_HEIGHT = new Integer(0);
		_columnNames = new ArrayList<String>();
		_content = new ArrayList<ArrayList<DataType> >();
	}
	
	/**
	 * Construct new Table object according to the
	 * defined into file t information.
	 * File t must contain xml-presentation of table,
	 * otherwise exception will be thrown.
	 * @param t - File with table
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	public Table(File t) throws SAXException, IOException, ParserConfigurationException 
	{
		_WIDTH = new Integer(0);
		_HEIGHT = new Integer(0);
		_columnNames = new ArrayList<String>();
		_content = new ArrayList<ArrayList<DataType> >();
		_name = t.getName();
		
		if(t == null || !t.canRead())
			throw new FileNotFoundException();
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document table = dBuilder.newDocument();
		Element root = table.createElement("table");
		table.appendChild(root);
		
		Element name = table.createElement("name");
		name.appendChild(table.createTextNode(t.getName()));
		root.appendChild(name);
		
		Element meta = table.createElement("meta");
		root.appendChild(meta);
		
		try
		{
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(table);
			StreamResult result = new StreamResult(t);
			transformer.transform(source, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns the name of the table
	 * @return String - name of the table
	 */
	public String getTableName() {
		return _name;
	}

	/**
	 * Perform all actions from action pool
	 */
	public void save()
	{
		performAll();
	}
	
	@Override
	public String toString() {
		return _name;
	}

	public void createColumn(String colName, DataType.TYPE type)
	{
		Action a = new Action(Action.ACTION_TYPE.CREATE);
		String typeString = null;
		switch(type)
		{
		case INTEGER:
			typeString = new String("INTEGER");
			break;
		case FLOAT:
			typeString = new String("FLOAT");
			break;
		case STRING:
			typeString = new String("STRING");
			break;
		case ENUM:
			typeString = new String("ENUM");
			break;
		default:
			typeString = new String("INTEGER");
			break;
		}
		a.setData(colName, typeString);
		System.out.println(colName);
	}
	
	public void setCellValue(int x, int y, Object newValue)
	{
		_content.get(x).get(y).setValue(newValue);
	}
	
	@Override
	protected void performAll() {
		while(!_actionPool.isEmpty())
		{
			Action action = _actionPool.get(0);
			if(action.getAction().equals(Action.ACTION_TYPE.CREATE))
			{
				// TODO check correctness
				String columnName = action.getField();
				String columnType = action.getValue();
				
				DataType.TYPE type;
				if(columnType.equals("INTEGER"))
					type = DataType.TYPE.INTEGER;
				else if(columnType.equals("FLOAT"))
					type = DataType.TYPE.FLOAT;
				else if(columnType.equals("STRING"))
					type = DataType.TYPE.STRING;
				else if(columnType.equals("ENUM"))
					type = DataType.TYPE.ENUM;
				else
					continue; 
				_columnPattern.add(type);
				_columnNames.add(columnName);
				for(int i = 0; i < _content.size(); i++)
					_content.get(i).add(new DataType(type));
				_WIDTH++;
			}
			else if(action.getAction().equals(Action.ACTION_TYPE.DELETE))
			{
				// TODO check correctness
				String columnName = action.getValue();
				int index = -1;
				for(int i = 0; i < _columnNames.size(); i++)
					if(_columnNames.get(i).equals(columnName))
						index = i;
				if(index >= 0)
					for(int i = 0; i < _content.size(); i++)
						_content.get(i).remove(index);
			}
			removeAction();
		}
	}

	public ArrayList<String> get_columnNames() {
		return _columnNames;
	}

	public ArrayList<ArrayList<DataType>> get_content() {
		return _content;
	}

	public Integer get_WIDTH() {
		return _WIDTH;
	}

	public Integer get_HEIGHT() {
		return _HEIGHT;
	}
}
