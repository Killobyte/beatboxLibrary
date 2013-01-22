package com.beatbox.lib.song;

import java.io.File;

public class SongFactory {

	public static Song buildSong(File file) {
		if (file.getName().endsWith(".mp3")) {
			return new MP3Song(file);
		} else {
			return null;
		}
	}

}
