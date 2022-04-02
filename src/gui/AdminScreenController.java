package gui;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import listeners.MessageEvent;
import network.Client;
import services.PlayMusic;

public class AdminScreenController implements Initializable, MessageEvent
{
	@FXML
	private TilePane tilePane;
	@FXML
	private ListView<String> listView;
	@FXML
	private TextField txtMessage;
	@FXML
	private HBox hbox;
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
	@FXML
	private Circle btNote;
	@FXML
	private ImageView imgView;
	@FXML
	private Button btImgSelect;
	@FXML
	private Button btImgRemove;

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
		Image img2 = new Image(AdminScreenController.class.getResource("/images/refresh.png").toExternalForm());
		btRefresh.setFill(new ImagePattern(img2));
		Image img3 = new Image(AdminScreenController.class.getResource("/images/pen.png").toExternalForm());
		btNote.setFill(new ImagePattern(img3));

		btRefresh.setOnMouseClicked(event ->
		{
			client.sendSocket(new Message(null, MessageType.ADMINREFRESHCONNECTIONS));
		});

		btNote.setOnMouseClicked(event ->
		{
			try
			{
				Stage stage = new Stage();
				stage.initStyle(StageStyle.UNDECORATED);
				stage.initModality(Modality.WINDOW_MODAL);
				stage.initOwner(btNote.getScene().getWindow());
				Parent parent = FXMLLoader.load(AdminScreenController.class.getResource("/gui/Note.fxml"));
				Scene scene = new Scene(parent);
				stage.setScene(scene);
				stage.show();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		});

		btPlay.setOnMouseEntered(mouseEnteredEvent);
		btPause.setOnMouseEntered(mouseEnteredEvent);
		volume.setOnMouseEntered(mouseEnteredEvent);
		btRefresh.setOnMouseEntered(mouseEnteredEvent);
		btNote.setOnMouseEntered(mouseEnteredEvent);
		btPlay.setOnMouseExited(mouseExitedEvent);
		btPause.setOnMouseExited(mouseExitedEvent);
		volume.setOnMouseExited(mouseExitedEvent);
		btRefresh.setOnMouseExited(mouseExitedEvent);
		btNote.setOnMouseExited(mouseExitedEvent);
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
		ObservableList<String> values = FXCollections.observableArrayList(
				Arrays.asList("Combate", "Investigação", "Triste", "Terror", "Boss", "Abertura", "Pausa"));
		choiceSong.setItems(values);

		ObservableList<String> combate = FXCollections.observableArrayList(Arrays.asList("Beat It (Solo Cover).mp3"));

		ObservableList<String> investigacao = FXCollections.observableArrayList(Arrays.asList());

		ObservableList<String> triste = FXCollections
				.observableArrayList(Arrays.asList("O Segredo na Floresta - Final Ending Theme.mp3"));

		ObservableList<String> terror = FXCollections.observableArrayList(Arrays.asList());

		ObservableList<String> boss = FXCollections
				.observableArrayList(Arrays.asList("Final Boss - O Segredo na Floresta.mp3",
						"Beastly Calls - O Segredo na Floresta Ost.mp3", "Pugna.mp3"));

		ObservableList<String> abertura = FXCollections.observableArrayList(Arrays.asList("Chukou.mp3"));

		ObservableList<String> pausa = FXCollections.observableArrayList(Arrays.asList());

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
			case "Pausa":
				tableSong.setItems(pausa);
				break;
			}
		});
	}

	public void onBtImgSelect()
	{
		FileChooser chooser = new FileChooser();
		FileChooser.ExtensionFilter exFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
		FileChooser.ExtensionFilter exFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
		chooser.getExtensionFilters().addAll(exFilterPNG, exFilterJPG);

		File file = chooser.showOpenDialog(null);
		if (file != null)
		{
			try
			{
				Image image = new Image(file.toURI().toString());
				imgView.setImage(image);
				
		        BufferedImage bufferedImage = ImageIO.read(file);
		        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		        int extension = file.getName().lastIndexOf('.');
		        String format = file.getName().substring(extension + 1);
		        ImageIO.write(bufferedImage, format, byteArrayOutputStream);
		        
				client.sendSocket(new Message(byteArrayOutputStream.toByteArray(), MessageType.IMAGE));
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	public void onBtImgRemove()
	{
		imgView.setImage(null);
		client.sendSocket(new Message(null, MessageType.IMAGE));
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1)
	{
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
		client.sendSocket(new Message(null, MessageType.ADMINREFRESHCONNECTIONS));

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
			for (Character c : client.getMessage().getCharacters())
			{
				addCharacter(c);
			}
			break;
		default:
			break;
		}
	}
}