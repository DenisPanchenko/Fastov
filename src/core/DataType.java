/**
 * Wrapper for data type available in database.
 * Supports next types:
 * 1) Integer
 * 2) Float
 * 3) String
 * 4) Enum
 * Enum is a list of strings.
 * Functionality:
 * 1) getValue
 * 2) setValue
 * 3) getType
 * 
 * @author:			Denis Panchenko, Tanya Pushchalo
 * Written:			27/10/2013
 * Last Updated: 	31/10/2013
 */

package core;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataType implements Serializable
{
	public enum TYPE {INTEGER, FLOAT, STRING, ENUM}; // list of available types
	protected TYPE _type; // current type
	private Object _value; // current value
	
	/**
	 * Constructor obligatory takes as input
	 * parameter that specifies data type
	 * 
	 * By default initialize value of object as
	 * 0 for Integer
	 * 0.0 for Float
	 * "" for String
	 * [] for Enum
	 * @param type - TYPE enum
	 */
	public DataType(TYPE type)
	{
		if(type == null)
			type = TYPE.INTEGER;
		_type = type;
		setValue(null); // set default value to variable
	}
	
	/**
	 * Constructor obligatory takes as input
	 * parameter that specifies data type.
	 * Here it also takes additional parameter - initial
	 * value.
	 * @param type - enum TYPE
	 * @param value - value of object
	 */
	public DataType(TYPE type, Object value)
	{
		if(type == null)
			type = TYPE.INTEGER;
		_type = type;
		setValue(value);
	}
	
	/**
	 * Returns type of object.
	 * Possible values are:
	 * INTEGER, FLOAT, STRING, ENUM.
	 * @return TYPE - enum
	 */
	public TYPE getType()
	{
		return _type;
	}
	
	/**
	 * Returns current value of object.
	 * Object type corresponds to one its type.
	 * @also see also getType()
	 * @return Object - current value
	 */
	public Object getValue()
	{
		return _value;
	}
	
	private void setDefaultValue() {
		if(_type == TYPE.INTEGER)
			_value = new Integer(0);
		else if(_type == TYPE.FLOAT)
			_value = new Float(0.0);
		else if(_type == TYPE.STRING)
			_value = new String("");
		else if(_type == TYPE.ENUM)
			_value = new ArrayList<String>();
		else return;
	}
	
	/**
	 * Sets the value.
	 * @param value - Object
	 */
	public void setValue(Object value) throws NumberFormatException
	{		
		if(value == null)
		{
			setDefaultValue();
		} else {
			if(_type == TYPE.INTEGER) {
				Integer intNum = new Integer(value.toString());
				_value = intNum;
			} else if(_type == TYPE.FLOAT) {
				Float fltNum = new Float(value.toString());
				_value = fltNum;
			} else if(_type == TYPE.STRING)
				_value = value.toString();
			else if(_type == TYPE.ENUM) {
				List<String> enumTypes = validateEnumValue(value.toString());
				_value = enumTypes;
			} else return;
		}
	}
	
	private List<String> validateEnumValue(String value) throws NumberFormatException {
		if(value.equals("[]")) {
			return new ArrayList<String>();
		}
		String types = value.toUpperCase().substring(1, value.length() - 1);
		String[] splitedTypes = types.split(", ");
		for(String type: splitedTypes) {
			try {
				TYPE.valueOf(type);
			} catch (Exception e) {
				throw new NumberFormatException();
			}
		}
		return Arrays.asList(splitedTypes);
				
	}
	
	public static TYPE fromString(String s)
	{
		if(s.equals("INTEGER"))
			return TYPE.INTEGER;
		else if(s.equals("FLOAT"))
			return TYPE.FLOAT;
		else if(s.equals("STRING"))
			return TYPE.STRING;
		else if(s.equals("ENUM"))
			return TYPE.ENUM;
		return TYPE.INTEGER;
	}
	/**
	 * Test the equality of two objects.
	 * @param another - DataType
	 * @return boolean - whether two objects are equals or not
	 */
	public boolean equals(DataType another)
	{
		boolean result = true;
		if(!another.getType().equals(getType()))
			result = false;
		else if(!another.toString().equals(toString()))
			result = false;
		return result;
	}
	
	public String toString()
	{
		return _value.toString();
	}
}