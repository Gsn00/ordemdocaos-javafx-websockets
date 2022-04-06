package services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class PlayMusic
{
	public static MediaPlayer mp;
	public static String NAME = "";
	public static double VOLUME = 0.4;
	public static Duration DURATION;
	public static ObservableList<String> playlist = FXCollections.observableArrayList();

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
					NAME = name;
					mp.setOnEndOfMedia(() ->
					{
						if (DURATION == null)
						{
							mp.stop();
							mp = null;
							return;
						}
						mp.seek(DURATION);
						mp.play();
					});
				}
			}).start();
		}
	}

	public static void playByPlaylist(String name)
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
					NAME = name;
					mp.setOnEndOfMedia(() ->
					{
						mp.stop();
						mp = null;
						int pos = playlist.indexOf(name);
						if (playlist.size() > (pos + 1))
						{
							String song = playlist.get(pos + 1);
							playByPlaylist(song);
						}
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
		}
		VOLUME = volume;
	}
}
