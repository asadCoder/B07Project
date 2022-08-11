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
    public venclickListener vlistenr;

    public Myadapter(Context context, ArrayList<Venue> venlist, venclickListener vlistener) {
        this.context = context;
        this.venlist = venlist;
        this.vlistenr = vlistener;
    }
    public interface venclickListener{
        void selectedvenue(Venue v);
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
        holder.starttime.setText(ven.starttime());
        holder.endtime.setText(ven.endtime());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vlistenr.selectedvenue(ven);
            }
        });

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
            endtime = itemView.findViewById(R.id.endtim);
            starttime = itemView.findViewById(R.id.starttim);

        }
    }
}
