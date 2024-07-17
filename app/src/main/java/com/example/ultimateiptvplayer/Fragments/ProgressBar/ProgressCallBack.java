package com.example.ultimateiptvplayer.Fragments.ProgressBar;

/**
 * This Interface allow us to follow a download task progress
 */
public interface ProgressCallBack {
    void onProgressUpdate(int progress);
    void onDownloadComplete();
    void onDownloadError(String error);
}
