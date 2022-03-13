package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import services.JSONService;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;

public class Main extends Application
{

	public static String VERSION = "2.0.0";

	@Override
	public void start(Stage stage)
	{
		try
		{
			if (JSONService.file.exists())
			{
				Parent parent = FXMLLoader.load(getClass().getResource("/gui/UserScreen.fxml"));
				Scene scene = new Scene(parent);
				stage.setTitle("Ordem do Caos");
				stage.getIcons().add(new Image(getClass().getResource("/images/dice.png").toExternalForm()));
				stage.setScene(scene);
				stage.setMaximized(true);
				stage.show();
			} else
			{
				Parent parent = FXMLLoader.load(getClass().getResource("/gui/ChoosingCharacter.fxml"));
				Scene scene = new Scene(parent);
				stage.setTitle("Ordem do Caos");
				stage.getIcons().add(new Image(getClass().getResource("/images/dice.png").toExternalForm()));
				stage.setScene(scene);
				stage.setMaximized(true);
				stage.show();
			}
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
