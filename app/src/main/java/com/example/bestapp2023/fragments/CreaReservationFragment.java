package com.example.bestapp2023.fragments;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.bestapp2023.R;
import com.example.bestapp2023.activities.MainActivity;
import com.example.bestapp2023.activities.RecyclerViewActivity;
import com.example.bestapp2023.models.FirebaseWrapper;
import com.example.bestapp2023.models.Reservation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;


public class CreaReservationFragment extends LogFragment {

    String orario;
    String persone;
    String ristoranti;

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.initArguments();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //PRENDO IL BUNDLE DI DATI PASSATO DALLA RECYCLERVIEW ACTIVITY

        Bundle bundle = this.getArguments();
        ArrayList<String> myArrayList = null;
        if (bundle != null) {
            myArrayList = bundle.getStringArrayList("Data");
        }



        // Inflate the layout for this fragment
        // See: https://developer.android.com/reference/android/view/LayoutInflater#inflate(org.xmlpull.v1.XmlPullParser,%20android.view.ViewGroup,%20boolean)
        View externalView = inflater.inflate(R.layout.fragment_creareservation, container, false);

        // SPINNER ORARIO

        Spinner dd_orario = externalView.findViewById(R.id.dd_orario);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.array_orario, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        dd_orario.setAdapter(adapter);

        dd_orario.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (parent.getId() == R.id.dd_orario) {
                    orario = parent.getSelectedItem().toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        // SPINNER PERSONE

        Spinner dd_persone = externalView.findViewById(R.id.dd_persone);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter_persone = ArrayAdapter.createFromResource(getContext(),
                R.array.array_persone, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        dd_persone.setAdapter(adapter_persone);


        dd_persone.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (parent.getId() == R.id.dd_persone) {
                    persone = parent.getSelectedItem().toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //SPINNER RISTORANTI

        Spinner dd_ristoranti = externalView.findViewById(R.id.dd_nome);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter_nome = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_dropdown_item,myArrayList);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        dd_ristoranti.setAdapter(adapter_nome);

        // non so perché me lo ha fatto dichiarare così
        final String[] selected_ristorante = new String[1];
        dd_ristoranti.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (parent.getId() == R.id.dd_nome) {
                    ristoranti = parent.getSelectedItem().toString();
                    // prendo valore selezionato per effettuare la scrittura su db.
                    selected_ristorante[0] = dd_ristoranti.getSelectedItem().toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ImageButton back = externalView.findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
         }

        });

       Button conferma_prenotazione = externalView.findViewById(R.id.conferma_prenotazione);

        conferma_prenotazione.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Reservation Paperino = new Reservation(selected_ristorante[0], "annikella","12", "2");
                FirebaseWrapper.RTDatabase.getDbReservation().setValue(Paperino);

            }

        });




        return externalView;
    }

}