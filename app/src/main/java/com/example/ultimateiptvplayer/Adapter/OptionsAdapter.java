package com.example.ultimateiptvplayer.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.ultimateiptvplayer.Enum.SETTINGS_OPTIONS;

import java.util.List;

public class OptionsAdapter extends ArrayAdapter<SETTINGS_OPTIONS> {
    private Context context;
    private List<SETTINGS_OPTIONS> options;

    public OptionsAdapter(Context context, List<SETTINGS_OPTIONS> options) {
        super(context, 0,options);
        this.context = context;
        this.options = options;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SETTINGS_OPTIONS option = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false);
        }

        TextView textView = convertView.findViewById(android.R.id.text1);
        assert option != null;
        textView.setText(option.getOption());

        return convertView;
    }
}
