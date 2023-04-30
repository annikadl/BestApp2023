package com.example.bestapp2023.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

import com.example.bestapp2023.R;
import com.example.bestapp2023.models.FirebaseWrapper;
import com.example.bestapp2023.models.PermissionManager;


@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {

    private static final String TAG = SplashActivity.class.getCanonicalName();

    // Only positive int
    private static final int PERMISSION_REQUEST_CODE = (new Random()).nextInt() & Integer.MAX_VALUE;

    private void goToActivity(Class<?> activity) {
        Intent intent = new Intent(this, activity);
        this.startActivity(intent);
        this.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // TODO: check user
        // copiato dal bro. TODO: Enter Activity che gestisce il login e la registrazione


        // copiato anche questo dal bro.
        // TODO: richiedere permessi e fare permission manager
        // Check permissions -- Do not request at the login!

        PermissionManager pm = new PermissionManager(this);
        if (!pm.askNeededPermissions(PERMISSION_REQUEST_CODE, false)) {
            // Go to MainActivity
            this.goToActivity(MainActivity.class);
        }

        // Add the listener to the 'Grant Now' permission
        this.findViewById(R.id.IntroButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!pm.askNeededPermissions(PERMISSION_REQUEST_CODE, true)) {
                    // Go to MainActivity
                    SplashActivity.this.goToActivity(MainActivity.class);
                }
            }
        });
    }

    // TODO: onClick






}