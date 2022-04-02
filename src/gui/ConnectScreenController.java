package gui;

import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import network.Client;
import network.Server;
import services.JSONService;

public class ConnectScreenController
{
	@FXML
	private TextField txtJogador;
	@FXML
	private Button btJogador;
	@FXML
	private Label lblJogador;
	@FXML
	private TextField txtHost;
	@FXML
	private Button btHost;

	public void onBtJogador()
	{
		if (!txtJogador.getText().trim().isEmpty())
		{
			Main.IP = txtJogador.getText();
			if (Client.canConnect())
			{
				Stage stage = (Stage) txtJogador.getScene().getWindow();
				stage.close();
				start();
			} else {
				lblJogador.setText("Não foi possível conectar-se ao IP " + Main.IP);
			}
		}
	}

	public void onBtHost()
	{
		if (!txtHost.getText().trim().isEmpty())
		{
			Main.IP = txtHost.getText();
			Thread t = new Thread(new Runnable()
			{
				@Override
				public void run()
				{
					Server.startServer();
				}
			});
			t.setDaemon(true);
			t.start();
			Stage stage = (Stage) txtHost.getScene().getWindow();
			stage.close();
			start();
		}
	}

	public void onMouseEntered()
	{
		btHost.getScene().setCursor(Cursor.HAND);
	}

	public void onMouseExited()
	{
		btHost.getScene().setCursor(Cursor.DEFAULT);
	}

	public void start()
	{
		try
		{
			if (JSONService.file.exists())
			{
				Parent parent = FXMLLoader.load(getClass().getResource("/gui/UserScreen.fxml"));
				Scene scene = new Scene(parent);
				Stage stage = new Stage();
				stage.setTitle("Ordem do Caos");
				stage.getIcons().add(new Image(getClass().getResource("/images/dice.png").toExternalForm()));
//				stage.initStyle(StageStyle.UNDECORATED);
				stage.setScene(scene);
				stage.setMaximized(true);
				stage.show();
			} else
			{
				Parent parent = FXMLLoader.load(getClass().getResource("/gui/ChoosingCharacter.fxml"));
				Scene scene = new Scene(parent);
				Stage stage = new Stage();
				stage.setTitle("Ordem do Caos");
				stage.getIcons().add(new Image(getClass().getResource("/images/dice.png").toExternalForm()));
//				stage.initStyle(StageStyle.UNDECORATED);
				stage.setScene(scene);
				stage.setMaximized(true);
				stage.show();
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
