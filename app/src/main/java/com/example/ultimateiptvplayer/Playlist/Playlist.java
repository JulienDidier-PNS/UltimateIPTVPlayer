package com.example.ultimateiptvplayer.Playlist;

import android.net.Uri;

import com.example.ultimateiptvplayer.Channels.Channel;
import com.example.ultimateiptvplayer.Channels.ChannelParser;

import java.util.ArrayList;

public class Playlist {
    private ChannelParser channelParser;
    /**
     * The URL of the playlist
     */
    private String internalPlaylistPath;
    /**
     * The name of the playlist
     */
    private String name;
    /**
     * The id of the playlist
     */
    private int id;
    private ArrayList<Channel> channels;

    public Playlist(String internalPlaylistPath, String name, int id) {
        this.internalPlaylistPath = internalPlaylistPath;
        this.name = name;
        this.id = id;
        this.channelParser = new ChannelParser();
    }

    public void updateChannels() {
        channelParser.updateChannels(this);
    }

    public String getInternalPlaylistPath() {
        return internalPlaylistPath;
    }

    public void setChannels(ArrayList<Channel> channels) {
        this.channels = channels;
    }

    public Channel getChannel(int index) {
        return channels.get(index);
    }

}
