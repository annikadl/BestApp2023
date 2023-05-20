package com.example.bestapp2023.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bestapp2023.R;
import com.example.bestapp2023.activities.EnterActivity;
import com.example.bestapp2023.models.FirebaseWrapper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;

public class LoginFragment extends LogFragment {
    ImageButton home;
    ImageButton profile;
    ImageButton invitefriend;

    private static final FirebaseAuth AUTH_STATUS = FirebaseAuth.getInstance();

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
        View externalView = inflater.inflate(R.layout.fragment_login, container, false);

        //metodo per passare al fragment di SignUp
        TextView link = externalView.findViewById(R.id.switchtosignup);
        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((EnterActivity)LoginFragment.this.requireActivity()).renderFragment(false);

                /*Fragment fragment = new SignupFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container_login, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();*/
            }
        });

        //Login managment

        Button button = externalView.findViewById(R.id.loginButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText email = externalView.findViewById(R.id.email);
                EditText password = externalView.findViewById(R.id.password);

                TextInputLayout error_email = externalView.findViewById(R.id.emailbox);
                TextInputLayout error_password = externalView.findViewById(R.id.passwordbox);

                /*if (email.getText().toString().isEmpty() || password.getText().toString().isEmpty()) {
                    // TODO: Better error handling + remove this hardcoded strings
                    email.setError("Email mancante");
                    password.setError("Password mancante");
                    return;
                }*/

                if(Credential_Validation(email, password, error_email, error_password)) {

                    // Perform SignIn
                    FirebaseWrapper.Auth auth = new FirebaseWrapper.Auth();
                    auth.signIn(
                            email.getText().toString(),
                            password.getText().toString(),
                            FirebaseWrapper.Callback
                                    .newInstance(LoginFragment.this.requireActivity(),
                                            LoginFragment.this.callbackName,
                                            LoginFragment.this.callbackPrms)
                    );

                    /*Toast
                            .makeText(LoginFragment.this.requireActivity(), "Invalid inputs", Toast.LENGTH_LONG)
                            .show();
                    return;*/
                }
                else
                    error_email.getError();
            }
        });



        return externalView;
    }

    private boolean Credential_Validation(EditText email, EditText password, TextInputLayout error_email, TextInputLayout error_password) {
        boolean email_valid = this.Email_Validation(email, error_email);

        boolean password_valid = this.Password_Validation(password, error_password);

        return (email_valid && password_valid);
    }

    public boolean Email_Validation(@NonNull EditText email, TextInputLayout error_email) {
        String error_string;

        if (email.getText().toString().isEmpty()) {
            error_string = "Indirizzo email obbligatorio";
            error_email.setError(error_string);
        }

        else if (!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
            error_string = "Formato non valido";
            error_email.setError(error_string);
        }

        else  {
            error_email.setErrorEnabled(false);
            return true;
        }
        return false;
    }

    public boolean Password_Validation(@NonNull EditText password, TextInputLayout error_password) {
        String error_string;
        int password_length = 8;

        if (password.getText().toString().isEmpty()) {
            error_string = "Obbligatorio";
            error_password.setError(error_string);
        }

        else if (password.getText().length() < password_length) {
            error_string = "Lunghezza almeno 8 caratteri";
            error_password.setError(error_string);
        } else  {
            error_password.setErrorEnabled(false);
            return true;
        }
        return false;
    }
}