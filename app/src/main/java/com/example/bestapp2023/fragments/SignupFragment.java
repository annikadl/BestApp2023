package com.example.bestapp2023.fragments;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Patterns;
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
    String email_required = "Indirizzo email necessario";
    String password_required = "Password necessaria";
    String name_required = "Nome necessario";
    String error_email = "Formato non valido";
    String different_password = "Le password sono diverse";
    String short_password = "Lunghezza almeno 8 caratteri";

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

                // Perform SignIn
                if(Credential_Validation(email, password, password2, name)) {
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
            }
        });

        return externalView;
    }

    public boolean Credential_Validation(EditText email, EditText password, EditText password2, EditText name) {

        //check di campi vuoti
        if (email.getText().toString().isEmpty() ||
                password.getText().toString().isEmpty() ||
                password2.getText().toString().isEmpty() || name.getText().toString().isEmpty()) {
            email.setError(email_required);
            password.setError(password_required);
            password2.setError(password_required);
            name.setError(name_required);
            return false;
        }

        //check formato email: deve esserci la chiocciola e il .com/.it/etc.
        if (!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
            email.setError(error_email);
            return false;
        }

        //check password uguali
        if (!password.getText().toString().equals(password2.getText().toString())) {
            Toast
                    .makeText(SignupFragment.this.requireActivity(), different_password, Toast.LENGTH_LONG)
                    .show();
            return false;
        }

        //check lunghezza password
        if (password.getText().length() < password_length) {
            Toast
                    .makeText(SignupFragment.this.requireActivity(), short_password, Toast.LENGTH_LONG)
                    .show();
            return false;
        }
        return true;
    }
}