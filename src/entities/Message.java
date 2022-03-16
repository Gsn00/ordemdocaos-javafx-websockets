package entities;

import java.io.Serializable;

import enums.MessageType;

public class Message implements Serializable
{

	private static final long serialVersionUID = 1L;

	private String message;
	private Character character;
	private MessageType messageType;
	private Double volume;
	private Boolean musicLooping;

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
			this.musicLooping = (Boolean) object;
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

	public Boolean getMusicLooping()
	{
		return musicLooping;
	}

	public void setMusicLooping(Boolean musicLooping)
	{
		this.musicLooping = musicLooping;
	}

	@Override
	public String toString()
	{
		return message;
	}
}
