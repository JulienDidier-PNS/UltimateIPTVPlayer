package com.example.ultimateiptvplayer.Channels;

import android.widget.ImageView;

public class Channel {
    ImageView channelLogo;
    String channelName;
    String channelUrl;
    String Country;
    String Category;

    public Channel(ImageView channelLogo, String channelName, String channelUrl, String Country, String Category) {
        this.channelLogo = channelLogo;
        this.channelName = channelName;
        this.channelUrl = channelUrl;
        this.Country = Country;
        this.Category = Category;
    }
}
