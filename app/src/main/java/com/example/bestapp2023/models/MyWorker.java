package com.example.bestapp2023.models;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.ListenableWorker;
import androidx.work.WorkerParameters;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.firebase.database.DatabaseReference;

public class MyWorker extends ListenableWorker {
    /**
     * @param appContext   The application {@link Context}
     * @param workerParams Parameters to setup the internal state of this worker
     */
    public MyWorker(@NonNull Context appContext, @NonNull WorkerParameters workerParams) {
        super(appContext, workerParams);
    }

    @NonNull
    @Override
    public ListenableFuture<Result> startWork() {

        //PRENDO RIFERIMENTO AL DB DELLE PRENOTAZIONI DELL'UTENTE
        DatabaseReference ref = FirebaseWrapper.RTDatabase.getDbReservation();

        //ELIMINO TUTTE LE PRENOTAZIONI ASSOCIATE ALL'UTENTE

        ref.removeValue();

        return null;
    }
}
