package entity;

import com.google.gson.annotations.SerializedName;

public class Track {
    @SerializedName("name")
    public String songName;

    public String getSongName() {
        return songName;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    @SerializedName("album")
    public Album album;

    public void setSongName(String songName) {
        this.songName = songName;
    }
}
