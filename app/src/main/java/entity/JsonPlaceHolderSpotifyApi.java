package entity;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface JsonPlaceHolderSpotifyApi {
    @GET("v1/search?")
    Call<SearchResult> getSearchResult(@Query("q") String query, @Query("type") String type, @Query("limit") int limit, @Header("Authorization") String token);

    @GET("v1/me")
    Call<SpotifyUser> getCurrentUser(@Header("Authorization") String token);

    @POST("v1/users/{user_id}/playlists")
    Call<CreatePlaylistResponse> createPlaylist(@Path(value = "user_id", encoded = true) String userId, @Body CreatePlaylistBody body, @Header("Authorization") String token);

    @POST("v1/playlists/{playlist_id}/tracks")
    Call<AddTrackResponse> addTrackToPlaylist(@Path(value = "playlist_id") String playlistId, @Body AddTrackBody body, @Header("Authorization") String TOKEN);

    @GET("v1/playlists/{playlist_id}/tracks")
    Call<PlaylistResult> getPlaylistItems(@Path(value = "playlist_id") String playlistId,  @Header("Authorization") String TOKEN);
}
