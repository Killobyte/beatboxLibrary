package com.beatbox.lib.song;

import java.io.File;
import java.io.IOException;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;

public class MP3Song extends Song {

	MediaPlayer player;
	boolean paused;

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
			if (tag != null) {
				artist = tag.getFirst(FieldKey.ARTIST);
				title = tag.getFirst(FieldKey.TITLE);
			}
		}
	}

	@Override
	public void play(Runnable onCompleteCallback) {
		Media media = null;
		media = new Media(new File(path).toURI().toString());
		if (media != null) {
			player = new MediaPlayer(media);
			player.play();
			player.setOnEndOfMedia(onCompleteCallback);
			paused = false;
		}
	}

	public void stop() {
		if (player != null) {
			player.stop();
		}
	}

	public void pauseOrResume() {
		if (player != null) {
			if (paused) {
				player.play();
			} else {
				player.pause();
			}
			paused = !paused;
		}
	}
}
