package entity;

import com.google.gson.annotations.SerializedName;

public class CreatePlaylistResponse {
    @SerializedName("id")
    private String playlistId;


    public String getPlaylistId() {
        return playlistId;
    }
}
