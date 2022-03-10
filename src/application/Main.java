package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import network.Client;
import services.JSONService;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class Main extends Application {
	
	public static String VERSION = "2.0.0";
	public static Client client = new Client();
	
	@Override
	public void start(Stage stage) {
		try {
			Parent parent = FXMLLoader.load(getClass().getResource("/gui/UserScreen.fxml"));
			Scene scene = new Scene(parent);
			stage.setScene(scene);
			stage.setMaximized(true);
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		JSONService.createDefaultFile();
		client.connect();
		client.listen();
		launch(args);
	}
}
