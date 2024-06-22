package com.example.ultimateiptvplayer.Menu;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.ultimateiptvplayer.Fragments.Login.LoginFragment;
import com.example.ultimateiptvplayer.Fragments.Login.OnLoginListener;
import com.example.ultimateiptvplayer.Menu.Channels.PlaylistManager;
import com.example.ultimateiptvplayer.R;

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
        fragmentTransaction.add(R.id.login_fragment, loginFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onLoginSuccess(String id,String password) {
        assert loginFragment != null;
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        LoginFragment loginFragment = (LoginFragment) fragmentManager.findFragmentById(R.id.login_fragment);
        fragmentTransaction.remove(loginFragment);
        //je telecharges la playliste
        this.playlistManager = new PlaylistManager(id,password);
        this.playlistManager.downloadPlaylist();
        fragmentTransaction.commit();
    }

    @Override
    public void onLoginFailure() {
        // Display a toast message to the user
        Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show();
    }
}