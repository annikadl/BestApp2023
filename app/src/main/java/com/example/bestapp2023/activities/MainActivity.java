package com.example.bestapp2023.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.app.FragmentManager;

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

import com.example.bestapp2023.R;
import com.example.bestapp2023.fragments.HomeFragment;
import com.example.bestapp2023.fragments.InviteFriendsFragment;
import com.example.bestapp2023.fragments.LoginFragment;
import com.example.bestapp2023.fragments.RestaurantFragment;
import com.example.bestapp2023.models.FirebaseWrapper;
import com.example.bestapp2023.models.MyPlaces;
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
        fragmentTransaction.replace(R.id.container_login, new HomeFragment());
        // fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();


        //Metodo per chiamare o l'activity del Profilo o la schermata di Login quando il pulsante "Profile" viene premuto
       ImageButton profile = findViewById(R.id.profile);

       // TODO: far diventare profilo un fragment
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //visto che non sono nella ProfileActivity, rendo il pulsante "Profile" cliccabile
                profile.setClickable(true);

                FirebaseWrapper.Auth auth = new FirebaseWrapper.Auth();
                if (auth.isAuthenticated()) {
                    MainActivity.this.goToActivity(ProfileActivity.class);
                } else {
                    MainActivity.this.goToActivity(EnterActivity.class);
                }
            }
        });

        // - Bottone home
        ImageButton home = findViewById(R.id.home);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

        // - bottone invita amici
        ImageButton invitefriend = findViewById(R.id.invitefriends);

        invitefriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment FragmentTest = fragmentManager.findFragmentByTag("InviteFriendsFragment");
                if(FragmentTest == null)
                {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setReorderingAllowed(true);
                fragmentTransaction.replace(R.id.container_login, new InviteFriendsFragment(),"InviteFriendsFragment");
                // fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }}
        });

    }

}


// TODO: potrebbe essere necessario spostare la logica di accesso sul main
// perché quando entro nella schermata di accesso