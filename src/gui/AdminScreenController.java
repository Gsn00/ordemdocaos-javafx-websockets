package gui;

import java.net.URL;
import java.util.ResourceBundle;

import entities.AdminStatusBar;
import enums.StatusBarType;
import entities.Character;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;

public class AdminScreenController implements Initializable
{
	@FXML
	private Label lblOrdemDoCaos;
	@FXML
	private HBox hbox;
	@FXML
	private ListView<String> listView;
	@FXML
	private TextField txtMessage;

	public void onTxtMessage()
	{

	}

	public void addCharacter(Character character)
	{
		HBox cHBox = new HBox();
		Circle cCircle = new Circle(50);
		VBox cVBox = new VBox();
		Label cLabel = new Label(character.getNome());

		cCircle.setFill(new ImagePattern(new Image(getClass().getResource(character.getImgUrl()).toExternalForm())));
		cCircle.setStrokeType(StrokeType.OUTSIDE);
		cCircle.setStroke(Color.BLACK);

		cLabel.setFont(Font.font("Bodoni MT", 22));
		cLabel.setTextFill(Color.WHITE);

		AdminStatusBar barVida = new AdminStatusBar("#b22626", character, StatusBarType.VIDA);
		barVida.setPadding(new Insets(0, 0, 0, 30));
		AdminStatusBar barEnergia = new AdminStatusBar("#f2f531", character, StatusBarType.ENERGIA);
		barEnergia.setPadding(new Insets(0, 0, 0, 16));
		AdminStatusBar barResistencia = new AdminStatusBar("#f79d25", character, StatusBarType.RESISTENCIA);
		barResistencia.setPadding(new Insets(0, 0, 0, 2));
		AdminStatusBar barSanidade = new AdminStatusBar("#27a6b0", character, StatusBarType.SANIDADE);
		barSanidade.setPadding(new Insets(0, 0, 0, -12));

		cVBox.setSpacing(5);

		cVBox.getChildren().add(cLabel);
		cVBox.getChildren().add(barVida);
		cVBox.getChildren().add(barEnergia);
		cVBox.getChildren().add(barResistencia);
		cVBox.getChildren().add(barSanidade);

		cHBox.getChildren().add(cCircle);
		cHBox.getChildren().add(cVBox);
		cHBox.setAlignment(Pos.CENTER);
		cHBox.setMaxHeight(120);

		hbox.getChildren().add(cHBox);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1)
	{
		addCharacter(new Character());
		addCharacter(new Character());
		addCharacter(new Character());
	}
}
