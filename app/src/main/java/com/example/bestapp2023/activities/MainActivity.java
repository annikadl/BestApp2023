package com.example.bestapp2023.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.bestapp2023.R;
import com.example.bestapp2023.fragments.LoginFragment;

public class MainActivity extends AppCompatActivity{
    Button login;

    private void goToActivity(Class<?> activity) {
        Intent intent = new Intent(this, activity);
        this.startActivity(intent);
        this.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*TextView link = findViewById(R.id.link_login);
        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(getApplicationContext(), LoginFragment.class));
                getSupportFragmentManager().beginTransaction().add(R.id.fragment_login_layout,new LoginFragment()).commit();


        });*/

        login = (Button)findViewById(R.id.link_login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Fragment fragment= new LoginFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.container_login, fragment);
                transaction.addToBackStack(null);
                transaction.commit();*/

                MainActivity.this.goToActivity(EnterActivity.class);
            }
        });
    }
}
