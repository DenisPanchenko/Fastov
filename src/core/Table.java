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
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Table extends ActionPool{
	private Integer _WIDTH; // width of table
	private Integer _HEIGHT; // height of table
	private String _name; // name of table
	private FileManager _fileManager;
	private ArrayList<DataType.TYPE> _columnPattern; // types sequence for column
	private ArrayList<String> _columnNames; // name sequence for column
	private ArrayList<ArrayList<DataType> > _content; // actually content of table
	
	class FileManager
	{
		// TODO all operations
		private File _file;
		private Document _document;
		
		public FileManager(File f)
		{
			if(f.canRead())
			{
				_file = f;
				try
				{
					DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
					DocumentBuilder docBuilder = dbFactory.newDocumentBuilder();
					_document = docBuilder.parse(f);
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		public void createColumn(String columnName, String colType)
		{
			Element type = _document.createElement("type");
			type.appendChild(_document.createTextNode(colType));
			Node types = _document.getElementsByTagName("types").item(0);
			types.appendChild(type);
			
			Element name = _document.createElement("name");
			name.appendChild(_document.createTextNode(columnName));
			Node names = _document.getElementsByTagName("names").item(0);
			names.appendChild(name);
			
			Node width = _document.getElementsByTagName("width").item(0);
			width.setTextContent(_WIDTH.toString());
			
			try
			{
				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				DOMSource source = new DOMSource(_document);
				StreamResult result = new StreamResult(_file);
				transformer.transform(source, result);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		public void deleteColumn(String columnName)
		{
			
		}
		
		public void setValue()
		{
			
		}
	}
	
	public static Table fromFile(File t) throws SAXException, IOException, ParserConfigurationException
	{
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(t);
		doc.normalize();
		
		String caption = doc.getElementsByTagName("caption").item(0).getFirstChild().getTextContent();
		
		Table result = new Table(caption, t.getAbsolutePath());

		String width = doc.getElementsByTagName("width").item(0).getFirstChild().getTextContent();
		result._WIDTH = Integer.parseInt(width);
		String height = doc.getElementsByTagName("height").item(0).getFirstChild().getTextContent();
		result._HEIGHT = Integer.parseInt(height);
		
		NodeList names = doc.getElementsByTagName("name");
		for(int i = 0; i < names.getLength(); i++)
			result._columnNames.add(names.item(i).getFirstChild().getTextContent());
		
		NodeList types = doc.getElementsByTagName("types");
		for(int i = 0; i < types.getLength(); i++)
			result._columnPattern.add(DataType.fromString(types.item(i).getTextContent()));
		
		NodeList rows = doc.getElementsByTagName("row");
		for(int i = 0; i < rows.getLength(); i++)
		{
			result._content.add(new ArrayList<DataType>());
			NodeList columns = doc.getElementsByTagName("columns");
			for(int j = 0; j < columns.getLength(); j++)
			{
				DataType data = new DataType(result._columnPattern.get(i),columns.item(j).getTextContent());
				result._content.get(i).set(j, data);
			}
		}
		
		return result;
	}
	
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
	public Table(String tableName, String file){
		if(tableName == null)
			_name = generateDefaultName();
		else
			_name = tableName;
		_WIDTH = new Integer(0);
		_HEIGHT = new Integer(0);
		_columnPattern = new ArrayList<DataType.TYPE>();
		_columnNames = new ArrayList<String>();
		_content = new ArrayList<ArrayList<DataType> >();
		_fileManager = new FileManager(new File(file));
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
		_columnPattern = new ArrayList<DataType.TYPE>();
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
		
		Element name = table.createElement("caption");
		name.appendChild(table.createTextNode(t.getName()));
		root.appendChild(name);
		
		Element meta = table.createElement("meta");
		
		Element width = table.createElement("width");
		width.appendChild(table.createTextNode(_WIDTH.toString()));
		meta.appendChild(width);
		
		Element height = table.createElement("height");
		height.appendChild(table.createTextNode(_HEIGHT.toString()));
		meta.appendChild(height);
		
		Element types = table.createElement("types");
		types.appendChild(table.createTextNode(""));
		meta.appendChild(types);
		
		Element names = table.createElement("names");
		names.appendChild(table.createTextNode(""));
		meta.appendChild(names);
		
		root.appendChild(meta);
		
		Element content = table.createElement("content");
		content.appendChild(table.createTextNode(""));
		root.appendChild(content);
		
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
		
		_fileManager = new FileManager(t);
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
		addAction(a);
		//System.out.println(colName);
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
				_fileManager.createColumn(columnName, columnType);
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
						_content.get(index).remove(i);
				_content.remove(index);
				_columnNames.remove(index);
				_fileManager.deleteColumn(columnName);
			}
			removeAction();
		}
	}

	public ArrayList<String> get_columnNames() {
		return _columnNames;
	}

	public ArrayList<ArrayList<DataType> > get_content() {
		return _content;
	}

	public Integer get_WIDTH() {
		return _WIDTH;
	}

	public Integer get_HEIGHT() {
		return _HEIGHT;
	}
}
