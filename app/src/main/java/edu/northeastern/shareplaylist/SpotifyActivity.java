package edu.northeastern.shareplaylist;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import entity.IP;
import com.bumptech.glide.Glide;
import com.spotify.android.appremote.api.SpotifyAppRemote;
import com.spotify.sdk.android.auth.AuthorizationClient;
import com.spotify.sdk.android.auth.AuthorizationRequest;
import com.spotify.sdk.android.auth.AuthorizationResponse;


import entity.JsonPlaceHolderArtists;
import entity.JsonPlaceHolderApi;
import entity.JsonPlaceHolderSpotifyApi;
import entity.SpotifyUser;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class SpotifyActivity extends AppCompatActivity {
    private static final String CLIENT_ID = "78698dcc31fd4845919e96fc61514de8";
    private static final String REDIRECT_URI = "http://localhost:8888/";
    private String TOKEN = "";
    private TextView info;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spotify);
        Button login = findViewById(R.id.login);
        info = findViewById(R.id.info);
        info.setText("Please Login First!");

//

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spotifyLogin();

            }
        });


    }




    private void getCurrentUser() {
        if (TOKEN.equals("")) {
            Log.d("TOKEN EMPTY", "Empty TOKEN please login");
            return;
        }
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.spotify.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonPlaceHolderSpotifyApi apiDOA = retrofit.create(JsonPlaceHolderSpotifyApi.class);
        Call<SpotifyUser> call = apiDOA.getCurrentUser(TOKEN);
        call.enqueue(new Callback<SpotifyUser>() {
            @Override
            public void onResponse(Call<SpotifyUser> call, Response<SpotifyUser> response) {
                if (response.isSuccessful()) {
                    info.setText(response.body().getUserID());
                    System.out.println(123);
                }
                else {
                    System.out.println("wtf" + response.code());
                }
            }

            @Override
            public void onFailure(Call<SpotifyUser> call, Throwable t) {
                System.out.println(321);
            }
        });

    }
    private void spotifyLogin() {
        AuthorizationRequest.Builder builder =
                new AuthorizationRequest.Builder(CLIENT_ID, AuthorizationResponse.Type.TOKEN, REDIRECT_URI);

        builder.setScopes(new String[]{"streaming", "playlist-modify-public"});
        AuthorizationRequest request = builder.build();

        AuthorizationClient.openLoginActivity(this, 1337, request);
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        // Check if result comes from the correct activity
        if (requestCode == 1337) {
            AuthorizationResponse response = AuthorizationClient.getResponse(resultCode, intent);
            Log.d("result", "onActivityResult: ");
            switch (response.getType()) {
                // Response was successful and contains auth token
                case TOKEN:
                    // Handle successful response
                    TOKEN = response.getAccessToken();
                    info.setText("Successfully Logged In!");
                    Log.d("good ", response.getAccessToken());
                    if (TOKEN.equals("")) {
                        Toast.makeText(getApplicationContext(), "Please Login First!", Toast.LENGTH_SHORT).show();
                    }

                    else {
                        Intent i = new Intent(getApplicationContext(), MainScreen.class);
                        i.putExtra("TOKEN", TOKEN);
                        startActivity(i);
                    }
                    break;

                // Auth flow returned an error
                case ERROR:
                    info.setText("Unable to login to Spotify!");
                    Log.d("error ", response.getError());
                    // Handle error response
                    break;

                // Most likely auth flow was cancelled
                default:
                    info.setText("Unexpected Error logging in");
                    Log.d("default ", response.getType().toString());
                    // Handle other cases
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {

    }




}