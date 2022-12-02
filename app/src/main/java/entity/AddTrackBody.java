package entity;

import com.google.gson.annotations.SerializedName;

public class AddTrackBody {

    @SerializedName("position")
    private int position;

    @SerializedName("uris")
    private String[] uris;
}
