package com.example.fitbook.exercise;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.fitbook.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class ExerciseFullListActivity extends AppCompatActivity {

    private static final String TAG = "ExerciseFullListActivity";
    public RecyclerView Workout_RecyclerView;
    public WorkoutAdapter Workout_Adapter;
    public Button StartBtn;
    public FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();

    @SuppressLint("LongLogTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_full_list);
        Workout_RecyclerView = findViewById(R.id.Workout_RecyclerView);
        StartBtn = findViewById(R.id.StartWorkoutBtn);

        String UID = getIntent().getExtras().getString("UID");
        String Key = getIntent().getExtras().getString("ID");
        Log.v(TAG,UID + "\t" + Key);
        Setup(UID,Key);
    }

    private void Setup(String uid, String key) {
        Query QB = mFirestore.collection("users").document(uid)
                .collection("Exercise").document(key)
                .collection("Workout").orderBy("workoutid",Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<Workout> options = new FirestoreRecyclerOptions.Builder<Workout>()
                .setQuery(QB,Workout.class)
                .build();

        Workout_Adapter = new WorkoutAdapter(options);
        Workout_RecyclerView.setHasFixedSize(true);
        Workout_RecyclerView.setLayoutManager(new LinearLayoutManager(this));
        Workout_RecyclerView.setAdapter(Workout_Adapter);

        StartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ExerciseFullListActivity.this,ExerciseStartActivity.class);
                intent.putExtra("UID",uid);
                intent.putExtra("ID",key);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Workout_Adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Workout_Adapter.stopListening();
    }
}