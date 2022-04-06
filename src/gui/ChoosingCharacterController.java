package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
	@FXML
	private HBox hboxAdmin;

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

	public void addExtra()
	{
		HBox cHBox = new HBox();
		Circle cCircle = new Circle(50);
		VBox cVBox = new VBox();
		Label cLabel = new Label("Criar");
		TextField cText = new TextField();

		cCircle.setFill(new ImagePattern(
				new Image(getClass().getResource("/images/characters/Silhouette.png").toExternalForm())));
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
				if (!cText.getText().trim().equalsIgnoreCase("") && JSONService.exists(cText.getText()))
				{
					JSONService.createDefaultFile(cText.getText());

					Parent parent = FXMLLoader.load(getClass().getResource("/gui/UserScreen.fxml"));
					Scene scene = cCircle.getScene();
					scene.setRoot(parent);
					scene.setCursor(Cursor.DEFAULT);
				}
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		});

		cLabel.setFont(Font.font("Bodoni MT", 22));
		cLabel.setTextFill(Color.WHITE);

		cText.setPadding(new Insets(0, 0, 0, 10));
		cText.setStyle(
				"-fx-background-color: transparent; -fx-border-width: 0 0 1 0; -fx-border-color: white; -fx-text-fill: white;");
		cText.setPromptText("Nome do personagem");
		cText.setFocusTraversable(false);

		cVBox.setSpacing(5);

		cVBox.getChildren().add(cLabel);
		cVBox.getChildren().add(cText);

		cHBox.getChildren().add(cCircle);
		cHBox.getChildren().add(cVBox);
		cHBox.setOpacity(0.6);

		hbox.getChildren().add(cHBox);
	}

	public void addAdmin(String name, String img)
	{
		VBox vbox = new VBox();
		Label lblName = new Label(name);
		Circle imgCircle = new Circle(50);

		lblName.setFont(Font.font("Bodoni MT", 22));
		lblName.setTextFill(Color.WHITE);

		imgCircle.setFill(new ImagePattern(
				new Image(AdminScreenController.class.getResource("/images/" + img).toExternalForm())));
		imgCircle.setStrokeType(StrokeType.OUTSIDE);
		imgCircle.setStroke(Color.BLACK);

		imgCircle.setOnMouseEntered(event ->
		{
			imgCircle.getScene().setCursor(Cursor.HAND);
			vbox.setOpacity(1);
		});
		imgCircle.setOnMouseExited(event ->
		{
			if (imgCircle.getScene() != null)
			{
				imgCircle.getScene().setCursor(Cursor.DEFAULT);
				vbox.setOpacity(0.6);
			}
		});
		imgCircle.setOnMouseClicked(event ->
		{
			try
			{
				Parent parent = FXMLLoader.load(UserScreenController.class.getResource("/gui/AdminScreen.fxml"));
				Scene scene = vbox.getScene();
				scene.setRoot(parent);
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		});

		vbox.setAlignment(Pos.CENTER);

		vbox.getChildren().addAll(lblName, imgCircle);
		vbox.setOpacity(0.6);
		hboxAdmin.getChildren().add(vbox);
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
		addExtra();
		addAdmin("Mestre", "Simbolo_RPG.jpg");
	}
}
