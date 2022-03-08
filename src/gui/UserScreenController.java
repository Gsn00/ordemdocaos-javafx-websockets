package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import entities.Message;
import entities.StatusBar;
import enums.MessageType;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.util.Duration;
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
	private TextField txtMsg;
	@FXML
	private Button btAddVida;
	@FXML
	private Button btAddEnergia;
	@FXML
	private Button btAddResistencia;
	@FXML
	private Button btAddSanidade;
	@FXML
	private Rectangle rectVida;
	@FXML
	private Rectangle rectEnergia;
	@FXML
	private Rectangle rectResistencia;
	@FXML
	private Rectangle rectSanidade;
	@FXML
	private Label lblVida;
	@FXML
	private Label lblEnergia;
	@FXML
	private Label lblResistencia;
	@FXML
	private Label lblSanidade;
	
	private StatusBar barVida;
	private StatusBar barEnergia;
	private StatusBar barResistencia;
	private StatusBar barSanidade;
	
	private Client client = new Client();

	private String name = (String) JSONService.getData("nome");

	private ObservableList<String> obsMsg = FXCollections.observableArrayList();

	public void onTxtMsg()
	{
		if (txtMsg.getText().length() != 0)
		{
			if (txtMsg.getText().charAt(0) == '!')
			{
				String msg = txtMsg.getText().toLowerCase();

				if (msg.contains("d"))
				{
					try
					{
						// Check if contains a value to sum
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

						// Roll the dices
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

						// Sum the final result
						int sum = 0;
						for (Integer i : values)
						{
							sum += i;
						}
						result += "\nTotal: ( " + (sum + addition) + " )";
						result = name + ":\n" + result;

						client.sendSocket(new Message(result, MessageType.MESSAGE));
						sendMessage(result);
					} catch (NumberFormatException e)
					{
						sendMessage(name + ": " + msg);
					}
				}

				txtMsg.setText("");
				return;
			}

			client.sendSocket(new Message((name + ": " + txtMsg.getText()), MessageType.MESSAGE));
			sendMessage(name + ": " + txtMsg.getText());
		}
	}
	
	public void onBtAddVida()
	{
		barVida.incrementMaxValue();
		updateBars();
	}
	
	public void onBtAddEnergia()
	{
		barEnergia.incrementMaxValue();
		updateBars();
	}
	
	public void onBtAddResistencia()
	{
		barResistencia.incrementMaxValue();
		updateBars();
	}
	
	public void onBtAddSanidade()
	{
		barSanidade.incrementMaxValue();
		updateBars();
	}

	public void sendMessage(String msg)
	{
		obsMsg.add(msg);
		listView.setItems(obsMsg);
		txtMsg.setText("");
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

	public void configButtons()
	{
		List<Button> buttons = Arrays.asList(btAddVida, btAddEnergia, btAddResistencia, btAddSanidade);
		for (Button bt : buttons)
		{
			bt.setOnMouseEntered(event ->
			{
				bt.setStyle("-fx-background-color: #424242");
				bt.getScene().setCursor(Cursor.HAND);
			});
			bt.setOnMouseExited(event ->
			{
				bt.setStyle("-fx-background-color: #383838; -fx-background-radius: 15");
				bt.getScene().setCursor(Cursor.DEFAULT);
			});
		}
	}

	public double parsePercent(double current, double max)
	{
		if (current > max)
		{
			current = max;
		}
		if (current < 0)
		{
			current = 0;
		}
		return (current / max * 100);
	}
	
	public int filterValue(double current, double max)
	{
		if (current > max)
		{
			return (int) max;
		}
		if (current < 0)
		{
			return 0;
		}
		return (int) current;
	}
	
	public void updateBars()
	{
		barVida = new StatusBar(rectVida, lblVida, "vida", "maxVida");
		barEnergia = new StatusBar(rectEnergia, lblEnergia, "energia", "maxEnergia");
		barResistencia = new StatusBar(rectResistencia, lblResistencia, "resistencia", "maxResistencia");
		barSanidade = new StatusBar(rectSanidade, lblSanidade, "sanidade", "maxSanidade");
		List<StatusBar> bars = Arrays.asList(barVida, barEnergia, barResistencia, barSanidade);
		for (StatusBar bar : bars)
		{
			bar.getLabel().setText(bar.getValue() + "/" + bar.getMaxValue());
			
			KeyValue width = new KeyValue(bar.getRectangle().widthProperty(), parsePercent(bar.getValue(), bar.getMaxValue()));
			KeyFrame frame = new KeyFrame(Duration.seconds(0.5), width);
			Timeline timeLine = new Timeline(frame);
			timeLine.play();
			
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1)
	{
		Image img = new Image(getClass().getResource("/images/Screenshot_1.png").toExternalForm());
		circle.setFill(new ImagePattern(img));
		Font popZero = Font.loadFont(getClass().getResourceAsStream("/fonts/Populationzerobb.otf"), 64);
		lblOrdemDoCaos.setFont(popZero);

		configListView();
		configButtons();
		updateBars();

		client.subscribeMessageEvent(this);
		client.connect();
		client.listen();
	}

	@Override
	public void onMessage()
	{
		sendMessage(client.getMessage());
	}
}
