package entities;

import enums.MessageType;
import enums.StatusBarType;
import gui.services.StatusService;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import network.Client;

public class AdminStatusBar extends HBox
{
	private AnchorPane anchorPane;
	private Rectangle baseRect;
	private Rectangle bar;
	private Label lblStatus;
	private Character character;
	private StatusBarType statusBarType;

	public AdminStatusBar(String color, Character character, StatusBarType statusBarType, Client client)
	{
		this.character = character;
		this.statusBarType = statusBarType;
		
		baseRect = new Rectangle(100, 15);
		baseRect.setFill(Paint.valueOf("#4f4f4f"));
		baseRect.setStroke(Paint.valueOf("#383838"));
		baseRect.setStrokeType(StrokeType.OUTSIDE);
		baseRect.setArcWidth(5);
		baseRect.setArcHeight(5);
		baseRect.setStrokeWidth(0.5);

		bar = new Rectangle(50, 15);
		bar.setFill(Paint.valueOf(color));
		bar.setArcWidth(5);
		bar.setArcHeight(5);

		lblStatus = new Label();
		lblStatus.setAlignment(Pos.TOP_CENTER);
		lblStatus.setPrefWidth(100);
		lblStatus.setTextFill(Color.BLACK);
		lblStatus.setStyle("-fx-font-weight: bold");

		updateBar();

		Circle button = new Circle();
		button.setFill(new ImagePattern(new Image(AdminStatusBar.class.getResource("/images/plus.png").toExternalForm())));
		button.setRadius(12);
		button.setOpacity(0.6);
		button.setLayoutX(110);
		button.setLayoutY(8);

		button.setOnMouseEntered(event ->
		{
			button.getScene().setCursor(Cursor.HAND);
			button.setOpacity(1);
		});
		button.setOnMouseExited(event ->
		{
			button.getScene().setCursor(Cursor.DEFAULT);
			button.setOpacity(0.6);
		});
		button.setOnMouseClicked(event -> {
			switch (statusBarType)
			{
			case VIDA:
				this.character.setMaxVida(StatusService.incrementMaxValue(this.character.getMaxVida()));
				StatusService.updateBar(bar, lblStatus, this.character.getVida(), this.character.getMaxVida());
				break;
			case ENERGIA:
				this.character.setMaxEnergia(StatusService.incrementMaxValue(this.character.getMaxEnergia()));
				StatusService.updateBar(bar, lblStatus, this.character.getEnergia(), this.character.getMaxEnergia());
				break;
			case RESISTENCIA:
				this.character.setMaxResistencia(StatusService.incrementMaxValue(this.character.getMaxResistencia()));
				StatusService.updateBar(bar, lblStatus, this.character.getResistencia(), this.character.getMaxResistencia());
				break;
			case SANIDADE:
				this.character.setMaxSanidade(StatusService.incrementMaxValue(this.character.getMaxSanidade()));
				StatusService.updateBar(bar, lblStatus, this.character.getSanidade(), this.character.getMaxSanidade());
				break;
			}
			Character newCharacter = new Character(this.character);
			client.sendSocket(new Message(newCharacter, MessageType.STATUS));
		});
		
		anchorPane = new AnchorPane();
		anchorPane.getChildren().add(baseRect);
		anchorPane.getChildren().add(bar);
		anchorPane.getChildren().add(lblStatus);
		anchorPane.getChildren().add(button);

		getChildren().add(anchorPane);

		setSpacing(10);
	}

	public AnchorPane getAnchorPane()
	{
		return anchorPane;
	}

	public void setAnchorPane(AnchorPane anchorPane)
	{
		this.anchorPane = anchorPane;
	}

	public Rectangle getBaseRect()
	{
		return baseRect;
	}

	public void setBaseRect(Rectangle baseRect)
	{
		this.baseRect = baseRect;
	}

	public Rectangle getBar()
	{
		return bar;
	}

	public void setBar(Rectangle bar)
	{
		this.bar = bar;
	}
	
	public Label getLblStatus()
	{
		return lblStatus;
	}

	public void setLblStatus(Label lblStatus)
	{
		this.lblStatus = lblStatus;
	}

	public Character getCharacter()
	{
		return character;
	}

	public void setCharacter(Character character)
	{
		this.character = character;
	}

	public StatusBarType getStatusBarType()
	{
		return statusBarType;
	}

	public void setStatusBarType(StatusBarType statusBarType)
	{
		this.statusBarType = statusBarType;
	}

	public void updateBar()
	{
		switch (statusBarType)
		{
		case VIDA:
			StatusService.updateBar(bar, lblStatus, character.getVida(), character.getMaxVida());
			break;
		case ENERGIA:
			StatusService.updateBar(bar, lblStatus, character.getEnergia(), character.getMaxEnergia());
			break;
		case RESISTENCIA:
			StatusService.updateBar(bar, lblStatus, character.getResistencia(), character.getMaxResistencia());
			break;
		case SANIDADE:
			StatusService.updateBar(bar, lblStatus, character.getSanidade(), character.getMaxSanidade());
			break;
		}
	}
}
