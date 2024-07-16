package com.example.ultimateiptvplayer.Playlist;

import android.content.Context;
import android.net.Uri;

import com.example.ultimateiptvplayer.FileDownloader;

import java.io.IOException;
import java.util.ArrayList;

public class PlaylistsManager {
    private static int playlistCounter = 0;

    public void addPlaylist(Context context, String id, String password, String playlistUrl) throws IOException {
        // Add playlist to the list
        //Download the playlist
        FileDownloader.download(context,Uri.parse("http://" + playlistUrl + "/get.php?username=" + id + "&password=" + password + "&output=ts&type=m3u_plus"), "playlist"+playlistCounter+".m3u8");

    }
}
