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
import com.example.bestapp2023.models.Reservation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class ReservationFragment extends LogFragment {

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
        View externalView = inflater.inflate(R.layout.fragment_reservation, container, false);

        //Reservation Pippino = new Reservation("Annika","Annikapizza","20");
       // FirebaseWrapper.RTDatabase.getDbReservation().setValue(Pippino);


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
