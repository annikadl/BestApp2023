package com.example.bestapp2023.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

import com.example.bestapp2023.R;
import com.example.bestapp2023.models.FirebaseWrapper;
import com.example.bestapp2023.models.PermissionManager;
import com.google.android.gms.auth.api.Auth;


@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {

    // la richiesta dei permessi funziona, ma se li rifiuto una volta non
    // me li richiede

    // TODO: far sì che i permessi vengano richiesti se non concessi.

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

        //Forse non da fare
        // TODO: check user
        // TODO: Enter Activity che gestisce il login e la registrazione

        // Firebase auth: https://firebase.google.com/docs/auth/android/start?hl=en#java
        /*FirebaseWrapper.Auth auth = new FirebaseWrapper.Auth();
        if (!auth.isAuthenticated()) {
            // Go to MainActivity
            //this.goToActivity(EnterActivity.class);
            this.goToActivity(MainActivity.class);
        }*/


        // Check permissions -- Do not request at the login!

        // copiato anche questo dal bro
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode != PERMISSION_REQUEST_CODE) {
            return;
        }

        for (int res : grantResults) {
            if (res == PackageManager.PERMISSION_DENIED) {
                Log.w(TAG, "A needed permission is not granted!");
                return;
            }
        }

        // All permissions are granted
        Log.d(TAG, "All the needed permissions are granted!");
        this.goToActivity(MainActivity.class);
    }






}