package network;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import entities.Message;
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

	public void connect()
	{
		try
		{
			socket = new Socket("26.163.199.159", 7000);
			objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
		} catch (Exception e)
		{
			System.err.println("Não foi possível conectar ao servidor.");
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

	public void listen()
	{
		if (socket != null)
		{
			Thread t = new Thread(new Runnable()
			{
				@Override
				public void run()
				{
					try
					{
						objectInputStream = new ObjectInputStream(socket.getInputStream());
						while (true)
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
