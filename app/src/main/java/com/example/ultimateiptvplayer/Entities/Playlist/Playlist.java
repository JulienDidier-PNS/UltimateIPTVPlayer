package com.example.ultimateiptvplayer.Entities.Playlist;

import com.example.ultimateiptvplayer.Entities.Channels.Channel;
import com.example.ultimateiptvplayer.Entities.Channels.ChannelParser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.TreeMap;

public class Playlist {
    private final ChannelParser channelParser;
    /**
     * The Internal path of the playlist
     */
    private final String internalPlaylistPath;
    /**
     * The name of the playlist
     */
    private String name;
    TreeMap<String, ArrayList<Channel>> channels = new TreeMap<>();

    public Playlist(String internalPlaylistPath, String name, int id) {
        this.internalPlaylistPath = internalPlaylistPath;
        this.name = name;
        this.channelParser = new ChannelParser();
    }

    public void updateChannels() {
        channelParser.updateChannels(this);
    }

    public String getInternalPlaylistPath() {
        return internalPlaylistPath;
    }

    public void setChannels(TreeMap<String,ArrayList<Channel>> channels) {
        this.channels = channels;
    }

    public ArrayList<Channel> getChannelsByCategoryName(String category) {return channels.get(category);}

    public TreeMap<String, ArrayList<Channel>> getAllChannels() {
        return channels;
    }

    public void addChannelToFavorites(Channel channel) throws IOException, ChannelAlreadyInFavoritesException {
        try{saveFavorites(channel);}
        catch (ChannelAlreadyInFavoritesException e){throw new ChannelAlreadyInFavoritesException(e.getMessage());}

    }
    //créé un fichier m3u8 qui contient la liste des favoris
    public void saveFavorites(Channel channel) throws IOException, ChannelAlreadyInFavoritesException{
        File file = new File(Paths.get(internalPlaylistPath).getParent().toString() + "/favorites.m3u8");
        if(!file.exists()) {
            file.createNewFile();
            BufferedWriter writer = new BufferedWriter(new java.io.FileWriter(file));
            writer.write("#EXTM3U\n");
            writer.close();
        }
        //create a channel clone
        Channel channelClone = new Channel(channel.getTvgId(), channel.getTvgName(), channel.getTvgLogo(), channel.getGroupTitle(), channel.getChannelName(), channel.getUrl());
        channelClone.setGroupTitle("Favorites");

        //get the favorites channels
        ArrayList<Channel> favorites = channels.get("Favorites");
        if(favorites == null) {
            favorites = new ArrayList<>();
            favorites.add(channelClone);
            channels.put("Favorites", favorites);
        } else {
            //check if the channel is already in the favorites
            for(Channel c : favorites) {
                if(c.getTvgName().equals(channelClone.getTvgName())) {throw new ChannelAlreadyInFavoritesException("The channel is already in the favorites");}
            }
            favorites.add(channelClone);
            //write the favorites channels to the file
            BufferedWriter writer = new BufferedWriter(new java.io.FileWriter(file,true));
            writer.write(channelParser.getM3U8Channel(channelClone));
            writer.close();
        }
    }
}
