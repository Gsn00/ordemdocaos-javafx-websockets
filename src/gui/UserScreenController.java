package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import listeners.MessageEvent;
import network.Client;
import network.Message;

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

	private Client client = new Client();

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
						for (int i=0; i<quantityDices; i++)
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
						
						client.sendSocket(new Message(result));
						sendMessage(result);
					} catch (NumberFormatException e)
					{
						sendMessage(msg);
					}
				}

				txtMsg.setText("");
				return;
			}

			client.sendSocket(new Message(txtMsg.getText()));
			sendMessage(txtMsg.getText());
		}
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

	@Override
	public void initialize(URL arg0, ResourceBundle arg1)
	{
		Image img = new Image(getClass().getResource("/images/Screenshot_1.png").toExternalForm());
		circle.setFill(new ImagePattern(img));
		Font popZero = Font.loadFont(getClass().getResourceAsStream("/fonts/Populationzerobb.otf"), 64);
		lblOrdemDoCaos.setFont(popZero);
		
		configListView();

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
