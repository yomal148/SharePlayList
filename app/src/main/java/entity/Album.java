package entity;

import com.google.gson.annotations.SerializedName;

import entity.Image;

public class Album {
    @SerializedName("id")
    public String albumId;

    @SerializedName("name")
    String albumName;

    @SerializedName("images")
    public Image[] albumImages;

    public Artist[] getArtists() {
        return artists;
    }

    public void setArtists(Artist[] artists) {
        this.artists = artists;
    }

    @SerializedName("artists")
    public Artist[] artists;

    public String getAlbumId() {
        return albumId;
    }

    public Image[] getAlbumImages() {
        return albumImages;
    }

    public String getAlbumName() {
        return albumName;
    }
}