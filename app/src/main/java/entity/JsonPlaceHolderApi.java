package entity;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonPlaceHolderApi {

    @GET("get")
    Call<IP> getIp();
}
