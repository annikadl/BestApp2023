package com.example.bestapp2023.activities;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bestapp2023.R;
import com.example.bestapp2023.models.Reservation;

import org.w3c.dom.Text;

public class ReservationAdapter extends RecyclerView.Adapter<ReservationAdapter.ViewHolder> {

    private Reservation[] reservations;

    public static class  ViewHolder extends RecyclerView.ViewHolder{

        private final TextView placenameid;

        //private final TextView usernameid;

        private final TextView dateid;

        private final TextView peopleid;

        private final TextView timeid;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.placenameid = itemView.findViewById(R.id.placenameid);

            //this.usernameid = itemView.findViewById(R.id.usernameid);

            this.dateid = itemView.findViewById(R.id.dateid);

            this.peopleid = itemView.findViewById(R.id.peopleid);

            this.timeid = itemView.findViewById(R.id.timeid);

        }

        public TextView getPlacenameid() {
            return placenameid;
        }

        /*public TextView getUsernameid() {
            return usernameid;
        }*/

        public TextView getDateid() {
            return dateid;
        }

        public TextView getPeopleid() {
            return peopleid;
        }

        public TextView getTimeid() {
            return timeid;
        }
    }

    public  ReservationAdapter(Reservation[] reservations) {
        this.reservations = reservations;
    }


    //METODI CHIAMATI PER OGNI ELEMENTO DELLA LISTA
    @NonNull
    @Override
    public ReservationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.reservation_list_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReservationAdapter.ViewHolder holder, int position) {
        holder.getPlacenameid().setText(String.valueOf(this.reservations[position].getPlace_name()));
        //holder.getCityid().setText(String.valueOf(this.places[position].getCity()));
        holder.getDateid().setText(String.valueOf(this.reservations[position].getDate()));
        holder.getPeopleid().setText(String.valueOf(this.reservations[position].getPeople()));
        holder.getTimeid().setText(String.valueOf(this.reservations[position].getTime()));
    }


    @Override
    public int getItemCount() {
        return this.reservations.length;
    }
}
