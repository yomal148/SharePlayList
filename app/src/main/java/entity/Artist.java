package entity;

import com.google.gson.annotations.SerializedName;

public class Artist {
    @SerializedName("id")
    public String artistId;

    @SerializedName("name")
    public String artistName;

    public String getArtistId() {
        return this.artistId;
    }
    public String getArtistName() {
        return artistName;
    }
}