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

    @SerializedName("uri")
    public String uri;

    public Item(String songName, String songId, Album album, Artist[] artist, String uri) {
        this.songName = songName;
        this.songId = songId;
        this.album = album;
        this.artist = artist;
        this.uri = uri;

    }
}