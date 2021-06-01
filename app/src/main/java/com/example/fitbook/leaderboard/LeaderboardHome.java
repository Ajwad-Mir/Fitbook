package com.example.fitbook.leaderboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.fitbook.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class LeaderboardHome extends AppCompatActivity {

    public RecyclerView LeaderboardTable;
    public LeaderboardAdapter leaderboardAdapter;
    public FirebaseFirestore mFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard_home);
        LeaderboardTable = findViewById(R.id.Leaderboard_body);
        mFirestore = FirebaseFirestore.getInstance();
        Setup();
    }

    private void Setup() {
        Query QB = mFirestore.collectionGroup("Leaderboard").orderBy("leaderboardTotalDistanceTravelled",Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<Leaderboard> options = new FirestoreRecyclerOptions.Builder<Leaderboard>()
                .setQuery(QB,Leaderboard.class).build();
        leaderboardAdapter = new LeaderboardAdapter(options);
        LeaderboardTable.setHasFixedSize(true);
        LeaderboardTable.setLayoutManager(new LinearLayoutManager(this));
        LeaderboardTable.setAdapter(leaderboardAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        leaderboardAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        leaderboardAdapter.stopListening();
    }
}