package com.example.bestapp2023.fragments;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

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

        return externalView;
    }

    //Prendi la stringa per il nome
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    /*
        if (user != null) {
        String userFullName = user.getDisplayName();
        TextView fullname_text = getView().findViewById(R.id.fullname_text);
        fullname_text.setText(userFullName);
    } else {
        // No user is signed in
    }

    //Prendi la stringa per l'user
    //FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
        String userEmail = user.getEmail();
        TextView username_text = getView().findViewById(R.id.username_text);
        username_text.setText(userEmail);
    } else {
        // No user is signed in
    }

    //Gestione del signout dell'utente
    Button signout = getView().findViewById(R.id.signout);

        signout.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            auth.signOut();
        }
    });

     */


}