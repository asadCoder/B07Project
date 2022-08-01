package com.example.b07project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Myadapter extends RecyclerView.Adapter<Myadapter.MyViewHolder> {
    Context context;
    ArrayList<Venue> venlist;

    public Myadapter(Context context, ArrayList<Venue> venlist) {
        this.context = context;
        this.venlist = venlist;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.venuess,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Venue ven = venlist.get(position);
        holder.venuname.setText(ven.getVenueName());
        holder.location.setText(ven.getLocation());
        holder.date.setText(ven.getDate());
        holder.starttime.setText(ven.starttime());
        holder.endtime.setText(ven.endtime());

    }

    @Override
    public int getItemCount() {
        return venlist.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView venuname, location, date, endtime, starttime;

        public MyViewHolder (@NonNull View itemView){
            super(itemView);
            venuname = itemView.findViewById(R.id.venuename);
            location = itemView.findViewById(R.id.locationf);
            date = itemView.findViewById(R.id.venuedate);
            endtime = itemView.findViewById(R.id.endtim);
            starttime = itemView.findViewById(R.id.starttim);

        }
    }
}
