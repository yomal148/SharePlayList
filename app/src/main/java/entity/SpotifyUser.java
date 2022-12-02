package entity;

import com.google.gson.annotations.SerializedName;

public class SpotifyUser {
    @SerializedName("display_name")
    public String username;

    @SerializedName("id")
    public String userID;

    public String getUsername() {
        return username;
    }

    public String getUserID() {
        return userID;
    }
}
