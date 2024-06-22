package com.example.ultimateiptvplayer.Channels;

import android.content.Context;
import android.net.Uri;
import com.example.ultimateiptvplayer.Channels.Exceptions.BadLoginException;
import com.example.ultimateiptvplayer.Channels.PlaylistDownloader;

import java.io.IOException;

public class PlaylistManager {

    public interface PlaylistDownloadCallback {
        void onPlaylistDownloaded(boolean success);
    }

    public void downloadPlaylist(String id, String password, String playlistUrl, final PlaylistDownloadCallback callback, Context context) {
        Uri finalUrl = Uri.parse("http://" + playlistUrl + "/get.php?username=" + id + "&password=" + password + "&output=ts&type=m3u_plus");

        PlaylistDownloader.DownloadCallback downloadCallback = new PlaylistDownloader.DownloadCallback() {
            @Override
            public void onDownloadComplete(boolean success) {
                if (callback != null) {callback.onPlaylistDownloaded(success);}
            }
        };

        PlaylistDownloader.downloadFile(context,finalUrl.toString(),  downloadCallback);
    }

    public void downloadFile(final PlaylistDownloadCallback callback, Context context) {
        Uri finalUrl = Uri.parse("https://filesamples.com/samples/document/txt/sample1.txt");
        PlaylistDownloader.DownloadCallback downloadCallback = new PlaylistDownloader.DownloadCallback() {
            @Override
            public void onDownloadComplete(boolean success) {
                if (callback != null) {callback.onPlaylistDownloaded(success);}
            }
        };
        PlaylistDownloader.downloadFile(context,finalUrl.toString(),  downloadCallback);
    }
}
