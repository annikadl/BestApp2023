package com.example.bestapp2023.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.bestapp2023.R;
import com.example.bestapp2023.fragments.LoginFragment;
import com.example.bestapp2023.models.FirebaseWrapper;

public class MainActivity extends AppCompatActivity{
    ImageButton home;
    ImageButton profile;
    ImageButton invitefriend;

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
        profile = (ImageButton)findViewById(R.id.profile);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //visto che non sono nella ProfileActivity, rendo il pulsante "Profile" cliccabile
                profile.setClickable(true);

                FirebaseWrapper.Auth auth = new FirebaseWrapper.Auth();
                if (auth.isAuthenticated()) {
                    MainActivity.this.goToActivity(ProfileActivity.class);
                }
                else {
                    MainActivity.this.goToActivity(EnterActivity.class);
                }
            }
        });

        //Metodo per chiamare la Main Activity quando il pulsante "Home" viene premuto
        home = (ImageButton)findViewById(R.id.home);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //visto che sono già nella MainActivity, rendo il pulsante "Home" non cliccabile
                home.setClickable(false);
            }
        });

        //Metodo per chiamare il fragment per invitare gli amici (ancora da fare)
        invitefriend = (ImageButton)findViewById(R.id.invitefriends);

        invitefriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //visto che non ho ancora creato il fragment per invitare gli amici, rendo il bottone non cliccabile
                invitefriend.setClickable(false);
            }
        });


    }
}
