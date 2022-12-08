package edu.northeastern.shareplaylist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import entity.CreatePlaylistBody;
import entity.CreatePlaylistResponse;
import entity.IP;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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
    private static final String CLIENT_ID = "e2ea72863e3a4ec196da146a74a79aac";
    private static final String REDIRECT_URI = "http://localhost:5037/";
    private String TOKEN = "";
    private TextView info;
    private String UUID = "";
    private String PIN = "";
    private String currentUserId = "";
    private String playlistId = "";
    private String playlistName = "";
    private DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spotify);
        Button login = findViewById(R.id.login);
        Button guestButton = findViewById(R.id.guest_button);
        info = findViewById(R.id.info);
        info.setText("To host a playlist, make sure you have a spotify account!");
        UUID = getIntent().getStringExtra("uuid");
        ref = FirebaseDatabase.getInstance().getReference();


//

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spotifyLogin();

            }
        });

        guestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText input = new EditText(getApplicationContext());
                input.setInputType(InputType.TYPE_CLASS_PHONE);
                AlertDialog dialog = new AlertDialog.Builder(SpotifyActivity.this)
                        .setTitle("Enter Pin Code")
                        .setMessage("Please Enter 4 Digit Code to Collaborate on Playlist")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ref.child("playlist").child(input.getText().toString()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                                        if (!task.isSuccessful()) {
                                            Log.e("firebase", "Error getting data", task.getException());
                                        }
                                        else {

                                            Intent i = new Intent(getApplicationContext(), PlaylistActivity.class);
                                            i.putExtra("TOKEN", task.getResult().child("token").getValue().toString());
                                            i.putExtra("UUID", UUID);
                                            i.putExtra("PIN", input.getText().toString());
                                            i.putExtra("user_id", task.getResult().child("user_id").getValue().toString());
                                            i.putExtra("playlist_id", task.getResult().child("playlist_id").getValue().toString());
                                            startActivity(i);
                                        }
                                    }
                                });
                            }
                        })
                        .setView(input)
                        .create();

                dialog.show();


            }
        });

    }





    private void spotifyLogin() {


        EditText pin_input = new EditText(getApplicationContext());
        EditText name_input = new EditText(getApplicationContext());
        pin_input.setInputType(InputType.TYPE_CLASS_PHONE);
        pin_input.setHint("Enter Pin Code");
        name_input.setHint("Enter playlist name");
        name_input.setInputType(InputType.TYPE_CLASS_TEXT);

        LinearLayout lila1= new LinearLayout(this);
        lila1.setOrientation(LinearLayout.VERTICAL);
        lila1.addView(pin_input);
        lila1.addView(name_input);

        AlertDialog dialog = new AlertDialog.Builder(SpotifyActivity.this)
                .setTitle("Enter Pin Code")
                .setMessage("Please Enter 4 Digit Code to Collaborate on Playlist")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        AuthorizationRequest.Builder builder =
                                new AuthorizationRequest.Builder(CLIENT_ID, AuthorizationResponse.Type.TOKEN, REDIRECT_URI);
                        builder.setScopes(new String[]{"streaming", "playlist-modify-public"});
                        AuthorizationRequest request = builder.build();
                        AuthorizationClient.openLoginActivity(SpotifyActivity.this, 1337, request);
                        PIN = pin_input.getText().toString();
                        playlistName = name_input.getText().toString();
                        dialogInterface.dismiss();
                    }
                })
                .setView(lila1)
                .create();

        dialog.show();




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
                    Log.d("good ", response.getAccessToken());
                    if (TOKEN.equals("")) {
                        Toast.makeText(getApplicationContext(), "Please Login First!", Toast.LENGTH_SHORT).show();
                    }

                    else {
                        TOKEN = "Bearer " + response.getAccessToken();
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
                                                 currentUserId = response.body().getUserID();
                                                 // update database user table
                                                 System.out.println("Current user id" + currentUserId);
                                                 final CreatePlaylistBody body = new CreatePlaylistBody(playlistName, true, false, "Playlist created by SharePlaylist App!");
                                                 Call<CreatePlaylistResponse> callPlaylist = apiDOA.createPlaylist(currentUserId, body, TOKEN);

                                                 callPlaylist.enqueue(new Callback<CreatePlaylistResponse>() {
                                                     @Override
                                                     public void onResponse(Call<CreatePlaylistResponse> call, Response<CreatePlaylistResponse> response) {
                                                         if (response.isSuccessful()) {
                                                             System.out.println("Successfully created playlist");
                                                             playlistId = response.body().getPlaylistId();
                                                             ref.child("playlist").child(PIN).child("token").setValue(TOKEN);
                                                             ref.child("playlist").child(PIN).child("user_id").setValue(currentUserId);
                                                             ref.child("playlist").child(PIN).child("playlist_id").setValue(playlistId);

                                                             Intent i = new Intent(getApplicationContext(), PlaylistActivity.class);
                                                             i.putExtra("TOKEN", TOKEN);
                                                             i.putExtra("UUID", UUID);
                                                             i.putExtra("PIN", PIN);
                                                             i.putExtra("user_id", currentUserId);
                                                             i.putExtra("playlist_id", playlistId);
                                                             startActivity(i);
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

                                             } else {

                                                 System.out.println("Error Retriving Current User Information!");
                                                 System.out.println(response.code());
                                                 System.out.println(response.errorBody());
                                             }
                                         }

                                         @Override
                                         public void onFailure(Call<SpotifyUser> call, Throwable t) {

                                         }
                        });





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