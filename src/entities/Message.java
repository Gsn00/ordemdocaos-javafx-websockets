package entities;

import java.io.Serializable;

import enums.MessageType;

public class Message implements Serializable
{

	private static final long serialVersionUID = 1L;

	private String message;
	private Character character;
	private MessageType messageType;

	public Message(Object object, MessageType messageType)
	{
		this.messageType = messageType;

		switch (messageType)
		{
		case MESSAGE:
			this.message = (String) object;
			break;
		case STATUS:
			this.character = (Character) object;
			break;
		case PLAYMUSIC:
			this.message = (String) object;
			break;
		case CONNECT:
			this.character = (Character) object;
			break;
		case DISCONNECT:
			this.message = (String) object;
			break;
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

	public Character getCharacter()
	{
		return character;
	}

	public void setCharacter(Character character)
	{
		this.character = character;
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
