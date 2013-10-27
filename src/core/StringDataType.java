package core;

public class StringDataType extends DataType
{
	private String _value;
	public StringDataType(TYPE type) 
	{
		super(type);
		_value = new String("");
	}

	public String getValue()
	{
		return _value;
	}
	
	public void setValue(String value)
	{
		if(value != null)
			_value = value;
	}
}
