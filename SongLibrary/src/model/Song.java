package model;

public class Song implements Comparable<Song>{
	private String title;
	private String artist;
	private String album;
	private String year;

	public Song(String title, String artist, String album, String year) {
		this.title = title;
		this.artist = artist;
		this.album = album;
		this.year= year;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}
	
	@Override
	public String toString(){
		return this.title + " - " + this.artist;
	}

	@Override
	public int compareTo(Song arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
	

}
