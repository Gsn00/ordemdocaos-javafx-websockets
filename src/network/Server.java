package network;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

import entities.Message;

public class Server extends Thread
{
	private static ServerSocket server;
	private ObjectInputStream objectInputStream;
	private ObjectOutputStream objectOutputStream;

	public static Set<ObjectOutputStream> players = new HashSet<>();

	public Server(Socket socket)
	{
		System.out.println("New player connected! " + socket.getLocalSocketAddress());
		try
		{
			objectInputStream = new ObjectInputStream(socket.getInputStream());
			objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
			players.add(objectOutputStream);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void run()
	{
		try
		{
			while (players.contains(objectOutputStream))
			{
				Message object = (Message) objectInputStream.readObject();
				if (object != null)
				{
					System.out.println("Server listen 1");
					sendToAll(objectOutputStream, object);
					System.out.println("Server listen 2");
				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
			players.remove(objectOutputStream);
		}
	}

	public void sendToAll(ObjectOutputStream socket, Message object)
	{
		ObjectOutputStream newClient;
		try
		{
			for (ObjectOutputStream client : players)
			{
				newClient = client;
				if (socket != newClient)
				{
					System.out.println("Server sendToAll 1");
					client.writeObject(object);
					System.out.println("Server sendToAll 2");
				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void startServer()
	{
		try
		{
			server = new ServerSocket(7000);

			while (true)
			{
				Thread thread = new Server(server.accept());
				thread.start();
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void main(String[] args)
	{
		startServer();
	}
}
