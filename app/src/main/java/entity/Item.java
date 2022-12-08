package entity;

import com.google.gson.annotations.SerializedName;

import entity.Album;
import entity.Artist;

public class Item {
    @SerializedName("name")
    public String songName;

    @SerializedName("id")
    public String songId;

    @SerializedName("album")
    public Album album;

    @SerializedName("artists")
    public Artist[] artist;

    @SerializedName("track")
    public Track track;

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getSongId() {
        return songId;
    }

    public void setSongId(String songId) {
        this.songId = songId;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public Artist[] getArtist() {
        return artist;
    }

    public void setArtist(Artist[] artist) {
        this.artist = artist;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    @SerializedName("uri")
    public String uri;

    public Item(String songName, String songId, Album album, Artist[] artist, String uri) {
        this.songName = songName;
        this.songId = songId;
        this.album = album;
        this.artist = artist;
        this.uri = uri;
    }

    @Override
    public String toString(){
        return uri;
    }
}



