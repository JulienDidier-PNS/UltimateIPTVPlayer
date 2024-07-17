package com.example.ultimateiptvplayer.Fragments.Login;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.ultimateiptvplayer.Channels.Exceptions.BadLoginException;
import com.example.ultimateiptvplayer.R;
import com.google.android.material.textfield.TextInputEditText;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.Objects;

public class LoginFragment extends Fragment {

    private TextInputEditText idInput;
    private TextInputEditText passwordInput;
    private Button loginButton;
    private TextInputEditText urlInput;
    private TextInputEditText playlistNameInput;
    private OnLoginListener callback;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            //Set the callback interface
            callback = (OnLoginListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnLoginSuccessListener");
        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        // Find views within the inflated layout
        idInput = view.findViewById(R.id.login_id_input);
        passwordInput = view.findViewById(R.id.login_password_input);
        loginButton = view.findViewById(R.id.login_button);
        urlInput = view.findViewById(R.id.login_url_input);
        playlistNameInput = view.findViewById(R.id.playlist_name);

        // Set an OnClickListener on the button
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get text information from the input fields
                String id = Objects.requireNonNull(idInput.getText()).toString();
                String password = Objects.requireNonNull(passwordInput.getText()).toString();
                String url = Objects.requireNonNull(urlInput.getText()).toString();
                String playlistName = Objects.requireNonNull(playlistNameInput.getText()).toString();

                try {
                    // Call the onLogin method of the callback
                    callback.onLogin(id,password,url,playlistName);
                } catch (IOException | BadLoginException e) {throw new RuntimeException(e);}
            }
        });

        return view;
    }
}
