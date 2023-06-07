package com.example.bestapp2023.models;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.List;

public class Reservation {

    String Place_name;
    String User_name;
    String Date;
    String People;
    String Time;
    int ID;

    public Reservation(String place_name, String user_name, String date, String people,String time, int id) {
        this.Place_name = place_name;
        this.User_name = user_name;
        this.Date = date;
        this.People = people;
        this.Time = time;
        this.ID = id;
    }

    public Reservation () {

    }

    public String getPlace_name() {
        return Place_name;
    }

    public void setPlace_name(String place_name) {
        this.Place_name = place_name;
    }

    public String getUser_name() {
        return User_name;
    }

    public void setUser_name(String user_name) {
        this.User_name = user_name;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        this.Date = date;
    }

    public String getPeople() { return People; }

    public void setPeople(String people) { this.Date = people; }
    public String getTime() { return Time; }
    public void setTime(String time) {this.Time = time;}

    public int getID() {return ID; }
    public void setID(int id) { this.ID = id;}

    public static class Collection<T extends Reservation> {
        private static final String TAG = Collection.class.getCanonicalName();
        public final List<T> reservations;

        public Collection(List<T> reservations) {
            this.reservations = reservations;
        }

        public void firebaseDbCallback(Task<DataSnapshot> result) {
            // NOTE: This is a callback -- I do not have any guarantee when it is invoked!
            // --> Do not wait for these results on the main activity
            assert result != null;

            if (result.isSuccessful()) {
                List<Reservation> ReservationArray = new ArrayList<Reservation>();

                // Iterate over the db objects
                for (DataSnapshot child : result.getResult().getChildren()) {
                    Reservation reservation = child.getValue(Reservation.class);
                    ReservationArray.add(reservation);
                }

            }
        }
    }

}
