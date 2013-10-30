package core;

public class FloatDataType extends DataType
{
	private Float _value;
	public FloatDataType()
	{
		super(DataType.TYPE.FLOAT);
	}
	public Float getValue()
	{
		return _value;
	}
	
	public void setValue(Float value)
	{
		if(value != null)
			_value = value;
	}
}