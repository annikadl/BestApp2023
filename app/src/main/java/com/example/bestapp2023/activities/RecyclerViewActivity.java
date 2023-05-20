package com.example.bestapp2023.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.bestapp2023.R;
import com.example.bestapp2023.models.FirebaseWrapper;
import com.example.bestapp2023.models.MyPlaces;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewActivity extends AppCompatActivity {

    List<MyPlaces> PlacesList = new ArrayList<MyPlaces>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        //Prendo reference al nostro database
        DatabaseReference ref = FirebaseWrapper.RTDatabase.getDb();

        //TODO:AL MOMENTO PRENDO TUTTO IL DB SENZA NESSUN FILTRO

        MyPlaces.Collection PlacesCollection  = new MyPlaces.Collection<>(PlacesList);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                PlacesList.clear(); // Pulisci la lista prima di aggiungere nuovi elementi
                for (DataSnapshot data : snapshot.getChildren()) {
                    MyPlaces Place = data.getValue(MyPlaces.class);
                    System.out.println(Place.getName());
                    PlacesList.add(Place);
                    Log.d("Lista", "AGGIUNTO CORRETTAMENTE ELEMENTO ALLA LISTA");
                }
                MyPlaces.Collection PlacesCollection = new MyPlaces.Collection<>(PlacesList);

                // Inizializza e imposta l'adattatore solo dopo aver ottenuto i dati
                RecyclerView recyclerView = findViewById(R.id.recyclerView);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(RecyclerViewActivity.this));
                recyclerView.setAdapter(new PlaceAdapter((MyPlaces[]) PlacesCollection.places.toArray(new MyPlaces[0])));
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





       /* RecyclerView recyclerView = this.findViewById(R.id.recyclerView);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new PlaceAdapter((MyPlaces[]) PlacesCollection.places.toArray(new MyPlaces[PlacesCollection.places.size()])));*/

    }
}