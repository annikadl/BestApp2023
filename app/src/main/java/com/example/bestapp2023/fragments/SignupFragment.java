package com.example.bestapp2023.fragments;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bestapp2023.R;
import com.example.bestapp2023.activities.MainActivity;
import com.example.bestapp2023.models.FirebaseWrapper;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignupFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignupFragment extends LogFragment {

    int password_length = 8;

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.initArguments();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // See: https://developer.android.com/reference/android/view/LayoutInflater#inflate(org.xmlpull.v1.XmlPullParser,%20android.view.ViewGroup,%20boolean)
        View externalView = inflater.inflate(R.layout.fragment_signup, container, false);

        TextView link = externalView.findViewById(R.id.switchtologin);
        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) SignupFragment.this.requireActivity()).renderFragment(true);

                /*Fragment fragment = new LoginFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container_login, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();*/

            }
        });

        Button button = externalView.findViewById(R.id.loginButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText email = externalView.findViewById(R.id.newemail);
                EditText password = externalView.findViewById(R.id.password1);
                EditText password2 = externalView.findViewById(R.id.password2);
                EditText name = externalView.findViewById(R.id.newname);

                if (email.getText().toString().isEmpty() ||
                        password.getText().toString().isEmpty() ||
                        password2.getText().toString().isEmpty() || name.getText().toString().isEmpty()) {
                    // TODO: Better error handling + remove this hardcoded strings
                    email.setError("Email is required");
                    password.setError("Password is required");
                    password2.setError("Password is required");
                    name.setError("Name is required");
                    return;
                }

                if (!password.getText().toString().equals(password2.getText().toString())) {
                    // TODO: Better error handling + remove this hardcoded strings
                    Toast
                            .makeText(SignupFragment.this.requireActivity(), "Le password sono diverse", Toast.LENGTH_LONG)
                            .show();
                    return;
                }

                if (password.getText().length() < password_length) {
                    // TODO: Better error handling + remove this hardcoded strings
                    Toast
                            .makeText(SignupFragment.this.requireActivity(), "Lunghezza almeno 8 caratteri", Toast.LENGTH_LONG)
                            .show();
                    return;
                }

                // Perform SignIn
                FirebaseWrapper.Auth auth = new FirebaseWrapper.Auth();
                auth.signUp(
                        name.getText().toString(),
                        email.getText().toString(),
                        password.getText().toString(),
                        FirebaseWrapper.Callback
                                .newInstance(SignupFragment.this.requireActivity(),
                                        SignupFragment.this.callbackName,
                                        SignupFragment.this.callbackPrms)
                );
            }
        });

        return externalView;
    }
}