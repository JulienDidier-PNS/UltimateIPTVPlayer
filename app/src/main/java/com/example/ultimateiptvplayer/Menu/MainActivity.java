package com.example.ultimateiptvplayer.Menu;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.ultimateiptvplayer.Channels.Exceptions.BadLoginException;
import com.example.ultimateiptvplayer.Fragments.Login.LoginFragment;
import com.example.ultimateiptvplayer.Fragments.Login.OnLoginListener;
import com.example.ultimateiptvplayer.Channels.PlaylistManager;
import com.example.ultimateiptvplayer.R;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements OnLoginListener {
    LoginFragment loginFragment;
    PlaylistManager playlistManager;
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
        id = "xx";
        password = "xx";
        url = "xx";
        this.playlistManager = new PlaylistManager();
        //download the m3u file
        playlistManager.downloadPlaylist(id,password,url,new PlaylistManager.PlaylistDownloadCallback() {
            @Override
            public void onPlaylistDownloaded(boolean success) {
                if (success) {
                    System.out.println("Downloaded playlist successfully");
                } else {
                    System.out.println("Failed to download playlist");
                }
            }
        }, this);

        /*playlistManager.downloadPlaylist(id, password, url, new PlaylistManager.PlaylistDownloadCallback() {
            @Override
            public void onPlaylistDownloaded(boolean success) {
                if (success) {
                    // Téléchargement réussi
                    // Faire quelque chose après le téléchargement réussi
                    assert loginFragment != null;
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    LoginFragment loginFragment = (LoginFragment) fragmentManager.findFragmentById(R.id.login_fragment);
                    fragmentTransaction.remove(loginFragment);
                    //je telecharges la playlist
                    fragmentTransaction.commit();
                } else {
                    // Échec du téléchargement
                    // Gérer l'échec du téléchargement
                    Toast.makeText(MainActivity.this, "Failed to download playlist", Toast.LENGTH_SHORT).show();
                }
            }
        }, this);*/
    }
}