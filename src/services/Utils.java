package services;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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

	public static List<String> readSongList(String name)
	{
		Scanner sc = null;
		try (BufferedReader br = new BufferedReader(
				new InputStreamReader(Utils.class.getResourceAsStream("/songs/list/" + name + ".txt"))))
		{
			List<String> songs = new ArrayList<>();
			sc = new Scanner(br);
			while (sc.hasNextLine())
			{
				songs.add(sc.nextLine());
			}
			return songs;
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
}
