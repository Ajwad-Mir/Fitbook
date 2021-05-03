package com.example.fitbook.exercise;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import com.example.fitbook.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;
import java.util.Random;

public class ExerciseAddWorkoutActivity extends AppCompatActivity {

    private EditText WorkoutName, WorkoutReps;
    private FirebaseFirestore mFirestore;

    public int index = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_add_workout);

        WorkoutName = findViewById(R.id.WorkoutNameInput);
        WorkoutReps = findViewById(R.id.WorkoutRepsInput);
        Button addWorkout = findViewById(R.id.CreateAnotherWorkoutBtn);
        Button saveList = findViewById(R.id.SaveWorkoutListBtn);
        mFirestore = FirebaseFirestore.getInstance();

        addWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //Exercise Details
                String ExID = getIntent().getExtras().getString("EXID");
                String Name = getIntent().getExtras().getString("EXNAME");
                String dateFormat = getIntent().getExtras().getString("EXDATE");
                String CreatorName = getIntent().getExtras().getString("EXCREATORNAME");
                String CreatorUID = getIntent().getExtras().getString("EXCREATORUID");

                //Workout Details
                String Workout_Name = WorkoutName.getText().toString();
                int Workout_Reps = Integer.parseInt(WorkoutReps.getText().toString());

                Exercise EXL =new Exercise(ExID,Name,dateFormat,CreatorName,CreatorUID);
                mFirestore.collection("users").document(CreatorUID)
                    .collection("Exercise").document(ExID).set(EXL).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                    }
                });
                Workout WKL = new Workout(FirebaseAuth.getInstance().getCurrentUser().getUid(),Workout_Name,Workout_Reps);
                mFirestore.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .collection("Exercise").document(ExID)
                .collection("Workout").add(WKL)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(ExerciseAddWorkoutActivity.this,"Workout Added Successfully",Toast.LENGTH_LONG).show();
                    }
                });
                WorkoutName.setText("");
                WorkoutReps.setText("");
            }
        });
        saveList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ExerciseAddWorkoutActivity.this, "Exercise List Saved Successfully", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(ExerciseAddWorkoutActivity.this, ExerciseHomeListActivity.class);
                startActivity(intent);
            }
        });
    }
}