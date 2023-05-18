package com.example.bestapp2023.models;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.List;


public class MyPlaces {

    //Campi del JSON

    private String City;
    private String Name;
    private String Type;
    private String Address;

    private int Open;

    private int Close;
    private boolean IsOpen;

    //Costruttore della classe

    public MyPlaces(){

    }

    //Costruttore con parametri

    public MyPlaces(String city,String name,String type,String address,int close,int open)
    {
        this.City = city;
        this.Name = name;
        this.Type = type;
        this.Address = address;
        this.Close = close;
        this.Open = open;
    }

    public boolean IsOpen()
    {
        return this.IsOpen;
    }


    public void firebaseDbCallback(Task<DataSnapshot> result) {
        // NOTE: This is a callback -- I do not have any guarantee when it is invoked!
        // --> Do not wait for these results on the main activity
        assert result != null;

        if (result.isSuccessful()) {
          List<MyPlaces>  MyPlacesArray = new ArrayList<MyPlaces>();

            // Iterate over the db objects
            for (DataSnapshot child : result.getResult().getChildren()) {
                MyPlaces Place = child.getValue(MyPlaces.class);
                MyPlacesArray.add(Place);
            }

        }
    }


}
