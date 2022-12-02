package entity;

import com.google.gson.annotations.SerializedName;

public class CreatePlaylistBody {

    @SerializedName("name")
    private String name;

    @SerializedName("public")
    private boolean isPublic;

    @SerializedName("collaborative")
    private boolean isCollaborative;

    @SerializedName("description")
    private String description;

    public CreatePlaylistBody(String name, boolean isPublic, boolean isCollaborative, String description) {
        this.name = name;
        this.isCollaborative = isCollaborative;
        this.isPublic = isPublic;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public Boolean isPublic() {
        return isPublic;
    }

    public Boolean isCollaborative() {
        return isCollaborative;
    }

    public String getDescription() {
        return description;
    }
}
