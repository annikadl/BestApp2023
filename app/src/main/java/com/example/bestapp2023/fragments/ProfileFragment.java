package com.example.bestapp2023.fragments;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.bestapp2023.R;
import com.example.bestapp2023.models.FirebaseWrapper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


// l'ho messo che estende LogFragment così abbiamo già una serie di funzioni generiche
// per la gestione dei fragment
public class ProfileFragment extends LogFragment {

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
        View externalView = inflater.inflate(R.layout.fragment_profile, container, false);

        FirebaseWrapper.Auth auth = new FirebaseWrapper.Auth();

        //Gestione del signout dell'utente
       Button signout = externalView.findViewById(R.id.signout);

        Log.d("Auth", "sono qui");

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View externalView) {
                auth.signOut();

                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();

                // Creare un'istanza del LoginFragment
                LoginFragment loginFragment = new LoginFragment();

                // Eseguire una transazione di fragment e sostituire il fragment corrente con il LoginFragment
                fragmentManager.beginTransaction()
                        .replace(R.id.container_login, loginFragment)
                        .commit();
            }
        });

        return externalView;
    }




}