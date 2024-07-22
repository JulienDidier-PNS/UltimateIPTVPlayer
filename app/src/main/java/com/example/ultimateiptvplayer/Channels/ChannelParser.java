package com.example.ultimateiptvplayer.Channels;

import com.example.ultimateiptvplayer.Playlist.Playlist;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Objects;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChannelParser {

    public void updateChannels(Playlist playlist) {
        ArrayList<Channel> channels = new ArrayList<>();
        String filePath = playlist.getInternalPlaylistPath();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            Channel channel = null;

            //Reference all channels
            while ((line = reader.readLine()) != null) {
                //we wanted ONLY channels
                Pattern pattern = Pattern.compile("movie|series", Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(line);
                boolean contain_movie_or_series = matcher.find();

                if (line.startsWith("#EXTINF:")) {
                    String tvgId = extractAttribute(line, "tvg-id");
                    String tvgName = extractAttribute(line, "tvg-name");
                    String tvgLogo = extractAttribute(line, "tvg-logo");
                    String groupTitle = extractAttribute(line, "group-title");
                    String[] parts = line.split(",");
                    String channelName = parts.length > 1 ? parts[1] : "";

                    channel = new Channel(tvgId, tvgName, tvgLogo, groupTitle, channelName, "");
                } else if (line.startsWith("http://") || line.startsWith("https://")) {
                    //if the link does not contain movie or series
                    if(!contain_movie_or_series){
                        if (channel != null) {
                            channel.setUrl(line);
                            channels.add(channel);
                            System.out.println("Channel added" + channel);
                            channel = null;
                        }
                    }
                    else{break;}
                }
            }
            System.out.println("Channels: " + channels.size());

            //Sort by group title in an Hashmap
            TreeMap<String, ArrayList<Channel>> categories = new TreeMap<>();

            for (Channel c : channels) {
                if (!categories.containsKey(c.getGroupTitle())) {categories.put(c.getGroupTitle(), new ArrayList<>());}
                Objects.requireNonNull(categories.get(c.getGroupTitle())).add(c);
            }
            //Finally, set the channels for the playlist
            playlist.setChannels(categories);

        } catch (IOException e) {e.printStackTrace();}
    }

    private String extractAttribute(String line, String attribute) {
        String result = "";
        String search = attribute + "=\"";
        int start = line.indexOf(search);
        if (start != -1) {
            start += search.length();
            int end = line.indexOf("\"", start);
            if (end != -1) {
                result = line.substring(start, end);
            }
        }
        return result;
    }
}
