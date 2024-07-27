package com.example.ultimateiptvplayer;

public enum QUALITY {
    SD("SD"),
    HD("HD"),
    FHD("FHD"),
    HEVC("HEVC"),
    UNKNOWN("UNKNOWN");

    private final String quality;

    QUALITY(String quality) {
        this.quality = quality;
    }

    public String getQuality() {
        return quality;
    }
}
