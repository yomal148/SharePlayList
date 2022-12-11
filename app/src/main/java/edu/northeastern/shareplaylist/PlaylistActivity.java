package edu.northeastern.shareplaylist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

import entity.AddTrackResponse;
import entity.Artist;
import entity.Item;
import entity.ItemAdapter;
import entity.JsonPlaceHolderSpotifyApi;
import entity.PlaylistItemAdapter;
import entity.PlaylistResult;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PlaylistActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<Item> itemList;
    FloatingActionButton fab;
    String TOKEN;
    String playlistId;
    String userId;
    Button send;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);

        playlistId = getIntent().getStringExtra("playlist_id");
        TOKEN = getIntent().getStringExtra("TOKEN");
        userId = getIntent().getStringExtra("user_id");
        System.out.println("TOKEN" + TOKEN);
        System.out.println("Playlist id " + playlistId);
        System.out.println("userId " + userId);
        this.itemList = new ArrayList<>();
        this.recyclerView = findViewById(R.id.playlist_view);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.spotify.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonPlaceHolderSpotifyApi apiDOA = retrofit.create(JsonPlaceHolderSpotifyApi.class);
        Call<PlaylistResult> fetchPlaylistItems = apiDOA.getPlaylistItems(playlistId, TOKEN);
        fetchPlaylistItems.enqueue(new Callback<PlaylistResult>() {
            @Override
            public void onResponse(Call<PlaylistResult> call, Response<PlaylistResult> response) {
                if (response.isSuccessful()) {
                    Item[] items = response.body().getItemsArray();
                    itemList = new ArrayList<>();
                    for (Item i: items) {
                        itemList.add(i);
                    }

//                    for (Item i: items){
//                        for (Artist a: i.track.getAlbum().getArtists()){
//                            System.out.println(a.getArtistName());
//                        }
//                    }
                    PlaylistItemAdapter adapter = new PlaylistItemAdapter(itemList, TOKEN, playlistId);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(adapter);
                }

                else {
                    System.out.println(111);
                    System.out.println(response.code());
                }
            }

            @Override
            public void onFailure(Call<PlaylistResult> call, Throwable t) {

            }
        });

        //Log.d("what ", fetchPlaylistItems.);
//        fetchPlaylistItems.enqueue(new Callback<PlaylistResult>() {
//            @Override
//            public void onResponse(Call<PlaylistResult> call, Response<PlaylistResult> response) {
//                if (response.isSuccessful()) {
//                    Item[] items = response.body().getItemsArray();
//                    itemList = new ArrayList<>();
//                    for (Item i: items) {
//                        itemList.add(i);
//                    }
//                    ItemAdapter adapter = new ItemAdapter(itemList, TOKEN, playlistId);
//                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
//                    recyclerView.setLayoutManager(layoutManager);
//                    recyclerView.setItemAnimator(new DefaultItemAnimator());
//                    recyclerView.setAdapter(adapter);
//                }
//
//                else {
//                    System.out.println(111);
//                    System.out.println(response.code());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<PlaylistResult> call, Throwable t) {
//
//            }
//
//        });
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SearchSong.class);
                intent.putExtra("TOKEN", TOKEN);
                intent.putExtra("playlist_id", playlistId);
                startActivity(intent);
            }
        });

        send = findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MailActivity.class);
                ArrayList<String> list = new ArrayList<>();
                for (Item item: itemList) {
                    list.add(item.track.getSongName());
                }
                intent.putStringArrayListExtra("playlist", list);
                startActivity(intent);
            }
        });
    }
}