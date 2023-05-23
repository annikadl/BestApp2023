package com.example.bestapp2023.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;

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
import com.example.bestapp2023.fragments.RestaurantFragment;
import com.example.bestapp2023.fragments.SignupFragment;
import com.example.bestapp2023.models.FirebaseWrapper;
import com.example.bestapp2023.models.MyPlaces;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MainActivity extends AppCompatActivity{

    // FRAGMENT MANAGER
    androidx.fragment.app.FragmentManager fragmentManager = getSupportFragmentManager();


    //Funzione per andare in una nuova acrivity
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
                FirebaseWrapper.Auth auth = new FirebaseWrapper.Auth();

                boolean logged = false;

                // check autenticazione funziona
                // ho usato un booleano perchè la auth.isAuthenticated direttamente nell'if
                // della transizione non funzionava
                if(auth.isAuthenticated()){
                    logged = true;
                }

                Log.d("Auth", "isAuthenticated: " + logged);

                // transizione tra login/registrazione e profilo avviene correttamente
                if (FragmentTest == null && logged)
                  {
                      //ASSEGNO I VALORI ALLA LISTA DA PASSARE COME BUNDLE

                      //CREO IL BUNDLE DA PASSARE AL FRAGMENT
                      Bundle UserNameEmailBundle = new Bundle();

                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.setReorderingAllowed(true);
                    // INIZIALIZZO IL FRAGMENT
                    Fragment ProfileFragment = new ProfileFragment();
                    // PASSO COME ARGOMENTI DEL FRAGMENT IL BUCKET
                    ProfileFragment.setArguments(UserNameEmailBundle);
                    // FACCIO REMOVE ED ADD NELLO STACK DEL PROIFLE FRAGMENT CON TAG ASSOCIATO
                    fragmentTransaction.replace(R.id.container_login, ProfileFragment,"ProfileFragment");
                    fragmentTransaction.commit();
                 } else if (FragmentTest == null){
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
               if (FragmentTest == null)
               {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setReorderingAllowed(true);
                fragmentTransaction.replace(R.id.container_login, new HomeFragment(),"HomeFragment");
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

                if(FragmentTest == null)
                {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setReorderingAllowed(true);
                fragmentTransaction.replace(R.id.container_login, new InviteFriendsFragment(),"InviteFriendsFragment");
                // fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                }
            }
        });

        // - Bottone back
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // bottone back non visibile sulla home
                back.setVisibility(View.GONE);

                Fragment FragmentTest = fragmentManager.findFragmentByTag("HomeFragment");

                if (FragmentTest == null)
                {
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.setReorderingAllowed(true);
                    fragmentTransaction.replace(R.id.container_login, new HomeFragment(),"HomeFragment");
                    //fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();

                }
            }
        });

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
