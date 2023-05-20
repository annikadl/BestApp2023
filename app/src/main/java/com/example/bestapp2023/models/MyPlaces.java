package com.example.bestapp2023.models;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class MyPlaces {

    //Campi del JSON

    private String City;
    private String Name;
    private String Type;
    private String Address;

    private int Open;

    private int Close;

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



    public int getClose() {
        return Close;
    }

    public String getCity() {
        return City;
    }

    public int getOpen() {
        return Open;
    }

    public String getAddress() {
        return Address;
    }

    public String getName() {
        return Name;
    }

    public String getType() {
        return Type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyPlaces myPlaces = (MyPlaces) o;
        return Open == myPlaces.Open && Close == myPlaces.Close && City.equals(myPlaces.City) && Name.equals(myPlaces.Name) && Type.equals(myPlaces.Type) && Address.equals(myPlaces.Address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(City, Name, Type, Address, Open, Close);
    }

    public static class Collection<T extends MyPlaces> {
        private static final String TAG = Collection.class.getCanonicalName();
        public final List<T> places;

        public Collection(List<T> places) {
            this.places = places;
        }

        public void firebaseDbCallback(Task<DataSnapshot> result) {
            // NOTE: This is a callback -- I do not have any guarantee when it is invoked!
            // --> Do not wait for these results on the main activity
            assert result != null;

            if (result.isSuccessful()) {
                List<MyPlaces> MyPlacesArray = new ArrayList<MyPlaces>();

                // Iterate over the db objects
                for (DataSnapshot child : result.getResult().getChildren()) {
                    MyPlaces Place = child.getValue(MyPlaces.class);
                    MyPlacesArray.add(Place);
                }

            }
        }
    }
}
