package services;

public class Utils
{
	public static double parsePercent(double current, double max)
	{
		if (current > max)
		{
			current = max;
		}
		if (current < 0)
		{
			current = 0;
		}
		return (current / max * 100);
	}
}
