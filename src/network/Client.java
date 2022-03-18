package network;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import entities.Character;
import entities.Message;
import enums.MessageType;
import javafx.application.Platform;
import listeners.MessageEvent;

public class Client
{
	public Socket socket;
	public Message message;
	private ObjectInputStream objectInputStream;
	private ObjectOutputStream objectOutputStream;

	private List<MessageEvent> messages = new ArrayList<>();

	public Message getMessage()
	{
		return message;
	}

	public void subscribeMessageEvent(MessageEvent event)
	{
		messages.add(event);
	}

	public void notifyMessageEvent()
	{
		for (MessageEvent event : messages)
		{
			event.onMessage();
		}
	}

	public boolean connect()
	{
		try
		{
			socket = new Socket("26.163.199.159", 7000);
			objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
			return true;
		} catch (Exception e)
		{
			System.err.println("N�o foi poss�vel conectar ao servidor.");
			return false;
		}
	}

	public void sendSocket(Message object)
	{
		if (socket != null)
		{
			try
			{
				objectOutputStream.writeObject(object);
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	public void disconnect(Character character)
	{
		if (socket != null)
		{
			try
			{
				sendSocket(new Message(character, MessageType.DISCONNECT));
				objectInputStream.close();
				objectOutputStream.close();
				socket.close();
				socket = null;
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	
	public void listen()
	{
		if (socket != null && socket.isConnected())
		{
			Thread t = new Thread(new Runnable()
			{
				@Override
				public void run()
				{
					try
					{
						objectInputStream = new ObjectInputStream(socket.getInputStream());
						while (socket != null)
						{
							Message object = (Message) objectInputStream.readObject();
							if (object != null)
							{
								message = object;
								Platform.runLater(new Runnable()
								{
									@Override
									public void run()
									{
										notifyMessageEvent();
									}
								});
							}
						}
					} catch (Exception e)
					{
						e.printStackTrace();
					}
				}
			});
			t.setDaemon(true);
			t.start();
		}
	}
}
