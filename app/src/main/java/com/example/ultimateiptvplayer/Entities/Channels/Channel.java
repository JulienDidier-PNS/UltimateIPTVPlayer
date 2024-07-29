package com.example.ultimateiptvplayer.Entities.Channels;

import com.example.ultimateiptvplayer.Enum.QUALITY;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Channel {
    private String tvgId;
    private String tvgName;
    private String tvgLogo;
    private String groupTitle;
    private String channelName;
    private String url;
    private QUALITY quality;

    public Channel(String tvgId, String tvgName, String tvgLogo, String groupTitle, String channelName, String url) {
        this.tvgId = tvgId;
        this.tvgName = tvgName;
        this.tvgLogo = tvgLogo;
        this.groupTitle = groupTitle;
        this.channelName = channelName;
        this.url = url;
        detectQuality();
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

    public QUALITY getQuality() {
        return quality;
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

    public void detectQuality() {
        //Add other pattern for other langages
        Pattern HDPattern = Pattern.compile(" HD$", Pattern.CASE_INSENSITIVE);
        Pattern FHDPattern = Pattern.compile(" FHD$", Pattern.CASE_INSENSITIVE);
        Pattern HEVCPattern = Pattern.compile(" HEVC$", Pattern.CASE_INSENSITIVE);
        Pattern SDPattern = Pattern.compile(" SD$", Pattern.CASE_INSENSITIVE);

        Matcher HDMatcher = HDPattern.matcher(this.channelName);
        Matcher FHDMatcher = FHDPattern.matcher(this.channelName);
        Matcher HEVCmatcher = HEVCPattern.matcher(this.channelName);
        Matcher SDmatcher = SDPattern.matcher(this.channelName);

        if (HDMatcher.find()) {this.quality = QUALITY.HD;}
        else if (FHDMatcher.find()) {this.quality = QUALITY.FHD;}
        else if (HEVCmatcher.find()) {this.quality = QUALITY.HEVC;}
        else if(SDmatcher.find()) {this.quality = QUALITY.SD;}
        else {this.quality =  QUALITY.UNKNOWN;}
    }


}
