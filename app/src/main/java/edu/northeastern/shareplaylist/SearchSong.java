package edu.northeastern.shareplaylist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.widget.SearchView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;


import entity.Item;
import entity.ItemAdapter;
import entity.JsonPlaceHolderArtists;
import entity.JsonPlaceHolderSpotifyApi;
import entity.SearchResult;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchSong extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<Item> itemList;
    private ItemAdapter itemAdapter;
    private SearchView searchView;
    private String TOKEN = "";
    private String playlistId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_song);
        playlistId = getIntent().getStringExtra("playlist_id");
        TOKEN = getIntent().getStringExtra("TOKEN");

        System.out.println("TOKEN" + TOKEN);
        this.itemList = new ArrayList<>();
        this.searchView = findViewById(R.id.search_bar);
        this.recyclerView = findViewById(R.id.search_result);


        searchView.clearFocus();
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://api.spotify.com/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                JsonPlaceHolderSpotifyApi apiDOA = retrofit.create(JsonPlaceHolderSpotifyApi.class);
                Call<SearchResult> searchCall = apiDOA.getSearchResult( s, "track", 5, TOKEN);

                searchCall.enqueue(new Callback<SearchResult>() {
                    @Override
                    public void onResponse(Call<SearchResult> call, Response<SearchResult> response) {
                        if (response.isSuccessful()) {
                            Item[] items = response.body().getItemsArray();
                            itemList = new ArrayList<>();
                            for (Item i: items) {
                                itemList.add(i);

                            }
                            ItemAdapter adapter = new ItemAdapter(itemList, TOKEN, playlistId);
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
                    public void onFailure(Call<SearchResult> call, Throwable t) {

                    }
                });
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
//                filterList(s);
                return false;
            }
        });


    }

    private void searchSong(String keyword) {


    }

//    private void filterList(String s) {
//        List<Item> filteredList = new ArrayList<>();
//        for (Item i: filteredList) {
//            if (i.getItemName().toLowerCase().contains())
//        }
//    }
}