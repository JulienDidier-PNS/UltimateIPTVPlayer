package com.example.ultimateiptvplayer.Fragments.Channels;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.ultimateiptvplayer.Channels.Channel;
import com.example.ultimateiptvplayer.QUALITY;
import com.example.ultimateiptvplayer.R;

import java.util.Set;
import java.util.TreeMap;
import java.util.ArrayList;

import android.widget.ArrayAdapter;
import android.widget.GridView;


public class ChannelQualityFragment extends Fragment{

    private GridView channelGrid;
    private final Set<QUALITY> qualities;
    private final TreeMap<QUALITY, ArrayList<Channel>> channelsByQuality;
    private final OnQualityListener onQualityListener;

    public ChannelQualityFragment(TreeMap<QUALITY, ArrayList<Channel>> channelsByQuality,OnQualityListener onQualityListener) {
        this.qualities = channelsByQuality.keySet();
        this.channelsByQuality = channelsByQuality;
        this.onQualityListener = onQualityListener;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_channels, container, false);
        this.channelGrid = view.findViewById(R.id.channels_grid);
        // Build the Quality GridLayout
        setupGridView();

       this.channelGrid.setOnItemClickListener((parent, view1, position, id) -> {
            onQualityListener.onQualityClick(position,(QUALITY) parent.getItemAtPosition(position));
        });
        return view;
    }

    private void setupGridView() {
        QualityAdapter qualityAdapter = new QualityAdapter(getContext(), new ArrayList<>(this.qualities));
        channelGrid.setAdapter(qualityAdapter);
    }
}
