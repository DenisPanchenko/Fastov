package core;

public class DataType 
{
	public enum TYPE {INTEGER, FLOAT, STRING, ENUM};
	protected TYPE _type;
	
	public DataType(TYPE type)
	{
		if(type == null)
			type = TYPE.INTEGER;
		_type = type;
	}
	
	public TYPE getType()
	{
		return _type;
	}
}
