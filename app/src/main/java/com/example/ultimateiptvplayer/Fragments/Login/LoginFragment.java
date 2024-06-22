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

import com.example.ultimateiptvplayer.R;
import com.google.android.material.textfield.TextInputEditText;

public class LoginFragment extends Fragment {

    private TextInputEditText idInput;
    private TextInputEditText passwordInput;
    private Button loginButton;
    private OnLoginListener callback;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
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

        // Set an OnClickListener on the button
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle button click here
                String id = idInput.getText().toString();
                String password = passwordInput.getText().toString();

                if (validateLogin(id, password)) {
                    callback.onLoginSuccess(id,password);
                } else {
                    callback.onLoginFailure();
                }
            }
        });

        return view;
    }

    private boolean validateLogin(String id, String password) {
        // Add your validation logic here
        return id.equals("user") && password.equals("password");
    }
}
