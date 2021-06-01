package com.example.fitbook.leaderboard;

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

public class LeaderboardAdapter extends FirestoreRecyclerAdapter<Leaderboard, LeaderboardAdapter.LeaderboardViewHolder> {

    public LeaderboardAdapter(@NonNull @NotNull FirestoreRecyclerOptions<Leaderboard> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull @NotNull LeaderboardAdapter.LeaderboardViewHolder leaderboardViewHolder, int i, @NonNull @NotNull Leaderboard leaderboard) {
        leaderboardViewHolder.leaderboardusername.setText(leaderboard.getleaderboardUserName());
        leaderboardViewHolder.leaderboardtotaldistance.setText(String.valueOf(leaderboard.getleaderboardTotalDistanceTravelled()));
    }

    @NonNull
    @Override
    public LeaderboardAdapter.LeaderboardViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.leaderboard_itemlist,parent,false);
        return new LeaderboardViewHolder(v);
    }

    public static class LeaderboardViewHolder extends RecyclerView.ViewHolder {
        public TextView leaderboardusername,leaderboardtotaldistance;
        public LeaderboardViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            leaderboardusername = itemView.findViewById(R.id.UserName_Output);
            leaderboardtotaldistance = itemView.findViewById(R.id.TotalDistance_Output);
        }
    }
}
