package com.example.ultimateiptvplayer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.ultimateiptvplayer.Fragments.Login.LoginFragment;
import com.example.ultimateiptvplayer.Playlist.PlaylistsManager;

import java.util.Objects;

public class MenuFragment extends Fragment {
    private final PlaylistsManager playlistManager;

    public MenuFragment(PlaylistsManager playlistManager) {
        this.playlistManager = playlistManager;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        System.out.println("MenuFragment onCreateView");

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.menu_layout, container, false);
    }

}
