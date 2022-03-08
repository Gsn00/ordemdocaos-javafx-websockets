package network;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Platform;
import listeners.MessageEvent;

public class Client
{
	public Socket socket;
	public String message;
	private ObjectInputStream objectInputStream;
	private ObjectOutputStream objectOutputStream;

	private List<MessageEvent> messages = new ArrayList<>();

	public String getMessage()
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
			socket = new Socket("127.0.0.1", 7000);
			objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
		} catch (Exception e)
		{
			System.err.println("Não foi possível conectar ao servidor.");
		}
	}
	
	public void disconnect()
	{
		try
		{
			objectOutputStream.close();
			objectInputStream.close();
			socket.close();
			Server.players.remove(objectOutputStream);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void sendSocket(Message object)
	{
		if (socket != null)
		{
			try
			{
				System.out.println("Client sendSocket 1");
				objectOutputStream.writeObject(object);
				System.out.println("Client sendSocket 2");
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
						System.out.println("S");
						while (true)
						{
							Message object = (Message) objectInputStream.readObject();
							if (object != null)
							{
								System.out.println("Client listen 1");
								message = object.toString();

								Platform.runLater(new Runnable()
								{
									@Override
									public void run()
									{
										System.out.println("Client listen 2");
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
