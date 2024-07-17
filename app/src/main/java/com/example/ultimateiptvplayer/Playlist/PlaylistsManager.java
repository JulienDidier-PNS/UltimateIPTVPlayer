package com.example.ultimateiptvplayer.Playlist;

import android.content.Context;
import android.net.Uri;

import com.example.ultimateiptvplayer.Download.FileDownloader;
import com.example.ultimateiptvplayer.Fragments.ProgressBar.ProgressCallBack;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class PlaylistsManager {
    public static PlaylistsManager instance;
    public static PlaylistsManager getInstance(Context context) {
        if (instance == null) {instance = new PlaylistsManager(context);}
        return instance;
    }

    private final Context appContext;
    private static int playlistCounter = 0;
    private final HashMap<Integer, Playlist> playlists = new HashMap<>();

    public PlaylistsManager(Context context) {this.appContext = context;initPlaylistCounter();}

    public void initPlaylistCounter() {
        //regarder combien il y a de playlist dans le dossier de l'app
        File directory = appContext.getExternalFilesDir(null);
        if (directory != null) {
            if (!directory.exists()) {
                directory.mkdirs();
            }
            File[] files = directory.listFiles();
            for (File file : files) {
                if (file.getName().contains("playlist")) {
                    playlistCounter++;
                }
            }
        }
        System.out.println("Playlist counter AFTER INITIALIZING : " + playlistCounter);
    }

    public void initCategories() {
        //build
    }

    public void addPlaylist(Context context, String id, String password, String playlistUrl,String playlistName, ProgressCallBack callback) throws IOException {
        // Add playlist to the list
        //Download the playlist
        System.out.println("Playlist name : " + playlistName);
        String url = "http://" + playlistUrl + "/get.php?username=" + id + "&password=" + password + "&output=ts&type=m3u_plus";
        Uri finalUrl = Uri.parse(url);
        //start the download
        FileDownloader.download(context,finalUrl, "playlist"+playlistCounter+".m3u8",callback);
        this.playlists.put(playlistCounter, new Playlist(finalUrl, playlistName, playlistCounter));
    }
}
