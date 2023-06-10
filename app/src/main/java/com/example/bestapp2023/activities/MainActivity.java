package com.example.bestapp2023.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.datastore.preferences.core.MutablePreferences;
import androidx.datastore.preferences.core.Preferences;
import androidx.datastore.preferences.core.PreferencesKeys;
import androidx.datastore.preferences.rxjava3.RxPreferenceDataStoreBuilder;
import androidx.datastore.rxjava3.RxDataStore;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Intent;


import android.content.pm.PackageManager;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.example.bestapp2023.R;
import com.example.bestapp2023.fragments.HomeFragment;
import com.example.bestapp2023.fragments.InviteFriendsFragment;
import com.example.bestapp2023.fragments.LogFragment;
import com.example.bestapp2023.fragments.LoginFragment;
import com.example.bestapp2023.fragments.ProfileFragment;
import com.example.bestapp2023.fragments.ReservationFragment;
import com.example.bestapp2023.fragments.RestaurantFragment;
import com.example.bestapp2023.fragments.SignupFragment;
import com.example.bestapp2023.models.FirebaseWrapper;
import com.example.bestapp2023.models.MyPlaces;
import com.example.bestapp2023.models.MyWorker;
import com.example.bestapp2023.models.Reservation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.example.bestapp2023.fragments.ReservationFragment;

import java.util.List;
import java.util.concurrent.TimeUnit;


import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.Flowable;


public class MainActivity extends AppCompatActivity{

    // FRAGMENT MANAGER
    androidx.fragment.app.FragmentManager fragmentManager = getSupportFragmentManager();

    //tag da usare per dare il riferimento della MainActivity al worker
    private static final String TAG = MainActivity.class.getCanonicalName();

    //Funzione per andare in una nuova activity
    private void goToActivity(Class<?> activity) {
        Intent intent = new Intent(this, activity);
        this.startActivity(intent);
        this.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // - parto con home fragment
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setReorderingAllowed(true);
        fragmentTransaction.add(R.id.container_login, new HomeFragment());
        // fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

        //////////////////////////// DICHIARAZIONE BOTTONI ////////////////////////////////////
        ImageButton profile = findViewById(R.id.profile);
        ImageButton home = findViewById(R.id.home);
        ImageButton invitefriend = findViewById(R.id.invitefriends);
        ImageButton back = findViewById(R.id.back);


        // inizialmente bottone back non visibile
        back.setVisibility(View.GONE);

        ////////////////////////////////// LOGICA BOTTONI /////////////////////////////////////

        // - Bottone profile
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // bottone back visibile
                back.setVisibility(View.VISIBLE);

                Fragment FragmentTest = fragmentManager.findFragmentByTag("ProfileFragment");
                Fragment LoginFragment = fragmentManager.findFragmentByTag("LoginFragment");
                FirebaseWrapper.Auth auth = new FirebaseWrapper.Auth();

                boolean logged = false;

                // check autenticazione funziona
                // ho usato un booleano perchè la auth.isAuthenticated direttamente nell'if
                // della transizione non funzionava
                if (auth.isAuthenticated()) {
                    logged = true;
                }

                Log.d("Auth", "isAuthenticated: " + logged);

                // transizione tra login/registrazione e profilo avviene correttamente
                if (FragmentTest == null && logged) {
                    //ASSEGNO I VALORI ALLA LISTA DA PASSARE COME BUNDLE

                    //CREO IL BUNDLE DA PASSARE AL FRAGMENT
                    Bundle UserNameEmailBundle = new Bundle();

                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.setReorderingAllowed(true);
                    // INIZIALIZZO IL FRAGMENT
                    Fragment ProfileFragment = new ProfileFragment();
                    // PASSO COME ARGOMENTI DEL FRAGMENT IL BUCKET
                    ProfileFragment.setArguments(UserNameEmailBundle);
                    // FACCIO REMOVE ED ADD NELLO STACK DEL PROFILE FRAGMENT CON TAG ASSOCIATO
                    fragmentTransaction.replace(R.id.container_login, ProfileFragment, "ProfileFragment");
                    fragmentTransaction.commit();
                } else if (FragmentTest == null) {
                    renderFragment(true);
                }
            }
        });


        // - Bottone home
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // bottone back non visibile
                back.setVisibility(View.GONE);

                Fragment FragmentTest = fragmentManager.findFragmentByTag("HomeFragment");
                if (FragmentTest == null) {
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.setReorderingAllowed(true);
                    fragmentTransaction.replace(R.id.container_login, new HomeFragment(), "HomeFragment");
                    //fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
            }
        });


        // - Bottone invita amici

        invitefriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // bottone back visibile
                back.setVisibility(View.VISIBLE);

                Fragment FragmentTest = fragmentManager.findFragmentByTag("InviteFriendsFragment");

                if (FragmentTest == null) {
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.setReorderingAllowed(true);
                    fragmentTransaction.replace(R.id.container_login, new InviteFriendsFragment(), "InviteFriendsFragment");
                    // fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
            }
        });

        // - Bottone back
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentManager fm = getSupportFragmentManager();
                Fragment currentFragment = fm.findFragmentById(R.id.container_login);

                if (currentFragment instanceof ReservationFragment) {
                    Fragment FragmentTest = fragmentManager.findFragmentByTag("ProfileFragment");

                    if (FragmentTest == null) {
                        back.setVisibility(View.VISIBLE);

                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.setReorderingAllowed(true);

                        Bundle UserNameEmailBundle = new Bundle();

                        // INIZIALIZZO IL FRAGMENT
                        Fragment ProfileFragment = new ProfileFragment();
                        // PASSO COME ARGOMENTI DEL FRAGMENT IL BUCKET
                        ProfileFragment.setArguments(UserNameEmailBundle);
                        // FACCIO REMOVE ED ADD NELLO STACK DEL PROFILE FRAGMENT CON TAG ASSOCIATO
                        fragmentTransaction.replace(R.id.container_login, ProfileFragment, "ProfileFragment");
                        fragmentTransaction.commit();
                    }
                } else {
                    // bottone back non visibile sulla home
                    back.setVisibility(View.GONE);

                    Fragment FragmentTest = fragmentManager.findFragmentByTag("HomeFragment");

                    if (FragmentTest == null) {
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.setReorderingAllowed(true);
                        fragmentTransaction.replace(R.id.container_login, new HomeFragment(), "HomeFragment");
                        //fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();

                    }
                }
            }
        });


        /////////////////////////////////// SERVIZIO PERIODICO  ////////////////////////////////////////
        //CREO WORKER

        PeriodicWorkRequest periodicWorkRequest = new PeriodicWorkRequest.Builder(MyWorker.class,
                24, TimeUnit.HOURS,
                23, TimeUnit.HOURS).addTag(TAG).setInitialDelay(0, TimeUnit.HOURS).build();
        //LANCIO IL WORKER, keep è la policy per cui mantiene il worker preesistente se lo si ha già
        WorkManager.getInstance(MainActivity.this).enqueueUniquePeriodicWork(TAG, ExistingPeriodicWorkPolicy.KEEP, periodicWorkRequest);
    }

    /////////////////////////////////// LOGICA LOGIN E SIGN UP ////////////////////////////////////////
    // Quello che avevo nella Enter Activity
    public void renderFragment(boolean isLogin) {
        Fragment fragment = null;
        if (isLogin) {
            fragment = LogFragment.newInstance(LoginFragment.class, "signinCallback", boolean.class);
        } else {
            fragment = LogFragment.newInstance(SignupFragment.class, "signinCallback", boolean.class);
        }
        if (this.fragmentManager == null) {
            this.fragmentManager = this.getSupportFragmentManager();
        }

        FragmentTransaction fragmentTransaction = this.fragmentManager.beginTransaction();
        fragmentTransaction.setReorderingAllowed(true);
        fragmentTransaction.replace(R.id.container_login, fragment);
        fragmentTransaction.commit();
    }

    public void signinCallback(boolean result) {
        if (!result) {
            // TODO: Better handling of the error message --> Put in a textview of the activity/fragment
            Toast
                    .makeText(this, "Username or password are not valid", Toast.LENGTH_LONG)
                    .show();
        } else {
            // Go To Splash to check the permissions
            Intent intent = new Intent(this, SplashActivity.class);
            this.startActivity(intent);
            this.finish();
        }
    }

}
