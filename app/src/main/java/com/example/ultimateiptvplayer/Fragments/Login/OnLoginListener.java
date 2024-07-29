package com.example.ultimateiptvplayer.Fragments.Login;

import com.example.ultimateiptvplayer.Entities.Channels.Exceptions.BadLoginException;

import java.io.IOException;

public interface OnLoginListener {
    void onLogin(String id,String password,String url,String playlistName) throws IOException, BadLoginException;
}
