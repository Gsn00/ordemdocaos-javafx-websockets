package gui;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import entities.Character;
import entities.Message;
import entities.UserStatusBar;
import enums.MessageType;
import enums.StatusBarType;
import gui.services.ChatService;
import gui.services.InventoryService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import listeners.MessageEvent;
import network.Client;
import services.JSONService;
import services.PlayMusic;

public class UserScreenController implements Initializable, MessageEvent
{

	@FXML
	private Label lblOrdemDoCaos;
	@FXML
	private Circle circle;
	@FXML
	private ListView<String> listView;
	@FXML
	private TextField txtMessage;
	@FXML
	private Label lblJogador;
	@FXML
	private TextField txtNome;
	@FXML
	private TextField txtOcupacao;
	@FXML
	private TextField txtIdade;
	@FXML
	private TextField txtSexo;
	@FXML
	private TextField txtLocalNascimento;
	@FXML
	private Circle btInventory;
	@FXML
	private VBox vboxInventory;
	@FXML
	private TableView<String> tbItems;
	@FXML
	private TextField txtItems;
	@FXML
	private TableColumn<String, String> columnItem;
	@FXML
	private TableColumn<String, String> columnButton;
	@FXML
	private Circle btD20;
	@FXML
	private VBox vboxSatusBar;

	private UserStatusBar barVida;
	private UserStatusBar barEnergia;
	private UserStatusBar barResistencia;
	private UserStatusBar barSanidade;
	private Client client = new Client();
	private Character character = new Character();
	private ChatService chat;

	private ObservableList<String> obsMsg = FXCollections.observableArrayList();

	public void onTxtMessage()
	{
		chat.sendMessage(txtMessage.getText());
	}

	public void sendMessage(String msg)
	{
		obsMsg.add(character.getNome() + ": " + msg);
		listView.setItems(obsMsg);
		client.sendSocket(new Message((character.getNome() + ": " + msg), MessageType.MESSAGE));
		txtMessage.setText("");
		listView.scrollTo(obsMsg.size());
	}

	private void configListView()
	{
		listView.setCellFactory(param -> new ListCell<String>()
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

	public void configCircleButtons()
	{
		List<Circle> circles = Arrays.asList(btInventory, btD20);
		for (Circle c : circles)
		{
			c.setOnMouseEntered(event ->
			{
				c.getScene().setCursor(Cursor.HAND);
			});
			c.setOnMouseExited(event ->
			{
				c.getScene().setCursor(Cursor.DEFAULT);
			});
		}
		btInventory.setOnMouseClicked(event ->
		{
			if (!vboxInventory.isVisible())
			{
				vboxInventory.setVisible(true);
				btInventory.setOpacity(0.7);
			} else
			{
				vboxInventory.setVisible(false);
				btInventory.setOpacity(0.4);
			}
		});
		btD20.setOnMouseClicked(event ->
		{
			chat.sendToMe("!1d20");
		});
	}

	public void updateBars()
	{
		for (UserStatusBar bar : Arrays.asList(barVida, barEnergia, barResistencia, barSanidade))
		{
			bar.setCharacter(character);
			bar.updateBar();
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1)
	{
		Image img = new Image(getClass().getResource((String) JSONService.getData("imgUrl")).toExternalForm());
		circle.setFill(new ImagePattern(img));
		Image img2 = new Image(getClass().getResource("/images/Backpack.png").toExternalForm());
		btInventory.setFill(new ImagePattern(img2));
		Image img3 = new Image(getClass().getResource("/images/dice.png").toExternalForm());
		btD20.setFill(new ImagePattern(img3));
		Font popZero = Font.loadFont(getClass().getResourceAsStream("/fonts/Populationzerobb.otf"), 64);
		lblOrdemDoCaos.setFont(popZero);

		barVida = new UserStatusBar("#b22626", character, StatusBarType.VIDA, client);
		barEnergia = new UserStatusBar("#f2f531", character, StatusBarType.ENERGIA, client);
		barResistencia = new UserStatusBar("#f79d25", character, StatusBarType.RESISTENCIA, client);
		barSanidade = new UserStatusBar("#27a6b0", character, StatusBarType.SANIDADE, client);
		vboxSatusBar.getChildren().addAll(barVida, barEnergia, barResistencia, barSanidade);

		lblJogador.setText(character.getJogador());
		txtNome.setText(character.getNome());
		txtOcupacao.setText(character.getOcupacao());
		txtIdade.setText(character.getIdade());
		txtSexo.setText(character.getSexo());
		txtLocalNascimento.setText(character.getLocalNascimento());

		new InventoryService(tbItems, txtItems, columnItem, columnButton);

		configListView();
		configCircleButtons();

		client.subscribeMessageEvent(this);
		client.connect();
		client.listen();
		client.sendSocket(new Message(character, MessageType.CONNECT));
		
		chat = new ChatService(txtMessage, listView, character, client);
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
			character = client.getMessage().getCharacter();
			character.setJSONData();
			updateBars();
			break;
		case PLAYMUSIC:
			PlayMusic.playByName(client.getMessage().toString());
			break;
		case CONNECT:
			chat.sendToMe("[ ! ] " + client.getMessage().getCharacter().getNome() + " conectou-se!");
			break;
		case DISCONNECT:
			chat.sendToMe("[ ! ] " + client.getMessage().toString() + " desconectou-se!");
			break;
		}
	}
}
