package entity;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface JsonPlaceHolderSpotifyApi {
    @GET("v1/search?")
    Call<SearchResult> getSearchResult(@Query("q") String query, @Query("type") String type, @Query("limit") int limit, @Header("Authorization") String token);

}
