package com.example.ultimateiptvplayer.Fragments.Categorie;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.ultimateiptvplayer.Channels.Channel;
import com.example.ultimateiptvplayer.Fragments.Categorie.ListView.CategoryAdapter;
import com.example.ultimateiptvplayer.Playlist.Playlist;
import com.example.ultimateiptvplayer.Playlist.PlaylistsManager;
import com.example.ultimateiptvplayer.R;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class CategorieFragment extends Fragment {
    private ListView categorieLV;
    private Playlist playlist;

    public CategorieFragment(Playlist playlist) {
        this.playlist = playlist;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categorie, container, false);
        categorieLV = view.findViewById(R.id.categories_LV);
        // Build the Channels ListView
        setupListView();

        return view;
    }

    private void setupListView() {
        TreeMap<String, ArrayList<Channel>> channels = playlist.getAllChannels(); // Assurez-vous que la méthode getChannels() existe et retourne le TreeMap
        List<String> categories = new ArrayList<>(channels.keySet());

        CategoryAdapter adapter = new CategoryAdapter(getContext(), categories);
        categorieLV.setAdapter(adapter);
    }
}
