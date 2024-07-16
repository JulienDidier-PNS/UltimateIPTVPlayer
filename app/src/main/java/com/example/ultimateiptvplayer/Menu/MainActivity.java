package com.example.ultimateiptvplayer.Menu;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.ultimateiptvplayer.Channels.Exceptions.BadLoginException;
import com.example.ultimateiptvplayer.FileDownloader;
import com.example.ultimateiptvplayer.Fragments.Login.LoginFragment;
import com.example.ultimateiptvplayer.Fragments.Login.OnLoginListener;
import com.example.ultimateiptvplayer.Playlist.PlaylistsManager;
import com.example.ultimateiptvplayer.R;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements OnLoginListener {
    LoginFragment loginFragment;
    PlaylistsManager playlistManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Add the login fragment to the activity if it's not already added
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        this.loginFragment = new LoginFragment();
        fragmentTransaction.add(R.id.fragment_container, loginFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onLogin(String id,String password,String url) throws IOException, BadLoginException {
        //if login is succesfull
        this.playlistManager = new PlaylistsManager();
        this.playlistManager.addPlaylist(getApplicationContext(),id,password,url);

    }
}