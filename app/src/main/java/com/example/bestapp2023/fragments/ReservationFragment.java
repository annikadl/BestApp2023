package com.example.bestapp2023.fragments;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bestapp2023.R;
import com.example.bestapp2023.activities.ReservationAdapter;
import com.example.bestapp2023.models.FirebaseWrapper;
import com.example.bestapp2023.models.Reservation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class ReservationFragment extends LogFragment {

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.initArguments();
    }

    List<Reservation> ReservationList = new ArrayList<Reservation>();

    ListView ReservationsListView;
    private SimpleCursorAdapter cursorAdapter;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // See: https://developer.android.com/reference/android/view/LayoutInflater#inflate(org.xmlpull.v1.XmlPullParser,%20android.view.ViewGroup,%20boolean)
        View externalView = inflater.inflate(R.layout.fragment_reservation, container, false);
        //ReservationsListView = externalView.findViewById(android.R.id.list);
        Context context = container.getContext();

        //Reservation Pippino = new Reservation("Annika","Annikapizza","20");
        //FirebaseWrapper.RTDatabase.getDbReservation().setValue(Pippino);

        DatabaseReference ref = FirebaseWrapper.RTDatabase.getDbReservationLettura();

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ReservationList.clear(); // Pulisci la lista prima di aggiungere nuovi elementi
                for (DataSnapshot data : snapshot.getChildren()) {
                    ReservationList.add(data.getValue(Reservation.class));
                    Log.d("Lista", "AGGIUNTO CORRETTAMENTE ELEMENTO ALLA LISTA");

                }

                Reservation.Collection ReservationCollection = new Reservation.Collection<>(ReservationList);

                //Inizializza e imposta l'adattatore solo dopo ver ottenuto i dati
                RecyclerView recyclerView = externalView.findViewById(R.id.recyclerView2);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
                recyclerView.setAdapter(new ReservationAdapter((Reservation[]) ReservationCollection.reservations.toArray(new Reservation[0])));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        ref.addValueEventListener(valueEventListener);


        return externalView;
    }

    public List<Reservation> getReservationList() {
        return ReservationList;
    }
}
