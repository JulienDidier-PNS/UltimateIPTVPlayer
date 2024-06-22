package com.example.ultimateiptvplayer.Fragments.Login;

import com.example.ultimateiptvplayer.Channels.Exceptions.BadLoginException;

import java.io.IOException;

public interface OnLoginListener {
    void onLogin(String id,String password,String url) throws IOException, BadLoginException;
}
