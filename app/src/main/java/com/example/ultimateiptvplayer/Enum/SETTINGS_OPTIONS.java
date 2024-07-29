package com.example.ultimateiptvplayer.Enum;

public enum SETTINGS_OPTIONS {
    CHANGE_PLAYLIST("Change Playlist"),
    ABOUT("About");

    private final String option;

    SETTINGS_OPTIONS(String option) {
        this.option = option;
    }

    public String getOption() {
        return option;
    }
}
