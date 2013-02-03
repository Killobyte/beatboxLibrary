package com.beatbox.lib;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.beatbox.lib.song.Song;
import com.beatbox.lib.song.SongFactory;

public class Library {
	private TreeMap<String, TreeMap<String, Song>> library;

	public Library() {
		library = new TreeMap<String, TreeMap<String, Song>>();
	}

	public void addSong(Song song) {
		String key = song.getArtist();
		if (!library.containsKey(key)) {
			library.put(key, new TreeMap<String, Song>());
		}
		library.get(key).put(song.getTitle(), song);
	}

	public void buildFromFilepath(File root) {
		if (root.isFile()) {
			Song song = SongFactory.buildSong(root);
			if (song != null) {
				addSong(song);
			}
		} else if (root.isDirectory()) {
			File[] files = root.listFiles();
			for (int i = 0; i < files.length; i++) {
				buildFromFilepath(files[i]);
			}
		}
	}

	public Set<String> getArtists() {
		SortedSet<String> artists = new TreeSet<String>(library.keySet());
		return artists;
	}

	public Song getSong(String artist, String title) {
		return library.get(artist).get(title);
	}

	public List<Song> getSongs(String artist) {
		TreeMap<String, Song> songTree = library.get(artist);
		SortedSet<String> songs = new TreeSet<String>(songTree.keySet());
		ArrayList<Song> ret = new ArrayList<Song>();
		for (String key : songs) {
			ret.add(songTree.get(key));
		}
		return ret;
	}

	public JSONObject toJSONObject() {
		JSONObject lib = new JSONObject();
		for (String artist : getArtists()) {
			JSONArray songs = new JSONArray();
			for (Song song : getSongs(artist)) {
				songs.put(song.toJSONObject());
			}
			try {
				lib.put(artist, songs);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return lib;
	}
}