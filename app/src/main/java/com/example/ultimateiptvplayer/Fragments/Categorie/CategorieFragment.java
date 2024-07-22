package com.example.ultimateiptvplayer.Fragments.Categorie;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.ultimateiptvplayer.Channels.Channel;
import com.example.ultimateiptvplayer.Fragments.Categorie.ListView.CategoryAdapter;
import com.example.ultimateiptvplayer.Playlist.Playlist;
import com.example.ultimateiptvplayer.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CategorieFragment extends Fragment {
    private ListView categorieLV;
    private Spinner langageFilter;
    private Playlist playlist;
    private final List<String> FrenchOrder = Arrays.asList("EU | FRANCE"); // Replace with your specific order

    public CategorieFragment(Playlist playlist) {
        this.playlist = playlist;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categorie, container, false);
        categorieLV = view.findViewById(R.id.categories_LV);
        langageFilter = view.findViewById(R.id.langage_filter);

        //Build the Langage Filter Spinner
        List<String> langages_choice = new ArrayList<>();
        for(LANGAGES langage : LANGAGES.values()) {langages_choice.add(langage.getLangage());}

        langageFilter.setAdapter(new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, langages_choice));

       langageFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedLangage = langages_choice.get(position);
                System.out.println("Selected Langage: " + selectedLangage);

                //get the selected langage
                LANGAGES langage = LANGAGES.valueOf(selectedLangage);

                // Filter the categories based on the selected langage
                setupListView(langage);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        // Build the Channels ListView
        setupListView(LANGAGES.OTHER);

        return view;
    }

    private void setupListView(LANGAGES langage) {
        TreeMap<String, ArrayList<Channel>> channels = playlist.getAllChannels(); // Assurez-vous que la méthode getChannels() existe et retourne le TreeMap
        System.out.println("Categories Count: " + channels.size());
        List<String> categories = filterAndSortCategories(new ArrayList<>(channels.keySet()), langage);

        CategoryAdapter adapter = new CategoryAdapter(getContext(), categories);
        categorieLV.setAdapter(adapter);
    }

    /**
     * Filter and sort the categories based on the specific order
     * @param categories
     * @return
     */
    private List<String> filterAndSortCategories(List<String> categories, LANGAGES langage) {
        List<String> sortedCategories = new ArrayList<>();

        Pattern frenchPattern = Pattern.compile("EU \\| FRANCE", Pattern.CASE_INSENSITIVE);

        if(langage == LANGAGES.FR){
            for (String category : categories) {
                Matcher matcher = frenchPattern.matcher(category);
                if (matcher.find()) {
                    System.out.println("Matched: " + category);
                    sortedCategories.add(category);
                }
            }
        }


        // Optionally add remaining categories not in specificOrder
        for (String category : categories) {
            if (!sortedCategories.contains(category)) {
                sortedCategories.add(category);
            }
        }

        System.out.println("First categories: " + sortedCategories.get(0));

        return sortedCategories;
    }
}
