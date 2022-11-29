package entity;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface JsonPlaceHolderArtists {

    @GET("v1/artists/7dGJo4pcD2V6oG8kP0tJRR")
    Call<Artist> getArtist(@Header("Authorization") String token);
}
