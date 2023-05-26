package com.example.bestapp2023.models;

public class Reservation {

    String Place_name;
    String User_name;
    String Date;

    String People;

    public Reservation(String place_name, String user_name, String date, String people) {
        this.Place_name = place_name;
        this.User_name = user_name;
        this.Date = date;
        this.People = people;
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

}
