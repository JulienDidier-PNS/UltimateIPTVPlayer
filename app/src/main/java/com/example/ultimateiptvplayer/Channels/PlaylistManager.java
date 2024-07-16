package com.example.ultimateiptvplayer.Channels;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;

public class PlaylistManager {

    public interface PlaylistDownloadCallback {
        void onPlaylistDownloaded(boolean success);
    }

    public void downloadPlaylist(String id, String password, String playlistUrl, final PlaylistDownloadCallback callback, Context context) {
        Uri finalUrl = Uri.parse("http://" + playlistUrl + "/get.php?username=" + id + "&password=" + password + "&output=ts&type=m3u_plus");
        System.out.println("Downloading playlist from: " + finalUrl);
        DownloadManager downloadmanager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(finalUrl);
        request.setTitle("Playlist Download");
        request.setDescription("Downloading playlist from " + finalUrl);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setVisibleInDownloadsUi(false);
        request.setDestinationInExternalFilesDir(context, null, "playlist.m3u");
        downloadmanager.enqueue(request);
        /*
        PlaylistDownloader.DownloadCallback downloadCallback = new PlaylistDownloader.DownloadCallback() {
            @Override
            public void onDownloadComplete(boolean success) {
                if (callback != null) {callback.onPlaylistDownloaded(success);}
            }
        };

        PlaylistDownloader.downloadFile(context,finalUrl.toString(),  downloadCallback);
        */
    }

    public void downloadSampleFile(final PlaylistDownloadCallback callback, Context context) {
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
