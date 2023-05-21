package com.example.bestapp2023.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.bestapp2023.R;
import com.example.bestapp2023.models.FirebaseWrapper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends Activity {
    ImageButton home;
    ImageButton profile;
    ImageButton invitefriend;
    Button signout;

    // TODO: togliere Profile Activity

    FirebaseWrapper.Auth auth = new FirebaseWrapper.Auth();

    private void goToActivity(Class<?> activity) {
        Intent intent = new Intent(this, activity);
        this.startActivity(intent);
        this.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_profile);


        profile = (ImageButton)findViewById(R.id.profile);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*FirebaseWrapper.Auth auth = new FirebaseWrapper.Auth();
                if (auth.isAuthenticated()) {
                    ProfileActivity.this.goToActivity(ProfileActivity.class);
                }
                else {
                    ProfileActivity.this.goToActivity(EnterActivity.class);
                }*/
                profile.setClickable(false);
            }
        });

        //Metodo per chiamare la Main Activity quando il pulsante "Home" viene premuto
        home = (ImageButton)findViewById(R.id.home);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                home.setClickable(true);
                ProfileActivity.this.goToActivity(MainActivity.class);
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

        //Prendi la stringa per il nome
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String userFullName = user.getDisplayName();
            TextView fullname_text = findViewById(R.id.fullname_text);
            fullname_text.setText(userFullName);
        } else {
            // No user is signed in
        }

        //Prendi la stringa per l'user
        //FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String userEmail = user.getEmail();
            TextView username_text = findViewById(R.id.username_text);
            username_text.setText(userEmail);
        } else {
            // No user is signed in
        }

        //Gestione del signout dell'utente
        signout = (Button)findViewById(R.id.signout);

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                ProfileActivity.this.goToActivity(MainActivity.class);
            }
        });

    }
}
