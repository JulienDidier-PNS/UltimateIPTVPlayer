package com.example.ultimateiptvplayer.Fragments.Login;

public interface OnLoginListener {
    void onLoginSuccess(String id,String password);
    void onLoginFailure();
}
