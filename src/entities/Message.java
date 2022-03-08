package entities;

import java.io.Serializable;

import enums.MessageType;

public class Message implements Serializable
{

	private static final long serialVersionUID = 1L;

	private String message;
	private MessageType messageType;
	
	public Message(Object object, MessageType messageType)
	{
		this.messageType = messageType;
		
		if (messageType == MessageType.MESSAGE)
		{
			message = (String) object;
		}
		if (messageType == MessageType.STATUS)
		{
			//
		}
		if (messageType == MessageType.METHOD)
		{
			//
		}
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

	public MessageType getMessageType()
	{
		return messageType;
	}

	public void setMessageType(MessageType messageType)
	{
		this.messageType = messageType;
	}

	@Override
	public String toString()
	{
		return message;
	}
}
