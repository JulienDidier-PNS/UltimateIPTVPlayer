package com.example.ultimateiptvplayer.Menu.Channels;

import android.net.Uri;

public class PlaylistManager {
    Uri playlist_url = Uri.parse("");
    String id;
    String password;

    public PlaylistManager(String id, String password) {
        this.id = id;
        this.password = password;
    }

    public void downloadPlaylist() {
        // Download the playlist from the server
        System.out.println("Downloading playlist for user: " + id + " with password: " + password);
    }

}
