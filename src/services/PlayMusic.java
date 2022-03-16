package services;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class PlayMusic
{
	private static MediaPlayer mp;
	public static boolean looping = false;
	public static double VOLUME = 0.4;

	public static void playByName(String name)
	{
		if (mp != null)
		{
			mp.stop();
			mp = null;
			playByName(name);
			return;
		}
		if (mp == null)
		{
			new Thread(new Runnable()
			{
				@Override
				public void run()
				{
					mp = new MediaPlayer(new Media(PlayMusic.class.getResource("/songs/" + name).toString()));
					mp.setVolume(VOLUME);
					mp.play();
					mp.setOnEndOfMedia(() ->
					{
						if (looping == false)
						{
							mp.stop();
							mp = null;
							return;
						}
						mp.seek(Duration.ZERO);
						mp.play();
					});
				}
			}).start();
		}
	}

	public static void stopTrack()
	{
		if (mp != null)
		{
			mp.stop();
			mp = null;
		}
	}

	public static void pause()
	{
		if (mp != null)
		{
			mp.pause();
		}
	}

	public static void play()
	{
		if (mp != null)
		{
			mp.play();
		}
	}

	public static void setVolume(double volume)
	{
		if (mp != null)
		{
			mp.setVolume(volume);
			VOLUME = volume;
		}
	}
}
