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

        // RECUPERO I DATI PASSATI COME BUNDLE, ALLA FINE NON UTILIZZATO LO TENGO PER IL FUTURO
       // String[] IncData = getArguments().getStringArray("MailUser");

        // CERCO IL RIFERIMENTO ASSOCIATO AL OGGETTO UI USER E EMAIL

        TextView Email = (TextView) externalView.findViewById(R.id.username_text);

        TextView User = (TextView) externalView.findViewById(R.id.fullname_text);

        // SETTO IL TESTO DI ENTRAMBI I CAMPI

        Email.setText(GetEmail());
        User.setText(GetUser());

        return externalView;
    }

String GetUser() {
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    if (user != null) {
        String userFullName = user.getDisplayName();

        return userFullName;

    } else {
        // No user is signed in
        return null;
    }

}

String GetEmail(){
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    if (user != null) {
        String userEmail = user.getEmail();

        return userEmail;

    } else {
        // No user is signed in
        return null;
    }
}

}