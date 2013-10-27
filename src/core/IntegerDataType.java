package core;

public class IntegerDataType extends DataType
{
	private Integer _value;
	
	public IntegerDataType(TYPE type) 
	{
		super(type);
		_value = new Integer(0);
	}
	
	public void setValue(Integer value)
	{
		if(value != null)
			_value = value;
	}

	public Integer getValue()
	{
		return _value;
	}
}
