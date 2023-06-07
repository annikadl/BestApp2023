package com.example.bestapp2023.activities;

import android.Manifest;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.bestapp2023.R;
import com.example.bestapp2023.models.Reservation;
import com.example.bestapp2023.fragments.ReservationFragment;

import java.util.ArrayList;
import java.util.List;

public class BookingReminderService extends JobService {
    private static final String TAG = "BookingReminderService";

    @Override
    public boolean onStartJob(JobParameters params) {
        // Avvia il lavoro in background per inviare i promemoria delle prenotazioni
        sendBookingReminders();

        // Indica che il lavoro è stato completato
        jobFinished(params, false);

        // Restituisce false se il lavoro è stato completato sincronicamente, true se è iniziato un lavoro asincrono
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        // Gestisci l'interruzione del lavoro, se necessario
        return true; // Ritorna true se si desidera ripetere il lavoro in caso di interruzione
    }

    private void sendBookingReminders() {
        // Recupera le prenotazioni future dal database o da una sorgente di dati
        List<Reservation> futureBookings = getFutureBookings();

        // Invia i promemoria per le prenotazioni future
        for (Reservation booking : futureBookings) {
            sendReminderNotification(booking);
        }
    }

    private List<Reservation> getFutureBookings() {
        List<Reservation> futureBookings = new ArrayList<>();
        // TODO: leggere da db le prenotazioni future
        return futureBookings;
    }

    private void sendReminderNotification(Reservation booking) {

        String tmp_string = booking.getTime();
        Double value = Double.parseDouble(tmp_string);
        long reminderTime = (long) (value - (15 * 60 * 1000)); // Notifica 15 minuti prima dell'evento

        // Costruisci la notifica per il promemoria della prenotazione
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "channel_id")
                .setContentTitle("Promemoria prenotazione")
                .setContentText("La tua prenotazione è programmata per oggi alle " + booking.getTime())
                // dialog alert di default di androdi
                .setSmallIcon(android.R.drawable.ic_dialog_alert);

        // Invia la notifica
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        notificationManager.notify(booking.getID(), builder.build());
    }
}



