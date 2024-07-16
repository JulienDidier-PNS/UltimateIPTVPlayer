package com.example.ultimateiptvplayer;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;

import androidx.annotation.OptIn;
import androidx.media3.common.util.Log;
import androidx.media3.common.util.UnstableApi;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class FileDownloader {

    @OptIn(markerClass = UnstableApi.class)
    public static void download(Context context, Uri url, String filename) {
        new DownloadFileTask(context, url, filename).execute();
    }

    private static class DownloadFileTask extends AsyncTask<Void, Void, Void> {
        private Context context;
        private Uri url;
        private String filename;

        public DownloadFileTask(Context context, Uri url, String filename) {
            this.context = context;
            this.url = url;
            this.filename = filename;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                downloadFile(url, filename);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        private void downloadFile(Uri url, String filename) throws IOException {
            System.out.println("Downloading file from: " + url);
            System.out.println("Downloading file to: " + Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + "/UltimateIPTVPlayer/" + filename);

            File directory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + "/UltimateIPTVPlayer");

            if (!directory.exists()) {
                directory.mkdirs();
            }

            File fileName = new File(directory, filename);

            if (!fileName.exists()) {
                fileName.createNewFile();
            }
            URL u = new URL(url.toString());

            HttpURLConnection connect = (HttpURLConnection) u.openConnection();

            if (connect.getResponseCode() != HttpURLConnection.HTTP_OK) {
                Log.w("ERROR SERVER RET HTTP", connect.getResponseCode() + "");
            }

            try (InputStream is = connect.getInputStream();
                 FileOutputStream fos = new FileOutputStream(fileName)) {
                byte[] bytes = new byte[1024];
                int b;

                while ((b = is.read(bytes, 0, 1024)) != -1) {
                    fos.write(bytes, 0, b);
                }
            }
        }
    }
}
