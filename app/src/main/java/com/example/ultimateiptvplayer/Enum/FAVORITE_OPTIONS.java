package com.example.ultimateiptvplayer.Enum;

public enum FAVORITE_OPTIONS {
    ADD_TO_FAVORITE("Add to Favorite"),
    REMOVE_FROM_FAVORITE("Remove from Favorite");

    private final String option;

    FAVORITE_OPTIONS(String option) {
        this.option = option;
    }

    public String getOption() {
        return option;
    }
}
