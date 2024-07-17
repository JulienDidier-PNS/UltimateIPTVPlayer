package com.example.ultimateiptvplayer.Channels;

import androidx.annotation.NonNull;

public class Channel {
    private String tvgId;
    private String tvgName;
    private String tvgLogo;
    private String groupTitle;
    private String channelName;
    private String url;

    public Channel(String tvgId, String tvgName, String tvgLogo, String groupTitle, String channelName, String url) {
        this.tvgId = tvgId;
        this.tvgName = tvgName;
        this.tvgLogo = tvgLogo;
        this.groupTitle = groupTitle;
        this.channelName = channelName;
        this.url = url;
    }

    // Getters and setters for each property
    public String getTvgId() {
        return tvgId;
    }

    public void setTvgId(String tvgId) {
        this.tvgId = tvgId;
    }

    public String getTvgName() {
        return tvgName;
    }

    public void setTvgName(String tvgName) {
        this.tvgName = tvgName;
    }

    public String getTvgLogo() {
        return tvgLogo;
    }

    public void setTvgLogo(String tvgLogo) {
        this.tvgLogo = tvgLogo;
    }

    public String getGroupTitle() {
        return groupTitle;
    }

    public void setGroupTitle(String groupTitle) {
        this.groupTitle = groupTitle;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Channel{" +
                "tvgId='" + tvgId + '\'' +
                ", tvgName='" + tvgName + '\'' +
                ", tvgLogo='" + tvgLogo + '\'' +
                ", groupTitle='" + groupTitle + '\'' +
                ", channelName='" + channelName + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
