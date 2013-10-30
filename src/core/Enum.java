package core;

import java.util.ArrayList;

public class Enum extends DataType
{
	private ArrayList<String> _value;
	
	public Enum()
	{
		super(DataType.TYPE.ENUM);
	}
	
	public void setValue(ArrayList<String> value)
	{
		if(value != null)
			_value = value;
	}
	
	public ArrayList<String> getValue()
	{
		return _value;
	}
}
