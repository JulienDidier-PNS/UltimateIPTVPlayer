package com.example.ultimateiptvplayer.Enum;

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
