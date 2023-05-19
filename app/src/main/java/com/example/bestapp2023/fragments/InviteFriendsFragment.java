package com.example.bestapp2023.fragments;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.bestapp2023.R;
import com.example.bestapp2023.models.FirebaseWrapper;

public class InviteFriendsFragment extends LogFragment {
    ImageButton home;
    ImageButton profile;
    ImageButton invitefriend;

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.initArguments();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View externalView = inflater.inflate(R.layout.fragment_invitefriends, container, false);

        return externalView;
    }



}

