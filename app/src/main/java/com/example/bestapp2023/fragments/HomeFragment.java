package com.example.bestapp2023.fragments;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.bestapp2023.R;
import com.example.bestapp2023.activities.RecyclerViewActivity;
import com.example.bestapp2023.models.FirebaseWrapper;

import java.lang.reflect.Type;
import java.util.ArrayList;

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

        //INIZIALIZZO TUTTI I RIFEREMNTI AGLI OGGETTI GRAFICI PRESENTI NEL HOME FRAGMENT, DA NOTARE
        //CHE VENGONO TUTTI ATTACCATI AL EXTERNALVIEW , OGGETTOCHE VIENE CHIAMATO PER LA GRAFICA
        //DEL FRAGMENT

        Button cerca = (Button) externalView.findViewById(R.id.cerca);

        RelativeLayout pizza = (RelativeLayout) externalView.findViewById(R.id.rlPizza);

        RelativeLayout dolci = (RelativeLayout) externalView.findViewById(R.id.rlDolci);

        RelativeLayout sushi = (RelativeLayout) externalView.findViewById(R.id.rlSushi);

        RelativeLayout cinese = (RelativeLayout) externalView.findViewById(R.id.rlCinese);

        RelativeLayout hamburger = (RelativeLayout) externalView.findViewById(R.id.rlHamburger);

        RelativeLayout piadina = (RelativeLayout) externalView.findViewById(R.id.rlPiadina);

        //Dichiaro tutti i TextView che mi servono
        TextView tvpizza = externalView.findViewById(R.id.tvPizza);
        TextView tvsushi = externalView.findViewById(R.id.tvSushi);
        TextView tvpiadina = externalView.findViewById(R.id.tvPiadina);
        TextView tvhamburger = externalView.findViewById(R.id.tvHamburger);
        TextView tvcinese = externalView.findViewById(R.id.tvCinese);
        TextView tvdolci = externalView.findViewById(R.id.tvDolci);

        //aggiungo tutti i TextView dichiarati in un ArrayList: mi servirà per la f.ne AllOthersToBlack
        ArrayList<TextView> listatv = new ArrayList<TextView>();
        listatv.add(tvpizza);
        listatv.add(tvsushi);
        listatv.add(tvpiadina);
        listatv.add(tvhamburger);
        listatv.add(tvcinese);
        listatv.add(tvdolci);

        //PER CIASCUN OGGETTO GRAFICO DI ESSI SETTO UN LISTENER CHE VA A SCRIVERE IL TIPO IN UNA STRINGA DA PASSARE
        //COME BUCKET ALLLA ACTIVITY DEL RECYCLER VIEW

        pizza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Type != "Pizza") {
                    Type = "Pizza";
                    tvpizza.setTextColor(Color.RED);
                    AllOthersToBlack(tvpizza, listatv);
                }
                else {
                    Type = "";
                    tvpizza.setTextColor(Color.BLACK);
                }
            }
        });

        dolci.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Type != "Dolci") {
                    Type = "Dolci";
                    tvdolci.setTextColor(Color.RED);
                    AllOthersToBlack(tvdolci, listatv);
                }
                else {
                    Type = "";
                    tvdolci.setTextColor(Color.BLACK);
                }
            }
        });

        sushi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Type != "Sushi") {
                    Type = "Sushi";
                    tvsushi.setTextColor(Color.RED);
                    AllOthersToBlack(tvsushi, listatv);
                }
                else {
                    Type = "";
                    tvsushi.setTextColor(Color.BLACK);
                }
            }
        });

        cinese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Type != "Cinese") {
                    Type = "Cinese";
                    tvcinese.setTextColor(Color.RED);
                    AllOthersToBlack(tvcinese, listatv);
                }
                else {
                    Type = "";
                    tvcinese.setTextColor(Color.BLACK);
                }
            }
        });

        hamburger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Type != "Hamburger") {
                    Type = "Hamburger";
                    tvhamburger.setTextColor(Color.RED);
                    AllOthersToBlack(tvhamburger, listatv);
                }
                else {
                    Type = "";
                    tvhamburger.setTextColor(Color.BLACK);
                }
            }
        });

        piadina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Type != "Piadina") {
                    Type = "Piadina";
                    tvpiadina.setTextColor(Color.RED);
                    AllOthersToBlack(tvpiadina, listatv);
                }
                else {
                    Type = "";
                    tvpiadina.setTextColor(Color.BLACK);
                }
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

    public void AllOthersToBlack(TextView selezione, ArrayList<TextView> listatv) {
        for (int i = 0; i < listatv.size(); i++) {
            if(selezione != listatv.get(i)) {
                listatv.get(i).setTextColor(Color.BLACK);
            }

        }
    }


}

