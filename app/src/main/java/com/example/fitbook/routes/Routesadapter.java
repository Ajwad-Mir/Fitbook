package com.example.fitbook.routes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitbook.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import org.jetbrains.annotations.NotNull;

public class Routesadapter extends FirestoreRecyclerAdapter<Routes,Routesadapter.RoutesViewHolder> {
    public Routesadapter(@NonNull @NotNull FirestoreRecyclerOptions<Routes> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull @NotNull Routesadapter.RoutesViewHolder holder, int position, @NonNull @NotNull Routes model) {
        holder.RouteID.setText(model.getRouteid());
        holder.RouteName.setText(model.getRoutename());
        holder.RouteDistance.setText(model.getRoutedistancetravelled());
        holder.RouteTime.setText(model.getRoutelaspedtime());
        holder.RouteSteps.setText(model.getRoutestepstaken());
    }

    @NonNull
    @NotNull
    @Override
    public RoutesViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View V = LayoutInflater.from(parent.getContext()).inflate(R.layout.routes_listitem,parent,false);
        return new Routesadapter.RoutesViewHolder(V);
    }

    public class RoutesViewHolder extends RecyclerView.ViewHolder {
        public TextView RouteID,RouteName,RouteDistance,RouteSteps,RouteTime;
        public RoutesViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            RouteID = itemView.findViewById(R.id.RouteID_Output);
            RouteName = itemView.findViewById(R.id.RouteName_Output);
            RouteDistance = itemView.findViewById(R.id.RouteDistance_Output);
            RouteSteps = itemView.findViewById(R.id.RouteSteps_Output);
            RouteTime = itemView.findViewById(R.id.RouteTimeElapsed_Output);
        }
    }
}
