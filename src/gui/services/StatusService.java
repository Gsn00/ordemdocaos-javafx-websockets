package gui.services;

import entities.Character;
import enums.StatusBarType;
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
	
	public static int incrementValue(int quantity, int value, int maxValue)
	{
		if ((value + quantity) < maxValue)
		{
			return value += quantity;
		}
		return maxValue;
	}

	public static int decrementValue(int value, int maxValue)
	{
		if (value > 0)
		{
			return value -= 1;
		}
		return value;
	}
	
	public static int decrementValue(int quantity, int value, int maxValue)
	{
		if ((value - quantity) > 0)
		{
			return value -= quantity;
		}
		return 0;
	}

	public static int incrementMaxValue(int maxValue)
	{
		return maxValue += 1;
	}

	public static void decrementMaxValue(Character character, StatusBarType statusBarType)
	{
		switch (statusBarType)
		{
		case VIDA:
			if (character.getMaxVida() > 1)
			{
				character.setMaxVida(character.getMaxVida() - 1);
				if (character.getVida() > character.getMaxVida())
				{
					character.setVida(character.getMaxVida());
				}
			}
			break;
		case ENERGIA:
			if (character.getMaxEnergia() > 1)
			{
				character.setMaxEnergia(character.getMaxEnergia() - 1);
				if (character.getEnergia() > character.getMaxEnergia())
				{
					character.setEnergia(character.getMaxEnergia());
				}
			}
			break;
		case RESISTENCIA:
			if (character.getMaxResistencia() > 1)
			{
				character.setMaxResistencia(character.getMaxResistencia() - 1);
				if (character.getResistencia() > character.getMaxResistencia())
				{
					character.setResistencia(character.getMaxResistencia());
				}
			}
			break;
		case SANIDADE:
			if (character.getMaxSanidade() > 1)
			{
				character.setMaxSanidade(character.getMaxSanidade() - 1);
				if (character.getSanidade() > character.getMaxSanidade())
				{
					character.setSanidade(character.getMaxSanidade());
				}
			}
			break;
		}
	}
}
