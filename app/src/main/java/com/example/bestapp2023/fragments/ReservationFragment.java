package com.example.bestapp2023.fragments;

import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.loader.content.CursorLoader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bestapp2023.R;
import com.example.bestapp2023.activities.MainActivity;
import com.example.bestapp2023.activities.PlaceAdapter;
import com.example.bestapp2023.activities.RecyclerViewActivity;
import com.example.bestapp2023.activities.ReservationAdapter;
import com.example.bestapp2023.models.FirebaseWrapper;
import com.example.bestapp2023.models.MyPlaces;
import com.example.bestapp2023.models.Reservation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
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
        Query query = ref.orderByKey();

        //query.addValueEventListener(new ValueEventListener() {
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ReservationList.clear(); // Pulisci la lista prima di aggiungere nuovi elementi
                for (DataSnapshot data : snapshot.getChildren()) {
                    //ESEGUO IL CHECK SULLA VARIABILE DATA, SE IL CAMPO CITY E UGUALE A QUELLO PASSATO
                    //INIZALIZZO OGGETTO DI CLASSE PLACES ASSOCIATO
                        //Reservation reservation = data.getValue(Reservation.class);
                        //System.out.println(reservation.getPlace_name());
                        //ReservationList.add(reservation);
                    Log.d("UID", user.getUid());
                    if(data.getKey().equals(user.getUid())) {
                        ReservationList.add(data.getValue(Reservation.class));
                        Log.d("Lista", "AGGIUNTO CORRETTAMENTE ELEMENTO ALLA LISTA");
                    }
                }

                Reservation.Collection ReservationCollection = new Reservation.Collection<>(ReservationList);

                //Inizializza e imposta l'adattatore solo dopo ver ottenuto i dati
                RecyclerView recyclerView = externalView.findViewById(R.id.recyclerView2);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
                recyclerView.setAdapter(new ReservationAdapter((Reservation[]) ReservationCollection.reservations.toArray(new Reservation[0])));

                /*ArrayAdapter<Reservation> TaskListArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ReservationList);
                ListView TaskDataListView = (ListView) externalView.findViewById(android.R.id.list);
                TaskDataListView.setAdapter(TaskListArrayAdapter);*/

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        query.addValueEventListener(valueEventListener);




        // CODICE PER CHIAMARE WORKER CHE OGNI 10 MINUTI CONTROLLA SE C'è UNA PRENOTAZIONE
        // TODO: prima implementare lettura da db

        /*
        // Crea un oggetto JobScheduler
        JobScheduler jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);

        // Crea un oggetto per il lavoro
        ComponentName componentName = new ComponentName(this, BookingReminderService.class);
J       obInfo jobInfo = new JobInfo.Builder(1, componentName)
        .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
        .setPeriodic(60 * 60 * 1000) // Esegui ogni ora
        .build();

// Avvia il lavoro
jobScheduler.schedule(jobInfo);
         */

        return externalView;
    }

}
