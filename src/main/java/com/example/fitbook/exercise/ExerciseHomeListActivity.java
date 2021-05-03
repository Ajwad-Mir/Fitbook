package com.example.fitbook.exercise;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.fitbook.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class ExerciseHomeListActivity extends AppCompatActivity {

    private RecyclerView Exercise_RecyclerView;
    private ExerciseAdapter Exercise_Adapter;
    private Button AddExercise;
    private final FirebaseFirestore mFirestone = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_home_list);
        Exercise_RecyclerView = findViewById(R.id.List_RecyclerView);
        AddExercise = findViewById(R.id.CreateNewExerciseBtn);
        setup();
    }

    private void setup() {
        Query QB = mFirestone.collectionGroup("Exercise");
        FirestoreRecyclerOptions<Exercise> options = new FirestoreRecyclerOptions.Builder<Exercise>()
                .setQuery(QB,Exercise.class)
                .build();
        Exercise_Adapter = new ExerciseAdapter(options);
        Exercise_RecyclerView.setHasFixedSize(true);
        Exercise_RecyclerView.setLayoutManager(new LinearLayoutManager(ExerciseHomeListActivity.this));
        Exercise_RecyclerView.setAdapter(Exercise_Adapter);
        
        Exercise_Adapter.setOnItemClickListener(new ExerciseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot dss, int position) {
                Intent intent = new Intent(ExerciseHomeListActivity.this, ExerciseFullListActivity.class);
                intent.putExtra("UID",dss.getString("creatoruid"));
                intent.putExtra("ID", dss.getId());
                startActivity(intent);
            }
        });
        AddExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ExerciseHomeListActivity.this, ExerciseCreateNewListActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Exercise_Adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Exercise_Adapter.stopListening();
    }
}