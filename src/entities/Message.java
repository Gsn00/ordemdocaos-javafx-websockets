package entities;

import java.io.Serializable;
import java.util.Set;

import enums.MessageType;
import javafx.util.Duration;

public class Message implements Serializable
{
	private static final long serialVersionUID = 1L;

	private String message;
	private Character character;
	private MessageType messageType;
	private Double volume;
	private Duration duration;
	private Set<Character> characters;

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
		case PAUSE:
			break;
		case PLAY:
			break;
		case VOLUME:
			this.volume = (Double) object;
			break;
		case MUSICLOOPING:
			this.setDuration((Duration) object);
			break;
		case CONNECT:
			this.character = (Character) object;
			break;
		case DISCONNECT:
			this.character = (Character) object;
			break;
		case REFRESHCONNECTIONS:
			this.character = (Character) object;
			break;
		case IMAGE:
			this.message = (String) object;
			break;
		default:
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

	public Double getVolume()
	{
		return volume;
	}

	public void setVolume(Double volume)
	{
		this.volume = volume;
	}

	public Duration getDuration()
	{
		return duration;
	}

	public void setDuration(Duration duration)
	{
		this.duration = duration;
	}
	
	public Set<Character> getCharacters()
	{
		return characters;
	}

	public void setCharacters(Set<Character> characters)
	{
		this.characters = characters;
	}

	@Override
	public String toString()
	{
		return message;
	}
}
