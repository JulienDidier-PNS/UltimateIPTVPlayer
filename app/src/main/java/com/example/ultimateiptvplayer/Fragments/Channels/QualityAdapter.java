package com.example.ultimateiptvplayer.Fragments.Channels;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.ultimateiptvplayer.QUALITY;

import java.util.ArrayList;
import java.util.List;

public class QualityAdapter extends ArrayAdapter<QUALITY> {
    private Context context;
    private List<QUALITY> qualities;

    public QualityAdapter(Context context, List<QUALITY> qualities) {
        super(context, 0, qualities);
        this.context = context;
        this.qualities = qualities;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        QUALITY quality = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false);
        }

        TextView textView = convertView.findViewById(android.R.id.text1);
        textView.setText(quality.getQuality());

        return convertView;
    }
}
