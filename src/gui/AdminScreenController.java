package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
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
import entities.ToggleSwitch;
import entities.TooltippedTableCell;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
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
	private TilePane tilePane;
	@FXML
	private ListView<String> listView;
	@FXML
	private TextField txtMessage;
	@FXML
	private HBox hboxMedia;
	@FXML
	private Circle btPlay;
	@FXML
	private Circle btPause;
	@FXML
	private Slider volume;
	@FXML
	private ComboBox<String> choiceSong;
	@FXML
	private TableView<String> tableSong;
	@FXML
	private TableColumn<String, String> columnItem;
	@FXML
	private TableColumn<String, String> columnButton;
	@FXML
	private Circle btRefresh;

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
			cHBox.setSpacing(10);

			tilePane.getChildren().add(cHBox);

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
		tilePane.getChildren().remove(components.get(nome));
		components.remove(nome);
	}

	public void configCircleButtons()
	{
		EventHandler<Event> mouseEnteredEvent = new EventHandler<Event>()
		{
			@Override
			public void handle(Event event)
			{
				btPlay.getScene().setCursor(Cursor.HAND);
			}
		};
		EventHandler<Event> mouseExitedEvent = new EventHandler<Event>()
		{
			@Override
			public void handle(Event event)
			{
				btPlay.getScene().setCursor(Cursor.DEFAULT);
			}
		};

		Image img = new Image(AdminScreenController.class.getResource("/images/play.png").toExternalForm());
		btPlay.setFill(new ImagePattern(img));
		Image img1 = new Image(AdminScreenController.class.getResource("/images/pause.png").toExternalForm());
		btPause.setFill(new ImagePattern(img1));
		Image im2 = new Image(AdminScreenController.class.getResource("/images/refresh.png").toExternalForm());
		btRefresh.setFill(new ImagePattern(im2));

		btRefresh.setOnMouseClicked(event ->
		{
			client.sendSocket(new Message(null, MessageType.REFRESHCONNECTIONS));
		});

		btPlay.setOnMouseEntered(mouseEnteredEvent);
		btPause.setOnMouseEntered(mouseEnteredEvent);
		volume.setOnMouseEntered(mouseEnteredEvent);
		btRefresh.setOnMouseEntered(mouseEnteredEvent);
		btPlay.setOnMouseExited(mouseExitedEvent);
		btPause.setOnMouseExited(mouseExitedEvent);
		volume.setOnMouseExited(mouseExitedEvent);
		btRefresh.setOnMouseExited(mouseExitedEvent);
	}

	private void configVolume()
	{
		volume.setValue(PlayMusic.VOLUME * 100);

		volume.valueProperty().addListener(new ChangeListener<Number>()
		{
			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number oldValue, Number newValue)
			{
				Double volume = (Double) newValue / 100;
				PlayMusic.setVolume(volume);
				client.sendSocket(new Message(volume, MessageType.VOLUME));
			}
		});
	}

	public void configMedia()
	{
		btPlay.setOnMouseClicked(event ->
		{
			PlayMusic.play();
			client.sendSocket(new Message(null, MessageType.PLAY));
		});
		btPause.setOnMouseClicked(event ->
		{
			PlayMusic.pause();
			client.sendSocket(new Message(null, MessageType.PAUSE));
		});
	}

	public void configTable()
	{

		columnItem.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		columnItem.setCellFactory(TooltippedTableCell.forTableColumn());
		columnButton.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		columnButton.setCellFactory(param -> new TableCell<String, String>()
		{
			@Override
			protected void updateItem(String item, boolean empty)
			{
				super.updateItem(item, empty);
				if (item == null)
				{
					setGraphic(null);
					return;
				}
				Button bt = new Button("Play");
				bt.setStyle("-fx-background-color:  #4f4f4f; -fx-text-fill: white");
				bt.setOpacity(0.4);
				int tableItem = getTableRow().getIndex();
				bt.setOnMouseEntered(event ->
				{
					bt.getScene().setCursor(Cursor.HAND);
				});
				bt.setOnMouseExited(event ->
				{
					bt.getScene().setCursor(Cursor.DEFAULT);
				});
				bt.setOnAction(e ->
				{
					PlayMusic.playByName(tableSong.getItems().get(tableItem));
					client.sendSocket(new Message(tableSong.getItems().get(tableItem), MessageType.PLAYMUSIC));
					tableSong.setVisible(false);
					tableSong.setVisible(true);
				});
				setGraphic(bt);
				setAlignment(Pos.CENTER);
			}
		});

		tableSong.setPlaceholder(new Label(""));
	}

	public void configChoiceSong()
	{
		ObservableList<String> values = FXCollections.observableArrayList(Arrays.asList("Combate", "Investigação", "Triste", "Terror", "Boss", "Abertura"));
		choiceSong.setItems(values);
		
		ObservableList<String> combate = FXCollections.observableArrayList(Arrays.asList("som1.mp3", "som2.mp3", "som3.mp3"));
		
		ObservableList<String> investigacao = FXCollections.observableArrayList(Arrays.asList());
		
		ObservableList<String> triste = FXCollections.observableArrayList(Arrays.asList("O Segredo na Floresta - Final Ending Theme.mp3"));
		
		ObservableList<String> terror = FXCollections.observableArrayList(Arrays.asList("Beastly Calls - O Segredo na Floresta Ost.mp3"));
		
		ObservableList<String> boss = FXCollections.observableArrayList(Arrays.asList("Final Boss - O Segredo na Floresta.mp3"));
		
		ObservableList<String> abertura = FXCollections.observableArrayList(Arrays.asList("Chukou.mp3"));

		choiceSong.setOnAction(event ->
		{
			switch (choiceSong.getSelectionModel().getSelectedItem())
			{
			case "Combate":
				tableSong.setItems(combate);
				break;
			case "Investigação":
				tableSong.setItems(investigacao);
				break;
			case "Triste":
				tableSong.setItems(triste);
				break;
			case "Terror":
				tableSong.setItems(terror);
				break;
			case "Boss":
				tableSong.setItems(boss);
				break;
			case "Abertura":
				tableSong.setItems(abertura);
				break;
			}
		});
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1)
	{
		Font popZero = Font.loadFont(getClass().getResourceAsStream("/fonts/Populationzerobb.otf"), 64);
		lblOrdemDoCaos.setFont(popZero);

		configCircleButtons();

		hboxMedia.getChildren().add(new ToggleSwitch(client));
		configVolume();
		configMedia();
		configTable();
		configChoiceSong();

		client.subscribeMessageEvent(this);
		client.connect();
		client.listen();
		client.sendSocket(new Message(master, MessageType.CONNECT));
		client.sendSocket(new Message(null, MessageType.REFRESHCONNECTIONS));

		chat = new ChatService(txtMessage, listView, master, client);
	}

	@Override
	public void onMessage()
	{
		switch (client.getMessage().getMessageType())
		{
		case MESSAGE:
			chat.addMessage(client.getMessage().toString());
			break;
		case STATUS:
			updateBars(client.getMessage().getCharacter());
			break;
		case PLAYMUSIC:
			PlayMusic.playByName(client.getMessage().toString());
			break;
		case CONNECT:
			addCharacter(client.getMessage().getCharacter());
			chat.addMessage("[ ! ] " + client.getMessage().getCharacter().getNome() + " conectou-se!");
			break;
		case DISCONNECT:
			disconnect(client.getMessage().getCharacter().getNome());
			chat.addMessage("[ ! ] " + client.getMessage().getCharacter().getNome() + " desconectou-se!");
			break;
		case REFRESHCONNECTIONS:
			addCharacter(client.getMessage().getCharacter());
			break;
		default:
			break;
		}
	}
}
