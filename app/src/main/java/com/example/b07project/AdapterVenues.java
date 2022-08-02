package com.example.b07project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
public class AdapterVenues extends RecyclerView.Adapter<AdapterVenues.MyViewHolder>{

    private final RecycleViewInterface recycleViewInterface;
    Context context;
    ArrayList<Venue> venues;

    public AdapterVenues(Context context, ArrayList<Venue> venues, RecycleViewInterface recycleViewInterface)
    {
        this.recycleViewInterface = recycleViewInterface;
        this.context = context;
        this.venues = venues;
    }

    @NonNull
    @Override
    public AdapterVenues.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.venuess, parent, false);
        return new AdapterVenues.MyViewHolder(view, recycleViewInterface).linkAdapter(this);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterVenues.MyViewHolder holder, int position) {

        Venue venue = venues.get(position);
        holder.location.setText(venue.getLocation());
        holder.timeStart.setText(Integer.toString(venue.getStartHour()));
        holder.timeEnd.setText(Integer.toString(venue.getEndHour()));
        holder.venueName.setText(venue.getVenueName());
    }

    @Override
    public int getItemCount() {
        return venues.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView location, timeStart, timeEnd, venueName;
        private AdapterVenues adapter;

        public MyViewHolder(@NonNull View itemView, RecycleViewInterface recycleViewInterface){
            super(itemView);
            location  = itemView.findViewById(R.id.locationf);
            venueName = itemView.findViewById(R.id.venuename);
            timeStart = itemView.findViewById((R.id.starttim));
            timeEnd = itemView.findViewById(R.id.endtim);

            itemView.setOnClickListener((new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(recycleViewInterface != null)
                    {
                        int pos = getAbsoluteAdapterPosition();

                        if(pos != RecyclerView.NO_POSITION)
                        {
                            recycleViewInterface.onItemClick(pos);
                        }
                    }
                }
            }));
        }

        public MyViewHolder linkAdapter(AdapterVenues adapter)
        {
            this.adapter = adapter;
            return this;
        }
    }
}