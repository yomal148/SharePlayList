package edu.northeastern.shareplaylist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import entity.CreatePlaylistBody;
import entity.CreatePlaylistResponse;
import entity.JsonPlaceHolderSpotifyApi;
import entity.SpotifyUser;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainScreen extends AppCompatActivity {
    private DatabaseReference ref;
    private Button createButton;
    private TextView userInfo;
    private String TOKEN =
            "";
    private String UUID =
            "";
    private String currentUserId;
    private String playlistId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        this.TOKEN = "Bearer " + getIntent().getStringExtra("TOKEN");
        createButton = findViewById(R.id.create_button);
        userInfo = findViewById(R.id.user_info);
        UUID = getIntent().getStringExtra("UUID");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.spotify.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonPlaceHolderSpotifyApi apiDOA = retrofit.create(JsonPlaceHolderSpotifyApi.class);

        Call<SpotifyUser> call = apiDOA.getCurrentUser(TOKEN);
        call.enqueue(new Callback<SpotifyUser>() {
            @Override
            public void onResponse(Call<SpotifyUser> call, Response<SpotifyUser> response) {
                System.out.println(TOKEN);
                if (response.isSuccessful()) {
                    userInfo.setText(response.body().getUserID());
                    currentUserId = response.body().getUserID();
                    // update database user table
                    ref = FirebaseDatabase.getInstance().getReference();
                    ref.child("user").child(UUID).setValue(currentUserId);
                }
                else {
                    userInfo.setText("Error Retrieving Current User Information!");
                    System.out.println("Error Retrieving Current User Information!");
                    System.out.println(response.code());
                    System.out.println(response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<SpotifyUser> call, Throwable t) {

            }
        });

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final CreatePlaylistBody body = new CreatePlaylistBody("aaaaa playlist", true, false, "Test 123");
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://api.spotify.com/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                JsonPlaceHolderSpotifyApi apiDOA = retrofit.create(JsonPlaceHolderSpotifyApi.class);
                Call<CreatePlaylistResponse> call = apiDOA.createPlaylist(currentUserId, body, TOKEN);

                call.enqueue(new Callback<CreatePlaylistResponse>() {
                    @Override
                    public void onResponse(Call<CreatePlaylistResponse> call, Response<CreatePlaylistResponse> response) {
                        if (response.isSuccessful()) {
                            System.out.println("Successfully created playlist");
                            playlistId = response.body().getPlaylistId();
                            Intent intent = new Intent(getApplicationContext(), PlaylistActivity.class);
                            intent.putExtra("playlist_id", playlistId);
                            intent.putExtra("user_id", currentUserId);
                            intent.putExtra("TOKEN", TOKEN);
                            startActivity(intent);

                        }
                        else {
                            System.out.println("Fail to create playlist");
                            System.out.println(response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<CreatePlaylistResponse> call, Throwable t) {

                    }
                });

            }
        });
    }
}