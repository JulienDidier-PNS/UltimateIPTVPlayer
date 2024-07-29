package com.example.ultimateiptvplayer.Entities.Download;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class UrlChecker {
    public static int performNetworkOperation(final String url) throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        Callable<Integer> networkTask = new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
                try {
                    return connection.getResponseCode();
                } finally {
                    connection.disconnect();
                }
            }
        };

        Future<Integer> future = executor.submit(networkTask);
        Integer result = future.get();  // Ceci attendra que la tâche soit terminée

        executor.shutdown();
        return result;
    }

}
