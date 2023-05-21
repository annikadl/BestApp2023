package com.example.bestapp2023.fragments;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.bestapp2023.R;
import com.example.bestapp2023.activities.RecyclerViewActivity;
import com.example.bestapp2023.models.FirebaseWrapper;

import java.lang.reflect.Type;

public class HomeFragment extends LogFragment {

    String Type;
    String City;

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.initArguments();



       /* Spinner citta_menu = getView().findViewById(R.id.citta_menu);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.citta, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        citta_menu.setAdapter(adapter); */


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View externalView = inflater.inflate(R.layout.fragment_home, container, false);

        //Spinner per la scelta della città

        Spinner citta_menu = externalView.findViewById(R.id.citta_menu);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.citta, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        citta_menu.setAdapter(adapter);

        //INIZIALIZZO TUTTI I REFIREMNTI AGLI OGGETTI GRAFICI PRESENTI NEL HOME FRAGMENT, DA NOTARE
        //CHE VENGONO TUTTI ATTACCATI AL EXTERNALVIEW , OGGETTOCHE VIENE CHIAMATO PER LA GRAFICA
        //DEL FRAGMENT

        Button cerca = (Button) externalView.findViewById(R.id.cerca);

        RelativeLayout pizza = (RelativeLayout) externalView.findViewById(R.id.rlPizza);

        RelativeLayout dolci = (RelativeLayout) externalView.findViewById(R.id.rlDolci);

        RelativeLayout sushi = (RelativeLayout) externalView.findViewById(R.id.rlSushi);

        RelativeLayout cinese = (RelativeLayout) externalView.findViewById(R.id.rlCinese);

        RelativeLayout hamburger = (RelativeLayout) externalView.findViewById(R.id.rlHamburger);

        RelativeLayout piadina = (RelativeLayout) externalView.findViewById(R.id.rlPiadina);

        //PER CIASCUNO DI ESSI SETTO UN LISTENER CHE VA A SCRIVERE IL TIPO IN UNA STRINGA DA PASSARE
        //COME BUCKET ALLLA ACTIVITY DEL RECYCLER VIEW

        pizza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Type = "Pizza";
            }
        });

        dolci.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Type = "Dolci";
            }
        });

        sushi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Type = "Sushi";
            }
        });

        cinese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Type = "Cinese";
            }
        });

        hamburger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Type = "Hamburger";
            }
        });

        piadina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Type = "Piadina";
            }
        });

        //Metodo per passare il nome della città scelta nello Spinner come stringa
        citta_menu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (parent.getId() == R.id.citta_menu) {
                    City = parent.getSelectedItem().toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //LISTENER ASSOCIATO AL REALE CAMBIO DI SCHERMATA, OVVERO QUELLO DEL BOTTONE CERCA

        cerca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), RecyclerViewActivity.class);
                //PASSO COME BUCKET LE VARIABILI DA METTERE NELLA QUERY
                intent.putExtra("Type",Type);
                intent.putExtra("City",City);
                startActivity(intent);
                ((Activity) getActivity()).overridePendingTransition(0, 0);
            }
        });



        return externalView;
    }



}

