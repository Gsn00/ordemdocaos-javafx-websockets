package network;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import entities.Message;
import gui.UserScreenController;
import javafx.application.Platform;
import listeners.MessageEvent;
import services.PlayMusic;

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
			socket = new Socket("26.163.199.159", 7000);
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
								switch (object.getMessageType())
								{
								case MESSAGE:
									treatMessage(object);
									break;
								case STATUS:
									treatStatus(object);
									break;
								case METHOD:
									treatMethod(object);
									break;
								case PLAYMUSIC:
									treatPlayMusic(object);
									break;
								}
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

	public void treatMessage(Message object)
	{
		message = object.toString();

		Platform.runLater(new Runnable()
		{
			@Override
			public void run()
			{
				notifyMessageEvent();
			}
		});
	}

	public void treatStatus(Message object)
	{

	}

	public void treatMethod(Message object) throws NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, ClassNotFoundException
	{
		String[] vect = object.getMessage().split(",");
		Class<?> objClass = Class.forName(vect[0]);
		Method method;
		switch (vect.length)
		{
		case 2:
			method = objClass.getMethod(vect[1]);
			method.invoke(UserScreenController.class);
			break;
		case 4:
			Class<?> paramClass = Class.forName(vect[2]);
			method = objClass.getMethod(vect[1], paramClass);
			method.invoke(UserScreenController.class, vect[3]);
			break;
		}
	}
	
	public void treatPlayMusic(Message object)
	{
		PlayMusic.playByName(object.getMessage());
	}
}
