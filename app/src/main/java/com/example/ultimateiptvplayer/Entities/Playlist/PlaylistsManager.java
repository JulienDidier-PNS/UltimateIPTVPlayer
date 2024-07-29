package com.example.ultimateiptvplayer.Entities.Playlist;

import android.content.Context;
import android.net.Uri;

import com.example.ultimateiptvplayer.Entities.Channels.Channel;
import com.example.ultimateiptvplayer.Entities.Download.FileDownloader;
import com.example.ultimateiptvplayer.Fragments.ProgressBar.ProgressCallBack;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class PlaylistsManager {
    public static PlaylistsManager instance;
    public static PlaylistsManager getInstance(Context context) {
        if (instance == null) {instance = new PlaylistsManager(context);}
        return instance;
    }

    private final Context appContext;
    private static int playlistCounter = 0;
    private final ArrayList<Playlist> playlists = new ArrayList<>();
    private Playlist currentPlaylist;

    public PlaylistsManager(Context context) {
        this.appContext = context;
        initPlaylistCounter();
        initPlaylists();
    }

    public void addChannelToFavorites(Channel channel) throws IOException, ChannelAlreadyInFavoritesException {
        this.currentPlaylist.addChannelToFavorites(channel);
    }

    public int getPlaylistCounter() {
        return playlistCounter;
    }

    public Playlist getCurrentPlaylist() {
        return currentPlaylist;
    }

    /**
     * Call when the PlaylistManager is created.
     *
     */
    private void initPlaylists(){
        String playlistPath = Objects.requireNonNull(appContext.getExternalFilesDir(null)).getPath() + "/playlist.m3u8";

        if(new File(playlistPath).exists()){
            Playlist newPlaylist = new Playlist(playlistPath, "playlist", playlistCounter);
            this.playlists.add(newPlaylist);
            this.currentPlaylist = newPlaylist;
            playlistCounter++;
            System.out.println(this.playlists.size() + " playlists added");
        }
        else{
            System.out.println("No playlist found, creating an empty list");
            this.currentPlaylist = null;
        }


    }

    /**
     * Initialize the playlist counter, count the number of playlist in the app folder
     */
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
        //System.out.println("Playlist counter AFTER INITIALIZING : " + playlistCounter);
    }

    /**
     * Add a playlist to the list
     * @param context is the Application context
     * @param id is the playlist username
     * @param password is the playlist password
     * @param playlistUrl is the playlist URL
     * @param playlistName is the playlist name which is choosen by the user
     * @param callback is the callback for the download progress
     * @throws IOException if the download fails
     */
    public void addPlaylist(Context context, String id, String password, String playlistUrl,String playlistName, ProgressCallBack callback) throws IOException {
        //Add playlist to the list
        //Download the playlist
        System.out.println("Playlist id : " + id);
        String url = "http://" + playlistUrl + "/get.php?username=" + id + "&password=" + password + "&output=ts&type=m3u_plus";
        String filename = "playlist.m3u8";
        String playlistPath = Objects.requireNonNull(context.getExternalFilesDir(null)).getPath() + "/" + filename;
        Uri finalUrl = Uri.parse(url);
        //start the download

        System.out.println(playlistPath);
        FileDownloader.download(context,finalUrl, playlistPath,callback);

        this.playlists.add(new Playlist(playlistPath, playlistName, playlistCounter));
        playlistCounter++;
    }

    public void removePlaylist(int id) {
        this.playlists.remove(id);
        playlistCounter--;
    }

    public void deleteCurrentPlaylist() {
        //remove the playlist file
        File file = new File(this.currentPlaylist.getInternalPlaylistPath());
        file.delete();
        if(file.exists()){
            System.out.println("File not deleted");
        }
        else{
            System.out.println("File deleted");
            this.playlists.remove(this.currentPlaylist);
            playlistCounter--;
        }
    }
}
