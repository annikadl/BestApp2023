package com.example.bestapp2023;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

import com.example.bestapp2023.R;
import com.example.bestapp2023.models.FirebaseWrapper;


public class SplashActivity extends AppCompatActivity {

    private static final String TAG = SplashActivity.class.getCanonicalName();

    // Only positive int
    private static final int PERMISSION_REQUEST_CODE = (new Random()).nextInt() & Integer.MAX_VALUE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // TODO: check user
        // if not logged switch to login fragment
    }

}