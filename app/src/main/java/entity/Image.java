package entity;

import com.google.gson.annotations.SerializedName;

public class Image {
    @SerializedName("url")
    public String imageUrl;

    public String getImageUrl() {
        return this.imageUrl;
    }
}
