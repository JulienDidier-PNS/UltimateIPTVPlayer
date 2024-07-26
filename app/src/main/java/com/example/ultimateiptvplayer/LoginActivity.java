package com.example.ultimateiptvplayer;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.ultimateiptvplayer.Channels.Exceptions.BadLoginException;
import com.example.ultimateiptvplayer.Fragments.Login.LoginFragment;
import com.example.ultimateiptvplayer.Fragments.Login.OnLoginListener;
import com.example.ultimateiptvplayer.Fragments.ProgressBar.ProgressBarFragment;
import com.example.ultimateiptvplayer.Playlist.PlaylistsManager;

import java.io.IOException;

public class LoginActivity extends AppCompatActivity implements OnLoginListener, OnDownloadListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Fragment loginFragment = new LoginFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.main_login_fragment, loginFragment).commit();
    }

    @Override
    public void onLogin(String id,String password,String url,String playlistName) throws IOException, BadLoginException {
        // If login is successful
        PlaylistsManager playlistManager = PlaylistsManager.getInstance(getApplicationContext());

        // Create and show the download fragment
        ProgressBarFragment progressBarFragment = new ProgressBarFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_login_fragment, progressBarFragment);
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
        switchToPlaylistNavigation();
    }

    private void switchToPlaylistNavigation(){
        // If download is complete
        System.out.println("Download complete, switching to playlist navigation");
        Intent navigationActivity = new Intent(this, NavigationActivity.class);
        startActivity(navigationActivity);
    }

    @Override
    public void onDownloadError(String error) {

    }
}
