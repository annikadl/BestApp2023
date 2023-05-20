package com.example.bestapp2023.activities;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bestapp2023.R;
import com.example.bestapp2023.models.MyPlaces;

import org.w3c.dom.Text;

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.ViewHolder> {

    private MyPlaces[] places;

    public static class  ViewHolder extends RecyclerView.ViewHolder{

        private final TextView cityid;

        private final TextView nameid;

        private final TextView typeid;

        private final TextView addressid;

        private final TextView openinid;

        private final TextView closeid;




        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.cityid = itemView.findViewById(R.id.cityid);

            this.nameid = itemView.findViewById(R.id.nameid);

            this.typeid = itemView.findViewById(R.id.typeid);

            this.addressid = itemView.findViewById(R.id.addressid);

            this.openinid = itemView.findViewById(R.id.openinid);

            this.closeid = itemView.findViewById(R.id.closeid);

        }

        public TextView getAddressid() {
            return addressid;
        }

        public TextView getCityid() {
            return cityid;
        }

        public TextView getCloseid() {
            return closeid;
        }

        public TextView getNameid() {
            return nameid;
        }

        public TextView getOpeninid() {
            return openinid;
        }

        public TextView getTypeid() {
            return typeid;
        }
    }

    public  PlaceAdapter(MyPlaces[] places) {
        this.places = places;
    }


    //METODI CHIAMATI PER OGNI ELEMENTO DELLA LISTA
    @NonNull
    @Override
    public PlaceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.places_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaceAdapter.ViewHolder holder, int position) {

        holder.getAddressid().setText(String.valueOf(this.places[position].getAddress()));
        holder.getCityid().setText(String.valueOf(this.places[position].getCity()));
        holder.getNameid().setText(String.valueOf(this.places[position].getName()));
        holder.getTypeid().setText(String.valueOf(this.places[position].getType()));
        holder.getOpeninid().setText(String.valueOf(this.places[position].getOpen()));
        holder.getCloseid().setText(String.valueOf(this.places[position].getClose()));
    }

    @Override
    public int getItemCount() {
        return this.places.length;
    }
}
