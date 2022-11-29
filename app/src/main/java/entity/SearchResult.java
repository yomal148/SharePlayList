package entity;

import com.google.gson.annotations.SerializedName;

public class SearchResult {
    @SerializedName("tracks")
    Tracks tracks;

    public String getSongId() {
        return tracks.items[0].songId;
    }

    public Item[] getItemsArray() {
        return tracks.items;
    }


}







