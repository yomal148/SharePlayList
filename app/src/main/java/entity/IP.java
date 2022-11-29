package entity;
import com.google.gson.annotations.SerializedName;
public class IP {
    @SerializedName("origin")
    private String ipAddress;


    public String getIpAddress() {
        return ipAddress;
    }
}
