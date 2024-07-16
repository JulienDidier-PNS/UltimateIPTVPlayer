package com.example.ultimateiptvplayer.Channels;

import android.content.Context;
import android.os.AsyncTask;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class PlaylistDownloader {

    public interface DownloadCallback {
        void onDownloadComplete(boolean success);
    }

    public static void downloadFile(Context context, String fileURL, DownloadCallback callback) {
        new FileDownloadTask(context, callback).execute(fileURL);
    }

    private static class FileDownloadTask extends AsyncTask<String, Void, Boolean> {
        private Context context;
        private DownloadCallback callback;

        public FileDownloadTask(Context context, DownloadCallback callback) {
            this.context = context;
            this.callback = callback;
        }

        //Download file in background
        @Override
        protected Boolean doInBackground(String... params) {
            String fileURL = params[0];

            try {
                URL url = new URL(fileURL);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    String fileName = "";
                    String disposition = connection.getHeaderField("Content-Disposition");

                    if (disposition != null) {
                        // Extraire le nom du fichier du header Content-Disposition
                        int index = disposition.indexOf("filename=");
                        if (index > 0) {
                            fileName = disposition.substring(index + 10, disposition.length() - 1);
                        }
                    } else {
                        // Extraire le nom du fichier de l'URL
                        fileName = fileURL.substring(fileURL.lastIndexOf("/") + 1, fileURL.length());
                    }

                    // Ouvrir un flux de lecture à partir de la connexion HTTP
                    InputStream inputStream = connection.getInputStream();

                    // Ouvrir un flux de sortie vers le fichier local dans le stockage interne
                    FileOutputStream outputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);

                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }

                    outputStream.close();
                    inputStream.close();
                    connection.disconnect();

                    return true;
                } else {
                    return false;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (callback != null) {
                callback.onDownloadComplete(result);
            }
        }
    }
}
