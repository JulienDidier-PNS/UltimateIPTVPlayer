package com.example.ultimateiptvplayer;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.ultimateiptvplayer.Channels.Exceptions.BadLoginException;
import com.example.ultimateiptvplayer.Fragments.Login.LoginFragment;
import com.example.ultimateiptvplayer.Fragments.Login.OnLoginListener;
import com.example.ultimateiptvplayer.Fragments.ProgressBar.ProgressBarFragment;
import com.example.ultimateiptvplayer.Playlist.PlaylistsManager;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements OnLoginListener, OnDownloadListener {

    MenuFragment menuFragment;
    LoginFragment loginFragment;
    PlaylistsManager playlistManager;
    ProgressBarFragment progressBarFragment;

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
    public void onLogin(String id,String password,String url,String playlistName) throws IOException, BadLoginException {
        // If login is successful
        this.playlistManager = new PlaylistsManager(getApplicationContext());

        // Create and show the download fragment
        this.progressBarFragment = new ProgressBarFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, progressBarFragment);
        transaction.commit();

        // Start the download and pass the download fragment as the callback
        playlistManager.addPlaylist(getApplicationContext(), id, password, url, playlistName, progressBarFragment);

    }

    /**
     * This method is called when the download is complete
     * It will replace the download fragment (now done) to the next step of playlist registration -> naming the playlist
     */
    @Override
    public void onDownloadComplete() {
        // If download is complete
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        this.menuFragment = new MenuFragment();
        this.playlistManager.initCategories();
        transaction.replace(R.id.fragment_container, menuFragment);
        transaction.commit();
    }

    @Override
    public void onDownloadError(String error) {

    }
}