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

import com.example.ultimateiptvplayer.Fragments.Categorie.CategorieFragment;
import com.example.ultimateiptvplayer.Playlist.PlaylistsManager;

public class MenuFragment extends Fragment {
    private final PlaylistsManager playlistManager;
    private final CategorieFragment categorieFragment;

    public MenuFragment(PlaylistsManager playlistManager) {
        this.playlistManager = playlistManager;
        this.categorieFragment = new CategorieFragment(playlistManager.getCurrentPlaylist());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        System.out.println("MenuFragment onCreateView");
        //Build the Channels ListView
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.categories_fragment, categorieFragment);
        transaction.commit();

        return inflater.inflate(R.layout.menu_layout, container, false);
    }

}
