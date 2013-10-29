/**
 * Interface for action entity.
 * Actions are used to save sequence of actions.
 * 
 * @author:			Denis Panchenko, Tanya Pushchalo
 * Written:			27/10/2013
 * Last Updated: 	27/10/2013
 */
package core;

public class Action 
{
	public enum ACTION_TYPE {CREATE, DELETE, UPDATE};
	public enum ACTION_LEVEL {DATABASE, TABLE, RECORD};
	
	private ACTION_TYPE _comType;
	private ACTION_LEVEL _comLevel;
	private String _name;
	private String _value;
	
	/**
	 * Returns the command as an enum
	 * @return COMMAND_TYPE - enum type of command
	 */
	public ACTION_TYPE getAction()
	{
		return _comType;
	};
	/**
	 * Returns the level of action
	 * Actions could refer to any of three levels:
	 * 1) database level
	 * 2) table level
	 * 3) record in table level
	 * @return COMMAND_LEVEL - enum
	 */
	ACTION_LEVEL getCommandLevel()
	{
		return _comLevel;
	};
	
	/**
	 * Constructor, obligatory parameters are:
	 * @param comType - enum command type
	 * @param comLevel - enum command level
	 */
	Action(ACTION_TYPE comType)
	{
		_comType = comType;
		_name = new String();
		_value = new String();
	}
	
	/**
	 * Sets the data for action.
	 * name - name of property
	 * value - value of property
	 * @param name - String
	 * @param value - String
	 */
	public void setData(String name, String value)
	{
		if(name == null || value == null)
			System.out.println("NULL");
		_name = name;
		_value = value;
	}
	
	/**
	 * Returns the name of property
	 * @return String - name
	 */
	public String getField()
	{
		return _name;
	}
	
	/**
	 * Returns the value of property 
	 * @return String - value
	 */
	public String getValue()
	{
		return _value;
	}
}
