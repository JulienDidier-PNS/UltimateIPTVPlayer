package com.example.ultimateiptvplayer.Download;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;

import androidx.annotation.OptIn;
import androidx.media3.common.util.Log;
import androidx.media3.common.util.UnstableApi;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import com.example.ultimateiptvplayer.Fragments.ProgressBar.ProgressCallBack;

public class FileDownloader {

    @OptIn(markerClass = UnstableApi.class)
    public static void download(Context context, Uri url, String playlistPath, ProgressCallBack callback) {
        new DownloadFileTask(context, url, playlistPath, callback).execute();
    }

    private static class DownloadFileTask extends AsyncTask<Void, Integer, String> {
        private Context context;
        private Uri url;
        private String path;
        private ProgressCallBack callback;

        public DownloadFileTask(Context context, Uri url, String playlistPath, ProgressCallBack callback) {
            this.context = context;
            this.url = url;
            this.path = playlistPath;
            this.callback = callback;
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                downloadFile(context, url, path);
            } catch (IOException e) {
                e.printStackTrace();
                return e.getMessage();
            }
            return null;
        }

        @OptIn(markerClass = UnstableApi.class)
        private void downloadFile(Context context, Uri url, String path) throws IOException {
            File directory = context.getExternalFilesDir(null);
            if (directory != null) {
                URL u = new URL(url.toString());

                HttpURLConnection connect = (HttpURLConnection) u.openConnection();

                if (!directory.exists()) {
                    directory.mkdirs();
                }

                File fileName = new File(path);

                if (!fileName.exists()) {
                    fileName.createNewFile();
                }

                int fileLength = connect.getContentLength();
                System.out.println("File length: " + fileLength);

                try (InputStream is = connect.getInputStream();
                     FileOutputStream fos = new FileOutputStream(fileName)) {
                    byte[] bytes = new byte[1024];
                    double total = 0;
                    int b;

                    double stock_progress = 0.0;
                    int progress_drop = 0;

                    while ((b = is.read(bytes, 0, 1024)) != -1) {
                        if(progress_drop < 1000000){progress_drop += 1;}
                        else{
                            System.out.println("Downloaded: " + total + " of " + fileLength + " bytes");
                            stock_progress =  (total * 100) / fileLength;
                            //Double progress_final = stock_progress;
                            System.out.println("Progress: " + stock_progress + "%");
                        }
                        total += b;
                        fos.write(bytes, 0, b);
                        if (fileLength > 0) {
                            int progress = (int) (total * 100 / fileLength);
                            publishProgress(progress);
                        }
                    }
                }
            } else {
                throw new IOException("External storage not available");
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            if (callback != null && values.length > 0) {
                callback.onProgressUpdate(values[0]);
            }
        }

        @Override
        protected void onPostExecute(String result) {
            if (callback != null) {
                if (result == null) {
                    callback.onDownloadComplete();
                } else {
                    callback.onDownloadError(result);
                }
            }
        }
    }
}
