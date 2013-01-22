package com.beatbox.lib.song;

import org.json.JSONException;
import org.json.JSONObject;

public class Song {
	protected String title;
	protected String artist;
	protected String path;

	public Song() {
		title = "";
		artist = "";
	}

	public Song(String title, String artist, String path) {
		this.title = title;
		this.artist = artist;
		this.path = path;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public String getArtist() {
		return artist;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getPath() {
		return path;
	}

	public JSONObject toJSONObject() {
		JSONObject song = new JSONObject();
		try {
			song.put("title", title);
			song.put("artist", artist);
		} catch (JSONException e) {
			System.err.println("Error creating JSONObject for " + artist
					+ " - " + title);
			e.printStackTrace();
		}
		return song;
	}
}
