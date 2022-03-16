package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import services.JSONService;

public class ChoosingCharacterController implements Initializable
{
	@FXML
	private Label lblOrdemDoCaos;
	@FXML
	private HBox hbox;

	public void addCharacter(String name)
	{
		HBox cHBox = new HBox();
		Circle cCircle = new Circle(50);
		VBox cVBox = new VBox();
		Label cLabel = new Label(name);
		Label cLblIdade = new Label();
		Label cLblOcupacao = new Label();

		cCircle.setFill(new ImagePattern(
				new Image(getClass().getResource("/images/characters/" + name + ".png").toExternalForm())));
		cCircle.setStrokeType(StrokeType.OUTSIDE);
		cCircle.setStroke(Color.BLACK);

		cCircle.setOnMouseEntered(event ->
		{
			cCircle.getScene().setCursor(Cursor.HAND);
			cHBox.setOpacity(1);
		});
		cCircle.setOnMouseExited(event ->
		{
			if (cCircle.getScene() != null)
			{
				cCircle.getScene().setCursor(Cursor.DEFAULT);
				cHBox.setOpacity(0.6);
			}
		});
		cCircle.setOnMouseClicked(event ->
		{
			try
			{
				JSONService.createDefaultFile(name);

				Parent parent = FXMLLoader.load(getClass().getResource("/gui/UserScreen.fxml"));
				Scene scene = cCircle.getScene();
				scene.setRoot(parent);
				scene.setCursor(Cursor.DEFAULT);
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		});

		cLabel.setFont(Font.font("Bodoni MT", 22));
		cLabel.setTextFill(Color.WHITE);

		cLblIdade.setText((String) JSONService.getData("idade", name));
		cLblIdade.setFont(Font.font("Bodoni MT", 16));
		cLblIdade.setTextFill(Color.SILVER);
		cLblIdade.setPadding(new Insets(0, 0, 0, 10));

		cLblOcupacao.setText((String) JSONService.getData("ocupacao", name));
		cLblOcupacao.setFont(Font.font("Bodoni MT", 16));
		cLblOcupacao.setTextFill(Color.SILVER);
		cLblOcupacao.setPadding(new Insets(0, 0, 0, 10));

		cVBox.setSpacing(5);

		cVBox.getChildren().add(cLabel);
		cVBox.getChildren().add(cLblIdade);
		cVBox.getChildren().add(cLblOcupacao);

		cHBox.getChildren().add(cCircle);
		cHBox.getChildren().add(cVBox);
		cHBox.setOpacity(0.6);

		hbox.getChildren().add(cHBox);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1)
	{
		Font popZero = Font.loadFont(getClass().getResourceAsStream("/fonts/Populationzerobb.otf"), 64);
		lblOrdemDoCaos.setFont(popZero);

		addCharacter("Zyan");
		addCharacter("Java");
		addCharacter("Moriart");
		addCharacter("Lorenzo");
		addCharacter("Robert");
		addCharacter("Gustavo");
	}
}
