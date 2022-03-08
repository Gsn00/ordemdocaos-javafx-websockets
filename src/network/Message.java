package network;

import java.io.Serializable;

public class Message implements Serializable
{

	private static final long serialVersionUID = 1L;

	String message;
	
	public Message(Object object)
	{
		if (object instanceof String)
		{
			message = (String) object;
		}
	}

	@Override
	public String toString()
	{
		return message;
	}
}
