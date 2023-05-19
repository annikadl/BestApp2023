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
import com.example.bestapp2023.fragments.InviteFriendsFragment;
import com.example.bestapp2023.fragments.LoginFragment;
import com.example.bestapp2023.fragments.RestaurantFragment;
import com.example.bestapp2023.models.FirebaseWrapper;
import com.example.bestapp2023.models.MyPlaces;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MainActivity extends AppCompatActivity{
    ImageButton home;
    ImageButton profile;
    ImageButton invitefriend;


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

        //Primo metodo per implementare il pulsante Accedi -> funzionante prima del primo layout della Home
        /*TextView link = findViewById(R.id.link_login);
        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(getApplicationContext(), LoginFragment.class));
                getSupportFragmentManager().beginTransaction().add(R.id.fragment_login_layout,new LoginFragment()).commit();


        });*/

        //Nuovo metodo per implementare il pulsante Accedi -> funziona, ma questo pulsante ora va tolto per mettere tutto nella sezione Profilo
        /*login = (Button)findViewById(R.id.link_login);
        login.setVisibility(View.VISIBLE);

        TextView login_text = findViewById(R.id.login_word);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Fragment fragment= new LoginFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.container_login, fragment);
                transaction.addToBackStack(null);
                transaction.commit();//

                MainActivity.this.goToActivity(EnterActivity.class);
            }
        });*/

        /*FirebaseWrapper.Auth auth = new FirebaseWrapper.Auth();
        if (auth.isAuthenticated()) {
            login.setVisibility(View.GONE);
            login_text.setVisibility(View.GONE);
        }*/
        //Fine parte pulsante Accedi

        //Metodo per chiamare o l'activity del Profilo o la schermata di Login quando il pulsante "Profile" viene premuto
        profile = (ImageButton) findViewById(R.id.profile);

        Spinner citta_menu = (Spinner) findViewById(R.id.citta_menu);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.citta, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        citta_menu.setAdapter(adapter);

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

        //Metodo per chiamare la Main Activity quando il pulsante "Home" viene premuto
        home = (ImageButton) findViewById(R.id.home);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //visto che sono già nella MainActivity, rendo il pulsante "Home" non cliccabile
                //home.setClickable(false);

                MainActivity.this.goToActivity(MainActivity.class);
            }
        });

        //Metodo per chiamare il fragment per invitare gli amici (ancora da fare)
        invitefriend = (ImageButton) findViewById(R.id.invitefriends);

        invitefriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //visto che non ho ancora creato il fragment per invitare gli amici, rendo il bottone non cliccabile
                // invitefriend.setClickable(false);

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setReorderingAllowed(true);
                fragmentTransaction.replace(R.id.container_login, new InviteFriendsFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });


        // PARTE FRAGMENT E FRAGMENT MANAGER
        // ricerca ristoranti
      Button cerca = findViewById(R.id.cerca);

        cerca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                // For optimizations -- See: https://developer.android.com/reference/androidx/fragment/app/FragmentTransaction#setReorderingAllowed(boolean)
                fragmentTransaction.setReorderingAllowed(true);
                fragmentTransaction.replace(R.id.container_login, new RestaurantFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        //////////////////////////////////////////////////////////////////////////////////

       // FirebaseWrapper.RTDatabase.getDb();

    }

}
