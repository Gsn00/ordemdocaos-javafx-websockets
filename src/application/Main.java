package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;

public class Main extends Application
{

	public static String VERSION = "2.0.0";
	public static String IP = null;

	@Override
	public void start(Stage stage)
	{
		try
		{
			Parent parent = FXMLLoader.load(getClass().getResource("/gui/ConnectScreen.fxml"));
			Scene scene = new Scene(parent);
			stage.setTitle("Ordem do Caos");
			stage.getIcons().add(new Image(getClass().getResource("/images/dice.png").toExternalForm()));
			stage.setResizable(false);
			stage.setScene(scene);
			stage.show();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws InterruptedException
	{
		launch(args);
	}
}
