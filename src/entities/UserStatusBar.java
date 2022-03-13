package entities;

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
import services.JSONService;

public class UserStatusBar extends HBox
{
	private AnchorPane anchorPane;
	private Rectangle baseRect;
	private Rectangle bar;
	private Label lblStatus;

	public UserStatusBar(String color, Character character, StatusBarType statusBarType)
	{
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

		updateBar(character, statusBarType);

		Circle buttonAdd = new Circle();
		buttonAdd.setFill(new ImagePattern(new Image(UserStatusBar.class.getResource("/images/plus.png").toExternalForm())));
		buttonAdd.setRadius(12);
		buttonAdd.setOpacity(0.6);
		buttonAdd.setLayoutX(110);
		buttonAdd.setLayoutY(8);

		buttonAdd.setOnMouseEntered(event ->
		{
			buttonAdd.getScene().setCursor(Cursor.HAND);
			buttonAdd.setOpacity(1);
		});
		buttonAdd.setOnMouseExited(event ->
		{
			buttonAdd.getScene().setCursor(Cursor.DEFAULT);
			buttonAdd.setOpacity(0.6);
		});
		buttonAdd.setOnMouseClicked(event -> {
			switch (statusBarType)
			{
			case VIDA:
				character.setVida(StatusService.incrementValue(character.getVida(), character.getMaxVida()));
				StatusService.updateBar(bar, lblStatus, character.getVida(), character.getMaxVida());
				JSONService.setData("vida", character.getVida());
				// Send socket
				break;
			case ENERGIA:
				character.setEnergia(StatusService.incrementValue(character.getEnergia(), character.getMaxEnergia()));
				StatusService.updateBar(bar, lblStatus, character.getEnergia(), character.getMaxEnergia());
				JSONService.setData("energia", character.getEnergia());
				// Send socket
				break;
			case RESISTENCIA:
				character.setResistencia(StatusService.incrementValue(character.getResistencia(), character.getMaxResistencia()));
				StatusService.updateBar(bar, lblStatus, character.getResistencia(), character.getMaxResistencia());
				JSONService.setData("resistencia", character.getResistencia());
				// Send socket
				break;
			case SANIDADE:
				character.setSanidade(StatusService.incrementValue(character.getSanidade(), character.getMaxSanidade()));
				StatusService.updateBar(bar, lblStatus, character.getSanidade(), character.getMaxSanidade());
				JSONService.setData("sanidade", character.getSanidade());
				// Send socket
				break;
			}
		});
		
		Circle buttonRem = new Circle();
		buttonRem.setFill(new ImagePattern(new Image(UserStatusBar.class.getResource("/images/hifen.png").toExternalForm())));
		buttonRem.setRadius(12);
		buttonRem.setOpacity(0.6);
		buttonRem.setLayoutX(-10);
		buttonRem.setLayoutY(8);

		buttonRem.setOnMouseEntered(event ->
		{
			buttonRem.getScene().setCursor(Cursor.HAND);
			buttonRem.setOpacity(1);
		});
		buttonRem.setOnMouseExited(event ->
		{
			buttonRem.getScene().setCursor(Cursor.DEFAULT);
			buttonRem.setOpacity(0.6);
		});
		buttonRem.setOnMouseClicked(event -> {
			switch (statusBarType)
			{
			case VIDA:
				character.setVida(StatusService.decrementValue(character.getVida(), character.getMaxVida()));
				StatusService.updateBar(bar, lblStatus, character.getVida(), character.getMaxVida());
				JSONService.setData("vida", character.getVida());
				//Send socket
				break;
			case ENERGIA:
				character.setEnergia(StatusService.decrementValue(character.getEnergia(), character.getMaxEnergia()));
				StatusService.updateBar(bar, lblStatus, character.getEnergia(), character.getMaxEnergia());
				JSONService.setData("energia", character.getEnergia());
				//Send socket
				break;
			case RESISTENCIA:
				character.setResistencia(StatusService.decrementValue(character.getResistencia(), character.getMaxResistencia()));
				StatusService.updateBar(bar, lblStatus, character.getResistencia(), character.getMaxResistencia());
				JSONService.setData("resistencia", character.getResistencia());
				//Send socket
				break;
			case SANIDADE:
				character.setSanidade(StatusService.decrementValue(character.getSanidade(), character.getMaxSanidade()));
				StatusService.updateBar(bar, lblStatus, character.getSanidade(), character.getMaxSanidade());
				JSONService.setData("sanidade", character.getSanidade());
				//Send socket
				break;
			}
		});

		anchorPane = new AnchorPane();
		anchorPane.getChildren().add(baseRect);
		anchorPane.getChildren().add(bar);
		anchorPane.getChildren().add(lblStatus);
		anchorPane.getChildren().add(buttonAdd);
		anchorPane.getChildren().add(buttonRem);

		getChildren().add(anchorPane);

		setSpacing(10);
		
		setAlignment(Pos.CENTER);
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
	
	public void updateBar(Character character, StatusBarType statusBarType)
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
