package com.example.fitbook.workout;

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

import com.example.fitbook.Homepage;
import com.example.fitbook.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import org.jetbrains.annotations.NotNull;

public class WorkoutHomeListActivity extends AppCompatActivity {

    private RecyclerView Workout_RecyclerView;
    private WorkoutAdapter Workout_Adapter;
    private Button AddWorkout;
    private final FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_home_list);

        Workout_RecyclerView = findViewById(R.id.WorkoutView_RecyclerView);
        AddWorkout = findViewById(R.id.AddWorkoutBtn);
        WorkoutSetup();
    }

    private void WorkoutSetup() {
        Query QB = mFirestore.collectionGroup("Workout");
        FirestoreRecyclerOptions<Workout> options = new FirestoreRecyclerOptions.Builder<Workout>()
                .setQuery(QB,Workout.class).build();

        Workout_Adapter = new WorkoutAdapter(options);
        Workout_RecyclerView.setHasFixedSize(true);
        Workout_RecyclerView.setLayoutManager(new LinearLayoutManager(this));
        Workout_RecyclerView.setAdapter(Workout_Adapter);
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT){
            @Override
            public boolean onMove(@NonNull @NotNull RecyclerView recyclerView, @NonNull @NotNull RecyclerView.ViewHolder viewHolder, @NonNull @NotNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull @NotNull RecyclerView.ViewHolder viewHolder, int direction) {
                Workout_Adapter.delete(viewHolder.getAdapterPosition());
            }
        }).attachToRecyclerView(Workout_RecyclerView);

        Workout_Adapter.setOnItemClickListener(new WorkoutAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot dss, int position) {
                Intent intent = new Intent(WorkoutHomeListActivity.this, WorkoutFullListActivity.class);
                intent.putExtra("UID",dss.getString("creatoruid"));
                intent.putExtra("ID", dss.getId());
                startActivity(intent);
            }
        });
        AddWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WorkoutHomeListActivity.this, WorkoutCreateNewExerciseListActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(WorkoutHomeListActivity.this, Homepage.class);
        startActivity(intent);
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