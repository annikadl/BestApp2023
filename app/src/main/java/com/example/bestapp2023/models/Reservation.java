package com.example.bestapp2023.models;

public class Reservation {

    String Place_name;
    String User_name;
    String Date;
    String People;
    String Time;
    int ID;

    public Reservation(String place_name, String user_name, String date, String people,String time) {
        this.Place_name = place_name;
        this.User_name = user_name;
        this.Date = date;
        this.People = people;
        this.Time = time;
        this.ID = 0;
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

}
