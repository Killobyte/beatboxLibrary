package com.beatbox.lib.song;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.media.Format;
import javax.media.Manager;
import javax.media.MediaLocator;
import javax.media.NoPlayerException;
import javax.media.Player;
import javax.media.PlugInManager;
import javax.media.Time;
import javax.media.format.AudioFormat;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;

public class MP3Song extends Song {

	Player player;
	boolean paused;
	Time resumeTime;

	public MP3Song(File file) {
		path = file.getAbsolutePath();
		AudioFile af = null;
		try {
			af = AudioFileIO.read(file);
		} catch (CannotReadException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TagException e) {
			e.printStackTrace();
		} catch (ReadOnlyFileException e) {
			e.printStackTrace();
		} catch (InvalidAudioFrameException e) {
			e.printStackTrace();
		}

		if (af != null) {
			Tag tag = af.getTag();
			artist = tag.getFirst(FieldKey.ARTIST);
			title = tag.getFirst(FieldKey.TITLE);
		}
	}

	@Override
	public void play() {
		File songFile = new File(path);
		MediaLocator ml;
		Format input1 = new AudioFormat(AudioFormat.MPEGLAYER3);
		Format input2 = new AudioFormat(AudioFormat.MPEG);
		Format output = new AudioFormat(AudioFormat.LINEAR);
		PlugInManager.addPlugIn("com.sun.media.codec.audio.mp3.JavaDecoder",
				new Format[] { input1, input2 }, new Format[] { output },
				PlugInManager.CODEC);
		try {
			ml = new MediaLocator(songFile.toURI().toURL());
			player = Manager.createPlayer(ml);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoPlayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		player.start();
		paused = false;
	}

	public void stop() {
		player.stop();
	}

	public void pauseOrResume() {
		if (paused) {
			player.setMediaTime(resumeTime);
			player.start();
		} else {
			resumeTime = player.getMediaTime();
			player.stop();
		}
		paused = !paused;
	}
}
