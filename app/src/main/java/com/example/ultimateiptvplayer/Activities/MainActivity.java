package com.example.ultimateiptvplayer.Activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ultimateiptvplayer.Entities.Playlist.PlaylistsManager;
import com.example.ultimateiptvplayer.R;

public class MainActivity extends AppCompatActivity {
    PlaylistsManager playlistManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Check if a playlist is already saved
        playlistManager = PlaylistsManager.getInstance(getApplicationContext());
        if(playlistManager.getCurrentPlaylist() != null){
            //There is a playlist saved, use this one
            System.out.println("A Playlist already saved !");
            switchToPlaylistNavigation();
        }
        else{
            //There is no playlist saved, add the login fragment
            System.out.println("No playlist saved, switching to login activity");
            Intent loginActivity = new Intent(this, LoginActivity.class);
            startActivity(loginActivity);
        }
    }

    private void switchToPlaylistNavigation(){
        // If download is complete
        System.out.println("A playlist already exist ! Switching to playlist navigation");
        Intent navigationActivity = new Intent(this, NavigationActivity.class);
        startActivity(navigationActivity);
    }
}