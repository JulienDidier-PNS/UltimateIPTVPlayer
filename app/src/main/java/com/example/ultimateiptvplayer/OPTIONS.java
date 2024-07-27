package com.example.ultimateiptvplayer;

public enum OPTIONS {
    CHANGE_PLAYLIST("Change Playlist"),
    ABOUT("About");

    private final String option;

    OPTIONS(String option) {
        this.option = option;
    }

    public String getOption() {
        return option;
    }
}
