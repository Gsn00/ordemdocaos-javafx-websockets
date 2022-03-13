package gui.services;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import services.Utils;

public class StatusService
{

	public static void updateBar(Rectangle rectangle, Label label, int value, int maxValue)
	{
		label.setText(value + "/" + maxValue);
		KeyValue width = new KeyValue(rectangle.widthProperty(), Utils.parsePercent(value, maxValue));
		KeyFrame frame = new KeyFrame(Duration.seconds(0.5), width);
		Timeline timeLine = new Timeline(frame);
		timeLine.play();
	}

	public static int incrementValue(int value, int maxValue)
	{
		if (value < maxValue)
		{
			return value += 1;
		}
		return value;
	}

	public static int decrementValue(int value, int maxValue)
	{
		if (value > 0)
		{
			return value -= 1;
		}
		return value;
	}

	public static int incrementMaxValue(int maxValue)
	{
		return maxValue += 1;
	}

	public static int decrementMaxValue(int value, int maxValue)
	{
		if (maxValue > 1)
		{
			maxValue -= 1;
			if (value > maxValue)
			{
				value = maxValue;
			}
			return value;
		}
		return value;
	}

}
