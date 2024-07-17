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
    private Uri url;
    /**
     * The name of the playlist
     */
    private String name;
    /**
     * The id of the playlist
     */
    private int id;
    private ArrayList<Channel> channels;

    public Playlist(Uri url, String name, int id) {
        this.url = url;
        this.name = name;
        this.id = id;
    }

    public void updateChannels(ArrayList<Channel> channels) {
        this.channels = channels;
    }

}
