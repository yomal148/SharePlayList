package edu.northeastern.shareplaylist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.sql.SQLOutput;

public class PlaylistActivity extends AppCompatActivity {

    FloatingActionButton fab;
    String TOKEN;
    String playlistId;
    String userId;


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
    }
}