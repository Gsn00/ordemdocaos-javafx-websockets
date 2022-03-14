package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import entities.Character;
import entities.Message;
import entities.UserStatusBar;
import enums.MessageType;
import enums.StatusBarType;
import gui.services.InventoryService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
	private Character c = new Character();

	private ObservableList<String> obsMsg = FXCollections.observableArrayList();

	public void onTxtMessage()
	{
		createMessage(txtMessage.getText());
	}

	public void createMessage(String msg)
	{
		if (!msg.trim().isEmpty())
		{
			if (!(msg.charAt(0) == '!')) sendMessage(msg);
			if (msg.charAt(0) == '!')
			{
				// Just for test
				if (msg.equalsIgnoreCase("!admin"))
				{
					try
					{
						Parent parent = FXMLLoader.load(UserScreenController.class.getResource("/gui/AdminScreen.fxml"));
						Scene scene = txtMessage.getScene();
						scene.setRoot(parent);
						return;
					} catch (IOException e)
					{
						e.printStackTrace();
					}
				}
				if (msg.contains("d"))
				{
					try
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

						sendMessage(result);
					} catch (Exception e)
					{
						sendMessage(msg);
					}
				}
			}
		}
	}

	public void sendMessage(String msg)
	{
		obsMsg.add(c.getNome() + ": " + msg);
		listView.setItems(obsMsg);
		client.sendSocket(new Message((c.getNome() + ": " + msg), MessageType.MESSAGE));
		txtMessage.setText("");
		listView.scrollTo(obsMsg.size());
	}

	public void addMessage(String msg)
	{
		obsMsg.add(msg);
		listView.setItems(obsMsg);
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
			createMessage("!1d20");
		});
	}
	
	public void updateBars()
	{
		for (UserStatusBar bar : Arrays.asList(barVida, barEnergia, barResistencia, barSanidade))
		{
			bar.setCharacter(c);
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

		barVida = new UserStatusBar("#b22626", c, StatusBarType.VIDA, client);
		barEnergia = new UserStatusBar("#f2f531", c, StatusBarType.ENERGIA, client);
		barResistencia = new UserStatusBar("#f79d25", c, StatusBarType.RESISTENCIA, client);
		barSanidade = new UserStatusBar("#27a6b0", c, StatusBarType.SANIDADE, client);
		
		vboxSatusBar.getChildren().addAll(barVida, barEnergia, barResistencia, barSanidade);
		
		lblJogador.setText(c.getJogador());
		txtNome.setText(c.getNome());
		txtOcupacao.setText(c.getOcupacao());
		txtIdade.setText(c.getIdade());
		txtSexo.setText(c.getSexo());
		txtLocalNascimento.setText(c.getLocalNascimento());

		new InventoryService(tbItems, txtItems, columnItem, columnButton);

		configListView();
		configCircleButtons();

		client.subscribeMessageEvent(this);
		client.connect();
		client.listen();
		client.sendSocket(new Message("[ ! ] " + c.getNome() + " conectou-se!", MessageType.MESSAGE));
		client.sendSocket(new Message(c, MessageType.CONNECT));
	}

	@Override
	public void onMessage()
	{
		switch (client.getMessage().getMessageType())
		{
		case MESSAGE:
			addMessage(client.getMessage().toString());
			break;
		case STATUS:
			c = client.getMessage().getCharacter();
			c.setJSONData();
			updateBars();
			break;
		case METHOD:
			
			break;
		case PLAYMUSIC:
			
			break;
		case CONNECT:
			
			break;
		case DISCONNECT:
			
			break;
		}
	}
}
