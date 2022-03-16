package entities;

import enums.MessageType;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import network.Client;
import services.PlayMusic;

public class ToggleSwitch extends StackPane
{
	private final Rectangle back = new Rectangle(30, 10, Color.WHITE);
	private final Button button = new Button();

	private String buttonStyleOff = "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 0.2, 0.0, 0.0, 2); -fx-background-color: #4f4f4f;";

	private String buttonStyleOn = "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 0.2, 0.0, 0.0, 2); -fx-background-color:#4f4f4f;";

	public boolean state;

	private void init() {
		getChildren().addAll(back, button);

		setMinSize(30, 15);
		setMaxSize(30, 15);

		back.maxWidth(30);
		back.minWidth(30);

		back.maxHeight(10);
		back.minHeight(10);

		back.setArcHeight(back.getHeight());
		back.setArcWidth(back.getHeight());
		back.setFill(Color.valueOf("white"));

		Double r = 2.0;
		button.setShape(new Circle(r));

		setAlignment(button, Pos.CENTER_LEFT);
		button.setMaxSize(15, 15);
		button.setMinSize(15, 15);
		button.setStyle(buttonStyleOff);
		setOpacity(0.8);
	}

	public ToggleSwitch(Client client) {
		init();
		EventHandler<Event> click = new EventHandler<Event>() {
			@Override
			public void handle(Event e) {
				if (state) {
					button.setStyle(buttonStyleOff);
					back.setFill(Color.valueOf("white"));
					setAlignment(button, Pos.CENTER_LEFT);
					state = false;
					setOpacity(0.8);
					PlayMusic.looping = state;
				} else {
					button.setStyle(buttonStyleOn);
					back.setFill(Color.valueOf("#383838"));
					setAlignment(button, Pos.CENTER_RIGHT);
					state = true;
					setOpacity(1);
					PlayMusic.looping = state;
				}
				client.sendSocket(new Message(state, MessageType.MUSICLOOPING));
			}
		};
		EventHandler<Event> mouseEnteredEvent = new EventHandler<Event>()
		{
			@Override
			public void handle(Event event)
			{
				getScene().setCursor(Cursor.HAND);
			}
		};
		EventHandler<Event> mouseExitedEvent = new EventHandler<Event>()
		{
			@Override
			public void handle(Event event)
			{
				getScene().setCursor(Cursor.DEFAULT);
			}
		};
		button.setFocusTraversable(false);

		setOnMouseClicked(click);
		button.setOnMouseClicked(click);
		setOnMouseEntered(mouseEnteredEvent);
		setOnMouseExited(mouseExitedEvent);
	}
}
