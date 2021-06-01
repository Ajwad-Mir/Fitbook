package com.example.fitbook.workout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.fitbook.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class WorkoutFullListActivity extends AppCompatActivity {

    public RecyclerView ExerciseList_RecyclerView;
    public ExerciseAdapter ExerciseAdapter;
    public Button StartBtn;
    public FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_full_list);
        ExerciseList_RecyclerView = findViewById(R.id.ExerciseList_RecyclerView);
        StartBtn = findViewById(R.id.StartWorkoutBtn);

        String UID = getIntent().getExtras().getString("UID");
        String Key = getIntent().getExtras().getString("ID");
        RecyclerViewSetup(UID,Key);
    }

    private void RecyclerViewSetup(String uid, String key) {

        Query QB = mFirestore.collection("users").document(uid)
                .collection("Workout").document(key)
                .collection("Exercise").orderBy("exerciseuid", Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<Exercise> options = new FirestoreRecyclerOptions.Builder<Exercise>()
                .setQuery(QB,Exercise.class)
                .build();
        ExerciseAdapter = new ExerciseAdapter(options);
        ExerciseList_RecyclerView.setHasFixedSize(true);
        ExerciseList_RecyclerView.setLayoutManager(new LinearLayoutManager(this));
        ExerciseList_RecyclerView.setAdapter(ExerciseAdapter);

        StartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WorkoutFullListActivity.this,WorkoutStartActivity.class);
                intent.putExtra("UID",uid);
                intent.putExtra("ID",key);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        ExerciseAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        ExerciseAdapter.stopListening();
    }
}