package com.example.ultimateiptvplayer.Fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.ultimateiptvplayer.Enum.SETTINGS_OPTIONS;
import com.example.ultimateiptvplayer.OnResetPlaylistListener;
import com.example.ultimateiptvplayer.R;

import java.util.Arrays;

public class HeaderFragment extends Fragment {

    private final Context context;
    private final OnResetPlaylistListener onResetPlaylistListener;

    public HeaderFragment(Context context, OnResetPlaylistListener onResetPlaylistListener) {
        this.context = context;
        this.onResetPlaylistListener = onResetPlaylistListener;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_header, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        System.out.println("HeaderFragment.onViewCreated");
        super.onViewCreated(view, savedInstanceState);
        ImageButton btnSettings = view.findViewById(R.id.btn_settings);
        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSettingsDialog();
            }
        });
    }

    private void showSettingsDialog() {
        // Créez un layout pour la boîte de dialogue
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_spinner, null);

        // Trouvez le Spinner dans le layout
        Spinner spinner = dialogView.findViewById(R.id.dialog_spinner);

        ArrayAdapter<SETTINGS_OPTIONS> adapter = new ArrayAdapter<>(this.context, android.R.layout.simple_spinner_item, Arrays.asList(SETTINGS_OPTIONS.values()));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        SETTINGS_OPTIONS[] selectedOption = new SETTINGS_OPTIONS[1];

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedOption[0] = (SETTINGS_OPTIONS) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Aucune action nécessaire ici
            }
        });

        // Construire l'AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Sélectionner parmi les choix suivants")
                .setView(dialogView)
                .setPositiveButton("OK", (dialog, which) -> {
                    // Gérer le choix lorsqu'on appuie sur le bouton positif
                    Toast.makeText(context, "Vous avez sélectionné : " + selectedOption[0], Toast.LENGTH_SHORT).show();
                    // Ajoutez ici le traitement spécifique pour l'option sélectionnée
                    performActionBasedOnSelection(selectedOption[0]);
                })
                .setNegativeButton("Annuler", (dialog, which) -> dialog.dismiss());

        // Afficher le Dialog
        builder.create().show();
    }

    private void performActionBasedOnSelection(SETTINGS_OPTIONS selectedItem) {
        // Effectuez une action en fonction de l'élément sélectionné

        if(selectedItem.equals(SETTINGS_OPTIONS.CHANGE_PLAYLIST)){
            //CHANGER LA PLAYLIST
            this.onResetPlaylistListener.onResetPlaylist();
        }
        else{
            // Action par défaut
        }
    }

}
