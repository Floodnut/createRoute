package com.example.createroute;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;


public class StartPointAdapter extends RecyclerView.Adapter<StartPointAdapter.ViewHolder> {
    String busNumber[], stationName[];
    Context context;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.start_pointview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bn.setText(busNumber[position]);
        holder.sn.setText(stationName[position]);

        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, MainActivity.class);
                i.putExtra("bus_num",busNumber[holder.getAdapterPosition()]);
                i.putExtra("station_name",stationName[holder.getAdapterPosition()]);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return busNumber.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView bn, sn;
        ConstraintLayout mainLayout;
        public ViewHolder(@NonNull View itemView){
            super(itemView);
            bn = itemView.findViewById(R.id.busNumber);
            sn = itemView.findViewById(R.id.stationName);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }

    public StartPointAdapter(Context cxt, String bNum[], String sName[]){
        context = cxt;
        busNumber = bNum;
        stationName = sName;
    }

}
