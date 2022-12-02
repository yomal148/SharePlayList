package entity;

import com.google.gson.annotations.SerializedName;

public class AddTrackBody {

    @SerializedName("position")
    private int position;

    @SerializedName("uris")
    private String[] uris;

    public int getPosition() {
        return position;
    }

    public String[] getUris() {
        return uris;
    }

    public AddTrackBody(int position, String[] uris) {
        this.uris = uris;
        this.position = position;
    }



}
