package gui;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import entities.Character;
import entities.Message;
import entities.UserStatusBar;
import enums.MessageType;
import enums.StatusBarType;
import gui.services.ChatService;
import gui.services.InventoryService;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
	@FXML
	private ImageView imgView;

	private UserStatusBar barVida;
	private UserStatusBar barEnergia;
	private UserStatusBar barResistencia;
	private UserStatusBar barSanidade;
	private Client client = new Client();
	private Character character = new Character();
	private ChatService chat;

	public void onTxtMessage()
	{
		chat.sendMessage(txtMessage.getText());
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
			chat.sendMessage("!1d20");
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
			chat.addMessage(client.getMessage().toString());
			break;
		case STATUS:
			if (character.getNome().equalsIgnoreCase(client.getMessage().getCharacter().getNome()))
			{
				character = client.getMessage().getCharacter();
				character.setJSONData();
				updateBars();
			}
			break;
		case PLAYMUSIC:
			PlayMusic.playByName(client.getMessage().toString());
			break;
		case PAUSE:
			PlayMusic.pause();
			break;
		case PLAY:
			PlayMusic.play();
			break;
		case VOLUME:
			PlayMusic.setVolume(client.getMessage().getVolume());
			break;
		case MUSICLOOPING:
			PlayMusic.DURATION = client.getMessage().getDuration();
			break;
		case CONNECT:
			chat.addMessage("[ ! ] " + client.getMessage().getCharacter().getNome() + " conectou-se!");
			break;
		case DISCONNECT:
			if (client.getMessage().getCharacter().getNome().equalsIgnoreCase("Mestre"))
				PlayMusic.DURATION = null;
			chat.addMessage("[ ! ] " + client.getMessage().getCharacter().getNome() + " desconectou-se!");
			break;
		case ADMINREFRESHCONNECTIONS:
			client.sendSocket(new Message(character, MessageType.REFRESHCONNECTIONS));
			break;
		case IMAGE:
			try
			{
				if (client.getMessage().getImageBytes() == null)
				{
					imgView.setImage(null);
					return;
				}
		        BufferedImage image = ImageIO.read(new ByteArrayInputStream(client.getMessage().getImageBytes()));
				imgView.setImage(SwingFXUtils.toFXImage(image, null));
			} catch (IOException e)
			{
				e.printStackTrace();
			}
			break;
		default:
			break;
		}
	}
}
