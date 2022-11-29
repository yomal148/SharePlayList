//package edu.northeastern.shareplaylist;
//
//import androidx.appcompat.app.AlertDialog;
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.app.ProgressDialog;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.ProgressBar;
//import android.widget.TextView;
//import entity.IP;
//import com.bumptech.glide.Glide;
//import com.spotify.android.appremote.api.SpotifyAppRemote;
//import com.spotify.sdk.android.auth.AuthorizationClient;
//import com.spotify.sdk.android.auth.AuthorizationRequest;
//import com.spotify.sdk.android.auth.AuthorizationResponse;
//
//
//import entity.JsonPlaceHolderArtists;
//import entity.JsonPlaceHolderApi;
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//import retrofit2.Retrofit;
//import retrofit2.converter.gson.GsonConverterFactory;
//
//
//public class SpotifyActivity extends AppCompatActivity {
//    private static final String CLIENT_ID = "78698dcc31fd4845919e96fc61514de8";
//    private static final String REDIRECT_URI = "http://localhost:8888/";
//    private String TOKEN = "";
//    private TextView info;
//    private ImageView icon;
//    private TextView popularity;
//    private TextView name;
//    private TextView genre;
//    private String iconUrl;
//    private int rank;
//    private String artistName;
//    private String genreString;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_spotify);
//        Button login = findViewById(R.id.login);
//        info = findViewById(R.id.wtf);
//        info.setText("Please Login First!");
//        icon = findViewById(R.id.icon);
//        name = findViewById(R.id.name);
//        popularity = findViewById(R.id.popularity);
//        genre = findViewById(R.id.genre);
////
//
//        login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                spotifyLogin();
//            }
//        });
//
//        Button fetchEminem = findViewById(R.id.eminem);
//        fetchEminem.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                runOnDifferentThread(view);
//
//            }
//        });
//    }
//
//    private void fetch() {
//
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                if (TOKEN.equals("")) {
//                    info.setText("Please Signin First!");
//                    openDialog();
//
//                    return;
//                }
//                Retrofit retrofit = new Retrofit.Builder()
//                        .baseUrl("https://api.spotify.com/")
//                        .addConverterFactory(GsonConverterFactory.create())
//                        .build();
//                JsonPlaceHolderArtists artists = retrofit.create(JsonPlaceHolderArtists.class);
//                Call<Artist> call = artists.getArtist("Bearer " + TOKEN);
//                final ProgressDialog progressDoalog;
//                progressDoalog = new ProgressDialog(SpotifyActivity.this);
//                progressDoalog.setMax(100);
//                progressDoalog.setMessage("Its loading....");
//                progressDoalog.setTitle("ProgressDialog bar example");
//                progressDoalog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//                // show it
//                progressDoalog.show();
//                call.enqueue(new Callback<Artist>() {
//                    @Override
//                    public void onResponse(Call<Artist> call, Response<Artist> response) {
//                        Log.d("code", "" + response.code());
//                        if (response.isSuccessful()) {
//                            Log.d("passed", "onResponse: " + response.body().getFirstImageUrl());
//                            Glide.with(getApplicationContext()).load(response.body().getFirstImageUrl()).into(icon);
//                            name.setText("Artist Name: " + response.body().getName());
//                            int popularityValue = response.body().getPopularity();
//                            popularity.setText("Rank: " + popularityValue);
//                            genre.setText("Genre: \n" + response.body().getGenres());
//                            artistName = response.body().getName();
//
//                            iconUrl = response.body().getFirstImageUrl();
//                            rank = response.body().getPopularity();
//                            genreString = response.body().getGenres();
//
//                        }
//                        progressDoalog.dismiss();
//                    }
//
//                    @Override
//                    public void onFailure(Call<Artist> call, Throwable t) {
//                        Log.d("Error", "onFailure: " +  t.getMessage());
//                        progressDoalog.dismiss();
//                    }
//                });
//
//            }
//        });
//
//
//    }
//
//
//    public void runOnDifferentThread(View view) {
//        differentThread differentThread = new differentThread();
//        differentThread.start();
//    }
//    private void openDialog() {
//        AlertDialog dialog = new AlertDialog.Builder(SpotifyActivity.this)
//                .setTitle("Please Login first")
//                .setMessage("Please Login to Spotify first before fetching data!")
//                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        dialogInterface.dismiss();
//                    }
//                }).create();
//
//        dialog.show();
//    }
//
//    private void spotifyLogin() {
//        AuthorizationRequest.Builder builder =
//                new AuthorizationRequest.Builder(CLIENT_ID, AuthorizationResponse.Type.TOKEN, REDIRECT_URI);
//
//        builder.setScopes(new String[]{"streaming"});
//        AuthorizationRequest request = builder.build();
//
//        AuthorizationClient.openLoginActivity(this, 1337, request);
//    }
//
//
//    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
//        super.onActivityResult(requestCode, resultCode, intent);
//
//        // Check if result comes from the correct activity
//        if (requestCode == 1337) {
//            AuthorizationResponse response = AuthorizationClient.getResponse(resultCode, intent);
//            Log.d("result", "onActivityResult: ");
//            switch (response.getType()) {
//                // Response was successful and contains auth token
//                case TOKEN:
//                    // Handle successful response
//                    TOKEN = response.getAccessToken();
//                    info.setText("Successfully Logged In!");
//                    Log.d("good ", response.getAccessToken());
//                    break;
//
//                // Auth flow returned an error
//                case ERROR:
//                    info.setText("Unable to login to Spotify!");
//                    Log.d("error ", response.getError());
//                    // Handle error response
//                    break;
//
//                // Most likely auth flow was cancelled
//                default:
//                    info.setText("Unexpected Error logging in");
//                    Log.d("default ", response.getType().toString());
//                    // Handle other cases
//            }
//        }
//    }
//
//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        outState.putString("icon", iconUrl);
//        outState.putString("genre", genreString);
//        outState.putString("name", artistName);
//        outState.putInt("rank", rank);
//        outState.putString("TOKEN", TOKEN);
//        super.onSaveInstanceState(outState);
//    }
//
//    @Override
//    protected void onRestoreInstanceState(Bundle savedInstanceState) {
//
//
//        this.iconUrl = savedInstanceState.getString("icon");
//        this.genreString = savedInstanceState.getString("genre");
//        this.artistName = savedInstanceState.getString("name");
//        this.rank = savedInstanceState.getInt("rank");
//        this.TOKEN = savedInstanceState.getString("TOKEN");
//
//        this.genre.setText("Genre: " + genreString);
//        this.popularity.setText("Rank: " + rank);
//        Glide.with(getApplicationContext()).load(iconUrl).into(icon);
//        this.name.setText("Name: " + artistName);
//
//        super.onRestoreInstanceState(savedInstanceState);
//    }
//
//    class differentThread extends Thread {
//        @Override
//        public void run() {
//            fetch();
//        }
//    }
//
//
//}