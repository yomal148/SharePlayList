package entity;

import com.google.gson.annotations.SerializedName;

import entity.Image;

public class Album {
    @SerializedName("id")
    public String albumId;

    @SerializedName("name")
    String albumName;

    @SerializedName("images")
    Image[] albumImages;

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