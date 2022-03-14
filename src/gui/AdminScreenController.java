package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import entities.AdminStatusBar;
import enums.MessageType;
import enums.StatusBarType;
import gui.services.ChatService;
import entities.Character;
import entities.Message;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import listeners.MessageEvent;
import network.Client;
import services.PlayMusic;

public class AdminScreenController implements Initializable, MessageEvent
{
	@FXML
	private Label lblOrdemDoCaos;
	@FXML
	private HBox hbox;
	@FXML
	private ListView<String> listView;
	@FXML
	private TextField txtMessage;
	
	private Character master = new Character("Mestre");
	private Client client = new Client();
	private ChatService chat;

	private static Map<String, HBox> components = new HashMap<>();
	private static List<AdminStatusBar> bars = new ArrayList<>();

	public void onTxtMessage()
	{
		chat.sendMessage(txtMessage.getText());
	}

	public void addCharacter(Character character)
	{
		if (!components.containsKey(character.getNome()))
		{
			HBox cHBox = new HBox();
			Circle cCircle = new Circle(50);
			VBox cVBox = new VBox();
			Label cLabel = new Label(character.getNome());

			cCircle.setFill(
					new ImagePattern(new Image(getClass().getResource(character.getImgUrl()).toExternalForm())));
			cCircle.setStrokeType(StrokeType.OUTSIDE);
			cCircle.setStroke(Color.BLACK);

			cLabel.setFont(Font.font("Bodoni MT", 22));
			cLabel.setTextFill(Color.WHITE);

			AdminStatusBar barVida = new AdminStatusBar("#b22626", character, StatusBarType.VIDA, client);
			barVida.setPadding(new Insets(0, 0, 0, 30));
			bars.add(barVida);
			AdminStatusBar barEnergia = new AdminStatusBar("#f2f531", character, StatusBarType.ENERGIA, client);
			barEnergia.setPadding(new Insets(0, 0, 0, 16));
			bars.add(barEnergia);
			AdminStatusBar barResistencia = new AdminStatusBar("#f79d25", character, StatusBarType.RESISTENCIA, client);
			barResistencia.setPadding(new Insets(0, 0, 0, 2));
			bars.add(barResistencia);
			AdminStatusBar barSanidade = new AdminStatusBar("#27a6b0", character, StatusBarType.SANIDADE, client);
			barSanidade.setPadding(new Insets(0, 0, 0, -12));
			bars.add(barSanidade);

			cVBox.setSpacing(5);

			cVBox.getChildren().add(cLabel);
			cVBox.getChildren().add(barVida);
			cVBox.getChildren().add(barEnergia);
			cVBox.getChildren().add(barResistencia);
			cVBox.getChildren().add(barSanidade);

			cHBox.getChildren().add(cCircle);
			cHBox.getChildren().add(cVBox);
			cHBox.setAlignment(Pos.CENTER);
			cHBox.setMaxHeight(120);

			hbox.getChildren().add(cHBox);

			components.put(character.getNome(), cHBox);
		}
	}

	public void updateBars(Character character)
	{
		for (AdminStatusBar bar : bars)
		{
			if (bar.getCharacter().getNome().equalsIgnoreCase(character.getNome()))
			{
				bar.setCharacter(character);
				bar.updateBar();
			}
		}
	}

	public void disconnect(String nome)
	{
		hbox.getChildren().remove(components.get(nome));
		components.remove(nome);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1)
	{
		Font popZero = Font.loadFont(getClass().getResourceAsStream("/fonts/Populationzerobb.otf"), 64);
		lblOrdemDoCaos.setFont(popZero);
		
		client.subscribeMessageEvent(this);
		client.connect();
		client.listen();
		client.sendSocket(new Message(master, MessageType.CONNECT));
		
		chat = new ChatService(txtMessage, listView, master, client);
	}

	@Override
	public void onMessage()
	{
		switch (client.getMessage().getMessageType())
		{
		case MESSAGE:
			chat.sendToMe(client.getMessage().toString());
			break;
		case STATUS:
			updateBars(client.getMessage().getCharacter());
			break;
		case PLAYMUSIC:
			PlayMusic.playByName(client.getMessage().toString());
			break;
		case CONNECT:
			addCharacter(client.getMessage().getCharacter());
			break;
		case DISCONNECT:
			disconnect(client.getMessage().toString());
			break;
		}
	}
}
