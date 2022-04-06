package entities;

import java.io.Serializable;
import java.util.List;
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
	private List<String> playlist;
	
	private byte[] imageBytes;
	
	@SuppressWarnings("unchecked")
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
		case PLAYLIST:
			this.playlist = (List<String>) object;
			break;
		case PLAYBYPLAYLIST:
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
			this.imageBytes = (byte[]) object;
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

	public byte[] getImageBytes()
	{
		return imageBytes;
	}

	public void setImageBytes(byte[] imageBytes)
	{
		this.imageBytes = imageBytes;
	}

	public List<String> getPlaylist()
	{
		return playlist;
	}

	public void setPlaylist(List<String> playlist)
	{
		this.playlist = playlist;
	}

	@Override
	public String toString()
	{
		return message;
	}
}
