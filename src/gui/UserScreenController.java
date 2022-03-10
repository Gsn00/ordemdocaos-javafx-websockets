package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import entities.Character;
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
import services.Utils;

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
	private Button btRemVida;
	@FXML
	private Button btRemEnergia;
	@FXML
	private Button btRemResistencia;
	@FXML
	private Button btRemSanidade;
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
	
	private StatusBar barVida;
	private StatusBar barEnergia;
	private StatusBar barResistencia;
	private StatusBar barSanidade;

	private Client client = Main.client;
	private Character c = new Character();

	private ObservableList<String> obsMsg = FXCollections.observableArrayList();

	public void onTxtMsg()
	{
		if (txtMsg.getText().length() != 0)
		{
			if (txtMsg.getText().charAt(0) == '!')
			{
				String msg = txtMsg.getText().toLowerCase();
				if (!msg.contains("d"))
					sendMessage(msg);
				if (msg.contains("d"))
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
					result = quantityDices + "D" + dice + "\n" + result;

					sendMessage(result);
				}
				return;
			}
			sendMessage(txtMsg.getText());
		}
	}

	public void onBtAddVida()
	{
		barVida.incrementValue();
		updateBars();
	}

	public void onBtAddEnergia()
	{
		barEnergia.incrementValue();
		updateBars();
	}

	public void onBtAddResistencia()
	{
		barResistencia.incrementValue();
		updateBars();
	}

	public void onBtAddSanidade()
	{
		barSanidade.incrementValue();
		updateBars();
	}

	public void onBtRemVida()
	{
		barVida.decrementValue();
		updateBars();
	}

	public void onBtRemEnergia()
	{
		barEnergia.decrementValue();
		updateBars();
	}

	public void onBtRemResistencia()
	{
		barResistencia.decrementValue();
		updateBars();
	}

	public void onBtRemSanidade()
	{
		barSanidade.decrementValue();
		updateBars();
	}
	
	public void sendMessage(String msg)
	{
		obsMsg.add(c.getNome() + ": " + msg);
		listView.setItems(obsMsg);
		client.sendSocket(new Message((c.getNome() + ": " + msg), MessageType.MESSAGE));
		txtMsg.setText("");
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

	public void configButtons()
	{
		List<Button> buttons = Arrays.asList(btAddVida, btAddEnergia, btAddResistencia, btAddSanidade,
				btRemVida, btRemEnergia, btRemResistencia, btRemSanidade);
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
			KeyValue width = new KeyValue(bar.getRectangle().widthProperty(), Utils.parsePercent(bar.getValue(), bar.getMaxValue()));
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

		lblJogador.setText(c.getJogador());
		txtNome.setText(c.getNome());
		txtOcupacao.setText(c.getOcupacao());
		txtIdade.setText(c.getIdade());
		txtSexo.setText(c.getSexo());
		txtLocalNascimento.setText(c.getLocalNascimento());
		
		configListView();
		configButtons();
		updateBars();

		client.subscribeMessageEvent(this);
	}

	@Override
	public void onMessage()
	{
		addMessage(client.getMessage());
	}
}
