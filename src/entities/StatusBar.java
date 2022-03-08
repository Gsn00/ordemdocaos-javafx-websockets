package entities;

import javafx.scene.control.Label;
import javafx.scene.shape.Rectangle;
import services.JSONService;

public class StatusBar
{
	private Rectangle rectangle;
	private String valueName;
	private String maxValueName;
	private Integer value;
	private Integer maxValue;
	private Label label;
	
	public StatusBar()
	{
	}

	public StatusBar(Rectangle rectangle, Label label, String valueName, String maxValueName)
	{
		this.rectangle = rectangle;
		this.label = label;
		this.valueName = valueName;
		this.maxValueName = maxValueName;
		this.value = (int) ((long) JSONService.getData(valueName));
		this.maxValue = (int) ((long) JSONService.getData(maxValueName));
	}

	public Rectangle getRectangle()
	{
		return rectangle;
	}

	public void setRectangle(Rectangle rectangle)
	{
		this.rectangle = rectangle;
	}

	public Label getLabel()
	{
		return label;
	}

	public void setLabel(Label label)
	{
		this.label = label;
	}

	public String getValueName()
	{
		return valueName;
	}

	public void setValueName(String valueName)
	{
		this.valueName = valueName;
	}

	public String getMaxValueName()
	{
		return maxValueName;
	}

	public void setMaxValueName(String maxValueName)
	{
		this.maxValueName = maxValueName;
	}

	public Integer getValue()
	{
		return (int) ((long) JSONService.getData(valueName));
	}

	public void setValue(Integer value)
	{
		if (value > maxValue)
		{
			value = maxValue;
		}
		if (value < 0)
		{
			value = 0;
		}
		JSONService.setData(valueName, value);
	}

	public Integer getMaxValue()
	{
		return (int) ((long) JSONService.getData(maxValueName));
	}

	public void setMaxValue(Integer maxValue)
	{
		JSONService.setData(maxValueName, maxValue);
	}
	
	public void incrementValue()
	{
		value += 1;
		JSONService.setData(valueName, value);
	}
	
	public void incrementMaxValue()
	{
		maxValue += 1;
		JSONService.setData(maxValueName, maxValue);
	}
}