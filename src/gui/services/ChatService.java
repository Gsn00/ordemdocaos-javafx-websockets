package gui.services;

import java.util.ArrayList;
import java.util.List;

import entities.Character;
import entities.Message;
import enums.MessageType;
import gui.UserScreenController;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import network.Client;

public class ChatService
{
	private TextField txt;
	private ListView<String> list;
	private Character character;
	private Client client;
	private UserScreenController userScreen;
	
	private ObservableList<String> messages = FXCollections.observableArrayList();

	public ChatService(TextField txt, ListView<String> list, Character character, Client client)
	{
		this.txt = txt;
		this.list = list;
		this.character = character;
		this.client = client;
		
		configListView();
	}
	
	public ChatService(TextField txt, ListView<String> list, Character character, Client client, UserScreenController userScreen)
	{
		this.txt = txt;
		this.list = list;
		this.character = character;
		this.client = client;
		this.userScreen = userScreen;
		
		configListView();
	}

	public void sendMessage(String msg)
	{
		if (!msg.trim().isEmpty())
		{
			if (msg.charAt(0) == '!')
			{
				try
				{
					msg = msg.toLowerCase();

					if (msg.equalsIgnoreCase("!commands"))
					{
						sendToMe("!V, !E, !R, !S, !ADMIN, !DISCONNECT, !CONNECT, !EXIT, !COMMANDS, !<Number>D<Number>");
						return;
					}
					
					if (msg.startsWith("!v"))
					{
						String[] vect = msg.split(" ");
						if (vect.length == 1)
						{
							sendToMe("[ ! ] Ex: !v +10 / !v -10");
							return;
						}
						if (vect.length > 1)
						{
							int quantity = Integer.parseInt(vect[1].substring(1));
							if (vect[1].startsWith("+"))
							{
								character.setVida(StatusService.incrementValue(quantity, character.getVida(), character.getMaxVida()));
								sendToMe("[ ! ] Sua vida foi aumentada em " + quantity);
							}
							if (vect[1].startsWith("-"))
							{
								character.setVida(StatusService.decrementValue(quantity, character.getVida(), character.getMaxVida()));
								sendToMe("[ ! ] Sua vida foi diminuida em " + quantity);
							}
							character.setJSONData();
							userScreen.updateBars();
						}
						return;
					}
					
					if (msg.startsWith("!e"))
					{
						String[] vect = msg.split(" ");
						if (vect.length == 1)
						{
							sendToMe("[ ! ] Ex: !e +10 / !e -10");
							return;
						}
						if (vect.length > 1)
						{
							int quantity = Integer.parseInt(vect[1].substring(1));
							if (vect[1].startsWith("+"))
							{
								character.setEnergia(StatusService.incrementValue(quantity, character.getEnergia(), character.getMaxEnergia()));
								sendToMe("[ ! ] Sua energia foi aumentada em " + quantity);
							}
							if (vect[1].startsWith("-"))
							{
								character.setEnergia(StatusService.decrementValue(quantity, character.getEnergia(), character.getMaxEnergia()));
								sendToMe("[ ! ] Sua energia foi diminuida em " + quantity);
							}
							character.setJSONData();
							userScreen.updateBars();
						}
						return;
					}
					
					if (msg.startsWith("!r"))
					{
						String[] vect = msg.split(" ");
						if (vect.length == 1)
						{
							sendToMe("[ ! ] Ex: !r +10 / !r -10");
							return;
						}
						if (vect.length > 1)
						{
							int quantity = Integer.parseInt(vect[1].substring(1));
							if (vect[1].startsWith("+"))
							{
								character.setResistencia(StatusService.incrementValue(quantity, character.getResistencia(), character.getMaxResistencia()));
								sendToMe("[ ! ] Sua resistência foi aumentada em " + quantity);
							}
							if (vect[1].startsWith("-"))
							{
								character.setResistencia(StatusService.decrementValue(quantity, character.getResistencia(), character.getMaxResistencia()));
								sendToMe("[ ! ] Sua resistência foi diminuida em " + quantity);
							}
							character.setJSONData();
							userScreen.updateBars();
						}
						return;
					}
					
					if (msg.startsWith("!s"))
					{
						String[] vect = msg.split(" ");
						if (vect.length == 1)
						{
							sendToMe("[ ! ] Ex: !s +10 / !s -10");
							return;
						}
						if (vect.length > 1)
						{
							int quantity = Integer.parseInt(vect[1].substring(1));
							if (vect[1].startsWith("+"))
							{
								character.setSanidade(StatusService.incrementValue(quantity, character.getSanidade(), character.getMaxSanidade()));
								sendToMe("[ ! ] Sua sanidade foi aumentada em " + quantity);
							}
							if (vect[1].startsWith("-"))
							{
								character.setSanidade(StatusService.decrementValue(quantity, character.getSanidade(), character.getMaxSanidade()));
								sendToMe("[ ! ] Sua sanidade foi diminuida em " + quantity);
							}
							character.setJSONData();
							userScreen.updateBars();
						}
						return;
					}
					
					if (msg.equalsIgnoreCase("!exit"))
					{
						client.disconnect(character);
						Platform.exit();
						System.exit(0);
						return;
					}
					
					if (msg.equalsIgnoreCase("!connect"))
					{
						if (client.socket != null && client.socket.isConnected())
						{
							sendToMe("[ ! ] Você já está conectado!");
							return;
						}
						if (client.connect())
						{
							client.sendSocket(new Message(character, MessageType.CONNECT));
							sendToMe("[ ! ] Conectado com sucesso!");
							client.listen();
							return;
						} else {
							sendToMe("[ ! ] Não foi possível conectar ao servidor.");
							return;
						}
					}
					
					if (msg.equalsIgnoreCase("!disconnect"))
					{
						if (client.socket != null && client.socket.isConnected())
						{
							client.disconnect(character);
							sendToMe("[ ! ] Desconectado...");
							return;
						}
						sendToMe("[ ! ] Você não está conectado em nenhum servidor.");
						return;
					}
					
					if (msg.equalsIgnoreCase("!admin"))
					{
						client.sendSocket(new Message(character, MessageType.DISCONNECT));
						Parent parent = FXMLLoader.load(UserScreenController.class.getResource("/gui/AdminScreen.fxml"));
						Scene scene = txt.getScene();
						scene.setRoot(parent);
						return;
					}

					if (msg.contains("d"))
					{
						int addition = 0;
						if (msg.contains("+"))
						{
							addition = msg.indexOf('+');
							if (addition != -1)
							{
								addition = Integer.parseInt(msg.substring(addition, msg.length()));
								String[] vect = msg.split(" ");
								msg = vect[0];
							}
						}

						int D = msg.indexOf('d');
						int quantityDices = Integer.parseInt(msg.substring(1, D));
						int dice = Integer.parseInt(msg.substring((D + 1), msg.length()));

						String result = "Resultado: ( ";
						List<Integer> values = new ArrayList<>();

						for (int i = 0; i < quantityDices; i++)
						{
							int value = (int) ((Math.random() * dice) + 1);
							values.add(value);
							result += value + " ";
						}
						result += ")";

						if (addition != 0)
						{
							result += (" +" + addition);
						}

						int sum = 0;
						for (Integer i : values)
						{
							sum += i;
						}
						result += "\nTotal: ( " + (sum + addition) + " )";
						result = quantityDices + "D" + dice + "\n" + result;

						sendToAll(result);
						return;
					}
				} catch (Exception e)
				{
					e.printStackTrace();
					sendToAll(msg);
					return;
				}
			}
			sendToAll(msg);
		}
	}

	private void sendToAll(String msg)
	{
		addMessage("Você: " + msg);
		client.sendSocket(new Message(character.getNome() + ": " + msg, MessageType.MESSAGE));
		txt.setText("");
		list.scrollTo(messages.size());
	}

	public void sendToMe(String msg)
	{
		messages.add(msg);
		list.setItems(messages);
		list.scrollTo(messages.size());
		txt.setText("");
	}
	
	public void addMessage(String msg)
	{
		messages.add(msg);
		list.setItems(messages);
		list.scrollTo(messages.size());
	}
	
	private void configListView()
	{
		list.setCellFactory(param -> new ListCell<String>()
		{
			@Override
			protected void updateItem(String item, boolean empty)
			{
				super.updateItem(item, empty);
				if (empty || item == null)
				{
					setGraphic(null);
					setText(null);
					return;
				}
				setMinWidth(param.getWidth() - 40);
				setPrefWidth(param.getWidth() - 40);
				setMaxWidth(param.getWidth() - 40);
				setWrapText(true);
				setText(item);
				setStyle("-fx-font: 14 arial");
			};
		});
	}
}
